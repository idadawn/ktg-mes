<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="方案编号" prop="planCode">
        <el-input
          v-model="queryParams.planCode"
          placeholder="请输入方案编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="方案名称" prop="planName">
        <el-input
          v-model="queryParams.planName"
          placeholder="请输入方案名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="盘点类型" prop="takingType">
        <el-select v-model="queryParams.takingType" placeholder="请选择盘点类型">
                <el-option
                  v-for="dict in dict.type.mes_stock_taking_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
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
          v-hasPermi="['mes:wm:stocktakingplan:add']"
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
          v-hasPermi="['mes:wm:stocktakingplan:edit']"
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
          v-hasPermi="['mes:wm:stocktakingplan:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="stocktakingplanList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="方案编号" align="center" prop="planCode" >
        <template slot-scope="scope">
          <el-button
            type="text"
            @click="handleView(scope.row)"
            v-hasPermi="['mes:wm:stocktakingplan:query']"
          >{{scope.row.planCode}}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="方案名称" align="center" prop="planName" />
      <el-table-column label="盘点类型" align="center" prop="takingType" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_stock_taking_type" :value="scope.row.takingType"/>
        </template>
      </el-table-column>
      <el-table-column label="开始时间" align="center" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否盲盘" align="center" prop="blindFlag" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.blindFlag"/>
        </template>
      </el-table-column>
      <el-table-column label="是否库存冻结" align="center" prop="frozenFlag" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.frozenFlag"/>
        </template>
      </el-table-column>
      <el-table-column label="是否启用" align="center" prop="enableFlag" >
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.enableFlag"
            active-text="是"
            inactive-text="否"
            active-value="Y"
            inactive-value="N"
            @change="handleEnableFlagChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="scope.row.enableFlag == 'N'"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mes:wm:stocktakingplan:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            v-if="scope.row.enableFlag == 'N'"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mes:wm:stocktakingplan:remove']"
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

    <!-- 添加或修改库存盘点方案对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="960px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="方案编号" prop="planCode">
              <el-input v-model="form.planCode" placeholder="请输入方案编号" />
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
            <el-form-item label="方案名称" prop="planName">
              <el-input v-model="form.planName" placeholder="请输入方案名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
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
          <el-col :span="8"></el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-tooltip class="item" effect="dark" content="方案参数用来设置被盘点物资的范围" placement="top">
        <el-divider v-if="form.planId !=null" content-position="center">方案参数</el-divider>
      </el-tooltip>
      <ParamLine v-if="form.planId != null" :planId="form.planId" :optType="optType"></ParamLine>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" v-if="form.status == 'PREPARE' && optType != 'view'" @click="submitForm">保 存</el-button>
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listStocktakingplan, getStocktakingplan, delStocktakingplan, addStocktakingplan, updateStocktakingplan } from "@/api/mes/wm/stocktakingplan";
import ParamLine from "./param.vue"
import {genCode} from "@/api/system/autocode/rule"
export default {
  name: "Stocktakingplan",
  components: { ParamLine },
  dicts: ['mes_stock_taking_type','sys_yes_no'],
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
      // 库存盘点方案表格数据
      stocktakingplanList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        planCode: null,        planName: null,        takingType: null,        startTime: null,        endTime: null,        blindFlag: null,        frozenFlag: null,        enableFlag: null,        dataSql: null,        status: null,      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        planCode: [
          { required: true, message: "盘点单编号不能为空", trigger: "blur" }
        ],
        planName: [
          { required: true, message: "盘点单名称不能为空", trigger: "blur" }
        ],
        takingType: [
          { required: true, message: "盘点类型不能为空", trigger: "change" }
        ],      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询库存盘点方案列表 */
    getList() {
      this.loading = true;
      listStocktakingplan(this.queryParams).then(response => {
        this.stocktakingplanList = response.rows;
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
        planId: null,        planCode: null,        planName: null,        takingType: null,        startTime: null,        endTime: null,        blindFlag: 'N',        frozenFlag: 'N',        enableFlag: 'N',        dataSql: null,        status: "PREPARE",        remark: null,        attr1: null,        attr2: null,        attr3: null,        attr4: null,        createBy: null,        createTime: null,        updateBy: null,        updateTime: null      };
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
      this.ids = selection.map(item => item.planId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.optType = "add";
      this.title = "添加库存盘点方案";
    },
    /** 查看操作 */
    handleView(row) {
      this.reset();
      const planId = row.planId || this.ids
      getStocktakingplan(planId).then(response => {
        this.form = response.data;
        this.open = true;
        this.optType = "view";
        this.title = "修改库存盘点方案";
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const planId = row.planId || this.ids
      getStocktakingplan(planId).then(response => {
        this.form = response.data;
        this.open = true;
        this.optType = "edit";
        this.title = "修改库存盘点方案";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.planId != null) {
            updateStocktakingplan(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStocktakingplan(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
        /**
     * 启用状态变更
     * @param row
     */
     handleEnableFlagChange(row){
      let text = row.enableFlag === "N" ? "停用" : "启用";
      this.$modal.confirm('确认要"' + text + '""' + row.planName + '"吗？').then(function() {
        return updateStocktakingplan(row);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.enableFlag = row.enableFlag === "N" ? "Y" : "N";
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const planIds = row.planId || this.ids;
      this.$modal.confirm('是否确认删除库存盘点方案编号为"' + planIds + '"的数据项？').then(function() {
        return delStocktakingplan(planIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wm/stocktakingplan/export', {
        ...this.queryParams
      }, `stocktakingplan_${new Date().getTime()}.xlsx`)
    },
    //自动生成编码
    handleAutoGenChange(autoGenFlag){
      if(autoGenFlag){
        genCode('WM_STOCK_TAKING_PLAN_CODE').then(response =>{
          this.form.planCode = response;
        });
      }else{
        this.form.planCode = null;
      }
    }
  }
};
</script>
