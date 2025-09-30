<template>
  <div class="app-container">
    <el-table v-loading="loading" :data="productproducelineList" @selection-change="handleSelectionChange">      
      <el-table-column label="物资编码" align="center" prop="itemCode" />
      <el-table-column label="物资名称" align="center" prop="itemName" />
      <el-table-column label="规格型号" align="center" prop="specification" />      
      <el-table-column label="单位" align="center" prop="unitName" />
      <el-table-column label="产出数量" align="center" prop="quantityProduce" />
      <el-table-column label="批次号" align="center" prop="batchCode" />
      <el-table-column label="质量状态" width="100px" align="center" prop="qualityStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_quality_status" :value="scope.row.qualityStatus"/>
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

    <!-- 添加或修改产品产出记录行对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="产出记录ID" prop="recordId">
          <el-input v-model="form.recordId" placeholder="请输入产出记录ID" />
        </el-form-item>
        <el-form-item label="产品物料ID" prop="itemId">
          <el-input v-model="form.itemId" placeholder="请输入产品物料ID" />
        </el-form-item>
        <el-form-item label="产品物料编码" prop="itemCode">
          <el-input v-model="form.itemCode" placeholder="请输入产品物料编码" />
        </el-form-item>
        <el-form-item label="产品物料名称" prop="itemName">
          <el-input v-model="form.itemName" placeholder="请输入产品物料名称" />
        </el-form-item>
        <el-form-item label="规格型号" prop="specification">
          <el-input v-model="form.specification" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="单位" prop="unitOfMeasure">
          <el-input v-model="form.unitOfMeasure" placeholder="请输入单位" />
        </el-form-item>
        <el-form-item label="单位名称" prop="unitName">
          <el-input v-model="form.unitName" placeholder="请输入单位名称" />
        </el-form-item>
        <el-form-item label="产出数量" prop="quantityProduce">
          <el-input v-model="form.quantityProduce" placeholder="请输入产出数量" />
        </el-form-item>
        <el-form-item label="批次ID" prop="batchId">
          <el-input v-model="form.batchId" placeholder="请输入批次ID" />
        </el-form-item>
        <el-form-item label="批次号" prop="batchCode">
          <el-input v-model="form.batchCode" placeholder="请输入批次号" />
        </el-form-item>
        <el-form-item label="质量状态">
          <el-radio-group v-model="form.qualityStatus">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listProductproduceline, getProductproduceline } from "@/api/mes/wm/productproduceline";

export default {
  name: "Productproduceline",
  dicts: ['mes_quality_status'],
  props:{
    feedbackId: null,
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
      // 产品产出记录行表格数据
      productproducelineList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        recordId: null,  feedbackId: this.feedbackId,      itemId: null,        itemCode: null,        itemName: null,        specification: null,        unitOfMeasure: null,        unitName: null,        quantityProduce: null,        batchId: null,        batchCode: null,        qualityStatus: null,      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        itemId: [
          { required: true, message: "产品物料ID不能为空", trigger: "blur" }
        ],        quantityProduce: [
          { required: true, message: "产出数量不能为空", trigger: "blur" }
        ],      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询产品产出记录行列表 */
    getList() {
      this.loading = true;
      listProductproduceline(this.queryParams).then(response => {
        this.productproducelineList = response.rows;
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
        lineId: null,        recordId: null,  feedbackId: null,        itemId: null,        itemCode: null,        itemName: null,        specification: null,        unitOfMeasure: null,        unitName: null,        quantityProduce: null,        batchId: null,        batchCode: null,        qualityStatus: "0",        remark: null,        attr1: null,        attr2: null,        attr3: null,        attr4: null,        createBy: null,        createTime: null,        updateBy: null,        updateTime: null      };
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
      this.title = "添加产品产出记录行";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const lineId = row.lineId || this.ids
      getProductproduceline(lineId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改产品产出记录行";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.lineId != null) {
            updateProductproduceline(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProductproduceline(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除产品产出记录行编号为"' + lineIds + '"的数据项？').then(function() {
        return delProductproduceline(lineIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wm/productproduceline/export', {
        ...this.queryParams
      }, `productproduceline_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
