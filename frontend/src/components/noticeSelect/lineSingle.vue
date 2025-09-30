<template>
    <el-dialog title="到货通知单选择"
      v-if="showFlag"
      :visible.sync="showFlag"
      :modal= false
      width="80%"
      center
      >
        <el-table v-loading="loading" :data="arrivalnoticelineList" @current-change="handleCurrent" @row-dblclick="handleRowDbClick">
        <el-table-column width="50" align="center" >
            <template v-slot="scope">
                <el-radio v-model="selectedId" :label="scope.row.lineId" @change="handleRowChange(scope.row)">{{""}}</el-radio>
            </template>
        </el-table-column>
        <el-table-column label="物料编码" width="120px" align="center" prop="itemCode" />
        <el-table-column label="物料名称" width="150px" align="center" prop="itemName" />
        <el-table-column label="规格型号" align="center" prop="specification" />
        <el-table-column label="单位" align="center" prop="unitName" />
        <el-table-column label="到货数量" align="center" prop="quantityArrival" />
        <el-table-column label="是否检验" align="center" prop="iqcCheck">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.iqcCheck"/>
          </template>
        </el-table-column>
        <el-table-column label="合格数量" align="center" prop="quantityQuanlified" />
        <el-table-column label="检验单号" align="center" prop="iqcCode" />
        <el-table-column label="备注" align="center" prop="remark" />
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
  import { listArrivalnoticeline} from "@/api/mes/wm/arrivalnoticeline";
  export default {
    name: "Arrivalnoticeline",
    dicts: ['sys_yes_no'],
    props:{
      noticeId: null
    },
    data() {
      return {
        showFlag:false,
        selectedId: null,
        // 遮罩层
        loading: true,
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 到货通知单行表格数据
        arrivalnoticelineList: [],

        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          noticeId: this.noticeId,        itemId: null,        itemCode: null,        itemName: null,        specification: null,        unitOfMeasure: null,        quantityArrival: null,        quantityQuanlified: null,        iqcCheck: null,        iqcId: null,        iqcCode: null,      },
      };
    },
    created() {
      this.getList();
    },
    methods: {
        /** 查询到货通知单行列表 */
        getList() {
            this.loading = true;
            listArrivalnoticeline(this.queryParams).then(response => {
            this.arrivalnoticelineList = response.rows;
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
