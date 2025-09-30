<template>
    <div>
    <el-row :gutter="20">
        <el-col :span="24">
            <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
            <el-form-item label="工单编码" prop="workorderCode">
                <el-input
                v-model="queryParams.workorderCode"
                placeholder="请输入工单编码"
                clearable
                @keyup.enter.native="handleQuery"
                />
            </el-form-item>
            <el-form-item label="工单名称" prop="workorderName">
                <el-input
                v-model="queryParams.workorderName"
                placeholder="请输入工单名称"
                clearable
                @keyup.enter.native="handleQuery"
                />
            </el-form-item>
            <el-form-item label="来源单据" prop="sourceCode">
                <el-input
                v-model="queryParams.sourceCode"
                placeholder="请输入来源单据"
                clearable
                @keyup.enter.native="handleQuery"
                />
            </el-form-item>

            <el-form-item label="产品编号" prop="productCode">
                <el-input
                v-model="queryParams.productCode"
                placeholder="请输入产品编号"
                clearable
                @keyup.enter.native="handleQuery"
                />
            </el-form-item>
            <el-form-item label="产品名称" prop="productName">
                <el-input
                v-model="queryParams.productName"
                placeholder="请输入产品名称"
                clearable
                @keyup.enter.native="handleQuery"
                />
            </el-form-item>

            <el-form-item label="客户编码" prop="clientCode">
                <el-input
                v-model="queryParams.clientCode"
                placeholder="请输入客户编码"
                clearable
                @keyup.enter.native="handleQuery"
                />
            </el-form-item>
            <el-form-item label="客户名称" prop="clientName">
                <el-input
                v-model="queryParams.clientName"
                placeholder="请输入客户名称"
                clearable
                @keyup.enter.native="handleQuery"
                />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>        
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-refresh" v-hasPermi="['mes:pro:protask:list']" circle="" @click="getList"></el-button>
            </el-form-item>
            </el-form>  
        </el-col>
    </el-row>
    <el-row :gutter="20">
        <!--工单信息-->
        <el-col class="borded-el" :xs="24">       
            <el-table
            v-loading="loading"
            :data="workorderList"
            row-key="workorderId"
            @row-click ="onWorkOrderSelected"
            default-expand-all
            :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
            >
            <el-table-column label="工单编码" width="180" prop="workorderCode" >
                <template slot-scope="scope">
                <el-button
                    size="mini"
                    type="text"
                    @click="handleView(scope.row)"
                    v-hasPermi="['mes:pro:protask:query']"
                >{{scope.row.workorderCode}}</el-button>
                </template>
            </el-table-column>
            <el-table-column label="工单名称" width="200" align="center" prop="workorderName" :show-overflow-tooltip="true"/>
            <el-table-column label="订单编号" width="140" align="center" prop="sourceCode" />
            <el-table-column label="产品编号" width="120" align="center" prop="productCode" />
            <el-table-column label="产品名称" width="200" align="center" prop="productName" :show-overflow-tooltip="true"/>
            <el-table-column label="工单数量" width="100" align="center" prop="quantity" />                 
            <el-table-column label="已生产数量" align="center" width="100px" prop="quantityProduced" /> 
            <el-table-column label="生产进度" align="center" :style="{ 'width': '200px', 'overflow': 'auto' }" prop="quantityProduced" >
            <template slot-scope="scope">             
                <div style="display: flex" >
                  <div v-for="(task,index) in scope.row.tasks" :key="task.processId">
                    <div style="align-items: center">
                      <el-progress type="circle" :width="120" :percentage="getPercentage(task)" />
                      <span>{{ task.processName }}</span>
                    </div>
                    <span v-if="index != scope.row.tasks.length -1" >-</span>
                  </div>
                </div>                   
            </template>
            </el-table-column>
            <el-table-column label="需求日期" align="center" prop="requestDate" width="180">
                <template slot-scope="scope">
                <span>{{ parseTime(scope.row.requestDate, '{y}-{m}-{d}') }}</span>
                </template>
            </el-table-column>
            <el-table-column label="工单状态" align="center" prop="status">
                <template slot-scope="scope">
                <dict-tag :options="dict.type.mes_order_status" :value="scope.row.status"/>
                </template>
            </el-table-column>
            </el-table>

            <pagination
            v-show="total>0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getList"
            />
        </el-col>
    </el-row>


    </div>
</template>
<script>
import { listWorkorder, listWithTaskJson,getWorkorder, delWorkorder, addWorkorder, updateWorkorder } from "@/api/mes/pro/workorder";
import ProcessInfo from "./process.vue";    
export default {
  name: "processview",
  dicts: ['mes_order_status','mes_workorder_sourcetype'],
  components: {
    ProcessInfo
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      //选中的生产工单
      selectedWorkorderId: -1,
      // 生产工单表格数据
      workorderList: [],
      // 生产工单树选项
      workorderOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        workorderCode: null,
        workorderName: null,
        workorderType: 'SELF', //这里的排产要排除自产之外的外协和外购
        orderSource: null,
        sourceCode: null,
        productId: null,
        productCode: null,
        productName: null,
        productSpc: null,
        unitOfMeasure: null,
        quantity: null,
        quantityProduced: null,
        quantityChanged: null,
        quantityScheduled: null,
        clientId: null,
        clientCode: null,
        clientName: null,
        requestDate: null,
        parentId: null,
        ancestors: null,
        status: 'CONFIRMED',
      },
      tasks:{
        data: [],
        links: []
      },
      // 表单参数
      form: {},
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询生产工单列表 */
    getList() {
      this.loading = true;
      listWithTaskJson(this.queryParams).then(response => {
        this.workorderList = this.handleTree(response.rows, "workorderId", "parentId");
        if(this.workorderList.length>0){
            this.selectedWorkorderId = this.workorderList[0].workorderId;
        }else{
            this.selectedWorkorderId = -1;
        }
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 转换生产工单数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.workorderId,
        label: node.workorderName,
        children: node.children
      };
    },
	/** 查询生产工单下拉树结构 */
    getTreeselect() {
      listWorkorder().then(response => {
        this.workorderOptions = [];
        const data = { workorderId: 0, workorderName: '顶级节点', children: [] };
        data.children = this.handleTree(response.data, "workorderId", "parentId");
        this.workorderOptions.push(data);
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },

    //点击某一行
    onWorkOrderSelected(r,c,e){
        this.selectedWorkorderId = r.workorderId;
        this.$nextTick(() => {
          this.$refs.process.getList();
        });
    },
    // 查询明细按钮操作
    handleView(row){
      this.reset();      
      this.getTreeselect();
      const workorderId = row.workorderId || this.ids;
      getWorkorder(workorderId).then(response => {
        this.form = response.data;
        this.getProcess();
        this.open = true;
        this.title = "查看工单信息";
        this.optType = "view";
      });
    },
    getPercentage(task){
      if(task.quantity !=0 && task.quantity != null){
        return parseFloat((task.quantityProduced/task.quantity*100).toFixed(0))
      }else{
        return 0;
      }      
    }
  }
};
</script>
<style>
.borded-el{
  border: 1px solid rgba(0, 0, 0, .12);
  padding: 10px;
}

</style>