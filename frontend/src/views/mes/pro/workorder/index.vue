<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="工单编码" prop="workorderCode">
        <el-input
          v-model="queryParams.workorderCode"
          placeholder="请输入工单编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工单名称" prop="workorderName">
        <el-input
          v-model="queryParams.workorderName"
          placeholder="请输入工单名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="来源单据" prop="sourceCode">
        <el-input
          v-model="queryParams.sourceCode"
          placeholder="请输入来源单据"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="产品编号" prop="productCode">
        <el-input
          v-model="queryParams.productCode"
          placeholder="请输入产品编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产品名称" prop="productName">
        <el-input
          v-model="queryParams.productName"
          placeholder="请输入产品名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="客户编码" prop="clientCode">
        <el-input
          v-model="queryParams.clientCode"
          placeholder="请输入客户编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户名称" prop="clientName">
        <el-input
          v-model="queryParams.clientName"
          placeholder="请输入客户名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工单类型" prop="workorderType">
        <el-input
          v-model="queryParams.workorderType"
          placeholder="请选择工单类型"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="需求日期" prop="requestDate">
        <el-date-picker clearable
                        v-model="queryParams.requestDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择需求日期">
        </el-date-picker>
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
          v-hasPermi="['mes:pro:workorder:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['mes:pro:workorder:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="workorderList"
      row-key="workorderId"
      default-expand-all
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="工单编码" width="180" prop="workorderCode" >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="handleView(scope.row)"
            v-hasPermi="['mes:pro:workorder:query']"
          >{{scope.row.workorderCode}}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="工单名称" width="200" align="center" prop="workorderName" :show-overflow-tooltip="true"/>
      <el-table-column label="工单类型" align="center" prop="workorderType" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_workorder_type" :value="scope.row.workorderType"/>
        </template>
      </el-table-column>
      <el-table-column label="工单来源" align="center" prop="orderSource" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_workorder_sourcetype" :value="scope.row.orderSource"/>
        </template>
      </el-table-column>
      <el-table-column label="订单编号" width="140" align="center" prop="sourceCode" />
      <el-table-column label="产品编号" width="120" align="center" prop="productCode" />
      <el-table-column label="产品名称" width="200" align="center" prop="productName" :show-overflow-tooltip="true"/>
      <el-table-column label="规格型号" align="center" prop="productSpc" :show-overflow-tooltip="true"/>
      <el-table-column label="单位" align="center" prop="unitName" />
      <el-table-column label="工单数量" align="center" prop="quantity" />
      <el-table-column label="已生产数量" align="center" width="100px" prop="quantityProduced" />
      <el-table-column label="客户编码" align="center" prop="clientCode" />
      <el-table-column label="客户名称" align="center" prop="clientName" :show-overflow-tooltip="true"/>
      <el-table-column label="需求日期" align="center" prop="requestDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.requestDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="单据状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_order_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="250px" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="scope.row.status =='PREPARE'"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mes:pro:workorder:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            v-if="scope.row.status =='CONFIRMED' && scope.row.workorderType =='SELF'"
            @click="handleAdd(scope.row)"
            v-hasPermi="['mes:pro:workorder:update']"
          >新增</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-circle-check"
            v-if="scope.row.status =='CONFIRMED'"
            @click="handleFinish(scope.row)"
            v-hasPermi="['mes:pro:workorder:update']"
          >完成</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-circle-close"
            v-if="scope.row.status =='CONFIRMED'"
            @click="handleCancel(scope.row)"
            v-hasPermi="['mes:pro:workorder:update']"
          >取消</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            v-if="scope.row.status =='PREPARE'"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mes:pro:workorder:remove']"
          >删除</el-button>
          <el-button
              size="mini"
              type="text"
              icon="el-icon-printer"
              @click="handlePreview(scope.row)"
          >预览</el-button>
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

    <!-- 添加或修改生产工单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="960px" @close="cancel" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="16">
            <el-row>
              <el-col :span="16">
                <el-form-item label="工单编号" prop="workorderCode">
                  <el-input v-model="form.workorderCode" placeholder="请输入工单编号" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item  label-width="80">
                  <el-switch v-model="autoGenFlag"
                             active-color="#13ce66"
                             active-text="自动生成"
                             @change="handleAutoGenChange(autoGenFlag)" v-if="optType != 'view' && form.status =='PREPARE'">
                  </el-switch>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="工单名称" prop="workorderName">
                  <el-input v-model="form.workorderName" placeholder="请输入工单名称" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="来源类型" prop="orderSource">
                  <el-radio-group v-model="form.orderSource" disabled v-if="optType=='view'">
                    <el-radio
                      v-for="dict in dict.type.mes_workorder_sourcetype"
                      :key="dict.value"
                      :label="dict.value"
                    >{{dict.label}}</el-radio>
                  </el-radio-group>
                  <el-radio-group v-model="form.orderSource" v-else>
                    <el-radio
                      v-for="dict in dict.type.mes_workorder_sourcetype"
                      :key="dict.value"
                      :label="dict.value"
                    >{{dict.label}}</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="12" v-if="form.orderSource == 'ORDER'">
                <el-form-item label="订单编号" prop="sourceCode">
                  <el-input v-model="form.sourceCode" placeholder="请输入订单编号" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="工单类型" prop="workorderType">
                  <el-select v-model="form.workorderType" placeholder="请选择类型">
                    <el-option
                      v-for="dict in dict.type.mes_workorder_type"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-col>
          <el-col :span="8">
            <BarcodeImg ref="barcodeImg" :bussinessId="form.workorderId" :bussinessCode="form.workorderCode" barcodeType="WORKORDER"></BarcodeImg>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="产品编号" prop="productCode">
              <el-input v-model="form.productCode" placeholder="请选择产品" >
                <el-button slot="append" @click="handleSelectProduct" icon="el-icon-search"></el-button>
              </el-input>
              <ItemSelect ref="itemSelect" @onSelected="onItemSelected" > </ItemSelect>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品名称" prop="productName">
              <el-input v-model="form.productName" placeholder="请选择产品" disabled/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="规格型号" prop="productSpc">
              <el-input v-model="form.productSpc" placeholder="请选择产品" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="单位" prop="unitName">
              <el-input v-model="form.unitName" placeholder="请选择产品" disabled/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="工单数量" prop="quantity">
              <el-input-number :min="1" :max="99999999" v-model="form.quantity" placeholder="请输入生产数量" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="需求日期" prop="requestDate">
              <el-date-picker clearable
                              v-model="form.requestDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择需求日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
          </el-col>
        </el-row>
        <el-row v-if="form.orderSource == 'ORDER'">
          <el-col :span="12">
            <el-form-item label="客户编码" prop="clientCode">
              <el-input v-model="form.clientCode" placeholder="请选择客户" >
                <el-button slot="append" @click="handleSelectClient" icon="el-icon-search"></el-button>
              </el-input>
              <ClientSelect ref="clientSelect" @onSelected="onClientSelected" > </ClientSelect>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="客户名称" prop="clientName">
              <el-input v-model="form.clientName" readonly="readonly" placeholder="请输入客户名称" />
            </el-form-item>
          </el-col>
          <el-col></el-col>
        </el-row>
        <el-row v-if="form.workorderType == 'OUTSOURCE' || form.workorderType == 'PURCHASE'">
          <el-col :span="12">
            <el-form-item label="供应商编码" prop="vendorCode">
              <el-input v-model="form.vendorCode" placeholder="请选择供应商" >
                <el-button slot="append" @click="handleSelectVendor" icon="el-icon-search"></el-button>
              </el-input>
              <VendorSelect ref="vendorSelect" @onSelected="onVendorSelected" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="供应商名称" prop="vendorName">
              <el-input v-model="form.vendorName" readonly="readonly" placeholder="请选择供应商" />
            </el-form-item>
          </el-col>
          <el-col></el-col>
        </el-row>
        <el-row>
          <el-col></el-col>
          <el-col></el-col>
          <el-col></el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-tabs type="border-card" v-if="form.workorderId != null" v-model="activeName" @tab-click="handleClickTab">
        <el-tab-pane label="BOM组成" name="bom">
          <Workorderbom ref="bomlist" :optType="optType" :workorder="form" @handleAddSub="handleSubAdd" ></Workorderbom>
        </el-tab-pane>
        <el-tab-pane label="物料需求" name="item">
          <WorkorderItemList ref="itemlist"  :workorder="form" :itemStatus="itemStatus"></WorkorderItemList>
        </el-tab-pane>
      </el-tabs>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" v-if="form.status =='PREPARE' && optType !='view' ">保 存</el-button>
        <el-button type="success" @click="handleConfirm" v-if="form.status =='PREPARE' && optType !='view'  && form.workorderId !=null">确 认</el-button>
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listWorkorder, getWorkorder, delWorkorder, addWorkorder, updateWorkorder ,dofinish ,doCancel} from "@/api/mes/pro/workorder";
import Workorderbom from "./bom/bom.vue";
import WorkorderItemList from "./items/item.vue";
import ItemSelect  from "@/components/itemSelect/single.vue";
import ClientSelect from "@/components/clientSelect/single.vue";
import VendorSelect from "@/components/vendorSelect/single.vue";
import {genCode} from "@/api/system/autocode/rule"
import Treeselect from "@riophae/vue-treeselect";
import BarcodeImg from "@/components/barcodeImg/index.vue"
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Workorder",
  dicts: ['mes_order_status','mes_workorder_sourcetype','mes_workorder_type'],
  components: {
    Treeselect,
    ItemSelect ,
    ClientSelect,
    VendorSelect,
    Workorderbom,
    WorkorderItemList,
    BarcodeImg
  },
  data() {
    return {
      itemStatus: false,
      activeName: 'bom',
      //自动生成编码
      autoGenFlag:false,
      optType: undefined,
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 非单个禁用
      single: true,
      // 总条数
      total: 0,
      // 非多个禁用
      multiple: true,
      // 生产工单表格数据
      workorderList: [],
      // 生产工单树选项
      workorderOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        workorderCode: null,
        workorderName: null,
        orderSource: null,
        sourceCode: null,
        productId: null,
        productCode: null,
        productName: null,
        productSpc: null,
        unitOfMeasure: null,
        quantity: null,
        quantityProduced: null,
        quantityChanged: null,
        quantityScheduled: null,
        clientId: null,
        clientCode: null,
        clientName: null,
        requestDate: null,
        parentId: null,
        ancestors: null,
        status: null,
      },
      // 表单参数
      form: {},
      formStatus: "parent",
      // 生成工单后的表单
      secondaryForm: {},
      primaryForm: {},
      // 表单校验
      rules: {
        workorderCode: [
          { required: true, message: "工单编码不能为空", trigger: "blur" },
          { max: 64, message: "字段过长", trigger: "blur" }
        ],
        workorderName: [
          { required: true, message: "工单名称不能为空", trigger: "blur" },
          { max: 100, message: "字段过长", trigger: "blur" }
        ],
        workorderType: [
          { required: true, message: "请选择生产工单类型", trigger: "blur" }
        ],
        orderSource: [
          { required: true, message: "来源类型不能为空", trigger: "blur" }
        ],
        productId: [
          { required: true, message: "产品不能为空", trigger: "blur" }
        ],
        productCode: [
          { required: true, message: "产品编号不能为空", trigger: "blur" }
        ],
        productName: [
          { required: true, message: "产品名称不能为空", trigger: "blur" }
        ],
        quantity: [
          { required: true, message: "生产数量不能为空", trigger: "blur" }
        ],
        requestDate: [
          { required: true, message: "需求日期不能为空", trigger: "blur" }
        ],
        remark: [
          { max: 250, message: '长度必须小于250个字符', trigger: 'blur' }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    handleClickTab(tab) {
      if (tab.name == 'item') {
        this.itemStatus = true
      } else {
        this.itemStatus = false
      }
    },
    /** 查询生产工单列表 */
    getList() {
      this.loading = true;
      listWorkorder(this.queryParams).then(response => {
        this.workorderList = this.handleTree(response.rows, "workorderId", "parentId");
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 转换生产工单数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.workorderId,
        label: node.workorderName,
        children: node.children
      };
    },
    /** 查询生产工单下拉树结构 */
    getTreeselect() {
      listWorkorder().then(response => {
        this.workorderOptions = [];
        const data = { workorderId: 0, workorderName: '顶级节点', children: [] };
        data.children = this.handleTree(response.rows, "workorderId", "parentId");
        this.workorderOptions.push(data);
      });
    },
    // 取消按钮
    cancel() {
      if (this.formStatus == 'parent') {
        this.open = false;
        this.reset();
      } else {
        this.reset()
        this.formStatus = 'parent'
        this.getTreeselect();
        const workorderId = this.primaryForm.workorderId;
        getWorkorder(workorderId).then(response => {
          this.form = response.data
          this.open = true;
          this.$nextTick(() => {
            this.$refs.barcodeImg.getBarcode();

          })
          this.title = "查看工单信息";
          this.optType = "view";
        });
      }

    },
    // 表单重置
    reset() {
      this.form = {
        workorderId: null,
        workorderCode: null,
        workorderName: null,
        workorderType: 'SELF',
        orderSource: null,
        sourceCode: null,
        productId: null,
        productCode: null,
        productName: null,
        productSpc: null,
        unitOfMeasure: null,
        quantity: null,
        quantityProduced: null,
        quantityChanged: null,
        quantityScheduled: null,
        clientId: null,
        clientCode: null,
        clientName: null,
        vendorId: null,
        vendorCode: null,
        vendorName: null,
        requestDate: null,
        parentId: null,
        status: "PREPARE",
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.autoGenFlag = false;
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    //从BOM行中直接新增
    handleSubAdd(row){
      this.primaryForm = this.form
      this.formStatus = "child"
      this.open = false;
      this.reset();
      this.getTreeselect();
      if (row != null && row.workorderId) {
        this.form = row;
        this.form.parentId = row.workorderId;
        this.form.workorderId = null;
        this.form.workorderCode = null;
      } else {
        this.form.parentId = 0;
      }
      this.open = true;
      this.title = "添加生产工单";
      this.optType="add";
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset();
      this.getTreeselect();
      if (row != null && row.workorderId) {
        this.form.parentId = row.workorderId;
        this.form.orderSource = row.orderSource;
        this.form.sourceCode = row.sourceCode;
        this.form.clientId = row.clientId;
        this.form.clientCode = row.clientCode;
        this.form.clientName = row.clientName;
      } else {
        this.form.parentId = 0;
      }
      this.open = true;
      this.title = "添加生产工单";
      this.optType="add";
    },
    // 查询明细按钮操作
    handleView(row){
      this.reset();
      this.getTreeselect();
      const workorderId = row.workorderId || this.ids;
      getWorkorder(workorderId).then(response => {
        this.form = response.data
        this.open = true;
        this.$nextTick(() => {
          this.$refs.barcodeImg.getBarcode();

        })
        this.title = "查看工单信息";
        this.optType = "view";
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      if (row != null) {
        this.form.parentId = row.workorderId;
      }
      getWorkorder(row.workorderId).then(response => {
        this.form = response.data;

        this.form.workorderCode = response.data.workorderCode
        this.form.workorderId = response.data.workorderId
        this.open = true;
        this.title = "修改生产工单";
        this.optType="edit";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.workorderId != null) {
            updateWorkorder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              //this.open = false;
              this.$refs["bomlist"].getList();
              this.getList();
            });
          } else {
            addWorkorder(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              //this.open = false;
              this.form.workorderId = response.data;
              this.getList();
            });
          }
        }
      });
    },
    handlePreview(row){
      //todo:本地环境报表地址
      window.open(process.env.VUE_APP_REPORT+"/ureport/preview?_u=mysql:生产工单打印模版.ureport.xml&id="+row.workorderId+"&code="+row.workorderCode)
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$modal.confirm('是否确认删除生产工单编号为"' + row.workorderId + '"的数据项？').then(function() {
        return delWorkorder(row.workorderId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    handleSelectProduct(){
      this.$refs.itemSelect.handleOpen(this.form.productId)
    },
    handleSelectClient(){
      this.$refs.clientSelect.showFlag = true;
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('mes/pro/workorder/export', {
        ...this.queryParams
      }, `workorder_${new Date().getTime()}.xlsx`)
    },
    handleConfirm(){
      let that = this;
      this.$modal.confirm('是确认完成工单编制？【确认后将不能更改】').then(function(){
        that.form.status = 'CONFIRMED';
        that.submitForm();
      });
    },
    handleFinish(row){
      const workorderIds = row.workorderId || this.ids;
      this.$modal.confirm('确认完成工单？一旦完成，此工单将无法继续报工').then(function() {
        return dofinish(workorderIds) //完成工单
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("更改成功");
      }).catch(() => {});
    },
    handleCancel(row){
      const workorderIds = row.workorderId || this.ids;
      this.$modal.confirm('确认取消工单？一旦完成，此工单将无法继续报工').then(function() {
        return doCancel(workorderIds) //取消工单
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("更改成功");
      }).catch(() => {});
    },
    //物料选择弹出框
    onItemSelected(obj){
      console.log(obj, '----------------')
      if(obj != undefined && obj != null){
        this.form.productId = obj.itemId;
        this.form.productCode = obj.itemCode;
        this.form.productName = obj.itemName;
        this.form.productSpc = obj.specification;
        this.form.unitOfMeasure = obj.unitOfMeasure;
        this.form.unitName = obj.unitName;
      }
    },
    //客户选择弹出框
    onClientSelected(obj){
      if(obj != undefined && obj != null){
        this.form.clientId = obj.clientId;
        this.form.clientCode = obj.clientCode;
        this.form.clientName = obj.clientName;
      }
    },
    //供应商选择
    handleSelectVendor(){
      this.$refs.vendorSelect.showFlag = true;
    },
    //供应商选择弹出框
    onVendorSelected(obj){
      debugger;
      if(obj != undefined && obj != null){
        this.form.vendorId = obj.vendorId;
        this.form.vendorCode = obj.vendorCode;
        this.form.vendorName = obj.vendorName;
      }
    },
    //自动生成编码
    handleAutoGenChange(autoGenFlag){
      if(autoGenFlag){
        genCode('WORKORDER_CODE').then(response =>{
          this.form.workorderCode = response;
        });
      }else{
        this.form.workorderCode = null;
      }
    }
  }
};
</script>
