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
      <el-form-item label="生产工单" prop="workorderCode">
        <el-input
          v-model="queryParams.workorderCode"
          placeholder="请输入生产工单编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="仓库" prop="warehouseName">
        <el-input
          v-model="queryParams.warehouseName"
          placeholder="请输入仓库名称"
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
          v-hasPermi="['mes:wm:productrecpt:add']"
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
          v-hasPermi="['mes:wm:productrecpt:edit']"
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
          v-hasPermi="['mes:wm:productrecpt:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['mes:wm:productrecpt:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="productrecptList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="入库单编号" width="130px" align="center" prop="recptCode" >
        <template slot-scope="scope">
          <el-button
            type="text"
            @click="handleView(scope.row)"
            v-hasPermi="['mes:wm:productrecpt:query']"
          >{{scope.row.recptCode}}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="入库单名称"  align="center" prop="recptName" :show-overflow-tooltip="true"/>
      <el-table-column label="生产工单编码" width="150px" align="center" prop="workorderCode" />
      <el-table-column label="产品编码" align="center" prop="itemCode" />
      <el-table-column label="产品名称" align="center" prop="itemName" />
      <el-table-column label="入库日期" align="center" prop="recptDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.recptDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="单据状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_order_status" :value="scope.row.status"/>
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
            v-hasPermi="['mes:wm:productrecpt:edit']"
          >执行入库</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-shopping-cart-full"
            v-if="scope.row.status =='APPROVING'"
            @click="handleStocking(scope.row)"
            v-hasPermi="['mes:wm:productrecpt:edit']"
          >执行上架</el-button>          
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="scope.row.status =='PREPARE'"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mes:wm:productrecpt:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            v-if="scope.row.status =='PREPARE'"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mes:wm:productrecpt:remove']"
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

    <!-- 添加或修改产品入库录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="960px" append-to-body>
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
            <el-form-item label="生产工单" prop="workorderCode">
              <el-input v-model="form.workorderCode" placeholder="请选择生产工单" >
                <el-button slot="append" icon="el-icon-search" @click="handleWorkorderSelect"></el-button>
              </el-input>
              <WorkorderSelect ref="woSelect" @onSelected="onWorkorderSelected"></WorkorderSelect>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="生产工单名称" prop="workorderName">
              <el-input v-model="form.workorderName" readonly="readonly" />
            </el-form-item>
          </el-col>
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
          <Productrecptline ref=line :recptId="form.recptId" :itemId="form.itemId" :optType="optType"></Productrecptline>
        </el-card>
        <el-card v-if="form.recptId !=null && form.status !='PREPARE'" class="box-card">
          <ProductrecptDetail ref="detail" :recptId="form.recptId" :optType="optType"></ProductrecptDetail>
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
import { listProductrecpt, getProductrecpt, delProductrecpt, addProductrecpt, updateProductrecpt, checkQuantity ,execute} from "@/api/mes/wm/productrecpt";
import WorkorderSelect from "@/components/workorderSelect/single.vue"
import Productrecptline from "./line.vue"
import ProductrecptDetail from "./detail.vue"
import {getTreeList} from "@/api/mes/wm/warehouse"
import {genCode} from "@/api/system/autocode/rule"
export default {
  name: "Productrecpt",
  dicts: ['mes_order_status'],
  components:{WorkorderSelect,Productrecptline,ProductrecptDetail},
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
      // 产品入库录表格数据
      productrecptList: [],
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
        workorderId: null,
        workorderCode: null,
        workorderName: null,
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
          { max: 100, message: "字段过长", trigger: "blur" }
        ],
        recptDate: [
          { required: true, message: "请选择入库日期", trigger: "blur" }
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
    /** 查询产品入库录列表 */
    getList() {
      this.loading = true;
      listProductrecpt(this.queryParams).then(response => {
        this.productrecptList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
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
        workorderId: null,
        workorderCode: null,
        workorderName: null,
        warehouseId: null,
        warehouseCode: null,
        warehouseName: null,
        locationId: null,
        locationCode: null,
        locationName: null,
        itemId: null,
        itemCode: null,
        itemName: null,
        unitOfMeasure: null,
        clientCode: null,
        clientName: null,
        areaId: null,
        areaCode: null,
        areaName: null,
        recptDate: new Date(),
        status: "PREPARE",
        remark: null,
        attr1: null,
        attr2: null,
        attr3: null,
        attr4: null,
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
      this.open = true;
      this.title = "添加产品入库单";
      this.optType = 'add'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const recptId = row.recptId || this.ids
      getProductrecpt(recptId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改产品入库单";
        this.optType = 'edit'
      });
    },
    /** 查看单据 */
    handleView(row) {
      this.reset();
      const recptId = row.recptId || this.ids
      getProductrecpt(recptId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "查看产品入库单";
        this.optType = 'view'
      });
    },
    /**
     * 上架按钮操作
     */
    handleStocking(row){
      const recptIds = row.recptId
      getProductrecpt(recptIds).then(response => {
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
    /** 提交按钮 */
    saveForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.recptId != null) {
            updateProductrecpt(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProductrecpt(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
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
          updateProductrecpt(this.form).then(response => {
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
      checkQuantity(this.form.recptId).then( response =>{
        if(response.data){
            that.form.status = 'APPROVED';
            updateProductrecpt(that.form).then(response => {
              that.$modal.msgSuccess("提交成功");
              that.open = false;
              that.getList();
            } 
            ).catch(() => {
              that.form.status = 'APPROVING';
            });    
          }else{
            this.$modal.confirm('入库数量与上架数量不一致，确认提交?').then(function() {
              that.form.status = 'APPROVED';
              updateProductrecpt(that.form).then(response => {
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


    //取消
    cancel(){
      let that = this;
      this.$modal.confirm('确认撤销入库单？').then(function() {        
        const oldStatus = that.form.status;
        that.form.status = 'CANCELED';
        updateProductrecpt(that.form).then(response => {
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
      const recptIds = row.recptId || this.ids;
      this.$modal.confirm('是否确认删除产品入库单编号为"' + row.recptCode + '"的数据项？').then(function() {
        return delProductrecpt(recptIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wm/productrecpt/export', {
        ...this.queryParams
      }, `productrecpt_${new Date().getTime()}.xlsx`)
    },
    //选择生产工单
    handleWorkorderSelect(){
      this.$refs.woSelect.handleOpen(this.form.workorderId)
    },
    onWorkorderSelected(row){
      if(row != undefined && row != null){
        this.form.workorderId = row.workorderId;
        this.form.workorderCode = row.workorderCode;
        this.form.workorderName = row.workorderName;
        this.form.itemId = row.productId;
        this.form.itemCode = row.productCode;
        this.form.itemName = row.productName;
        this.form.unitOfMeasure = row.unitOfMeasure;
        this.form.unitName = row.unitName;
        this.form.clientCode = row.clientCode;
        this.form.clientName = row.clientName;
      }
    },
    //自动生成编码
    handleAutoGenChange(autoGenFlag){
      if(autoGenFlag){
        genCode('PRODUCTRECPT_CODE').then(response =>{
          this.form.recptCode = response;
        });
      }else{
        this.form.recptCode = null;
      }
    }
  }
};
</script>
