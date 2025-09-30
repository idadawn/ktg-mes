<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="入库单编号" prop="recptCode">
        <el-input
          v-model="queryParams.recptCode"
          placeholder="请输入入库单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="入库单名称" prop="recptName">
        <el-input
          v-model="queryParams.recptName"
          placeholder="请输入入库单名称"
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
      <el-form-item label="订单编号" prop="poCode">
        <el-input
          v-model="queryParams.poCode"
          placeholder="请输入订单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="入库日期" prop="recptDate">
        <el-date-picker clearable
          v-model="queryParams.recptDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择入库日期">
        </el-date-picker>
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
          v-hasPermi="['mes:wm:itemrecpt:add']"
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
          v-hasPermi="['mes:wm:itemrecpt:edit']"
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
          v-hasPermi="['mes:wm:itemrecpt:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['mes:wm:itemrecpt:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="itemrecptList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="入库单编号" align="center" width="180px" prop="recptCode" >
        <template slot-scope="scope">
          <el-button
            type="text"
            @click="handleView(scope.row)"
            v-hasPermi="['mes:wm:itemrecpt:query']"
          >{{scope.row.recptCode}}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="入库单名称" align="center" prop="recptName" :show-overflow-tooltip="true"/>
      <el-table-column label="供应商名称" align="center" prop="vendorName" :show-overflow-tooltip="true"/>
      <el-table-column label="入库日期" align="center" prop="recptDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.recptDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="采购订单号" align="center" prop="poCode" />
      <el-table-column label="单据状态" align="center" prop="status" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_itemrecpt_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-video-play"
            v-if="scope.row.status =='APPROVED'"
            @click="handleExecute(scope.row)"
            v-hasPermi="['mes:wm:itemrecpt:edit']"
          >执行入库</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-shopping-cart-full"
            v-if="scope.row.status =='APPROVING'"
            @click="handleStocking(scope.row)"
            v-hasPermi="['mes:wm:itemrecpt:edit']"
          >执行上架</el-button>          
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="scope.row.status =='PREPARE'"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mes:wm:itemrecpt:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            v-if="scope.row.status =='PREPARE'"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mes:wm:itemrecpt:remove']"
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

    <!-- 添加或修改物料入库单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="入库单编号" prop="recptCode">
              <el-input v-model="form.recptCode" placeholder="请输入入库单编号" />
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
            <el-form-item label="入库单名称" prop="recptName">
              <el-input v-model="form.recptName" placeholder="请输入入库单名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="入库日期" prop="recptDate">
              <el-date-picker clearable
                v-model="form.recptDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择入库日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="到货通知单" prop="noticeCode">
              <el-input v-model="form.noticeCode" readonly="readonly" placeholder="请选择到货通知单" >
                <el-button slot="append" @click="handleSelectNotice" icon="el-icon-search"></el-button>
              </el-input>
              <NoticeSelect ref="noticeSelect" @onSelected="onNoticeSelected"></NoticeSelect>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="采购订单号" prop="poCode">
              <el-input v-model="form.poCode" placeholder="请输入采购订单号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="供应商" prop="vendorName">
              <el-input v-model="form.vendorName" readonly="readonly" placeholder="请选择供应商" >
                <el-button slot="append" @click="handleSelectVendor" icon="el-icon-search"></el-button>
              </el-input>
              <VendorSelect ref="vendorSelect" @onSelected="onVendorSelected" />
            </el-form-item>
          </el-col>
          <el-col :span="8">

          </el-col>
          <el-col :span="8">

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
      <el-divider v-if="form.recptId !=null" content-position="center">物料信息</el-divider>
        <el-card shadow="always" v-if="form.recptId !=null && form.status == 'PREPARE'" class="box-card">
          <Itemrecptline ref=line :recptId="form.recptId" :noticeId="form.noticeId" :optType="optType"></Itemrecptline>
        </el-card>
        <el-card v-if="form.recptId !=null && form.status !='PREPARE'" class="box-card">
          <ItemrecptDetail ref="detail" :recptId="form.recptId" :optType="optType"></ItemrecptDetail>
        </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveForm" v-if="form.status =='PREPARE' && optType !='view' ">保 存</el-button>
        <el-button type="warning" @click="submitToStock" v-if="form.status =='PREPARE' && form.recptId !=null && optType !='view' ">提 交</el-button>
        <el-button type="warning" @click="submitToExecute" v-if="form.status =='APPROVING' && form.recptId !=null && optType !='view' ">提 交</el-button>
        <el-button type="danger" @click="cancel"  v-if="form.status !='PREPARE' && form.status !='FINISHED' && optType !='view' " >取 消</el-button>
        <el-button @click="close">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listItemrecpt, getItemrecpt, delItemrecpt, addItemrecpt, updateItemrecpt, confirmItemrecpt,execute } from "@/api/mes/wm/itemrecpt";
import {getTreeList} from "@/api/mes/wm/warehouse"
import {genCode} from "@/api/system/autocode/rule"
import VendorSelect from "@/components/vendorSelect/single.vue";
import IqcSelect from "@/components/iqcSelect/single.vue";
import NoticeSelect from "@/components/noticeSelect/single.vue"
import Itemrecptline from "./line.vue";
import ItemrecptDetail from "./detail.vue";
export default {
  name: "Itemrecpt",
  dicts:['mes_itemrecpt_status'],
  components :{VendorSelect,IqcSelect,Itemrecptline,ItemrecptDetail, NoticeSelect},
  data() {
    return {
      //自动生成编码
      autoGenFlag:false,
      optType: undefined,
      warehouseInfo:[],
      warehouseOptions:[],
      warehouseProps:{
        multiple: false,
        value: 'pId',
        label: 'pName',
      },
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
      // 物料入库单表格数据
      itemrecptList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        recptCode: null,
        recptName: null,
        iqcId: null,
        iqcCode: null,
        vendorId: null,
        vendorCode: null,
        vendorName: null,
        vendorNick: null,
        warehouseId: null,
        warehouseCode: null,
        warehouseName: null,
        locationId: null,
        locationCode: null,
        locationName: null,
        areaId: null,
        areaCode: null,
        areaName: null,
        recptDate: null,
        poCode: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        recptCode: [
          { required: true, message: "入库单编号不能为空", trigger: "blur" },
          { max: 64, message: "字段过长", trigger: "blur" }
        ],
        recptName: [
          { required: true, message: "入库单名称不能为空", trigger: "blur" },
          { max: 100, message: "字段过长", trigger: "blur" }
        ],
        recptDate: [
          { required: true, message: "入库时间不能为空", trigger: "blur"}
        ],
        vendorName: [
          { required: true, message: "请选择对应的供应商", trigger: "blur"}
        ],
        remark: [
          { max: 250, message: '长度必须小于250个字符', trigger: 'blur' }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getWarehouseList();
  },
  methods: {
    /** 查询物料入库单列表 */
    getList() {
      this.loading = true;
      listItemrecpt(this.queryParams).then(response => {
        this.itemrecptList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getWarehouseList(){
      getTreeList().then( response =>{
        if(response.data){
          this.warehouseOptions = response.data.filter((el) =>{
              return el.warehouseCode.indexOf('VIR') == -1;
          });;
        }
        this.warehouseOptions.map(w =>{
          w.children.map(l =>{
                  let lstr =JSON.stringify(l.children).replace(/locationId/g,'lId').replace(/areaId/g, 'pId').replace(/areaName/g,'pName');
                  l.children = JSON.parse(lstr);
          });

          let wstr = JSON.stringify(w.children).replace(/warehouseId/g,'wId').replace(/locationId/g, 'pId').replace(/locationName/g,'pName');
          w.children =  JSON.parse(wstr);

        });
        let ostr=JSON.stringify(this.warehouseOptions).replace(/warehouseId/g,'pId').replace(/warehouseName/g, 'pName');
        this.warehouseOptions = JSON.parse(ostr);
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
        recptId: null,
        recptCode: null,
        recptName: null,
        noticeId: null,
        noticeCode: null,
        iqcId: null,
        iqcCode: null,
        vendorId: null,
        vendorCode: null,
        vendorName: null,
        vendorNick: null,
        warehouseId: null,
        warehouseCode: null,
        warehouseName: null,
        locationId: null,
        locationCode: null,
        locationName: null,
        areaId: null,
        areaCode: null,
        areaName: null,
        recptDate: new Date(),
        poCode: null,
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
      this.ids = selection.map(item => item.recptId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.warehouseInfo = [];
      this.open = true;
      this.title = "添加物料入库单";
      this.optType = "add";
    },
    // 查询明细按钮操作
    handleView(row){
      this.reset();
      const recptIds = row.recptId
      getItemrecpt(recptIds).then(response => {
        this.form = response.data;
        this.warehouseInfo[0] = response.data.warehouseId;
        this.warehouseInfo[1] = response.data.locationId;
        this.warehouseInfo[2] = response.data.areaId;
        this.open = true;
        this.title = "查看入库单信息";
        this.optType = "view";
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const recptId = row.recptId || this.ids
      getItemrecpt(recptId).then(response => {
        this.form = response.data;
        this.warehouseInfo[0] = response.data.warehouseId;
        this.warehouseInfo[1] = response.data.locationId;
        this.warehouseInfo[2] = response.data.areaId;
        this.open = true;
        this.title = "修改物料入库单";
        this.optType = "edit";
      });
    },

    /**
     * 上架按钮操作
     */
    handleStocking(row){
      const recptIds = row.recptId
      getItemrecpt(recptIds).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "入库单上架";
        this.optType = "edit";
      });
    },

    //执行入库
    handleExecute(row){
      const recptIds = row.recptId || this.ids;
      this.$modal.confirm('确认执行入库？').then(function() {
        return execute(recptIds)//执行入库
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("入库成功");
      }).catch(() => {});
    },
    /** 保存按钮 */
    saveForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.recptId != null) {
            updateItemrecpt(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.getList();
            });
          } else {
            addItemrecpt(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.form.recptId = response.data.recptId;
              this.getList();
            });
          }
        }
      });
    },

    //提交按钮(提交到待上架状态)
    submitToStock(){
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.status = 'APPROVING';
          updateItemrecpt(this.form).then(response => {
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
      this.form.status = 'APPROVED';
        updateItemrecpt(this.form).then(response => {
          this.$modal.msgSuccess("提交成功");
          this.open = false;
          this.getList();
        } 
        ).catch(() => {
          this.form.status = 'APPROVING';
        });;       
    },

    //取消
    cancel(){
      let that = this;
      this.$modal.confirm('确认撤销入库单？').then(function() {        
        const oldStatus = that.form.status;
        that.form.status = 'CANCELED';
          updateItemrecpt(that.form).then(response => {
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


    doconfirm(){
      let that = this;
      this.$modal.confirm('是否完成入库单编制？【完成后将不能更改】').then(function(){
        that.form.status = 'CONFIRMED';
        that.submitForm();
      });
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const recptIds = row.recptId || this.ids;
      this.$modal.confirm('是否确认删除物料入库单？').then(function() {
        return delItemrecpt(recptIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('mes/wm/itemrecpt/export', {
        ...this.queryParams
      }, `itemrecpt_${new Date().getTime()}.xlsx`)
    },
    //选择默认的仓库、库区、库位
    handleWarehouseChanged(obj){
      debugger;
      if(obj !=null){
        this.form.warehouseId = obj[0];
        this.form.locationId = obj[1];
        this.form.areaId = obj[2];
      }
    },
    //IQC检验单选择
    handleSelectIqc(){
      this.$refs.iqcSelect.showFlag = true;
    },
    //IQC检验单选择弹出框
    onIqcSelected(obj){
        if(obj != undefined && obj != null){
          this.form.iqcId = obj.iqcId;
          this.form.iqcCode = obj.iqcCode;
          this.form.recptName = obj.iqcName;
          this.form.vendorId = obj.vendorId;
          this.form.vendorCode = obj.vendorCode;
          this.form.vendorName = obj.vendorName;
          this.form.vendorNick = obj.vendorNick;
        }
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
    //到货通知单选择
    handleSelectNotice(){
      this.$refs.noticeSelect.handleOpen(this.form.noticeId)
    },
    //到货通知单选择弹出框
    onNoticeSelected(obj){
      debugger;
      if(obj != undefined && obj != null){
          this.form.noticeId = obj.noticeId;
          this.form.noticeCode = obj.noticeCode;
          this.form.poCode = obj.poCode;
          this.form.vendorId = obj.vendorId;
          this.form.vendorName = obj.vendorName;
          this.form.vendorNick = obj.vendorNick;
        }
    },
    //自动生成编码
    handleAutoGenChange(autoGenFlag){
      if(autoGenFlag){
        genCode('ITEMRECPT_CODE').then(response =>{
          this.form.recptCode = response;
        });
      }else{
        this.form.recptCode = null;
      }
    }
  }
};
</script>
<style scoped>
.collapse-title {
  flex: 0 1 54%;
  order: 1;
}
</style>
