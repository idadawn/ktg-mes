<template>
  <div class="app-container">
    <el-row :gutter="10" v-if="optType !== 'view'" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['dv:checkrecordline:add']"
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
          v-hasPermi="['dv:checkrecordline:edit']"
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
          v-hasPermi="['dv:checkrecordline:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="checkrecordlineList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="项目名称" align="center" prop="subjectName" />
      <el-table-column label="检查内容" align="center" prop="subjectContent" :show-overflow-tooltip="true"/>
      <el-table-column label="标准" align="center" prop="subjectStandard" />
      <el-table-column label="点检结果" align="center" prop="checkStatus" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.dv_cm_result_status" :value="scope.row.checkStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="异常描述" align="center" prop="checkResult" :show-overflow-tooltip="true" />
      <el-table-column label="操作" align="center" v-if="optType !== 'view'" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['dv:checkrecordline:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['dv:checkrecordline:remove']"
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

    <!-- 添加或修改设备点检记录行对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="960px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="项目名称" prop="subjectName">
              <el-input v-model="form.subjectName" placeholder="请选择检查项目" >
                <el-button slot="append" @click="handleSelectSubject" icon="el-icon-search"></el-button>
              </el-input>
            </el-form-item>
            <DvSubjectSelect ref="subjectSelect" subjectType="CHECK" @onSelected="onSubjectSelected"></DvSubjectSelect>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="标准" prop="subjectStandard">
              <el-input v-model="form.subjectStandard" placeholder="请输入标准" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="项目内容">
              <el-input type="textarea" v-model="form.subjectContent" :min-height="192"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="点检结果">
          <el-radio-group v-model="form.checkStatus">
            <el-radio label="Y">正常</el-radio>
            <el-radio label="N">异常</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-if="form.checkStatus === 'N'" label="异常描述" prop="checkResult">
          <el-input v-model="form.checkResult" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCheckrecordline, getCheckrecordline, delCheckrecordline, addCheckrecordline, updateCheckrecordline } from "@/api/mes/dv/checkrecordline";
import DvSubjectSelect from "@/components/dvsubjectSelect/single.vue";
export default {
  name: "Checkrecordline",
  props:{
    optType: null,
    recordId: null,
  },
  dicts: ['dv_cm_result_status'],
  components:{ DvSubjectSelect },
  data() {
    return {
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
      // 设备点检记录行表格数据
      checkrecordlineList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        recordId: this.recordId,        subjectId: null,        subjectCode: null,        subjectName: null,        subjectType: null,        subjectContent: null,        subjectStandard: null,        checkStatus: null,        checkResult: null,      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        recordId: [
          { required: true, message: "计划ID不能为空", trigger: "blur" }
        ],        subjectId: [
          { required: true, message: "项目ID不能为空", trigger: "blur" }
        ],        subjectCode: [
          { required: true, message: "项目编码不能为空", trigger: "blur" }
        ],        subjectContent: [
          { required: true, message: "项目内容不能为空", trigger: "blur" }
        ],        checkStatus: [
          { required: true, message: "点检结果不能为空", trigger: "blur" }
        ],      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询设备点检记录行列表 */
    getList() {
      this.loading = true;
      listCheckrecordline(this.queryParams).then(response => {
        this.checkrecordlineList = response.rows;
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
        lineId: null,        recordId: this.recordId,        subjectId: null,        subjectCode: null,        subjectName: null,        subjectType: null,        subjectContent: null,        subjectStandard: null,        checkStatus: "Y",        checkResult: null,        attr1: null,        attr2: null,        attr3: null,        attr4: null,        createBy: null,        createTime: null,        updateBy: null,        updateTime: null      };
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
      this.ids = selection.map(item => item.lineId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加设备点检记录行";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const lineId = row.lineId || this.ids
      getCheckrecordline(lineId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改设备点检记录行";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.lineId != null) {            
            updateCheckrecordline(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCheckrecordline(this.form).then(response => {
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
      const lineIds = row.lineId || this.ids;
      this.$modal.confirm('是否确认删除设备点检记录行编号为"' + lineIds + '"的数据项？').then(function() {
        return delCheckrecordline(lineIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    handleSelectSubject(){
      this.$refs.subjectSelect.showFlag = true;
    },
    onSubjectSelected(obj){
      if(obj){
        this.form.subjectId = obj.subjectId;
        this.form.subjectCode = obj.subjectCode;
        this.form.subjectName = obj.subjectName;
        this.form.subjectType = obj.subjectType;
        this.form.subjectContent = obj.subjectContent;
        this.form.subjectStandard = obj.subjectStandard;
      }
    }
  }
};
</script>
