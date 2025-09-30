<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
            v-model="itemTypeName"
            placeholder="请输入分类名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            :data="itemTypeOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            default-expand-all
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>
      <el-col :span="20" :xs="24">

      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
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
        <el-form-item label="仓库名称" prop="warehouseName">
          <el-input
            v-model="queryParams.warehouseName"
            placeholder="请输入仓库名称"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="库区名称" prop="locationName">
          <el-input
            v-model="queryParams.locationName"
            placeholder="请输入库区名称"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="库位编码" prop="areaCode">
          <el-input
            v-model="queryParams.areaCode"
            placeholder="请输入库位编码"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="是否冻结" prop="frozenFlag">
          <el-select v-model="queryParams.frozenFlag" placeholder="请选择">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
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
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['mes:wm:wmstock:export']"
          >导出</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="wmstockList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="产品物料编码" width="120px" align="center" prop="itemCode" />
        <el-table-column label="产品物料名称" width="150px" align="center" prop="itemName" :show-overflow-tooltip="true"/>
        <el-table-column label="规格型号" align="center" prop="specification" :show-overflow-tooltip="true"/>
        <el-table-column label="在库数量" align="center" prop="quantityOnhand" />
        <el-table-column label="单位" align="center" prop="unitName" />
        <el-table-column label="批次号" width="150px" align="center" prop="batchCode" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <el-button
              type="text"
              @click="handleBatchClick(scope.row)"
            >{{scope.row.batchCode}}</el-button>
          </template>
        </el-table-column>
        <el-table-column label="仓库" align="center" prop="warehouseName" />
        <el-table-column label="库区" align="center" prop="locationName" />
        <el-table-column label="库位" align="center" prop="areaName" >
          <template slot-scope="scope">
          <el-button
            type="text"
            @click="handleAreaClick(scope.row)"
          >{{scope.row.areaName}}</el-button>
        </template>
        </el-table-column>
        <el-table-column label="入库日期" align="center" prop="recptDate" width="120">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.recptDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="是否冻结" align="center" width="100">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.frozenFlag"
              active-text="是"
              inactive-text="否"
              active-value="Y"
              inactive-value="N"
              @change="handleFrozenChange(scope.row)"
            ></el-switch>
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
      </el-col>
    </el-row>

 <!-- 添加或修改库位设置对话框 -->
 <el-dialog title="储位详情" :visible.sync="open" width="960px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="14">
            <el-row>
              <el-col :span="16">
                <el-form-item label="库位编码" prop="areaCode">
                  <el-input v-model="form.areaCode" readonly="readonly" maxlength="64"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">

              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="库位名称" prop="areaName">
                  <el-input v-model="form.areaName" readonly="readonly" maxlength="255"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="面积" prop="area">
                  <el-input-number :min="0" :max="99999999" :step="1" :percision="2" v-model="form.area" readonly="readonly" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="最大载重量" prop="maxLoa">
                  <el-input-number v-model="form.maxLoa" :max="99999999" :step="1" :percision="2" readonly="readonly" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="允许产品混放" prop="productMixing">
                  <el-radio-group v-model="form.productMixing">
                    <el-radio
                      v-for="dict in dict.type.sys_yes_no"
                      :key="dict.value"
                      :label="dict.value"
                    >{{dict.label}}</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="允许批次混放" prop="batchMixing">
                  <el-radio-group v-model="form.batchMixing">
                    <el-radio
                      v-for="dict in dict.type.sys_yes_no"
                      :key="dict.value"
                      :label="dict.value"
                    >{{dict.label}}</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
          </el-col>
          <el-col :span="10">
            <BarcodeImg ref="barcodeImg" :bussinessId="form.areaId" :bussinessCode="form.areaCode" barcodeType="AREA"></BarcodeImg>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="库位位置X" prop="positionX">
              <el-input-number :min="0" :max="99999999" :step="1" v-model="form.positionX" readonly="readonly" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="库位位置y" prop="positionY">
              <el-input-number :min="0" :max="99999999" :step="1" v-model="form.positionY" readonly="readonly" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="库位位置z" prop="positionZ">
              <el-input-number :min="0" :max="99999999" :step="1" v-model="form.positionZ" readonly="readonly" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" maxlength="500" readonly="readonly"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeArea">关 闭</el-button>
      </div>
    </el-dialog>

    <el-dialog title="批次详情" :visible.sync="batchOpen" width="960px" append-to-body>
      <el-form ref="batchform" :model="batchform" label-width="100px">
        <el-row>
          <el-col :span="14">
            <el-row>
              <el-col :span="24">
                <el-form-item label="批次编号" prop="batchCode">
                  <el-input v-model="batchform.batchCode" readonly="readonly"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="产品物料编码" prop="itemCode">
                  <el-input v-model="batchform.itemCode" readonly="readonly" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="产品物料名称" prop="itemName">
                  <el-input v-model="batchform.itemName" readonly="readonly" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="规格型号" prop="specification">
                  <el-input v-model="batchform.specification" type="textarea" readonly="readonly" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-col>
          <el-col :span="10">
            <BarcodeImg ref="batchBarcodeImg" :bussinessId="batchform.batchId" :bussinessCode="batchform.batchCode" barcodeType="BATCH"></BarcodeImg>
          </el-col>
        </el-row>      
        <el-row>
          <el-col :span="8">
            <el-form-item label="单位" prop="unitName">
              <el-input v-model="batchform.unitName" readonly="readonly" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="生产日期" prop="produceDate">
              <el-date-picker clearable
                v-model="batchform.produceDate"
                type="date"
                value-format="yyyy-MM-dd">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="有效期" prop="expireDate">
              <el-date-picker clearable
                v-model="batchform.expireDate"
                type="date"
                value-format="yyyy-MM-dd">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>  
        <el-row>
              <el-col :span="12">
                <el-form-item label="供应商名称" prop="vendorName">
                  <el-input v-model="batchform.vendorName" readonly="readonly" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="客户名称" prop="clientName">
                  <el-input v-model="batchform.clientName" readonly="readonly" />
                </el-form-item>
              </el-col>
            </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="销售订单编号" prop="coCode">
              <el-input v-model="batchform.coCode" readonly="readonly" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="采购订单编号" prop="poCode">
              <el-input v-model="batchform.poCode" readonly="readonly" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="生产工单" prop="workorderCode">
              <el-input v-model="batchform.workorderCode" readonly="readonly" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="生产任务" prop="taskCode">
              <el-input v-model="batchform.taskCode" readonly="readonly" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="工作站编码" prop="workstationCode">
              <el-input v-model="batchform.workstationCode" readonly="readonly" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="生产批号" prop="productCode">
              <el-input v-model="batchform.productCode" readonly="readonly" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeBatch">关 闭</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import { listWmstock, changeFrozenState } from "@/api/mes/wm/wmstock";
import { getArea } from "@/api/mes/wm/area";
import { getBatch } from "@/api/mes/wm/batch";
import { treeselect } from "@/api/mes/md/itemtype";
import BarcodeImg from "@/components/barcodeImg/index.vue"
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
export default {
  name: "Wmstock",
  dicts: ['sys_yes_no'],
  components: { Treeselect, BarcodeImg },
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
      itemTypeName: null,
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 总条数
      total: 0,
      //物料产品分类树
      itemTypeOptions: undefined,
      // 库存记录表格数据
      wmstockList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      batchOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        itemTypeId: null,
        itemId: null,
        itemCode: null,
        itemName: null,
        specification: null,
        unitOfMeasure: null,
        batchCode: null,
        warehouseId: null,
        warehouseCode: null,
        warehouseName: null,
        locationId: null,
        locationCode: null,
        locationName: null,
        areaId: null,
        areaCode: null,
        areaName: null,
        vendorId: null,
        vendorCode: null,
        vendorName: null,
        vendorNick: null,
        quantityOnhand: null,
        workorderCode: null,
        expireDate: null,
      },
      // 表单参数
      form: {},
      batchform: {},
    };
  },
  watch: {
    // 根据名称筛选分类树
    itemTypeName(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
    this.getList();
    this.getTreeselect();
  },
  methods: {
    /** 查询库存记录列表 */
    getList() {
      this.loading = true;
      listWmstock(this.queryParams).then(response => {
        this.wmstockList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询分类下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.itemTypeOptions = response.data;
      });
    },
    /**
     * 冻结状态变更
     * @param row 
     */
    handleFrozenChange(row){
      let text = row.frozenFlag === "Y" ? "冻结" : "解冻";
      this.$modal.confirm('确认要"' + text + '""' + row.materialStockId + '"此库存吗？').then(function() {
        return changeFrozenState(row.materialStockId,row.frozenFlag);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.frozenFlag = row.frozenFlag === "N" ? "Y" : "N";
      });

    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.itemTypeId = data.id;
      this.handleQuery();
    },
    // 表单重置
    reset() {
      this.form = {
        areaId: null,
        areaCode: null,
        areaName: null,
        locationId: null,
        area: null,
        maxLoa: null,
        productMixing: 'N',
        batchMixing: 'N',
        positionX: null,
        positionY: null,
        positionZ: null,
        enableFlag: 'Y',
        remark: null,
        attr1: null,
        attr2: null,
        attr3: null,
        attr4: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    resetBatch(){
      this.batchform = {
        batchId: null,
        batchCode: null,
        itemCode: null,
        itemName: null,
        specification: null,
        produceDate: null,
        expireDate: null,
        vendorName: null,
        clientName: null,
        coCode: null,
        poCode: null,
        workorderCode: null,
        taskCode: null,
        workstationCode: null,
        productCode: null,
      };
      this.resetForm("batchform");
    },
    //库位点击事件
    handleAreaClick(row){
      this.reset();
      const areaId = row.areaId || this.ids
      getArea(areaId).then(response => {
        this.form = response.data;
        this.open = true;
        this.$nextTick(()=>{
          this.$refs.barcodeImg.getBarcode();
        })
      });
    },
    closeArea(){
      this.open = false;
    },
    //批次点击
    handleBatchClick(row){
      this.resetBatch();
      const batchId = row.batchId
      getBatch(batchId).then(response => {
        this.batchform = response.data;
        this.batchOpen = true;
        this.$nextTick(()=>{
          this.$refs.batchBarcodeImg.getBarcode();
        });
      });
    },
    closeBatch(){
      this.batchOpen = false;
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
      this.ids = selection.map(item => item.materialStockId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('mes/wm/wmstock/export', {
        ...this.queryParams
      }, `wmstock_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
