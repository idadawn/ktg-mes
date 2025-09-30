<template>
  <div class="app-container">
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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['mes:wm:salesnotice:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['mes:wm:salesnotice:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['mes:wm:salesnotice:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['mes:wm:salesnotice:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="salesnoticeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="通知单编号" align="center" width="180px" prop="noticeCode" >
        <template slot-scope="scope">
          <el-button
            type="text"
            @click="handleView(scope.row)"
            v-hasPermi="['mes:wm:salesnotice:query']"
          >{{scope.row.noticeCode}}</el-button>
        </template>
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
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="scope.row.status =='PREPARE'"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mes:wm:salesnotice:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            v-if="scope.row.status =='PREPARE'"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mes:wm:salesnotice:remove']"
          >删除</el-button>
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

    <!-- 添加或修改发货通知单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="960px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="通知单编号" prop="noticeCode">
              <el-input v-model="form.noticeCode" placeholder="请输入退货单编号" />
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item  label-width="80">
              <el-switch v-model="autoGenFlag"
                  active-color="#13ce66"
                  active-text="自动生成"
                  @change="handleAutoGenChange(autoGenFlag)" v-if="optType != 'view' && form.status =='PREPARE'">
              </el-switch>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="通知单名称" prop="noticeName">
              <el-input v-model="form.noticeName" placeholder="请输入通知单名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="销售订单编号" prop="soCode">
              <el-input v-model="form.soCode" placeholder="请输入销售订单编号" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="客户编码" prop="clientCode">
              <el-input v-model="form.clientCode" placeholder="请选择客户" >
                <el-button slot="append" @click="handleSelectClient" icon="el-icon-search"></el-button>
              </el-input>
              <ClientSelect ref="clientSelect" @onSelected="onClientSelected" > </ClientSelect>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="客户名称" prop="clientName">
              <el-input v-model="form.clientName" readonly="readonly" placeholder="请选择客户" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="发货日期" prop="salesDate">
              <el-date-picker clearable
                v-model="form.salesDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择发货日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="收货人" prop="recipient">
              <el-input v-model="form.recipient" placeholder="请输入收货人" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="联系方式" prop="tel">
              <el-input v-model="form.tel" placeholder="请输入联系方式" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="收货地址" prop="address">
              <el-input v-model="form.address" placeholder="请输入收货地址" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-divider v-if="form.noticeId !=null" content-position="center">物料信息</el-divider>
      <el-card shadow="always" v-if="form.noticeId !=null" class="box-card">
        <NoticeLine :noticeId="form.noticeId" :optType="optType"></NoticeLine>
      </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveForm" v-if="form.status =='PREPARE' && optType !='view' ">保 存</el-button>
        <el-button type="success" @click="submitForm" v-if="form.status =='PREPARE' && optType !='view' && form.noticeId !== null ">提 交</el-button>
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listSalesnotice, getSalesnotice, delSalesnotice, addSalesnotice, updateSalesnotice } from "@/api/mes/wm/salesnotice";
import ClientSelect from "@/components/clientSelect/single.vue";
import NoticeLine from './line.vue'
import {genCode} from "@/api/system/autocode/rule"
export default {
  name: "Salesnotice",
  dicts: ['mes_sales_notice_status'],
  components: {  NoticeLine, ClientSelect },
  data() {
    return {
      //自动生成编码
      autoGenFlag:false,
      optType: undefined,
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
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        noticeCode: null,        noticeName: null,        soCode: null,        clientId: null,        clientCode: null,        clientName: null,        clientNick: null,        salesDate: null,        recipient: null,        tel: null,        address: null,        status: null,      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        noticeCode: [
          { required: true, message: "出库单编号不能为空", trigger: "blur" }
        ],        noticeName: [
          { required: true, message: "出库单名称不能为空", trigger: "blur" }
        ],        clientCode: [
          { required: true, message: "客户不能为空", trigger: "blur" }
        ],}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询发货通知单列表 */
    getList() {
      this.loading = true;
      listSalesnotice(this.queryParams).then(response => {
        this.salesnoticeList = response.rows;
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
      noticeId: null,        noticeCode: null,        noticeName: null,        soCode: null,        clientId: null,        clientCode: null,        clientName: null,        clientNick: null,        salesDate: null,        recipient: null,        tel: null,        address: null,        status: "PREPARE",        remark: null,        attr1: null,        attr2: null,        attr3: null,        attr4: null,        createBy: null,        createTime: null,        updateBy: null,        updateTime: null      };
      this.autoGenFlag = false;
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
      this.ids = selection.map(item => item.noticeId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 查看 */
    handleView(row) {
      this.reset();
      const noticeId = row.noticeId || this.ids
      getSalesnotice(noticeId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "查看发货通知单";
        this.optType = "view";
      });
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加发货通知单";
      this.optType = "add";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const noticeId = row.noticeId || this.ids
      getSalesnotice(noticeId).then(response => {
        this.form = response.data;
        this.open = true;
        this.optType = "edit";
        this.title = "修改发货通知单";
      });
    },
    /** 保存按钮 */
    saveForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.noticeId != null) {
            updateSalesnotice(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSalesnotice(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.status = 'APPROVED';
          if (this.form.noticeId != null) {
            updateSalesnotice(this.form).then(response => {
              this.$modal.msgSuccess("提交成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const noticeIds = row.noticeId || this.ids;
      this.$modal.confirm('是否确认删除发货通知单编号为"' + noticeIds + '"的数据项？').then(function() {
        return delSalesnotice(noticeIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wm/salesnotice/export', {
        ...this.queryParams
      }, `salesnotice_${new Date().getTime()}.xlsx`)
    },

    handleSelectClient(){
      this.$refs.clientSelect.showFlag = true;
    },
    //客户选择弹出框
    onClientSelected(obj){
      if(obj != undefined && obj != null){
        this.form.clientId = obj.clientId;
        this.form.clientCode = obj.clientCode;
        this.form.clientName = obj.clientName;
        this.form.clientNick = obj.clientNick;
      }
    },
    //自动生成编码
    handleAutoGenChange(autoGenFlag){
      if(autoGenFlag){
        genCode('WM_SALES_NOTICE_CODE').then(response =>{
          this.form.noticeCode = response;
        });
      }else{
        this.form.noticeCode = null;
      }
    }
  }
};
</script>
