<template>
    <el-dialog title="到货通知单选择"
      v-if="showFlag"
      :visible.sync="showFlag"
      :modal= false
      width="80%"
      center
      >
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="通知单编号" prop="noticeCode">
        <el-input
          v-model="queryParams.noticeCode"
          placeholder="请输入通知单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="通知单名称" prop="noticeName">
        <el-input
          v-model="queryParams.noticeName"
          placeholder="请输入通知单名称"
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
      <el-form-item label="供应商名称" prop="vendorName">
        <el-input
          v-model="queryParams.vendorName"
          placeholder="请输入供应商名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="到货日期" prop="arrivalDate">
        <el-date-picker clearable
          v-model="queryParams.arrivalDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择到货日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="单据状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择单据状态" clearable>
          <el-option
            v-for="dict in orderStatusDict"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="arrivalnoticeList" @current-change="handleCurrent" @row-dblclick="handleRowDbClick">
        <el-table-column width="50" align="center" >
            <template v-slot="scope">
                <el-radio v-model="selectedId" :label="scope.row.noticeId" @change="handleRowChange(scope.row)">{{""}}</el-radio>
            </template>
        </el-table-column>
      <el-table-column label="入库单编号" align="center" width="180px" prop="noticeCode" >
        <template slot-scope="scope">
          <el-button
            type="text"
            @click="handleView(scope.row)"
            v-hasPermi="['mes:wm:arrivalnotice:query']"
          >{{scope.row.noticeCode}}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="通知单名称" align="center" prop="noticeName" />
      <el-table-column label="采购订单号" align="center" prop="poCode" />
      <el-table-column label="供应商名称" width="200px" align="center" prop="vendorName" />
      <el-table-column label="联系人" align="center" prop="contact" />
      <el-table-column label="联系方式" align="center" prop="tel" />
      <el-table-column label="到货日期" align="center" prop="arrivalDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.arrivalDate, '{y}-{m}-{d}') }}</span>
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
</template>
<script>
import { listArrivalnotice} from "@/api/mes/wm/arrivalnotice";
export default {
    name: "ArrivalnoticeSelect",
    data() {
    return {
      showFlag:false,
      orderStatusDict: [],
      // 遮罩层
      loading: true,
      // 选中
      selectedId: null,
      selectedRow: undefined,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 到货通知单表格数据
      arrivalnoticeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        noticeCode: null,
        noticeName: null,
        poCode: null,
        vendorId: null,
        vendorCode: null,
        vendorName: null,
        vendorNick: null,
        arrivalDate: null,
        status: 'APPROVED',
      },
    };
  },
  created() {
    this.getList();
    this.getDicts("mes_order_status").then(res => {
      this.orderStatusDict = res.data
    })
  },
  methods: {
      handleOpen(id) {
        this.showFlag = true
        this.selectedId = id
        this.getList();
      },
    /** 查询到货通知单列表 */
    getList() {
      this.loading = true;
      listArrivalnotice(this.queryParams).then(response => {
        this.arrivalnoticeList = response.rows;
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

}

</script>
