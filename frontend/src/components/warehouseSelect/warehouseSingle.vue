<template>
    <el-dialog title="仓库选择"
    v-if="showFlag"
    :visible.sync="showFlag"
    :modal= false
    width="80%"
    center
    >
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="仓库编码" prop="warehouseCode">
          <el-input
            v-model="queryParams.warehouseCode"
            placeholder="请输入仓库编码"
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
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
  
      <el-table v-loading="loading" :data="warehouseList" @current-change="handleCurrent" @row-dblclick="handleRowDbClick">
        <el-table-column  width="55" align="center" >
        <template v-slot="scope">
          <el-radio v-model="selectedId" :label="scope.row.warehouseId" @change="handleRowChange(scope.row)">{{""}}</el-radio>
        </template>
      </el-table-column>
        <el-table-column label="仓库编码" align="center" prop="warehouseCode" >
        </el-table-column>
        <el-table-column label="仓库名称" align="center" prop="warehouseName" />
        <el-table-column label="位置" align="center" prop="location" :show-overflow-tooltip="true"/>
        <el-table-column label="面积" align="center" prop="area" >
          <template slot-scope="scope">
            {{scope.row.area}} ㎡
          </template>
        </el-table-column>
        <el-table-column label="负责人" align="center" prop="charge" />
        <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true"/>
      </el-table>
  
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />

      <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="confirmSelect">确 定</el-button>
      <el-button @click="showFlag=false">取 消</el-button>
    </div>
  </el-dialog>
  </template>
  
  <script>
  import { listWarehouse } from "@/api/mes/wm/warehouse";
  export default {
    name: "Warehouse",
    data() {
      return {
        showFlag:false,
        selectedId: undefined,
        selectedRow: undefined,
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
        // 仓库设置表格数据
        warehouseList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          warehouseCode: null,
          warehouseName: null,
          location: null,
          area: null,
          charge: null,
        }
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询仓库设置列表 */
      getList() {
        this.loading = true;
        listWarehouse(this.queryParams).then(response => {
          this.warehouseList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
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
      handleCurrent(row){
        if(row){
            this.selectedRow = row;
        }
    },
    //行双击选中
    handleRowDbClick(row){
      if(row){
        this.selectedRow = row;
        this.$emit('onSelected',this.selectedRow);
        this.showFlag = false;
      }
    },
    // 单选选中数据
    handleRowChange(row) {
      debugger;
      if(row){
        this.selectedRow = row;
      }
    },
    //确定选中
    confirmSelect(){
        if(this.selectedId ==null || this.selectedId==0){
            this.$notify({
                title:'提示',
                type:'warning',
                message: '请至少选择一条数据!'
            });
            return;
        }
        this.$emit('onSelected',this.selectedRow);
        this.showFlag = false;
    }
    }
  };
  </script>
  