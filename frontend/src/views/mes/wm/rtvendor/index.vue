<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="110px">
      <el-form-item label="退货单编号" prop="rtCode">
        <el-input
          v-model="queryParams.rtCode"
          placeholder="请输入退货单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="退货单名称" prop="rtName">
        <el-input
          v-model="queryParams.rtName"
          placeholder="请输入退货单名称"
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
          v-hasPermi="['mes:wm:rtvendor:add']"
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
          v-hasPermi="['mes:wm:rtvendor:edit']"
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
          v-hasPermi="['mes:wm:rtvendor:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['mes:wm:rtvendor:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="rtvendorList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="退货单编号" align="center" prop="rtCode" >
        <template slot-scope="scope">
          <el-button
            type="text"
            @click="handleView(scope.row)"
            v-hasPermi="['mes:wm:rtvendor:query']"
          >{{scope.row.rtCode}}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="退货单名称" align="center" prop="rtName" />
      <el-table-column label="采购订单编号" align="center" prop="poCode" />
      <el-table-column label="供应商编码" align="center" prop="vendorCode" />
      <el-table-column label="供应商名称" align="center" width="150px" prop="vendorName" :show-overflow-tooltip="true"/>
      <el-table-column label="退货日期" align="center" prop="rtDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.rtDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="单据状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_rtvendor_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-video-play"
            v-if="scope.row.status =='APPROVED'"
            @click="handleExecute(scope.row)"
            v-hasPermi="['mes:wm:rtvendor:edit']"
          >执行退货</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-shopping-cart-2"
            v-if="scope.row.status =='APPROVING'"
            @click="handleStocking(scope.row)"
            v-hasPermi="['mes:wm:rtvendor:edit']"
          >执行拣货</el-button>        
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="scope.row.status =='PREPARE'"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mes:wm:rtvendor:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            v-if="scope.row.status =='PREPARE'"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mes:wm:rtvendor:remove']"
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

    <!-- 添加或修改供应商退货对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="960px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="退货单编号" prop="rtCode">
              <el-input v-model="form.rtCode" placeholder="请输入退货单编号" />
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
            <el-form-item label="退货单名称" prop="rtName">
              <el-input v-model="form.rtName" placeholder="请输入退货单名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="采购订单编号" prop="poCode">
              <el-input v-model="form.poCode" placeholder="请输入采购订单编号" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="供应商" prop="vendorName">
              <el-input v-model="form.vendorName" readonly="readonly" placeholder="请选择供应商" >
                <el-button slot="append" @click="handleSelectVendor" icon="el-icon-search"></el-button>
              </el-input>
              <VendorSelect ref="vendorSelect" @onSelected="onVendorSelected" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="退货日期" prop="rtDate">
              <el-date-picker clearable
                v-model="form.rtDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择退货日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="退货原因" prop="rtReason">
              <el-input v-model="form.rtReason" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="运单号" prop="transportCode">
              <el-input v-model="form.transportCode" placeholder="请输入运单号" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="联系人" prop="transportTel">
              <el-input v-model="form.transportTel" placeholder="请输入联系人" />
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
      <el-divider v-if="form.rtId !=null" content-position="center">物料信息</el-divider>
      <el-card shadow="always" v-if="form.rtId !=null && form.status == 'PREPARE'" class="box-card">
        <Rtvendorline ref=line :rtId="form.rtId" :batchCode="form.batchCdoe" :vendorId="form.vendorId" :optType="optType"></Rtvendorline>
      </el-card>
      <el-card shadow="always" v-if="form.rtId !=null && form.status != 'PREPARE'" class="box-card">
        <RtvendorDetail ref=detail :rtId="form.rtId" :batchCode="form.batchCdoe" :optType="optType"></RtvendorDetail>
      </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveForm" v-if="form.status =='PREPARE' && optType !='view' ">保 存</el-button>
        <el-button type="warning" @click="submitToStock" v-if="form.status =='PREPARE' && form.rtId !=null && optType !='view' ">提 交</el-button>
        <el-button type="warning" @click="submitToExecute" v-if="form.status =='APPROVING' && form.rtId !=null && optType !='view' ">提 交</el-button>
        <el-button type="danger" @click="cancel"  v-if="form.status !='PREPARE' && form.status !='FINISHED' && optType !='view' " >取 消</el-button>
        <el-button @click="close">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listRtvendor, getRtvendor, checkQuantity, delRtvendor, addRtvendor, updateRtvendor,execute } from "@/api/mes/wm/rtvendor";
import {genCode} from "@/api/system/autocode/rule"
import VendorSelect from "@/components/vendorSelect/single.vue";
import Rtvendorline from "./line.vue"
import RtvendorDetail from "./detail.vue"
export default {
  name: "Rtvendor",
  components:{VendorSelect,Rtvendorline,RtvendorDetail},
  dicts: ['mes_rtvendor_status'],
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
      // 供应商退货表格数据
      rtvendorList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        rtCode: null,
        rtName: null,
        poCode: null,
        vendorId: null,
        vendorCode: null,
        vendorName: null,
        vendorNick: null,
        rtDate: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        rtCode: [
          { required: true, message: "退货单编号不能为空", trigger: "blur" },
          { max: 64, message: "字段过长", trigger: "blur" }
        ],
        rtName: [
          { required: true, message: "退货单名称不能为空", trigger: "blur" },
          { max: 100, message: "字段过长", trigger: "blur" }
        ],
        poCode: [
          { max: 64, message: "字段过长", trigger: "blur" }
        ],
        vendorName: [
          { required: true, message: "供应商不能为空", trigger: "blur" }
        ],
        rtDate: [
          { required: true, message: "退货日期不能为空", trigger: "blur" }
        ],
        remark: [
          { max: 250, message: '长度必须小于250个字符', trigger: 'blur' }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询供应商退货列表 */
    getList() {
      this.loading = true;
      listRtvendor(this.queryParams).then(response => {
        this.rtvendorList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 关闭按钮
    close() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        rtId: null,
        rtCode: null,
        rtName: null,
        poCode: null,
        vendorId: null,
        vendorCode: null,
        vendorName: null,
        vendorNick: null,
        rtReason: null,
        transportCode: null,
        transportTel: null,
        rtDate: new Date(),
        status: "PREPARE",
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
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
      this.ids = selection.map(item => item.rtId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加采购退货";
      this.optType = "add";
    },
    // 查询明细按钮操作
    handleView(row){
      this.reset();
      const rtIds = row.rtId
      getRtvendor(rtIds).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "查看退货单信息";
        this.optType = "view";
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const rtId = row.rtId || this.ids
      getRtvendor(rtId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改采购退货单";
        this.optType = "edit";
      });
    },


    /**
     * 拣货按钮操作
     */
     handleStocking(row){
      this.reset();
      const rtId = row.rtId || this.ids
      getRtvendor(rtId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "采购退货单拣货";
        this.optType = "edit";
      });
    },

    /** 保存按钮 */
    saveForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.rtId != null) {
            updateRtvendor(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRtvendor(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },

    //提交按钮(提交到待拣货状态)
    submitToStock(){
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.status = 'APPROVING';
          updateRtvendor(this.form).then(response => {
              this.$modal.msgSuccess("提交成功");
              this.open = false;
              this.getList();
          } 
         ).catch(() => {
            this.form.status = 'PREPARE';
         });;          
        }
      });

    },

    submitToExecute(){
      let that = this;
      checkQuantity(this.form.rtId).then( response =>{
          debugger;
          if(response.data){
            that.form.status = 'APPROVED';
            updateRtvendor(that.form).then(response => {
              that.$modal.msgSuccess("提交成功");
              that.open = false;
              that.getList();
            } 
            ).catch(() => {
              that.form.status = 'APPROVING';
            });    
          }else{
            this.$modal.confirm('退货数量与拣货数量不一致，确认提交?').then(function() {
              that.form.status = 'APPROVED';
              updateRtvendor(that.form).then(response => {
                that.$modal.msgSuccess("提交成功");
                that.open = false;
                that.getList();
              } 
              ).catch(() => {
                that.form.status = 'APPROVING';
              });    
            }).catch(() => {

            });
          }
        }
      );


    },
    //执行退货
    handleExecute(row){
      const rtIds = row.rtId || this.ids;
      this.$modal.confirm('确认执行退货？').then(function() {
        return execute(rtIds)//执行入库
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("退货成功");
      }).catch(() => {});
    },

    //取消
    cancel(){
      let that = this;
      this.$modal.confirm('确认撤销退货单？').then(function() {        
        const oldStatus = that.form.status;
        that.form.status = 'CANCELED';
          updateRtvendor(that.form).then(response => {
            that.$modal.msgSuccess("撤销成功");
            that.open = false;
            that.getList();
          } 
          ).catch(() => {
            that.form.status = oldStatus;
          });
          return true;
      }).catch(() => {});
    },


    /** 删除按钮操作 */
    handleDelete(row) {
      const rtIds = row.rtId || this.ids;
      this.$modal.confirm('是否确认删除供应商退货编号为"' + row.rtCode + '"的数据项？').then(function() {
        return delRtvendor(rtIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wm/rtvendor/export', {
        ...this.queryParams
      }, `rtvendor_${new Date().getTime()}.xlsx`)
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
        genCode('WM_RTVENDOR_CODE').then(response =>{
          this.form.rtCode = response;
        });
      }else{
        this.form.rtCode = null;
      }
    }
  }
};
</script>
