<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="检验单编号" prop="rqcCode">
        <el-input
          v-model="queryParams.rqcCode"
          placeholder="请输入检验单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
     
      <el-form-item label="来源单据类型" prop="sourceDocType">
        <el-select v-model="queryParams.sourceDocType" placeholder="请选择来源单据类型" clearable>
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
      <el-form-item label="产品物料编码" prop="itemCode">
        <el-input
          v-model="queryParams.itemCode"
          placeholder="请输入产品物料编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产品物料名称" prop="itemName">
        <el-input
          v-model="queryParams.itemName"
          placeholder="请输入产品物料名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="批次号" prop="batchCode">
        <el-input
          v-model="queryParams.batchCode"
          placeholder="请输入批次号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="检测结果" prop="checkResult">
        <el-input
          v-model="queryParams.checkResult"
          placeholder="请输入检测结果"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="检测人员" prop="nickName">
        <el-input
          v-model="queryParams.nickName"
          placeholder="请输入检测人员"
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
          v-hasPermi="['qc:rqc:add']"
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
          v-hasPermi="['qc:rqc:edit']"
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
          v-hasPermi="['qc:rqc:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="rqcList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="检验单编号" width="150px" align="center" prop="rqcCode" >
        <template slot-scope="scope">
          <el-button
            type="text"
            @click="handleView(scope.row)"
            v-hasPermi="['mes:qc:rqc:query']"
          >{{scope.row.rqcCode}}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="检验单名称" align="center" prop="rqcName" />
      <el-table-column label="来源单据类型" align="center" prop="sourceDocType" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_source_doc_type" :value="scope.row.sourceDocType"/>
        </template>
      </el-table-column>
      <el-table-column label="来源单据编号" align="center" prop="sourceDocCode" />
      <el-table-column label="物资编码" align="center" prop="itemCode" />
      <el-table-column label="物资名称" align="center" prop="itemName" />
      <el-table-column label="规格型号" align="center" prop="specification" />
      <el-table-column label="单位" align="center" prop="unitName" />
      <el-table-column label="批次号" align="center" prop="batchCode" />
      <el-table-column label="检测结论" align="center" prop="checkResult" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_qc_result" :value="scope.row.checkResult"/>
        </template>
      </el-table-column>
      <el-table-column label="检测日期" align="center" prop="inspectDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.inspectDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="检测人员" align="center" prop="nickName" />
      <el-table-column label="单据状态" align="center" prop="status" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_rt_issue_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['qc:rqc:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['qc:rqc:remove']"
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

    <!-- 添加或修改退料检验单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="960px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="检验单编号" prop="rqcCode">
              <el-input v-model="form.rqcCode" placeholder="请输入检验单编号" />
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
            <el-form-item label="检验单名称" prop="rqcName">
              <el-input v-model="form.rqcName" placeholder="请输入检验单名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="检验类型" prop="rqcType">
              <el-select v-model="form.rqcType" placeholder="请选择检验类型">
                <el-option
                  v-for="dict in dict.type.mes_rqc_type"
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
              <el-input v-model="form.sourceDocCode" readonly="readonly" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="物资编码" prop="itemCode">
              <el-input v-if="form.rqcId ==null " v-model="form.itemCode" readonly placeholder="请选择物资" >
                <el-button slot="append" @click="handleSelectProduct" icon="el-icon-search"></el-button>
              </el-input>
              <!--如果已经保存过，则产品不允许再修改，需要修改就删除重做-->
              <el-input v-else v-model="form.itemCode">
              </el-input>
            </el-form-item>
            <ItemSelect ref="itemSelect" @onSelected="onItemSelected" > </ItemSelect>
          </el-col>
          <el-col :span="8">
            <el-form-item label="物资名称" prop="itemName">
              <el-input v-model="form.itemName" placeholder="请选择物资" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="单位" prop="unitName">
              <el-input v-model="form.unitName" placeholder="请选择物资" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="规格型号" prop="specification">
              <el-input v-model="form.specification" type="textarea" placeholder="请选择物资" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="批次号" prop="batchCode">
              <el-input v-model="form.batchCode" placeholder="请输入批次号" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="检测结论" prop="checkResult">
              <el-select v-model="form.checkResult" placeholder="请选择检测结论">
                <el-option
                  v-for="dict in dict.type.mes_qc_result"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="检测日期" prop="inspectDate">
              <el-date-picker clearable
                v-model="form.inspectDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择检测日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="检测数量" prop="quantityCheck">
              <el-input-number :min="0" :max="99999999" v-model="form.quantityCheck" placeholder="检测数量" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="合格品数量" prop="quantityQualified">
              <el-input-number :min="0" :max="99999999" v-model="form.quantityQualified" placeholder="请输入合格品数量" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="不合格数量" prop="quantityUnqualified">
              <el-input-number :min="0" :max="99999999" v-model="form.quantityUnqualified" placeholder="请输入不合格数" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="审核人" prop="nickName">
              <el-input v-model="form.nickName" placeholder="请选择审核人" >
                <el-button slot="append" @click="handleUser2Select" icon="el-icon-search"></el-button>
              </el-input>
            </el-form-item>
            <UserSingleSelect ref="user2Select" @onSelected="onUser2Selected"></UserSingleSelect>
          </el-col>
          <el-col :span="8"></el-col>
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
      <el-tabs type="border-card" v-if="form.rqcId != null">
        <el-tab-pane label="检测项">
          <RqcLine ref=line :rqcId="form.rqcId" :optType="optType"></RqcLine>
        </el-tab-pane>
        <el-tab-pane label="检测结果">
          <QCResutl ref="qcResult" :qcId="form.rqcId" :qcType="'RQC'" :optType="optType"></QCResutl>
        </el-tab-pane>
      </el-tabs>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveForm" v-if="form.status =='PREPARE' && optType !='view' ">保 存</el-button>
        <el-button type="warning" @click="submitForm" v-if="form.status =='PREPARE' && form.rqcId !=null && optType !='view' ">提 交</el-button>
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listRqc, getRqc, delRqc, addRqc, updateRqc } from "@/api/mes/qc/rqc";
import ItemSelect  from "@/components/itemSelect/single.vue";
import UserSingleSelect from "@/components/userSelect/single.vue"
import QCResutl from "../qcresult/index.vue";
import RqcLine from "./line.vue"
import {genCode} from "@/api/system/autocode/rule";
export default {
  name: "Rqc",
  components: {ItemSelect,UserSingleSelect,RqcLine,QCResutl},
  dicts: ['mes_rqc_type','mes_qc_result','mes_source_doc_type','mes_rt_issue_status'],
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
      // 退料检验单表格数据
      rqcList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        rqcCode: null,        rqcName: null,        templateId: null,  rqcType: null,      sourceDocId: null,        sourceDocType: null,        sourceDocCode: null,        sourceLineId: null,    itemId: null,        itemCode: null,        itemName: null,        specification: null,        unitOfMeasure: null,        unitName: null,        batchId: null,        batchCode: null,        quantityCheck: null,        quantityUnqualified: null,        quantityQualified: null,        checkResult: null,        inspectDate: null,        userId: null,        userName: null,        nickName: null,        status: null,      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        rqcCode: [
          { required: true, message: "检验单编号不能为空", trigger: "blur" }
        ],        rqcType: [
          { required: true, message: "请选择退料检测类型", trigger: "blur" }
        ],        checkResult: [
          { required: true, message: "请选择检测结果", trigger: "blur" }
        ],        inspectDate: [
          { required: true, message: "检测日期不能为空", trigger: "blur" }
        ],        nickName: [
          { required: true, message: "请选择检测人员", trigger: "blur" }
        ],        quantityCheck: [
          { required: true, message: "请填写检测数量", trigger: "blur" }
        ],      quantityQualified: [
          { required: true, message: "请填写合格品数量", trigger: "blur" }
        ],    quantityUnqualified: [
          { required: true, message: "请填写不良品数量", trigger: "blur" }
        ],                
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询退料检验单列表 */
    getList() {
      this.loading = true;
      listRqc(this.queryParams).then(response => {
        this.rqcList = response.rows;
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
        rqcId: null,        rqcCode: null,        rqcName: null,        templateId: null,        sourceDocId: null,        sourceDocType: null,        sourceDocCode: null,        sourceLineId: null,     
        itemId: null,        itemCode: null,        itemName: null,        specification: null,        unitOfMeasure: null,        unitName: null,        batchId: null,        batchCode: null,       
         quantityCheck: null,        quantityUnqualified: 0,        quantityQualified: 0,        checkResult: null,        inspectDate: new Date(),        userId: null,        userName: null,        nickName: null,        status: "PREPARE",        remark: null,        attr1: null,        attr2: null,        attr3: null,        attr4: null,        createBy: null,        createTime: null,        updateBy: null,        updateTime: null      };
      this.resetForm("form");
      this.autoGenFlag = false;
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
      this.ids = selection.map(item => item.rqcId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 查看操作 */
    handleView(row) {
      this.reset();
      const rqcId = row.rqcId || this.ids
      getRqc(rqcId).then(response => {
        this.form = response.data;
        this.open = true;
        this.optType = "view";
        this.title = "查看退料检验单";
      });
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.optType = "add";
      this.title = "添加退料检验单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const rqcId = row.rqcId || this.ids
      getRqc(rqcId).then(response => {
        this.form = response.data;
        this.open = true;
        this.optType = "edit";
        this.title = "修改退料检验单";
      });
    },
    /** 保存按钮 */
    saveForm() {
      // 判断接收总数与合格不合格数之间的校验
      if (this.form.quantityQualified != '' && this.form.quantityQualified != undefined && this.form.quantityQualified != 0
        && this.form.quantityUnqualified != '' && this.form.quantityUnqualified != undefined && this.form.quantityUnqualified != 0) {
        if ((this.form.quantityQualified + this.form.quantityUnqualified) != this.form.quantityCheck) {
          this.$message.warning("合格数与不合格数之和必须等于检测总数！");
          return
        }
      }
      if (this.form.quantityQualified != '' && this.form.quantityQualified != undefined) {
        if (this.form.quantityQualified > this.form.quantityCheck) {
          this.$message.warning("合格数不能大于检测总数！");
          return
        }
      }
      if (this.form.quantityUnqualified != '' && this.form.quantityUnqualified != undefined) {
        if (this.form.quantityUnqualified > this.form.quantityCheck) {
          this.$message.warning("不合格数不能大于检测总数！");
          return
        }
      }

      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.rqcId != null) {
            updateRqc(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRqc(this.form).then(response => {
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
      const rqcIds = row.rqcId || this.ids;
      this.$modal.confirm('是否确认删除退料检验单编号为"' + rqcIds + '"的数据项？').then(function() {
        return delRqc(rqcIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('qc/rqc/export', {
        ...this.queryParams
      }, `rqc_${new Date().getTime()}.xlsx`)
    },
    handleSelectProduct(){
      this.$refs.itemSelect.showFlag = true;
    },
    //物料选择弹出框
    onItemSelected(obj){        
        if(obj != undefined && obj != null){
          this.form.itemId = obj.itemId;
          this.form.itemCode = obj.itemCode;
          this.form.itemName = obj.itemName;
          this.form.specification = obj.specification;
          this.form.unitOfMeasure = obj.unitOfMeasure;
          this.form.unitName = obj.unitName
        }
    },
    //点击人员选择按钮
    handleUser2Select(){
        this.$refs.user2Select.showFlag = true;
    },
    //人员选择返回
    onUser2Selected(row){
        this.form.userId = row.userId;
        this.form.userName = row.userName;
        this.form.nickName = row.nickName;                
    },
    //自动生成编码
    handleAutoGenChange(autoGenFlag){
      if(autoGenFlag){
        genCode('QC_RQC_CODE').then(response =>{
          this.form.rqcCode = response;
        });
      }else{
        this.form.rqcCode = null;
      }
    }
  }
};
</script>
