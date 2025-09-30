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
          v-hasPermi="['mes:wm:salesnotice:add']"
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
          v-hasPermi="['mes:wm:salesnotice:edit']"
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
          v-hasPermi="['mes:wm:salesnotice:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="salesnoticelineList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="物资编码" width="150px" align="center" prop="itemCode" />
      <el-table-column label="物资名称" width="200px" align="center" prop="itemName" :show-overflow-tooltip="true"/>
      <el-table-column label="规格型号" align="center" prop="specification" :show-overflow-tooltip="true"/>      
      <el-table-column label="单位名称" align="center" prop="unitName" />      
      <el-table-column label="批次号" align="center" prop="batchCode" />
      <el-table-column label="发货数量" align="center" prop="quantitySales" />
      <el-table-column label="是否检验" align="center" prop="oqcCheck">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.oqcCheck"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true" />
      <el-table-column label="操作" v-if="optType !== 'view'" width="120px" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mes:wm:salesnotice:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mes:wm:salesnotice:remove']"
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

    <!-- 添加或修改发货通知单行对话框 -->
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
          <el-col :span="24">
            <el-form-item label="规格型号" prop="specification">
              <el-input v-model="form.specification" readonly type="textarea" placeholder="请输入内容" />
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
            <el-form-item label="发货数量" prop="quantitySales">
              <el-input-number v-model="form.quantitySales" :max="99999999" placeholder="请输入发货数量" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否检验" prop="oqcCheck">
              <el-radio-group v-model="form.oqcCheck">
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
        <el-button type="primary" v-if="optType !== 'view'" @click="submitForm">保 存</el-button>
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listSalesnoticeline, getSalesnoticeline, delSalesnoticeline, addSalesnoticeline, updateSalesnoticeline } from "@/api/mes/wm/salesnoticeline";
import ItemSelect  from "@/components/itemSelect/single.vue";
export default {
  name: "Salesnoticeline",
  components: {
    ItemSelect
  },
  dicts: ['sys_yes_no'],
  props:{
    noticeId: null,
    optType: null,
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
      // 发货通知单行表格数据
      salesnoticelineList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        noticeId: this.noticeId,        itemId: null,        itemCode: null,        itemName: null,        specification: null,        unitOfMeasure: null,        unitName: null,        batchId: null,        batchCode: null,        quantitySales: null,        oqcCheck: null,      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        itemId: [
          { required: true, message: "产品物料ID不能为空", trigger: "blur" }
        ],        quantitySales: [
          { required: true, message: "发货数量不能为空", trigger: "blur" }
        ],      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询发货通知单行列表 */
    getList() {
      this.loading = true;
      listSalesnoticeline(this.queryParams).then(response => {
        this.salesnoticelineList = response.rows;
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
        lineId: null,        noticeId: this.noticeId,        itemId: null,        itemCode: null,        itemName: null,        specification: null,        unitOfMeasure: null,        unitName: null,        batchId: null,        batchCode: null,        quantitySales: 0,        oqcCheck: 'Y',        remark: null,        attr1: null,        attr2: null,        attr3: null,        attr4: null,        createBy: null,        createTime: null,        updateBy: null,        updateTime: null      };
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
      this.title = "添加发货通知单行";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const lineId = row.lineId || this.ids
      getSalesnoticeline(lineId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改发货通知单行";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.lineId != null) {
            updateSalesnoticeline(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSalesnoticeline(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除发货通知单行编号为"' + lineIds + '"的数据项？').then(function() {
        return delSalesnoticeline(lineIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wm/salesnoticeline/export', {
        ...this.queryParams
      }, `salesnoticeline_${new Date().getTime()}.xlsx`)
    },
    handleSelectProduct(){
      this.$refs.itemSelect.handleOpen(this.form.itemId)
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
  }
};
</script>
