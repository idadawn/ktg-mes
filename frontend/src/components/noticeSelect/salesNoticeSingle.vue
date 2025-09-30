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
        <el-form-item label="销售订单编号" prop="soCode">
          <el-input
            v-model="queryParams.soCode"
            placeholder="请输入销售订单编号"
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
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
  
      <el-table v-loading="loading" :data="salesnoticeList" @current-change="handleCurrent" @row-dblclick="handleRowDbClick">
        <el-table-column width="50" align="center" >
            <template v-slot="scope">
                <el-radio v-model="selectedId" :label="scope.row.noticeId" @change="handleRowChange(scope.row)">{{""}}</el-radio>
            </template>
        </el-table-column>
        <el-table-column label="通知单编号" align="center" width="180px" prop="noticeCode" >
        </el-table-column>
        <el-table-column label="通知单名称" align="center" prop="noticeName" />
        <el-table-column label="销售订单编号" align="center" prop="soCode" />
        <el-table-column label="客户名称" align="center" prop="clientName" />
        <el-table-column label="发货日期" align="center" prop="salesDate" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.salesDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="收货人" align="center" prop="recipient" />
        <el-table-column label="联系方式" align="center" prop="tel" />
        <el-table-column label="收货地址" align="center" prop="address" />
        <el-table-column label="单据状态" align="center" prop="status" >
          <template slot-scope="scope">
            <dict-tag :options="dict.type.mes_sales_notice_status" :value="scope.row.status"/>
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
  import { listSalesnotice } from "@/api/mes/wm/salesnotice";
  export default {
    name: "Salesnotice",
    dicts: ['mes_sales_notice_status'],
    data() {
      return {
        showFlag: false,
        selectedId: null,
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
        // 发货通知单表格数据
        salesnoticeList: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          noticeCode: null,        noticeName: null,        soCode: null,        clientId: null,        clientCode: null,        clientName: null,        clientNick: null,        salesDate: null,        recipient: null,        tel: null,        address: null,        status: 'APPROVED',      },
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
        /** 查询发货通知单列表 */
        getList() {
            this.loading = true;
            listSalesnotice(this.queryParams).then(response => {
            this.salesnoticeList = response.rows;
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
  