<template>
    <div class="app-container">
        <el-dialog title="批次选择"
            v-if="showFlag"
            :visible.sync="showFlag"
            :modal= false
            width="80%"
            center
            >
            <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
                <el-form-item label="批次编号" prop="batchCode">
                <el-input
                    v-model="queryParams.batchCode"
                    placeholder="请输入批次编号"
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
                <el-form-item label="生产日期" prop="produceDate">
                <el-date-picker clearable
                    v-model="queryParams.produceDate"
                    type="date"
                    value-format="yyyy-MM-dd"
                    placeholder="请选择生产日期">
                </el-date-picker>
                </el-form-item>
                <el-form-item label="有效期" prop="expireDate">
                <el-date-picker clearable
                    v-model="queryParams.expireDate"
                    type="date"
                    value-format="yyyy-MM-dd"
                    placeholder="请选择有效期">
                </el-date-picker>
                </el-form-item>
                <el-form-item label="入库日期" prop="recptDate">
                <el-date-picker clearable
                    v-model="queryParams.recptDate"
                    type="date"
                    value-format="yyyy-MM-dd"
                    placeholder="请选择入库日期">
                </el-date-picker>
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
                <el-form-item label="供应商简称" prop="vendorNick">
                <el-input
                    v-model="queryParams.vendorNick"
                    placeholder="请输入供应商简称"
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
                <el-form-item label="客户简称" prop="clientNick">
                <el-input
                    v-model="queryParams.clientNick"
                    placeholder="请输入客户简称"
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
                <el-form-item label="生产工单编码" prop="workorderCode">
                <el-input
                    v-model="queryParams.workorderCode"
                    placeholder="请输入生产工单编码"
                    clearable
                    @keyup.enter.native="handleQuery"
                />
                </el-form-item>
                <el-form-item label="生产任务编号" prop="taskCode">
                <el-input
                    v-model="queryParams.taskCode"
                    placeholder="请输入生产任务编号"
                    clearable
                    @keyup.enter.native="handleQuery"
                />
                </el-form-item>
                <el-form-item label="工作站编码" prop="workstationCode">
                <el-input
                    v-model="queryParams.workstationCode"
                    placeholder="请输入工作站编码"
                    clearable
                    @keyup.enter.native="handleQuery"
                />
                </el-form-item>
                <el-form-item label="工具编号" prop="toolCode">
                <el-input
                    v-model="queryParams.toolCode"
                    placeholder="请输入工具编号"
                    clearable
                    @keyup.enter.native="handleQuery"
                />
                </el-form-item>
                <el-form-item label="模具编号" prop="moldCode">
                <el-input
                    v-model="queryParams.moldCode"
                    placeholder="请输入模具编号"
                    clearable
                    @keyup.enter.native="handleQuery"
                />
                </el-form-item>
                <el-form-item label="生产批号" prop="productCode">
                <el-input
                    v-model="queryParams.productCode"
                    placeholder="请输入生产批号"
                    clearable
                    @keyup.enter.native="handleQuery"
                />
                </el-form-item>
                <el-form-item label="质量状态" prop="qualityStatus">
                <el-select v-model="queryParams.qualityStatus" placeholder="请选择质量状态" clearable>
                    <el-option label="请选择字典生成" value="" />
                </el-select>
                </el-form-item>
                <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
                </el-form-item>
            </el-form>
            <el-table v-loading="loading" :data="batchList" @current-change="handleCurrent" @row-dblclick="handleRowDbClick">
                <el-table-column  width="55" align="center" >
                    <template v-slot="scope">
                        <el-radio v-model="selectedId" :label="scope.row.batchId" @change="handleRowChange(scope.row)">{{""}}</el-radio>
                    </template>
                </el-table-column>
                <el-table-column label="批次编号" width="120" align="center" prop="batchCode" />
                <el-table-column label="物资编码" width="150" align="center" prop="itemCode" />
                <el-table-column label="物资名称" width="150" align="center" prop="itemName" />
                <el-table-column label="供应商编码" align="center" prop="vendorCode" />
                <el-table-column label="供应商名称" align="center" prop="vendorName" />
                <el-table-column label="客户编码" align="center" prop="clientCode" />
                <el-table-column label="客户名称" align="center" prop="clientName" />
                <el-table-column label="销售订单编号" align="center" prop="coCode" />
                <el-table-column label="采购订单编号" align="center" prop="poCode" />
                <el-table-column label="生产工单编码" align="center" prop="workorderCode" />
                <el-table-column label="生产任务编号" align="center" prop="taskCode" />
                <el-table-column label="工作站编码" align="center" prop="workstationCode" />
                <el-table-column label="工具编号" align="center" prop="toolCode" />
                <el-table-column label="模具编号" align="center" prop="moldCode" />
                <el-table-column label="生产批号" align="center" prop="lotNumber" />
                <el-table-column label="质量状态" align="center" prop="qualityStatus" />
                <el-table-column label="生产日期" align="center" prop="produceDate" width="180">
                <template slot-scope="scope">
                    <span>{{ parseTime(scope.row.produceDate, '{y}-{m}-{d}') }}</span>
                </template>
                </el-table-column>
                <el-table-column label="有效期" align="center" prop="expireDate" width="180">
                <template slot-scope="scope">
                    <span>{{ parseTime(scope.row.expireDate, '{y}-{m}-{d}') }}</span>
                </template>
                </el-table-column>
                <el-table-column label="入库日期" align="center" prop="recptDate" width="180">
                <template slot-scope="scope">
                    <span>{{ parseTime(scope.row.recptDate, '{y}-{m}-{d}') }}</span>
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
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="confirmSelect">确 定</el-button>
                <el-button @click="showFlag=false">取 消</el-button>
            </div>
        </el-dialog>
    </div>
  </template>
  
  <script>
  import { listBatch} from "@/api/mes/wm/batch";
  
  export default {
    name: "Batch",
    props:{
        itemId: null,
        itemCode: null,
        itemName: null,
        vendorId: null,
        vendorCode: null,
        vendorName: null,
        clientId: null,
        clientCode: null,
        clientName: null,
        poCode: null,
        coCode: null,
        workorderId: null,
        workorderCode: null,
        taskId: null,
        taskCode: null,
        workstationId: null,
        workstationCode: null,
        toolId: null,
        toolCode: null,
        moldId: null,
        moldCode: null,
        qualityStatus: null,
        lotNumber: null,
    },
    watch:{
        itemId(newVal){
            this.queryParams.itemId = newVal;
        },
        itemCode(newVal){
            this.queryParams.itemCode = newVal;
        },
        itemName(newVal){
            this.queryParams.itemName = newVal;
        },
        vendorId(newVal){
        this.queryParams.vendorId = newVal;
        },
        vendorCode(newVal){
        this.queryParams.vendorCode = newVal;
        },
        vendorName(newVal){
        this.queryParams.vendorName = newVal;
        },
        clientId(newVal){
        this.queryParams.clientId = newVal;
        },
        clientCode(newVal){
        this.queryParams.clientCode = newVal;
        },
        clientName(newVal){
        this.queryParams.clientName = newVal;
        },
        poCode(newVal){
        this.queryParams.poCode = newVal;
        },
        coCode(newVal){
        this.queryParams.coCode = newVal;
        },
        workorderId(newVal){
        this.queryParams.workorderId = newVal;
        },
        workorderCode(newVal){
        this.queryParams.workorderCode = newVal;
        },
        taskId(newVal){
        this.queryParams.taskId = newVal;
        },
        taskCode(newVal){
        this.queryParams.taskCode = newVal;
        },
        workstationId(newVal){
        this.queryParams.workstationId = newVal;
        },
        workstationCode(newVal){
        this.queryParams.workstationCode = newVal;
        },
        toolId(newVal){
        this.queryParams.toolId = newVal;
        },
        toolCode(newVal){
        this.queryParams.toolCode = newVal;
        },
        
    },
    data() {
      return {
        showFlag:false,
        selectedId: undefined,
        selectedRow: undefined,
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
          batchCode: null,        itemId: this.itemId,        itemCode: this.itemCode,        itemName: this.itemName,        specification: null,        unitOfMeasure: null,        produceDate: null,        expireDate: null,      
          recptDate: null,        vendorId: this.vendorId,        vendorCode: this.vendorCode,        vendorName: this.vendorName,        vendorNick: null,        clientId: this.clientId,        clientCode: this.clientCode,        clientName: this.clientName,        clientNick: null,
          coCode: this.coCode,        poCode: this.poCode,        workorderId: this.workorderId,        workorderCode: this.workorderCode,        taskId: this.taskId,        taskCode: this.taskCode,        
          workstationId: this.workstationId,        workstationCode: this.workorderCode,        toolId: this.toolId,        toolCode: this.toolCode,        moldId: this.moldId,        moldCode: this.moldCode,        lotNumber: this.lotNumber,        qualityStatus: this.qualityStatus,      },
      };
    },
    created() {
      this.getList();
    },
    methods: {
        handleOpen(id) {
            this.showFlag = true
            this.selectedId = id
            this.getList();
        },
        /** 查询批次记录列表 */
        getList() {
            this.loading = true;
            listBatch(this.queryParams).then(response => {
                this.batchList = response.rows;
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
        
        handleCurrent(row){
            if(row){
                this.selectedRow = row;
            }
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
  