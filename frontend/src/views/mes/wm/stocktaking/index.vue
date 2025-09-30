<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="盘点类型" prop="takingType">
        <el-select v-model="queryParams.takingType" placeholder="请选择盘点类型" clearable>
          <el-option
            v-for="dict in dict.type.mes_stock_taking_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>     
      <el-form-item label="盘点单编号" prop="takingCode">
        <el-input
          v-model="queryParams.takingCode"
          placeholder="请输入盘点单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="盘点单名称" prop="takingName">
        <el-input
          v-model="queryParams.takingName"
          placeholder="请输入盘点单名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="盘点日期" prop="takingDate">
        <el-date-picker clearable
          v-model="queryParams.takingDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择盘点日期">
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
          v-hasPermi="['mes:wm:stocktaking:add']"
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
          v-hasPermi="['mes:wm:stocktaking:edit']"
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
          v-hasPermi="['mes:wm:stocktaking:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="stocktakingList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="任务编号" align="center" prop="takingCode" >
        <template slot-scope="scope">
          <el-button
            type="text"
            @click="handleView(scope.row)"
            v-hasPermi="['mes:wm:stocktaking:query']"
          >{{scope.row.takingCode}}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="任务名称" align="center" prop="takingName" />
      <el-table-column label="盘点类型" align="center" prop="takingType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_stock_taking_type" :value="scope.row.takingType"/>
        </template>
      </el-table-column>
      <el-table-column label="盘点方案" align="center" prop="planCode"></el-table-column>
      <el-table-column label="盘点日期" align="center" prop="takingDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.takingDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="盘点人" align="center" prop="nickName" />
      <el-table-column label="单据状态" align="center" prop="status" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_stock_taking_status" :value="scope.row.status"/>
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
            v-hasPermi="['mes:wm:stocktaking:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            v-if="scope.row.status == 'PREPARE'"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mes:wm:stocktaking:remove']"
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

    <!-- 添加或修改库存盘点记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="960px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="任务编号" prop="takingCode">
              <el-input v-model="form.takingCode" placeholder="请输入盘点单编号" />
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
            <el-form-item label="任务名称" prop="takingName">
              <el-input v-model="form.takingName" placeholder="请输入盘点单名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="盘点方案" prop="planName">
              <el-input v-model="form.planName" readonly="readonly" placeholder="请选择盘点方案" >
                <el-button slot="append" @click="handleSelectPlan" icon="el-icon-search"></el-button>
              </el-input>
            </el-form-item>
            <PlanSelect ref="planSelect" @onSelected="onPlanSelected"></PlanSelect>
          </el-col>
          <el-col :span="8">
            <el-form-item label="盘点类型" prop="takingType">
              <el-select v-model="form.takingType" placeholder="请选择盘点类型">
                <el-option
                  v-for="dict in dict.type.mes_stock_taking_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="盘点日期" prop="takingDate">
              <el-date-picker clearable
                v-model="form.takingDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择盘点日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item v-if="form.takingType == 'DYNAMIC'" label="开始时间" prop="startTime">
              <el-date-picker clearable
                v-model="form.startTime"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="请选择开始时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="结束时间" v-if="form.takingType == 'DYNAMIC'" prop="endTime">
              <el-date-picker clearable
                v-model="form.endTime"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="请选择结束时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="是否盲盘">
              <el-radio-group v-model="form.blindFlag">
                <el-radio
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否库存冻结" prop="frozenFlag">
              <el-radio-group v-model="form.frozenFlag">
                <el-radio
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="盘点人" prop="nickName">
              <el-input v-model="form.nickName" placeholder="请选择盘点人" >
                <el-button slot="append" @click="handleUserSelect" icon="el-icon-search"></el-button>
              </el-input>
            </el-form-item>
            <UserSingleSelect ref="userSelect" @onSelected="onUserSelected"></UserSingleSelect>
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
      <el-tabs type="border-card" v-if="form.takingId != null">        
        <el-tab-pane v-if="form.blindFlag=='N'" label="盘点清单">
          <TakingLine :takingId.sync="form.takingId" :optType.sync="optType"></TakingLine>
        </el-tab-pane>
        <el-tab-pane v-if="form.status != 'PREPARE'" label="盘点结果">
          <TakingResult :takingId.sync="form.takingId" :optType.sync="optType"></TakingResult>  
        </el-tab-pane>
      </el-tabs>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" v-if="optType !='view' && form.status =='PREPARE'" @click="submitForm">保 存</el-button>
        <el-button type="warning" @click="submitToTaking" v-if="form.status =='PREPARE' && form.takingId !=null && optType !='view' ">提 交</el-button>
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listStocktaking, getStocktaking, delStocktaking, addStocktaking, updateStocktaking ,execute} from "@/api/mes/wm/stocktaking";
import UserSingleSelect from "@/components/userSelect/single.vue"
import PlanSelect from '@/components/stocktakingplanSelect/single.vue'
import {getUserProfile} from "@/api/system/user";
import {genCode} from "@/api/system/autocode/rule"
import TakingLine from './line.vue';
import TakingResult from './result.vue';
export default {
  name: "Stocktaking",
  components: {TakingLine,TakingResult,UserSingleSelect,PlanSelect},
  dicts: ['mes_stock_taking_type','sys_yes_no','mes_stock_taking_status'],
  data() {
    return {
      //自动生成编码
      autoGenFlag:false,
      optType: undefined,
      user:{},
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
      // 库存盘点记录表格数据
      stocktakingList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        takingCode: null, takingName: null, takingDate: null, userName: null, nickName: null, takingType: null,   blindFlag: null, frozenFlag: null, planId: null, planCode: null, planName: null,  status: null,      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        takingCode: [
          { required: true, message: "盘点任务编号不能为空", trigger: "blur" }
        ],        takingDate: [
          { required: true, message: "盘点日期不能为空", trigger: "blur" }
        ],        takingType: [
          { required: true, message: "盘点类型不能为空", trigger: "change" }
        ],       nickName: [
          { required: true, message: "请指定盘点人", trigger: "change" }
        ],}
    };
  },
  created() {
    this.getList();
    getUserProfile().then(response => {      
      this.user = response.data;
    });
  },
  methods: {
    /** 查询库存盘点记录列表 */
    getList() {
      this.loading = true;
      listStocktaking(this.queryParams).then(response => {
        this.stocktakingList = response.rows;
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
        takingId: null,        takingCode: null,        takingName: null,        takingDate: new Date(),        userName: null,        nickName: null,        takingType: null,        blindFlag: null, frozenFlag: null, planId: null, planCode: null, planName: null,        status: "PREPARE",        remark: null,        attr1: null,        attr2: null,        attr3: null,        attr4: null,        createBy: null,        createTime: null,        updateBy: null,        updateTime: null      };
        this.form.userId = this.user.userId;
      this.form.userName = this.user.userName;
      this.form.nickName = this.user.nickName;
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
      this.ids = selection.map(item => item.takingId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加库存盘点任务";
      this.optType = 'add';
    },

    handleView(row){
      this.reset();
      const takingId = row.takingId || this.ids
      getStocktaking(takingId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改库存盘点任务";
        this.optType = 'view';
      });
    },

    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const takingId = row.takingId || this.ids
      getStocktaking(takingId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改库存盘点任务";
        this.optType = 'edit';
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.takingId != null) {
            updateStocktaking(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStocktaking(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const takingIds = row.takingId || this.ids;
      this.$modal.confirm('是否确认删除库存盘点任务编号为"' + takingIds + '"的数据项？').then(function() {
        return delStocktaking(takingIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wm/stocktaking/export', {
        ...this.queryParams
      }, `stocktaking_${new Date().getTime()}.xlsx`)
    },
    //提交到待盘点状态
    submitToTaking(){
      this.form.status = 'APPROVING';
      updateStocktaking(this.form).then(response => {
              this.$modal.msgSuccess("提交成功");
              this.open = false;
              this.getList();
      }).catch(() => {
            this.form.status = 'PREPARE';
      });
    },
    //执行盘点
    handleExecute(row){
      const takingIds = row.takingId || this.ids;
      this.$modal.confirm('确认执行盘点？').then(function() {
        return execute(takingIds)//执行入库
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("盘点完成");
      }).catch(() => {});
    },
    //点击人员选择按钮
    handleUserSelect(){
        this.$refs.userSelect.showFlag = true;
    },
    //人员选择返回
    onUserSelected(row){
        this.form.nickName = row.nickName;
        this.form.userName = row.userName;
    },
    handleSelectPlan(){
      this.$refs.planSelect.handleOpen(this.form.planId);
    },
    onPlanSelected(row){
      this.form.planId = row.planId;
      this.form.planCode = row.planCode;
      this.form.planName = row.planName;
      this.form.takingType = row.takingType;
      this.form.blindFlag = row.blindFlag;
      this.form.frozenFlag = row.frozenFlag;
      this.form.takingName = row.planName;
      this.form.startTime = row.startTime;
      this.form.endTime = row.endTime;
    },
    //自动生成编码
    handleAutoGenChange(autoGenFlag){
      if(autoGenFlag){
        genCode('WM_STOCK_TAKING_CODE').then(response =>{
          this.form.takingCode = response;
        });
      }else{
        this.form.takingCode = null;
      }
    }
  }
};
</script>
