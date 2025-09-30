<template>
  <div class="app-container">
    <el-row v-if="optType !='view'" :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['mes:wm:oursourcerecpt:add']"
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
          v-hasPermi="['mes:wm:oursourcerecpt:edit']"
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
          v-hasPermi="['mes:wm:oursourcerecpt:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="outsourcerecptlineList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="产品物料编码" width="120" align="center" prop="itemCode" />
      <el-table-column label="产品物料名称" width="150" align="center" prop="itemName" />
      <el-table-column label="规格型号" align="center" prop="specification" />
      <el-table-column label="单位" align="center" prop="unitName" />
      <el-table-column label="入库数量" align="center" prop="quantityRecived" />
      <el-table-column label="批次号" width="150" align="center" prop="batchCode" />
      <el-table-column label="是否检验" align="center" prop="iqcCheck">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.iqcCheck"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="100" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="optType != 'view'"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mes:wm:oursourcerecpt:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            v-if="optType != 'view'"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mes:wm:oursourcerecpt:remove']"
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

    <!-- 添加或修改外协入库单行对话框 -->
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
          <el-col :span="8">
            <el-form-item label="生产日期期" prop="produceDate">
              <el-date-picker clearable
                v-model="form.produceDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择生产日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="有效期" prop="expireDate">
              <el-date-picker clearable
                v-model="form.expireDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择有效期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="生产批号" prop="lotNumber">
              <el-input v-model="form.lotNumber" placeholder="请输入生产批号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="入库数量" prop="quantityRecived">
              <el-input-number :min="0" :max="99999999" v-model="form.quantityRecived" placeholder="请输入入库数量" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="批次号" prop="batchCode">
              <el-input v-model="form.batchCode" readonly="readonly" placeholder="系统自动生成" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否检验">
              <el-radio-group v-model="form.iqcCheck" disabled v-if="optType=='view'">
                <el-radio
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>

              <el-radio-group v-model="form.iqcCheck" v-else>
                <el-radio
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
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
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listOutsourcerecptline, getOutsourcerecptline, delOutsourcerecptline, addOutsourcerecptline, updateOutsourcerecptline } from "@/api/mes/wm/outsourcerecptline";
import ItemSelect  from "@/components/itemSelect/single.vue";
import IqcSelect from "@/components/iqcSelect/single.vue";
import {getTreeList} from "@/api/mes/wm/warehouse"
export default {
  name: "Outsourcerecptline",
  dicts: ['sys_yes_no','mes_quality_status'],
  components :{ItemSelect,IqcSelect},
  props:{
    recptId: null,
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
      // 外协入库单行表格数据
      outsourcerecptlineList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        recptId: this.recptId,
        itemId: null,
        itemCode: null,
        itemName: null,
        specification: null,
        unitOfMeasure: null,
        quantityRecived: null,
        batchCode: null,
        expireDate: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        itemCode: [
          { required: true, message: "产品物料不能为空", trigger: "blur" }
        ],
        quantityRecived: [
          { required: true, message: "入库数量不能为空", trigger: "blur" }
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
    /** 查询外协入库单行列表 */
    getList() {
      this.loading = true;
      listOutsourcerecptline(this.queryParams).then(response => {
        this.outsourcerecptlineList = response.rows;
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
        lineId: null,
        recptId: this.recptId,
        itemId: null,
        itemCode: null,
        itemName: null,
        specification: null,
        unitOfMeasure: null,
        unitName: null,
        quantityRecived: null,
        batchId: null,
        batchCode: null,
        expireDate: null,
        produceDate: null,
        lotNumber: null,
        iqcCheck: 'Y',
        iqcId: null,
        iqcCode: null,
        remark: null,
        attr1: null,        attr2: null,        attr3: null,        attr4: null,        createBy: null,        createTime: null,        updateBy: null,        updateTime: null      };


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
      this.title = "添加外协入库单行";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const lineId = row.lineId || this.ids
      getOutsourcerecptline(lineId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改外协入库单行";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.lineId != null) {
            updateOutsourcerecptline(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOutsourcerecptline(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除外协入库单行编号为"' + lineIds + '"的数据项？').then(function() {
        return delOutsourcerecptline(lineIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    handleSelectProduct(){
      this.$refs.itemSelect.showFlag = true;
    },
    //物料选择弹出框
    onItemSelected(obj){
        debugger;
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
