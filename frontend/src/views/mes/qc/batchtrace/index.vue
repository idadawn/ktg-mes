<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="批次号" prop="batchCode">
        <el-input
          v-model="queryParams.batchCode"
          placeholder="请输入批次号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产品物料编码" prop="itemCode">
        <el-input
          v-model="queryParams.itemCode"
          placeholder="请输入产品物料编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产品物料名称" prop="itemName">
        <el-input
          v-model="queryParams.itemName"
          placeholder="请输入产品物料名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单位" prop="unitOfMeasure">
        <el-input
          v-model="queryParams.unitOfMeasure"
          placeholder="请输入单位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供应商编码" prop="vendorCode">
        <el-input
          v-model="queryParams.vendorCode"
          placeholder="请输入供应商编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供应商名称" prop="vendorName">
        <el-input
          v-model="queryParams.vendorName"
          placeholder="请输入供应商名称"
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
      <el-form-item label="销售订单编号" prop="coCode">
        <el-input
          v-model="queryParams.coCode"
          placeholder="请输入销售订单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="采购订单编号" prop="poCode">
        <el-input
          v-model="queryParams.poCode"
          placeholder="请输入采购订单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="batchList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />      
      <el-table-column label="批次编号" align="center" prop="batchCode" />      
      <el-table-column label="产品物料编码" align="center" prop="itemCode" />
      <el-table-column label="产品物料名称" align="center" prop="itemName" />
      <el-table-column label="规格型号" align="center" prop="specification" />
      <el-table-column label="单位" align="center" prop="unitName" />     
      <el-table-column label="供应商编码" align="center" prop="vendorCode" />
      <el-table-column label="供应商名称" align="center" prop="vendorName" />
      <el-table-column label="客户编码" align="center" prop="clientCode" />
      <el-table-column label="客户名称" align="center" prop="clientName" />
      <el-table-column label="销售订单编号" align="center" prop="coCode" />
      <el-table-column label="采购订单编号" align="center" prop="poCode" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-aim"
            @click="handleTrace(scope.row)"
            v-hasPermi="['mes:wm:batch:query']"
          >批次追溯</el-button>
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

    <!-- 添加或修改批次记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="960px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="批次编号" prop="batchCode">
              <el-input v-model="form.batchCode" readonly="readonly" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="物资编码" prop="itemCode">
              <el-input v-model="form.itemCode" readonly="readonly" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="物资名称" prop="itemName">
              <el-input v-model="form.itemName" readonly="readonly" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="规格型号" prop="specification">
              <el-input v-model="form.specification" type="textarea" readonly="readonly" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="采购订单编号" prop="poCode">
              <el-input v-model="form.poCode" readonly="readonly" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="供应商编码" prop="vendorCode">
              <el-input v-model="form.vendorCode" readonly="readonly" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="供应商名称" prop="vendorName">
              <el-input v-model="form.vendorName" readonly="readonly" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="销售订单编号" prop="coCode">
              <el-input v-model="form.coCode" readonly="readonly" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="客户编码" prop="clientCode">
              <el-input v-model="form.clientCode" readonly="readonly" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="客户名称" prop="clientName">
              <el-input v-model="form.clientName" readonly="readonly" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="生产批号" prop="productCode">
              <el-input v-model="form.productCode" readonly="readonly" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="生产工单" prop="workorderCode">
              <el-input v-model="form.workorderCode" readonly="readonly" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="工作站编码" prop="workstationCode">
              <el-input v-model="form.workstationCode" readonly="readonly" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-tabs type="border-card">
        <el-tab-pane label="向前追溯">
          <forward :batchId="form.batchId" :batchCode="form.batchCode"></forward>
        </el-tab-pane>
        <el-tab-pane label="向后追溯">
          <backward :batchId="form.batchId" :batchCode="form.batchCode"></backward>
        </el-tab-pane>
      </el-tabs>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listBatch, getBatch, delBatch, addBatch, updateBatch } from "@/api/mes/wm/batch";
import forward from "./forward.vue";
import backward from "./backward.vue";
export default {
  name: "Batch",
  components: {forward,backward},
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 批次记录表格数据
      batchList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        batchCode: null,        itemId: null,        itemCode: null,        itemName: null,        specification: null,        unitOfMeasure: null,        produceDate: null,        expireDate: null,        recptDate: null,        vendorId: null,        vendorCode: null,        vendorName: null,        vendorNick: null,        clientId: null,        clientCode: null,        clientName: null,        clientNick: null,        coCode: null,        poCode: null,        workorderId: null,        workorderCode: null,        taskId: null,        taskCode: null,        workstationId: null,        workstationCode: null,        toolId: null,        toolCode: null,        moldId: null,        moldCode: null,        productCode: null,        qualityStatus: null,      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        batchCode: [
          { required: true, message: "批次编号不能为空", trigger: "blur" }
        ],        itemId: [
          { required: true, message: "产品物料ID不能为空", trigger: "blur" }
        ],      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询批次记录列表 */
    getList() {
      this.loading = true;
      listBatch(this.queryParams).then(response => {
        this.batchList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        batchId: null,        batchCode: null,        itemId: null,        itemCode: null,        itemName: null,        specification: null,        unitOfMeasure: null,        produceDate: null,        expireDate: null,        recptDate: null,        vendorId: null,        vendorCode: null,        vendorName: null,        vendorNick: null,        clientId: null,        clientCode: null,        clientName: null,        clientNick: null,        coCode: null,        poCode: null,        workorderId: null,        workorderCode: null,        taskId: null,        taskCode: null,        workstationId: null,        workstationCode: null,        toolId: null,        toolCode: null,        moldId: null,        moldCode: null,        productCode: null,        qualityStatus: "0",        remark: null,        attr1: null,        attr2: null,        attr3: null,        attr4: null,        createBy: null,        createTime: null,        updateBy: null,        updateTime: null      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.batchId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },

    /** 追溯按钮操作 */
    handleTrace(row) {
      this.reset();
      const batchId = row.batchId || this.ids
      getBatch(batchId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "批次追溯";
      });
    }    
  }
};
</script>
