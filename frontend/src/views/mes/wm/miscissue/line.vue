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
          v-hasPermi="['mes:wm:miscissue:add']"
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
          v-hasPermi="['mes:wm:miscissue:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="miscissuelineList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="物资编码" align="center" prop="itemCode" />
      <el-table-column label="物资名称" align="center" prop="itemName" />
      <el-table-column label="规格型号" align="center" prop="specification" />
      <el-table-column label="单位" align="center" prop="unitName" />
      <el-table-column label="出库数量" align="center" prop="quantityIssued" />
      <el-table-column label="批次号" align="center" prop="batchCode" />
      <el-table-column label="存储位置" align="center">
        <template slot-scope="scope">
          {{ scope.row.warehouseName + '/' + scope.row.locationName + '/' + scope.row.areaName }}
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" v-if="optType!== 'view'" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mes:wm:miscissue:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mes:wm:miscissue:remove']"
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

    <!-- 添加或修改杂项出库单行对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="960px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
       <el-row>
            <el-col :span="8">
                <el-form-item label="产品物料编码" prop="itemCode">
                    <el-input v-model="form.itemCode" readonly="readonly" placeholder="请选择库存" >
                      <el-button slot="append" @click="handleSelectStock" icon="el-icon-search"></el-button>
                    </el-input>
                </el-form-item>
                <StockSelect ref="stockSelect" :itemId="form.itemId" @onSelected="onStockSelected"></StockSelect>
            </el-col>
            <el-col :span="8">
                <el-form-item label="产品物料名称" prop="itemName">
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
                    <el-input v-model="form.specification" readonly="readonly" type="textarea" />
                </el-form-item>
            </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="批次号" prop="batchCode">
              <el-input v-model="form.batchCode" readonly="readonly" placeholder="请选择库存" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="存储位置">
              <el-input readonly="readonly" :value="!form.warehouseId?'':form.warehouseName + '/' + form.locationName + '/' + form.areaName" placeholder="请选择库存" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="出库数量" prop="quantityIssued">
              <el-input-number :min="0" :max="form.quantityMax" v-model="form.quantityIssued" placeholder="请输入出库数量" />
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
        <el-button type="primary" @click="submitForm">保 存</el-button>
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listMiscissueline, getMiscissueline, delMiscissueline, addMiscissueline, updateMiscissueline } from "@/api/mes/wm/miscissueline";
import {getTreeList} from "@/api/mes/wm/warehouse"
import StockSelect from "@/components/stockSelect/single.vue"
export default {
  name: "Miscissueline",
  components: {
    StockSelect
  },
  props: {
    optType: null,
    issueId: null,
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
      // 杂项出库单行表格数据
      miscissuelineList: [],
      warehouseInfo:[],
      warehouseOptions:[],
      warehouseProps:{
        multiple: false,
        value: 'pId',
        label: 'pName',
      },
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        issueId: this.issueId,        sourceDocLineId: null,        materialStockId: null,        itemId: null,        itemCode: null,        itemName: null,        specification: null,        unitOfMeasure: null,        unitName: null,        quantityIssued: null,        batchId: null,        batchCode: null,        warehouseId: null,        warehouseCode: null,        warehouseName: null,        locationId: null,        locationCode: null,        locationName: null,        areaId: null,        areaCode: null,        areaName: null,      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        itemId: [
          { required: true, message: "产品物料ID不能为空", trigger: "blur" }
        ],        quantityIssued: [
          { required: true, message: "出库数量不能为空", trigger: "blur" }
        ],      }
    };
  },
  created() {
    this.getList();
    this.getWarehouseList();
  },
  methods: {
    /** 查询杂项出库单行列表 */
    getList() {
      this.loading = true;
      listMiscissueline(this.queryParams).then(response => {
        this.miscissuelineList = response.rows;
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
        lineId: null,        issueId: this.issueId,        sourceDocLineId: null,        materialStockId: null,        itemId: null,        itemCode: null,        itemName: null,        specification: null,        unitOfMeasure: null,        unitName: null,        quantityIssued: null,        batchId: null,        batchCode: null,        warehouseId: null,        warehouseCode: null,        warehouseName: null,        locationId: null,        locationCode: null,        locationName: null,        areaId: null,        areaCode: null,        areaName: null,        remark: null,        attr1: null,        attr2: null,        attr3: null,        attr4: null,        createBy: null,        createTime: null,        updateBy: null,        updateTime: null      };
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
      this.title = "添加杂项出库单行";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const lineId = row.lineId || this.ids
      getMiscissueline(lineId).then(response => {
        this.form = response.data;
        this.warehouseInfo[0] = response.data.warehouseId;
        this.warehouseInfo[1] = response.data.locationId;
        this.warehouseInfo[2] = response.data.areaId;
        this.open = true;
        this.title = "修改杂项出库单行";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.lineId != null) {
            updateMiscissueline(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMiscissueline(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除杂项出库单行编号为"' + lineIds + '"的数据项？').then(function() {
        return delMiscissueline(lineIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    getWarehouseList(){
      getTreeList().then( response =>{
        if(response.data){
          this.warehouseOptions = response.data.filter((el) =>{
              return el.warehouseCode.indexOf('VIR') == -1;
          });;
        }
        this.warehouseOptions.map(w =>{          
          w.children.map(l =>{
                  let lstr =JSON.stringify(l.children).replace(/locationId/g,'lId').replace(/areaId/g, 'pId').replace(/areaName/g,'pName');
                  l.children = JSON.parse(lstr);
          });

          let wstr = JSON.stringify(w.children).replace(/warehouseId/g,'wId').replace(/locationId/g, 'pId').replace(/locationName/g,'pName');
          w.children =  JSON.parse(wstr);

        });
        let ostr=JSON.stringify(this.warehouseOptions).replace(/warehouseId/g,'pId').replace(/warehouseName/g, 'pName');
        this.warehouseOptions = JSON.parse(ostr);        
      });
    },
    //选择仓库、库区、库位
    handleWarehouseChanged(obj){    
      if(obj !=null){
        this.form.warehouseId = obj[0];
        this.form.locationId = obj[1];
        this.form.areaId = obj[2];
      }
    },
    validateWarehouseInfo(rule, value, callback) {      
      if ( this.warehouseInfo.length === 0) {
        callback(new Error("请选择仓库、库区、库位"));
      } else {
        callback();
      }
    },
    handleSelectStock(){
      this.$refs.stockSelect.handleOpen();
    },
        //物料选择弹出框
    onStockSelected(obj){
        if(obj != undefined && obj != null){
          this.form.materialStockId = obj.materialStockId;
          this.form.itemId = obj.itemId;
          this.form.itemCode = obj.itemCode;
          this.form.itemName = obj.itemName;
          this.form.specification = obj.specification;
          this.form.unitOfMeasure = obj.unitOfMeasure;
          this.form.unitName = obj.unitName;
          this.form.batchId = obj.batchId;
          this.form.batchCode = obj.batchCode;
          this.form.warehouseId = obj.warehouseId;
          this.form.warehouseCode = obj.warehouseCode;
          this.form.warehouseName = obj.warehouseName;
          this.form.locationId = obj.locationId;
          this.form.locationCode = obj.locationCode;
          this.form.locationName = obj.locationName;
          this.form.areaId = obj.areaId;
          this.form.areaCode = obj.areaCode;
          this.form.areaName = obj.areaName;
          this.form.quantityIssued = obj.quantityOnhand;
          this.form.quantityMax = obj.quantityOnhand;          
        }
    },
  }
};
</script>
