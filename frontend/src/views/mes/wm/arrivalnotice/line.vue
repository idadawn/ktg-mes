<template>
  <div class="app-container">
    <el-row :gutter="10" v-if="optType!='view'" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['mes:wm:arrivalnotice:add']"
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
          v-hasPermi="['mes:wm:arrivalnotice:edit']"
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
          v-hasPermi="['mes:wm:arrivalnotice:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="arrivalnoticelineList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="物料编码" width="120px" align="center" prop="itemCode" />
      <el-table-column label="物料名称" width="150px" align="center" prop="itemName" :show-overflow-tooltip="true"/>
      <el-table-column label="规格型号" align="center" prop="specification" :show-overflow-tooltip="true"/>
      <el-table-column label="单位" align="center" prop="unitName" />
      <el-table-column label="到货数量" align="center" prop="quantityArrival" />
      <el-table-column label="是否检验" align="center" prop="iqcCheck">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.iqcCheck"/>
        </template>
      </el-table-column>
      <el-table-column label="合格数量" align="center" prop="quantityQuanlified" />
      <el-table-column label="检验单号" align="center" prop="iqcCode" />
      <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true"/>
      <el-table-column label="操作" width="100px" v-if="optType !='view'" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mes:wm:arrivalnotice:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mes:wm:arrivalnotice:remove']"
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

    <!-- 添加或修改到货通知单行对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="960px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="物料编码"  prop="itemCode">
              <el-input v-model="form.itemCode" readonly="readonly" placeholder="请选择物料编码" >
                <el-button slot="append" @click="handleSelectProduct" icon="el-icon-search"></el-button>
              </el-input>
            </el-form-item>
            <ItemSelect ref="itemSelect" @onSelected="onItemSelected" > </ItemSelect>
          </el-col>
          <el-col :span="8">
            <el-form-item label="物料名称" prop="itemName">
              <el-input v-model="form.itemName" readonly="readonly" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="单位" prop="unitName">
              <el-input v-model="form.unitName" readonly="readonly" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="16">
            <el-form-item label="规格型号" prop="specification">
              <el-input v-model="form.specification" readonly type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="到货数量" prop="quantityArrival">
              <el-input-number v-model="form.quantityArrival" :max="99999999" placeholder="请输入到货数量" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="是否检验" prop="iqcCheck">
              <el-radio-group v-model="form.iqcCheck">
                <el-radio
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item v-if="form.iqcCheck == 'Y'" label="来料检验单" prop="iqcCode">
              <el-input v-model="form.iqcCode" disabled  >
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item v-if="form.iqcCheck == 'Y'" label="合格数量" prop="quantityQuanlified">
              <el-input v-model="form.quantityQuanlified" disabled />
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
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" v-if="optType !='view'" @click="submitForm">保 存</el-button>
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listArrivalnoticeline, getArrivalnoticeline, delArrivalnoticeline, addArrivalnoticeline, updateArrivalnoticeline } from "@/api/mes/wm/arrivalnoticeline";
import ItemSelect  from "@/components/itemSelect/single.vue";
import IqcSelect from "@/components/iqcSelect/single.vue";
export default {
  name: "Arrivalnoticeline",
  dicts: ['sys_yes_no'],
  components :{ItemSelect,IqcSelect},
  props:{
    noticeId: null,
    optType: null
  },
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
      // 到货通知单行表格数据
      arrivalnoticelineList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        noticeId: this.noticeId,        itemId: null,        itemCode: null,        itemName: null,        specification: null,        unitOfMeasure: null,        quantityArrival: null,        quantityQuanlified: null,        iqcCheck: null,        iqcId: null,        iqcCode: null,      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        itemCode: [
          { required: true, message: "产品物料编码不能为空", trigger: "blur" }
        ],
        quantityArrival: [
          { required: true, message: "到货数量不能为空", trigger: "blur" }
        ],
        remark: [
          { max: 250, message: "字段过长", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询到货通知单行列表 */
    getList() {
      this.loading = true;
      listArrivalnoticeline(this.queryParams).then(response => {
        this.arrivalnoticelineList = response.rows;
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
        lineId: null,        noticeId: this.noticeId,        itemId: null,        itemCode: null,        itemName: null,        specification: null,        unitOfMeasure: null,        quantityArrival: null,        quantityQuanlified: null,        iqcCheck: 'N',        iqcId: null,        iqcCode: null,        remark: null,        attr1: null,        attr2: null,        attr3: null,        attr4: null,        createBy: null,        createTime: null,        updateBy: null,        updateTime: null      };
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
      this.title = "添加到货通知单行";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const lineId = row.lineId || this.ids
      getArrivalnoticeline(lineId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改到货通知单行";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.lineId != null) {
            updateArrivalnoticeline(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addArrivalnoticeline(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除到货通知单行编号为"' + lineIds + '"的数据项？').then(function() {
        return delArrivalnoticeline(lineIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wm/arrivalnoticeline/export', {
        ...this.queryParams
      }, `arrivalnoticeline_${new Date().getTime()}.xlsx`)
    },
    handleSelectProduct(){
      this.$refs.itemSelect.handleOpen(this.form.itemId)
    },
    //物料选择弹出框
    onItemSelected(obj){
      console.log(obj, '-----obj=========')
        if(obj != undefined && obj != null){
          this.form.itemId = obj.itemId;
          this.form.itemCode = obj.itemCode;
          this.form.itemName = obj.itemName;
          this.form.specification = obj.specification;
          this.form.unitOfMeasure = obj.unitOfMeasure;
          this.form.unitName = obj.unitName
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
        }
    },
  }
};
</script>
