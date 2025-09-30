<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="入库单编号" prop="recptCode">
        <el-input
          v-model="queryParams.recptCode"
          placeholder="请输入杂项入库单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="入库单名称" prop="recptName">
        <el-input
          v-model="queryParams.recptName"
          placeholder="请输入杂项入库单名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="事务类型" prop="miscType">
        <el-select v-model="queryParams.miscType" placeholder="请选择杂项事务类型" clearable>
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>

      <el-form-item label="来源单据编号" prop="sourceDocCode">
        <el-input
          v-model="queryParams.sourceDocCode"
          placeholder="请输入来源单据编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="来源单据类型" prop="sourceDocType">
        <el-select v-model="queryParams.sourceDocType" placeholder="请选择来源单据类型" clearable>
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="入库日期" prop="recptDate">
        <el-date-picker clearable
          v-model="queryParams.recptDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择入库日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="单据状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择单据状态" clearable>
          <el-option
            v-for="dict in dict.type.mes_order_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
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
          v-hasPermi="['mes:wm:miscrecpt:add']"
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
          v-hasPermi="['mes:wm:miscrecpt:edit']"
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
          v-hasPermi="['mes:wm:miscrecpt:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="miscrecptList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="入库单编号" align="center" prop="recptCode" >
        <template slot-scope="scope">
          <el-button
            type="text"
            @click="handleView(scope.row)"
            v-hasPermi="['mes:wm:miscrecpt:query']"
          >{{scope.row.recptCode}}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="入库单名称" align="center" prop="recptName" />
      <el-table-column label="业务类型" align="center" prop="miscType" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_misc_recpt_type" :value="scope.row.miscType"/>
        </template>
      </el-table-column>
      <el-table-column label="来源单据编号" align="center" prop="sourceDocCode" />
      <el-table-column label="来源单据类型" align="center" prop="sourceDocType" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_source_doc_type" :value="scope.row.sourceDocType"/>
        </template>
      </el-table-column>
      <el-table-column label="入库日期" align="center" prop="recptDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.recptDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="单据状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_misc_recpt_status" :value="scope.row.status"/>
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
            v-hasPermi="['mes:wm:miscrecpt:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            v-if="scope.row.status =='PREPARE'"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mes:wm:miscrecpt:remove']"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-video-play"
            v-if="scope.row.status =='APPROVED'"
            @click="handleExecute(scope.row)"
            v-hasPermi="['mes:wm:miscrecpt:edit']"
          >执行入库</el-button>
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

    <!-- 添加或修改杂项入库单对话框 -->
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
            <el-form-item label="业务类型" prop="miscType">
              <el-select v-model="form.miscType" placeholder="请选择业务类型">
                  <el-option
                      v-for="dict in dict.type.mes_misc_recpt_type"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8"> 
            <el-form-item label="来源单据类型" prop="sourceDocType">
              <el-select v-model="form.sourceDocType" disabled>
                    <el-option
                      v-for="dict in dict.type.mes_source_doc_type"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="来源单据编号" prop="sourceDocCode">
              <el-input v-model="form.sourceDocCode" disabled />
            </el-form-item>  
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="入库日期" prop="recptDate">
              <el-date-picker clearable
                v-model="form.recptDate"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="请选择入库日期">
              </el-date-picker>
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
        <el-card shadow="always" v-if="form.recptId !=null" class="box-card">
          <MiscRecptLine ref=line :recptId="form.recptId" :optType="optType"></MiscRecptLine>
        </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" v-if="form.status =='PREPARE' && optType != 'view'" @click="saveForm">保 存</el-button>
        <el-button type="success" v-if="form.status =='PREPARE' && form.recptId !=null && optType != 'view'" @click="submitForm">提 交</el-button>
        <el-button type="danger" v-if="form.status =='APPROVED'" @click="cancel">取 消</el-button>
        <el-button @click="close">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listMiscrecpt, getMiscrecpt, delMiscrecpt, addMiscrecpt, updateMiscrecpt, execute } from "@/api/mes/wm/miscrecpt";
import {genCode} from "@/api/system/autocode/rule"
import MiscRecptLine from "./line.vue";
export default {
  name: "Miscrecpt",
  dicts: ['mes_misc_recpt_status','mes_misc_recpt_type','mes_source_doc_type'],
  components: {
    MiscRecptLine
  },
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
      // 杂项入库单表格数据
      miscrecptList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        recptCode: null,        recptName: null,        miscType: null,        sourceDocId: null,        sourceDocCode: null,        sourceDocType: null,        recptDate: null,        status: null,      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        recptCode: [
          { required: true, message: "杂项入库单编号不能为空", trigger: "blur" }
        ],        recptName: [
          { required: true, message: "杂项入库单名称不能为空", trigger: "blur" }
        ],      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询杂项入库单列表 */
    getList() {
      this.loading = true;
      listMiscrecpt(this.queryParams).then(response => {
        this.miscrecptList = response.rows;
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
        recptId: null,        recptCode: null,        recptName: null,        miscType: null,        sourceDocId: null,        sourceDocCode: null,        sourceDocType: null,        recptDate: new Date(),        status: 'PREPARE',        remark: null,        attr1: null,        attr2: null,        attr3: null,        attr4: null,        createBy: null,        createTime: null,        updateBy: null,        updateTime: null      };
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
      this.title = "添加杂项入库单";
      this.optType = "add";
    },
    handleView(row) {
      this.reset();
      const recptId = row.recptId || this.ids
      getMiscrecpt(recptId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "查看杂项入库单";
        this.optType = "view";
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const recptId = row.recptId || this.ids
      getMiscrecpt(recptId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改杂项入库单";
        this.optType = "edit";
      });
    },
    /** 保存按钮 */
    saveForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.recptId != null) {
            updateMiscrecpt(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.getList();
            });
          } else {
            addMiscrecpt(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.form.recptId = response.data.recptId;
              this.getList();
            });
          }
        }
      });
    },
    submitForm(){
      this.form.status = 'APPROVED';
      updateMiscrecpt(this.form).then(response => {
        this.$modal.msgSuccess("修改成功");
        this.open = false;
        this.getList();
      },err => {
        this.form.status = 'PREPARE';
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
    cancel(){
      let that = this;
      this.$modal.confirm('确认撤销杂项入库单？').then(function() {        
        const oldStatus = that.form.status;
        that.form.status = 'CANCELED';
          updateMiscrecpt(that.form).then(response => {
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
      this.$modal.confirm('是否确认删除杂项入库单编号为"' + recptIds + '"的数据项？').then(function() {
        return delMiscrecpt(recptIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wm/miscrecpt/export', {
        ...this.queryParams
      }, `miscrecpt_${new Date().getTime()}.xlsx`)
    },
    //自动生成编码
    handleAutoGenChange(autoGenFlag){
      if(autoGenFlag){
        genCode('MISC_RECPT_CODE').then(response =>{
          this.form.recptCode = response;
        });
      }else{
        this.form.recptCode = null;
      }
    }
  }
};
</script>
