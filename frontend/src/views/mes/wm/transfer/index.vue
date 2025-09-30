<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="转移单编号" prop="transferCode">
        <el-input
          v-model="queryParams.transferCode"
          placeholder="请输入转移单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="转移单名称" prop="transferName">
        <el-input
          v-model="queryParams.transferName"
          placeholder="请输入转移单名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="转移单类型" prop="transferType">
        <el-select v-model="queryParams.transferType" placeholder="请选择转移单类型" clearable>
          <el-option
            v-for="dict in dict.type.mes_transfer_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['mes:wm:transfer:add']"
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
          v-hasPermi="['mes:wm:transfer:edit']"
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
          v-hasPermi="['mes:wm:transfer:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="transferList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="转移单编号" align="center" prop="transferCode" >
        <template slot-scope="scope">
          <el-button
            type="text"
            @click="handleView(scope.row)"
            v-hasPermi="['mes:wm:transfer:query']"
          >{{scope.row.transferCode}}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="转移单名称" align="center" prop="transferName" />
      <el-table-column label="转移单类型" align="center" prop="transferType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_transfer_type" :value="scope.row.transferType"/>
        </template>
      </el-table-column>
      <el-table-column label="是否配送" align="center" prop="deliveryFlag">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.deliveryFlag"/>
        </template>
      </el-table-column>
      <el-table-column label="转移日期" align="center" prop="transferDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.transferDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="单据状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.mes_transfer_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-truck"
            v-if="(scope.row.status =='UNCONFIRMED' && scope.row.transferType == 'OUTER')"
            @click="handleConfirm(scope.row)"
            v-hasPermi="['mes:wm:transfer:edit']"
          >到货确认</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-shopping-cart-full"
            v-if="(scope.row.status =='UNSTOCK')"
            @click="handleStocking(scope.row)"
            v-hasPermi="['mes:wm:transfer:edit']"
          >执行上架</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-video-play"
            v-if="(scope.row.status =='UNEXECUTE')"
            @click="handleExecute(scope.row)"
            v-hasPermi="['mes:wm:transfer:edit']"
          >执行转移</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-if="scope.row.status == 'PREPARE'"
            v-hasPermi="['mes:wm:transfer:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-if="scope.row.status == 'PREPARE'"
            v-hasPermi="['mes:wm:transfer:remove']"
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

    <!-- 添加或修改转移单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="960px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="转移单编号" prop="transferCode">
              <el-input v-model="form.transferCode" placeholder="请输入转移单编号" />
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
            <el-form-item label="转移单名称" prop="transferName">
              <el-input v-model="form.transferName" placeholder="请输入转移单名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="转移单类型">
              <el-radio-group v-model="form.transferType">
                <el-radio
                  v-for="dict in dict.type.mes_transfer_type"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="转移日期" prop="transferDate">
              <el-date-picker clearable
                v-model="form.transferDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择转移日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col v-if="form.transferType == 'OUTER'" :span="8">
            <el-form-item label="是否配送" prop="deliveryFlag">
              <el-switch
                v-model="form.deliveryFlag"
                active-text="是"
                inactive-text="否"
                active-value="Y"
                inactive-value="N"
                @change="handleDeliveryFlagChange()"
              ></el-switch>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="form.deliveryFlag == 'Y'">
          <el-col :span="8">
            <el-form-item label="收货人" prop="recipient">
              <el-input v-model="form.recipient" placeholder="情输入收货人" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="联系方式" prop="tel">
              <el-input v-model="form.tel" placeholder="情输入联系方式" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="承运商" prop="carrier">
              <el-input v-model="form.carrier" placeholder="请输入承运商" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="form.deliveryFlag == 'Y'">
          <el-col :span="8">
            <el-form-item label="运输单号" prop="shippingNumber">
              <el-input v-model="form.shippingNumber" placeholder="请输入运输单号" />
            </el-form-item>
          </el-col>
          <el-col :span="16">
            <el-form-item label="目的地" prop="destination">
              <el-input v-model="form.destination" placeholder="请输入目的地" />
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
      <el-divider v-if="form.transferId !=null" content-position="center">物料信息</el-divider>
        <el-card shadow="always" v-if="form.transferId !=null && (form.status == 'PREPARE' || form.status == 'UNCONFIRMED' )"  class="box-card">
          <Transferline ref=line :transferId="form.transferId" :optType="optType"></Transferline>
        </el-card>
        <el-card shadow="always" v-if="form.transferId !=null && form.status != 'PREPARE' && form.status != 'UNCONFIRMED'" class="box-card">
          <TransferDetail ref=line :transferId="form.transferId" :optType="optType"></TransferDetail>
        </el-card>
        <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveForm" v-if="form.status =='PREPARE' && optType !='view' ">保 存</el-button>
        <el-button type="warning" @click="submitToStock" v-if="(form.status =='PREPARE' || form.status == 'UNCONFIRMED' )&& form.transferId !=null && optType !='view' ">提 交</el-button>        
        <el-button type="warning" @click="submitToExecute" v-if="form.status =='UNSTOCK' && form.transferId !=null && optType !='view' ">提 交</el-button>
        <el-button type="danger" @click="cancel"  v-if="form.status !='PREPARE' && form.status !='FINISHED' && optType !='view' " >取 消</el-button>
        <el-button @click="close">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listTransfer, getTransfer, delTransfer, addTransfer, updateTransfer, execute} from "@/api/mes/wm/transfer";
import {listWarehouse} from "@/api/mes/wm/warehouse";
import {genCode} from "@/api/system/autocode/rule";
import Transferline from "./line.vue";
import TransferDetail from "./detail.vue";
export default {
  name: "Transfer",
  components: {Transferline,TransferDetail},
  dicts: ['mes_transfer_type','mes_transfer_status','sys_yes_no'],
  data() {
    return {
      //自动生成编码
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
      // 转移单表格数据
      transferList: [],
      warehouseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        transferCode: null,        transferName: null,        transferType: null,  deliveryFlag: null, recipient: null, tel: null, destination: null,        carrier: null,        shippingNumber: null,    transferDate: null,        status: null,      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        transferCode: [
          { required: true, message: "转移单编号不能为空", trigger: "blur" },
          { max: 64, message: "字段过长", trigger: "blur" }
        ],
        transferName: [
          { required: true, message: "转移单名称不能为空", trigger: "blur" },
          { max: 100, message: "字段过长", trigger: "blur" }
        ],
        transferType: [
          { required: true, message: "转移单类型不能为空", trigger: "blur" }
        ],
        transferDate: [
          { required: true, message: "请选择转移时间", trigger: "blur" }
        ],
        remark: [
          { max: 250, message: '长度必须小于250个字符', trigger: 'blur' }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getWarehouseList();
  },
  methods: {
    /** 查询转移单列表 */
    getList() {
      this.loading = true;
      listTransfer(this.queryParams).then(response => {
        this.transferList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    //获取仓库信息
    getWarehouseList(){
      listWarehouse().then(response => {
        this.warehouseList = response.rows;
      });
    },
    // 关闭按钮
    close() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        transferId: null,
        transferCode: null,
        transferName: null,
        transferType: "INNER",
        deliveryFlag: "N",
        confirmFlag: 'N',
        recipient: null,
        tel: null,
        destination: null,
        carrier: null,
        shippingNumber: null,
        transferDate: new Date(),
        status: "PREPARE",
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
      this.autoGenFlag = false;
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
      this.ids = selection.map(item => item.transferId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加转移单";
      this.optType = "add";
    },
    // 查询明细按钮操作
    handleView(row){
      this.reset();
      const transferId = row.transferId
      getTransfer(transferId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "查看转移单信息";
        this.optType = "view";
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const transferId = row.transferId || this.ids
      getTransfer(transferId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改转移单";
        this.optType = "edit";
      });
    },
    /** 到货确认按钮操作 */
    handleConfirm(row) {
      this.reset();
      const transferId = row.transferId || this.ids
      getTransfer(transferId).then(response => {
        this.form = response.data;
        this.form.confirmFlag = 'Y';
        this.open = true;
        this.title = "修改转移单";
        this.optType = "confirm";
      });
    },
    /** 执行上架按钮操作 */
    handleStocking(row){
      this.reset();
      const transferId = row.transferId || this.ids
      getTransfer(transferId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改转移单";
        this.optType = "edit";
      });
    },
    /** 保存按钮 */
    saveForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          debugger;
          if (this.form.transferId != null) {
            updateTransfer(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTransfer(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    //提交按钮(提交到待上架状态)，如果需要培训，后台会更新到UNCONFIRMED状态
    submitToStock(){
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.status = 'UNSTOCK';
          updateTransfer(this.form).then(response => {
            this.$modal.msgSuccess("提交成功");
            this.open = false;
            this.getList();
          } 
         ).catch(() => {
            this.form.status = 'PREPARE';
         });;          
        }
      });
    },
    //提交到待执行转移状态
    submitToExecute(){
      let oldStatus = this.form.status;
      this.form.status = 'UNEXECUTE';
        updateTransfer(this.form).then(response => {
          this.$modal.msgSuccess("提交成功");
          this.open = false;
          this.getList();
        } 
        ).catch(() => {
          this.form.status = oldStatus;
        });;       
    },

    //取消
    cancel(){
      let that = this;
      this.$modal.confirm('确认撤销转移单？').then(function() {        
        const oldStatus = that.form.status;
        that.form.status = 'CANCELED';
        updateTransfer(that.form).then(response => {
            that.$modal.msgSuccess("撤销成功");
            that.open = false;
            that.getList();
          } 
          ).catch(() => {
            that.form.status = oldStatus;
          });
          return true;
      }).catch(() => {});
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const transferIds = row.transferId || this.ids;
      this.$modal.confirm('是否确认删除转移单编号为"' + transferIds + '"的数据项？').then(function() {
        return delTransfer(transferIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    //执行转移
    handleExecute(row){
      const transferIds = row.transferId || this.ids;
      this.$modal.confirm('确认执行转移？').then(function() {
        return execute(transferIds)//执行转移
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("转移成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wm/transfer/export', {
        ...this.queryParams
      }, `transfer_${new Date().getTime()}.xlsx`)
    },
    //自动生成编码
    handleAutoGenChange(autoGenFlag){
      if(autoGenFlag){
        genCode('TRANSFER_CODE').then(response =>{
          this.form.transferCode = response;
        });
      }else{
        this.form.transferCode = null;
      }
    }
  }
};
</script>
