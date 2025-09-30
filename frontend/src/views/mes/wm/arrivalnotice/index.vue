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
          <el-option label="请选择字典生成" value="" />
        </el-select>
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
          v-hasPermi="['mes:wm:arrivalnotice:add']"
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
          v-hasPermi="['mes:wm:arrivalnotice:edit']"
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
          v-hasPermi="['mes:wm:arrivalnotice:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="arrivalnoticeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="通知单编号" align="center" width="180px" prop="noticeCode" >
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
      <el-table-column label="单据状态" align="center" prop="status" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_rn_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="scope.row.status == 'PREPARE'"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mes:wm:arrivalnotice:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            v-if="scope.row.status == 'PREPARE'"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mes:wm:arrivalnotice:remove']"
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

    <!-- 添加或修改到货通知单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="960px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="通知单编号" prop="noticeCode">
              <el-input v-model="form.noticeCode" placeholder="请输入入库单编号" />
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
            <el-form-item label="采购订单编号" prop="poCode">
              <el-input v-model="form.poCode" placeholder="请输入采购订单编号" />
            </el-form-item>
          </el-col>
          <el-col :span="16">
            <el-form-item label="供应商" prop="vendorName">
              <el-input v-model="form.vendorName" readonly="readonly" placeholder="请选择供应商" >
                <el-button slot="append" @click="handleSelectVendor" icon="el-icon-search"></el-button>
              </el-input>
              <VendorSelect ref="vendorSelect" @onSelected="onVendorSelected" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="到货日期" prop="arrivalDate">
              <el-date-picker clearable
                v-model="form.arrivalDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择到货日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="联系人" prop="contact">
              <el-input v-model="form.contact" placeholder="请输入联系人" />
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
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listArrivalnotice, getArrivalnotice, delArrivalnotice, addArrivalnotice, updateArrivalnotice } from "@/api/mes/wm/arrivalnotice";
import {genCode} from "@/api/system/autocode/rule"
import VendorSelect from "@/components/vendorSelect/single.vue";
import NoticeLine from "./line.vue"
export default {
  name: "Arrivalnotice",
  dicts:['mes_rn_status'],
  components :{VendorSelect,NoticeLine},
  data() {
    return {
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
        noticeCode: null,        noticeName: null,        poCode: null,        vendorId: null,        vendorCode: null,        vendorName: null,        vendorNick: null,        arrivalDate: null,        status: null,      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        noticeCode: [
          { required: true, message: "入库单编号不能为空", trigger: "blur" },
          { max: 64, message: "字段过长", trigger: "blur" }
        ],
        noticeName: [
          { required: true, message: "入库单名称不能为空", trigger: "blur" },
          { max: 100, message: "字段过长", trigger: "blur" }
        ],
        poCode: [
          { max: 64, message: "字段过长", trigger: "blur" }
        ],
        vendorName: [
          { required: true, message: "请选择供应商", trigger: "blur" }
        ],
        arrivalDate: [
          { required: true, message: "请选择到货日期", trigger: "blur" }
        ],
        contact: [
          { max: 32, message: "字段过长", trigger: "blur" }
        ],
        tel: [
          { max: 60, message: "字段过长", trigger: "blur" }
        ],
        remark: [
          { max: 250, message: "字段过长", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询到货通知单列表 */
    getList() {
      this.loading = true;
      listArrivalnotice(this.queryParams).then(response => {
        this.arrivalnoticeList = response.rows;
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
        noticeId: null,        noticeCode: null,        noticeName: null,        poCode: null,        vendorId: null,        vendorCode: null,        vendorName: null,        vendorNick: null,        arrivalDate: new Date(),        status: "PREPARE",        remark: null,        attr1: null,        attr2: null,        attr3: null,        attr4: null,        createBy: null,        createTime: null,        updateBy: null,        updateTime: null      };
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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加到货通知单";
      this.optType = "add";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const noticeId = row.noticeId || this.ids
      getArrivalnotice(noticeId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改到货通知单";
        this.optType = "edit";
      });
    },
    handleView(row){
      this.reset();
      const noticeId = row.noticeId || this.ids
      getArrivalnotice(noticeId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "查看到货通知单";
        this.optType = "view";
      });
    },
    /**
     * 保存
     */
    saveForm(){
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.noticeId != null) {
            updateArrivalnotice(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addArrivalnotice(this.form).then(response => {
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
          this.form.status = 'APPROVING';
          if (this.form.noticeId != null) {
            updateArrivalnotice(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除到货通知单编号为"' + noticeIds + '"的数据项？').then(function() {
        return delArrivalnotice(noticeIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wm/arrivalnotice/export', {
        ...this.queryParams
      }, `arrivalnotice_${new Date().getTime()}.xlsx`)
    },
    //供应商选择
    handleSelectVendor(){
      this.$refs.vendorSelect.handleOpen(this.form.vendorId)
    },
    //供应商选择弹出框
    onVendorSelected(obj){
        if(obj != undefined && obj != null){
          this.form.vendorId = obj.vendorId;
          this.form.vendorCode = obj.vendorCode;
          this.form.vendorName = obj.vendorName;
          this.form.vendorNick = obj.vendorNick;
        }
    },
    //自动生成编码
    handleAutoGenChange(autoGenFlag){
      if(autoGenFlag){
        genCode('ARRIVALNOTICE_CODE').then(response =>{
          this.form.noticeCode = response;
        });
      }else{
        this.form.noticeCode = null;
      }
    }
  }
};
</script>
