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

      <el-form-item label="外协工单编号" prop="workorderCode">
        <el-input
          v-model="queryParams.workorderCode"
          placeholder="请输入外协工单编号"
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
          v-hasPermi="['mes:wm:outsourcerecpt:add']"
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
          v-hasPermi="['mes:wm:outsourcerecpt:edit']"
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
          v-hasPermi="['mes:wm:outsourcerecpt:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="outsourcerecptList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="入库单编号" align="center" prop="recptCode" >
        <template slot-scope="scope">
          <el-button
            type="text"
            @click="handleView(scope.row)"
            v-hasPermi="['mes:wm:outsourcerecpt:query']"
          >{{scope.row.recptCode}}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="入库单名称" align="center" prop="recptName" />
      <el-table-column label="外协工单编号" align="center" prop="workorderCode" />
      <el-table-column label="供应商编码" align="center" prop="vendorCode" />
      <el-table-column label="供应商名称" align="center" prop="vendorName" />
      <el-table-column label="入库日期" align="center" prop="recptDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.recptDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="单据状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_outsource_recpt_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-video-play"
            v-if="scope.row.status =='UNEXECUTE'"
            @click="handleExecute(scope.row)"
            v-hasPermi="['mes:wm:outsourcerecpt:edit']"
          >执行退料</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-shopping-cart-full"
            v-if="scope.row.status =='UNSTOCK'"
            @click="handleStocking(scope.row)"
            v-hasPermi="['mes:wm:outsourcerecpt:edit']"
          >执行上架</el-button>   
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="scope.row.status =='PREPARE'"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mes:wm:outsourcerecpt:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            v-if="scope.row.status =='PREPARE'"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mes:wm:outsourcerecpt:remove']"
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

    <!-- 添加或修改外协入库单对话框 -->
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
            <el-form-item label="外协工单" prop="workorderCode">
              <el-input v-model="form.workorderCode" placeholder="请选择外协工单" >
                <el-button slot="append" icon="el-icon-search" @click="handleWorkorderSelect"></el-button>
              </el-input>
            </el-form-item>
            <WorkorderSelect ref="woSelect" :workorder="workorder" @onSelected="onWorkorderSelected"></WorkorderSelect>
          </el-col>
          <el-col :span="8">
            <el-form-item label="供应商" prop="vendorName">
              <el-input v-model="form.vendorName" readonly="readonly">
              </el-input>
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
          <Itemrecptline ref=line :recptId="form.recptId"  :optType="optType"></Itemrecptline>
        </el-card>
        <el-card shadow="always" v-if="form.recptId !=null && form.status != 'PREPARE'" class="box-card">
          <ItemrecptDetail ref=line :recptId="form.recptId" :status="form.status"  :optType="optType"></ItemrecptDetail>
        </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveForm" v-if="form.status =='PREPARE' && optType !='view' ">保 存</el-button>
        <el-button type="warning" @click="submitToStock" v-if="form.status =='PREPARE' && form.recptId !=null && optType !='view' ">提 交</el-button>
        <el-button type="warning" @click="submitToExecute" v-if="form.status =='UNSTOCK' && form.recptId !=null && optType !='view' ">提 交</el-button>        
        <el-button type="danger" @click="cancel"  v-if="form.status !='PREPARE' && form.status !='FINISHED' && optType !='view' " >取 消</el-button>
        <el-button @click="close">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listOutsourcerecpt, getOutsourcerecpt, delOutsourcerecpt, addOutsourcerecpt, updateOutsourcerecpt, execute } from "@/api/mes/wm/outsourcerecpt";
import {genCode} from "@/api/system/autocode/rule"
import WorkorderSelect from "@/components/workorderSelect/single.vue"
import Itemrecptline from "./line.vue";
import ItemrecptDetail from './detail.vue';
export default {
  name: "Outsourcerecpt",
  dicts:['mes_outsource_recpt_status'],
  components :{WorkorderSelect,Itemrecptline,ItemrecptDetail},
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
      // 外协入库单表格数据
      outsourcerecptList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //工单弹出框结果过滤参数
      workorder: { workorderType: 'OUTSOURCE'},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        recptCode: null,   recptName: null,   workorderId: null,        workorderCode: null,        vendorId: null,        vendorCode: null,        vendorName: null,        vendorNick: null,         recptDate: null,        status: null,      },
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
        workorderCode: [
          { required: true, message: "请选择外协工单", trigger: "blur" }
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
    /** 查询外协入库单列表 */
    getList() {
      this.loading = true;
      listOutsourcerecpt(this.queryParams).then(response => {
        this.outsourcerecptList = response.rows;
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
        recptId: null,
        recptCode: null,
        recptName: null,
        workorderId: null,
        workorderCode: null,
        vendorId: null,
        vendorCode: null,
        vendorName: null,
        vendorNick: null,
        recptDate: new Date(),
        status: "PREPARE", remark: null, attr1: null, attr2: null, attr3: null, attr4: null, createBy: null, createTime: null, updateBy: null, updateTime: null
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
      this.title = "添加外协入库单";
      this.optType = "add";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const recptId = row.recptId || this.ids
      getOutsourcerecpt(recptId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改外协入库单";
        this.optType = "edit";
      });
    },

    handleStocking(row){
      this.reset();
      const recptId = row.recptId || this.ids
      getOutsourcerecpt(recptId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "外协入库单上架";
        this.optType = "edit";
      });
    },


    /** 保存按钮 */
    saveForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.recptId != null) {
            updateOutsourcerecpt(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOutsourcerecpt(this.form).then(response => {
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
          this.form.status = 'UNSTOCK';
          updateOutsourcerecpt(this.form).then(response => {
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

       /**
     * 提交到待执行退料状态
     */
    submitToExecute(){
      let oldStatus = this.form.status;
      this.form.status = 'UNEXECUTE';
      updateOutsourcerecpt(this.form).then(response => {
          this.$modal.msgSuccess("提交成功");
          this.open = false;
          this.getList();
        } 
        ).catch(() => {
          this.form.status = oldStatus;
        });;       
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

    //取消
    cancel(){
      let that = this;
      this.$modal.confirm('确认撤销退料单？').then(function() {        
        const oldStatus = that.form.status;
        that.form.status = 'CANCELED';
        updateOutsourcerecpt(that.form).then(response => {
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
      this.$modal.confirm('是否确认删除外协入库单编号为"' + recptIds + '"的数据项？').then(function() {
        return delOutsourcerecpt(recptIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    // 查询明细按钮操作
    handleView(row){
      this.reset();
      const recptIds = row.recptId
      getOutsourcerecpt(recptIds).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "查看外协入库单信息";
        this.optType = "view";
      });
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
        this.form.poCode = row.sourceCode;
        this.form.vendorId = row.vendorId;
        this.form.vendorCode = row.vendorCode;
        this.form.vendorName = row.vendorName;
      }
    },
    //自动生成编码
    handleAutoGenChange(autoGenFlag){
      if(autoGenFlag){
        genCode('WM_OUTSOURCE_RECPT_CODE').then(response =>{
          this.form.recptCode = response;
        });
      }else{
        this.form.recptCode = null;
      }
    }
  }
};
</script>
