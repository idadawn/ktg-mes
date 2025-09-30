<template>
  <div class="app-container">
    <el-row :gutter="10" v-if="optType!== 'view'" class="mb8">
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

    <el-table v-loading="loading" :data="stocktakingparamList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="条件类型" align="center" prop="paramType" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_stock_taking_param_type" :value="scope.row.paramType"/>
        </template>
      </el-table-column>
      <el-table-column label="条件值编码" align="center" prop="paramValueCode" />
      <el-table-column label="条件值名称" align="center" prop="paramValueName" />
      <el-table-column label="操作" v-if="optType !== 'view'" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mes:wm:stocktakingplan:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
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

    <!-- 添加或修改库存盘点方案参数对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="960px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="8"> 
            <el-select v-model="form.paramType" placeholder="请选择参数类型">
              <el-option
                v-for="dict in dict.type.mes_stock_taking_param_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-col>
          <el-col :span="8">
            <el-form-item label="条件值编码" prop="paramValueCode">
              <el-input v-model="form.paramValueCode" readonly="readonly" placeholder="请选择条件值" >
                <el-button slot="append" @click="handleSelect" icon="el-icon-search"></el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8"> 
            <el-form-item label="条件值名称" prop="paramValueName">
              <el-input v-model="form.paramValueName" placeholder="请输入条件值名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <ItemSelect  ref="itemSelect" v-if="form.paramType=='ITEM'" @onSelected="onItemSelected" > </ItemSelect>
        <BatchSelect ref="batchSelect" v-if="form.paramType=='BATCH'"  @onSelected="onBatchSelected" ></BatchSelect>
        <WarehouseSelect ref="warehouseSelect" v-if="form.paramType=='WAREHOUSE'"  @onSelected="onWarehouseSelected" ></WarehouseSelect>
        <LocationSelect ref="locationSelect" v-if="form.paramType=='LOCATION'"  @onSelected="onLocationSelected" ></LocationSelect>
        
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listStocktakingparam, getStocktakingparam, delStocktakingparam, addStocktakingparam, updateStocktakingparam } from "@/api/mes/wm/stocktakingparam";
import WarehouseSelect from "@/components/warehouseSelect/warehouseSingle.vue";
import LocationSelect from "@/components/warehouseSelect/locationSingle.vue";
import ItemSelect  from "@/components/itemSelect/single.vue";
import BatchSelect from "@/components/batchSelect/single.vue";
export default {
  name: "Stocktakingparam",
  dicts: ['mes_stock_taking_param_type'],
  components: {WarehouseSelect,LocationSelect,ItemSelect,BatchSelect},
  props:{
    planId: null,
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
      // 库存盘点方案参数表格数据
      stocktakingparamList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        planId: this.planId,        paramType: null,        paramValueId: null,        paramValueCode: null,        paramValueName: null,      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        planId: [
          { required: true, message: "方案ID不能为空", trigger: "blur" }
        ],      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询库存盘点方案参数列表 */
    getList() {
      this.loading = true;
      listStocktakingparam(this.queryParams).then(response => {
        this.stocktakingparamList = response.rows;
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
        paramId: null,        planId: this.planId,        paramType: null,        paramValueId: null,        paramValueCode: null,        paramValueName: null,        remark: null,        attr1: null,        attr2: null,        attr3: null,        attr4: null,        createBy: null,        createTime: null,        updateBy: null,        updateTime: null      };
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
      this.ids = selection.map(item => item.paramId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加库存盘点方案参数";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const paramId = row.paramId || this.ids
      getStocktakingparam(paramId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改库存盘点方案参数";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.paramId != null) {
            updateStocktakingparam(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStocktakingparam(this.form).then(response => {
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
      const paramIds = row.paramId || this.ids;
      this.$modal.confirm('是否确认删除库存盘点方案参数编号为"' + paramIds + '"的数据项？').then(function() {
        return delStocktakingparam(paramIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wm/stocktakingparam/export', {
        ...this.queryParams
      }, `stocktakingparam_${new Date().getTime()}.xlsx`)
    },
    handleSelect(){
      if(this.form.paramType=='ITEM'){
        this.$refs.itemSelect.showFlag = true;
      }else if(this.form.paramType=='BATCH'){
        this.$refs.batchSelect.showFlag = true;
      }else if(this.form.paramType=='WAREHOUSE'){
        this.$refs.warehouseSelect.showFlag = true;
      }else if(this.form.paramType=='LOCATION'){
        this.$refs.locationSelect.showFlag = true;
      }
    },
    onItemSelected(item){
      this.form.paramValueId = item.itemId;
      this.form.paramValueCode = item.itemCode;
      this.form.paramValueName = item.itemName;
    },
    onBatchSelected(batch){
      this.form.paramValueId = batch.batchId;
      this.form.paramValueCode = batch.batchCode;
      this.form.paramValueName = batch.batchCode;
    },
    onWarehouseSelected(warehouse){
      this.form.paramValueId = warehouse.warehouseId;
      this.form.paramValueCode = warehouse.warehouseCode;
      this.form.paramValueName = warehouse.warehouseName;
    },
    onLocationSelected(location){
      this.form.paramValueId = location.locationId;
      this.form.paramValueCode = location.locationCode;
      this.form.paramValueName = location.locationName;
    }
  }
}
</script>
