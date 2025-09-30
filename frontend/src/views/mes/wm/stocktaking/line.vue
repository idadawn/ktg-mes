<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="物资编码" prop="itemCode">
        <el-input
          v-model="queryParams.itemCode"
          placeholder="请输入产品物料编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物资名称" prop="itemName">
        <el-input
          v-model="queryParams.itemName"
          placeholder="请输入产品物料名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="仓库名称" prop="warehouseName">
        <el-input
          v-model="queryParams.warehouseName"
          placeholder="请输入仓库名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="盘点状态" prop="takingStatus">
        <el-select v-model="queryParams.takingStatus" placeholder="请选择盘点状态" clearable>
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" v-if="optType !='view'" class="mb8">
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
      <stockSelect ref="stockSelect" @onSelected="onStockSelected" ></stockSelect>
    </el-row>

    <el-table v-loading="loading" :data="stocktakinglineList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="物资编码" width="150px" align="center" prop="itemCode" />
      <el-table-column label="物资名称" width="200px" align="center" prop="itemName" :show-overflow-tooltip="true"/>
      <el-table-column label="规格型号" align="center" prop="specification" />
      <el-table-column label="单位" align="center" prop="unitName" />
      <el-table-column label="在库数量" align="center" prop="quantity" />
      <el-table-column label="仓库" align="center" prop="warehouseName" />
      <el-table-column label="库区" align="center" prop="locationName" />
      <el-table-column label="库位" align="center" prop="areaName" />
      <el-table-column label="操作" align="center" v-if="optType !== 'view'" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
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

  </div>
</template>

<script>
import { listStocktakingline, getStocktakingline, delStocktakingline, addStocktakingline, updateStocktakingline } from "@/api/mes/wm/stocktakingline";
import stockSelect from "@/components/stockSelect/multi.vue";
export default {
  name: "Stocktakingline",
  components: { stockSelect },
  dicts: ['mes_stock_taking_result_status'],
  props:{
    optType: null,
    takingId: null,
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
      // 库存盘点明细表格数据
      stocktakinglineList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        takingId: this.takingId,        materialStockId: null,        itemId: null,        itemCode: null,        itemName: null,        specification: null,        unitOfMeasure: null,        unitName: null,        quantity: null,        takingQuantity: null,        warehouseId: null,        warehouseCode: null,        warehouseName: null,        locationId: null,        locationCode: null,        locationName: null,        areaId: null,        areaCode: null,        areaName: null,        takingStatus: null,      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        itemId: [
          { required: true, message: "产品物料ID不能为空", trigger: "blur" }
        ],        quantity: [
          { required: true, message: "数量不能为空", trigger: "blur" }
        ],        takingStatus: [
          { required: true, message: "盘点状态不能为空", trigger: "blur" }
        ],      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询库存盘点明细列表 */
    getList() {
      this.loading = true;
      listStocktakingline(this.queryParams).then(response => {
        this.stocktakinglineList = response.rows;
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
        lineId: null,        takingId: null,        materialStockId: null,        itemId: null,        itemCode: null,        itemName: null,        specification: null,        unitOfMeasure: null,        unitName: null,        quantity: null,        takingQuantity: null,        warehouseId: null,        warehouseCode: null,        warehouseName: null,        locationId: null,        locationCode: null,        locationName: null,        areaId: null,        areaCode: null,        areaName: null,        takingStatus: "NORMAL",        remark: null,        attr1: null,        attr2: null,        attr3: null,        attr4: null,        createBy: null,        createTime: null,        updateBy: null,        updateTime: null      };
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
      this.$refs.stockSelect.showFlag = true;
    },
    onStockSelected(rows){
        if(rows && rows.length > 0){
          let lines = [];
          for(let i=0;i<rows.length;i++){
            this.form.takingId = this.takingId;
            this.form.materialStockId = rows[i].materialStockId;
            this.form.itemId = rows[i].itemId;
            this.form.itemCode = rows[i].itemCode;
            this.form.itemName = rows[i].itemName;
            this.form.specification = rows[i].specification;
            this.form.unitOfMeasure = rows[i].unitOfMeasure;
            this.form.unitName = rows[i].unitName;
            this.form.quantity = rows[i].quantity;
            this.form.takingQuantity = 0;
            this.form.warehouseId = rows[i].warehouseId;
            this.form.warehouseCode = rows[i].warehouseCode;
            this.form.warehouseName = rows[i].warehouseName;
            this.form.locationId = rows[i].locationId;
            this.form.locationCode = rows[i].locationCode;
            this.form.locationName = rows[i].locationName;
            this.form.areaId = rows[i].areaId;
            this.form.areaCode = rows[i].areaCode;
            this.form.areaName = rows[i].areaName;
            this.form.takingStatus = "0";
            lines.push(this.form);
          }
          addStocktakingline(lines).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.getList();
          });
        }
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const lineIds = row.lineId || this.ids;
      this.$modal.confirm('是否确认删除库存盘点明细编号为"' + lineIds + '"的数据项？').then(function() {
        return delStocktakingline(lineIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
  }
};
</script>
