<template>
    <el-dialog title="发货通知单选择"
      v-if="showFlag"
      :visible.sync="showFlag"
      :modal= false
      width="80%"
      center
      >
      <el-table v-loading="loading" :data="salesnoticelineList" @current-change="handleCurrent" @row-dblclick="handleRowDbClick">
        <el-table-column width="50" align="center" >
            <template v-slot="scope">
                <el-radio v-model="selectedId" :label="scope.row.lineId" @change="handleRowChange(scope.row)">{{""}}</el-radio>
            </template>
        </el-table-column>
        <el-table-column label="物资编码" width="150px" align="center" prop="itemCode" />
        <el-table-column label="物资名称" width="200px" align="center" prop="itemName" :show-overflow-tooltip="true"/>
        <el-table-column label="规格型号" align="center" prop="specification" :show-overflow-tooltip="true"/>      
        <el-table-column label="单位名称" align="center" prop="unitName" />      
        <el-table-column label="批次号" align="center" prop="batchCode" />
        <el-table-column label="发货数量" align="center" prop="quantitySales" />
        <el-table-column label="是否检验" align="center" prop="oqcCheck">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.oqcCheck"/>
          </template>
        </el-table-column>
        <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true" />
      </el-table>
      
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="confirmSelect">确 定</el-button>
        <el-button @click="showFlag=false">取 消</el-button>
      </div>
    </el-dialog>
  </template>
  
  <script>
  import { listSalesnoticeline } from "@/api/mes/wm/salesnoticeline";
  export default {
    name: "Salesnoticeline",
    dicts: ['sys_yes_no'],
    props:{
      noticeId: null
    },
    data() {
      return {
        selectedId: null,
        showFlag:false,
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
        // 发货通知单行表格数据
        salesnoticelineList: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          noticeId: this.noticeId,        itemId: null,        itemCode: null,        itemName: null,        specification: null,        unitOfMeasure: null,        unitName: null,        batchId: null,        batchCode: null,        quantitySales: null,        oqcCheck: null,      },
      };
    },
    created() {
      this.getList();
    },
    methods: {
        /** 查询发货通知单行列表 */
        getList() {
            this.loading = true;
            listSalesnoticeline(this.queryParams).then(response => {
            this.salesnoticelineList = response.rows;
            this.total = response.total;
            this.loading = false;
            });
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
        //行双击选中
        handleRowDbClick(row){
            if(row){
                this.selectedRow = row;
                this.$emit('onSelected',this.selectedRow);
                this.showFlag = false;
            }
        },
        // 单选选中数据
        handleRowChange(row) {
            if(row){
                this.selectedRow = row;
            }
        },
        handleCurrent(row){
            if(row){
                this.selectedRow = row;
            }
        },
        //确定选中
        confirmSelect(){
            if(this.selectedId ==null || this.selectedId==0){
                this.$notify({
                    title:'提示',
                    type:'warning',
                    message: '请至少选择一条数据!'
                });
                return;
            }
            this.$emit('onSelected',this.selectedRow);
            this.showFlag = false;
        }

    }
  };
  </script>
  