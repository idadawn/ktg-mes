<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="工作站编号" prop="workstationCode">
        <el-input
          v-model="queryParams.workstationCode"
          placeholder="请输入工作站编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工作站名称" prop="workstationName">
        <el-input
          v-model="queryParams.workstationName"
          placeholder="请输入工作站名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发起人" prop="nickName">
        <el-input
          v-model="queryParams.nickName"
          placeholder="请输入发起人名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="生产工单编号" prop="workorderCode">
        <el-input
          v-model="queryParams.workorderCode"
          placeholder="请输入生产工单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工序名称" prop="processName">
        <el-input
          v-model="queryParams.processName"
          placeholder="请输入工序名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="处置人名称" prop="handlerNickName">
        <el-input
          v-model="queryParams.handlerNickName"
          placeholder="请输入处置人名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="处置状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择处置状态" clearable>
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        <el-button
          type="success"
          icon="el-icon-s-tools"
          size="mini"
          @click="handleConfig"
          v-hasPermi="['mes:pro:andonconfig:list']"
        >安灯设置</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="andonrecordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="工作站编号" align="center" prop="workstationCode" />
      <el-table-column label="工作站名称" align="center" prop="workstationName" />
      <el-table-column label="工单编号" align="center" prop="workorderCode" />
      <el-table-column label="工序" align="center" prop="processName" />
      <el-table-column label="发起人" align="center" prop="nickName" />
      <el-table-column label="发起时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="呼叫原因" align="center" prop="andonReason" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <el-button
            type="text"
            @click="handleView(scope.row)"
            v-hasPermi="['mes:pro:andonrecord:query']"
          >{{scope.row.andonReason}}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="级别" align="center" prop="andonLevel" />
      <el-table-column label="处置时间" align="center" prop="handleTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.handleTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="处置人" align="center" prop="handlerNickName" />
      <el-table-column label="处置状态" align="center" prop="status" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_andon_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="scope.row.status === 'ACTIVE'"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mes:pro:andonrecord:edit']"
          >处置</el-button>
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

    <!-- 添加或修改安灯呼叫记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="960px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="工作站编号" prop="workstationCode">
              <el-input v-model="form.workstationCode" readonly="readonly" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="工作站名称" prop="workstationName">
              <el-input v-model="form.workstationName" readonly="readonly" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="工序名称" prop="processName">
              <el-input v-model="form.processName" readonly="readonly" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="生产工单编号" prop="workorderCode">
              <el-input v-model="form.workorderCode" readonly="readonly" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="发起时间" prop="createTime">
              <el-date-picker clearable
                v-model="form.createTime"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="请选择发起时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="发起人" prop="nickName">
              <el-input v-model="form.nickName" readonly="readonly" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="呼叫原因" prop="andonReason">
              <el-input v-model="form.andonReason" type="textarea" readonly="readonly" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="级别" prop="andonLevel">
              <el-input v-model="form.andonLevel" placeholder="请输入级别" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="处置时间" prop="handleTime">
              <el-date-picker clearable
                v-model="form.handleTime"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="请选择处置时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="处置人" prop="handlerNickName">
              <el-input v-model="form.handlerNickName" placeholder="请选择处置人" >
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
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" v-if="optType!='view' && form.status == 'ACTIVE'" @click="saveForm('ACTIVE')">保 存</el-button>
        <el-button type="success" v-if="optType!='view' && form.status == 'ACTIVE'" @click="saveForm('HANDLED')">已处置</el-button>
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
    <AndonConfig ref="andonConfig" v-if="checkPermission(['mes:pro:andonconfig:list'])" ></AndonConfig>
  </div>
</template>

<script>
import { listAndonrecord, getAndonrecord, delAndonrecord, addAndonrecord, updateAndonrecord } from "@/api/mes/pro/andonrecord";
import UserSingleSelect from "@/components/userSelect/single.vue"
import AndonConfig from "./config.vue"
export default {
  name: "Andonrecord",
  components: {UserSingleSelect,AndonConfig},
  dicts: ['mes_andon_status'],
  data() {
    return {
      optType: null,
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
      // 安灯呼叫记录表格数据
      andonrecordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        workstationId: null,        workstationCode: null,        workstationName: null,        userId: null,        userName: null,        nickName: null,        workorderId: null,        workorderCode: null,        workorderName: null,        processId: null,        processCode: null,        processName: null,        andonReason: null,        andonLevel: null,        handleTime: null,        handlerUserId: null,        handlerUserName: null,        handlerNickName: null,        status: null,        attr1: null,        attr2: null,        attr3: null,        attr4: null,      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        workstationId: [
          { required: true, message: "工作站ID不能为空", trigger: "blur" }
        ],        userId: [
          { required: true, message: "用户ID不能为空", trigger: "blur" }
        ],        andonReason: [
          { required: true, message: "呼叫原因不能为空", trigger: "blur" }
        ],      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询安灯呼叫记录列表 */
    getList() {
      this.loading = true;
      listAndonrecord(this.queryParams).then(response => {
        this.andonrecordList = response.rows;
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
        recordId: null,        workstationId: null,        workstationCode: null,        workstationName: null,        userId: null,        userName: null,        nickName: null,        workorderId: null,        workorderCode: null,        workorderName: null,        processId: null,        processCode: null,        processName: null,        andonReason: null,        andonLevel: null,        handleTime: this.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"),        handlerUserId: null,        handlerUserName: null,        handlerNickName: null,        status: "0",        remark: null,        attr1: null,        attr2: null,        attr3: null,        attr4: null,        createBy: null,        createTime: this.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"),        updateBy: null,        updateTime: null      };
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
      this.ids = selection.map(item => item.recordId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    handleView(row) {
      this.reset();
      const recordId = row.recordId || this.ids
      getAndonrecord(recordId).then(response => {
        this.form = response.data;
        this.open = true;
        optType = 'view';
        this.title = "查看安灯呼叫记录";
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const recordId = row.recordId || this.ids
      getAndonrecord(recordId).then(response => {
        this.form = response.data;
        this.open = true;
        optType = 'edit';
        this.title = "修改安灯呼叫记录";
      });
    },
    handleConfig() {
      this.$refs.andonConfig.handleOpen();
    },
    /** 提交按钮 */
    saveForm(status) {
      this.form.status = status;
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateAndonrecord(this.form).then(response => {
            this.$modal.msgSuccess("保存成功");
            this.open = false;
            this.getList();
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const recordIds = row.recordId || this.ids;
      this.$modal.confirm('是否确认删除安灯呼叫记录编号为"' + recordIds + '"的数据项？').then(function() {
        return delAndonrecord(recordIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    //点击人员选择按钮
    handleUserSelect(){
      this.$refs.userSelect.showFlag = true;
    },
    //人员选择返回
    onUserSelected(row){
        this.form.handlerUserId = row.userId;
        this.form.handlerUserName = row.userName;
        this.form.handlerNickName = row.nickName;
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('pro/andonrecord/export', {
        ...this.queryParams
      }, `andonrecord_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
