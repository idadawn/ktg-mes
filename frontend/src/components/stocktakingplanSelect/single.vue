<template>
  <el-dialog title="供应商选择"
    v-if="showFlag"
    :visible.sync="showFlag"
    :modal= false
    width="80%"
    center
    >
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
        <el-form-item label="方案编号" prop="planCode">
          <el-input
            v-model="queryParams.planCode"
            placeholder="请输入方案编号"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="方案名称" prop="planName">
          <el-input
            v-model="queryParams.planName"
            placeholder="请输入方案名称"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="盘点类型" prop="takingType">
          <el-select v-model="queryParams.takingType" placeholder="请选择盘点类型">
                  <el-option
                    v-for="dict in dict.type.mes_stock_taking_type"
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

      <el-table v-loading="loading" :data="stocktakingplanList"  @current-change="handleCurrent" @row-dblclick="handleRowDbClick">
        <el-table-column type="selection" width="55" align="center" >
            <template v-slot="scope">
                <el-radio v-model="selectedId" :label="scope.row.planId" @change="handleRowChange(scope.row)">{{""}}</el-radio>
            </template>
        </el-table-column>
        <el-table-column label="方案编号" align="center" prop="planCode" >
        </el-table-column>
        <el-table-column label="方案名称" align="center" prop="planName" />
        <el-table-column label="盘点类型" align="center" prop="takingType" >
          <template slot-scope="scope">
            <dict-tag :options="dict.type.mes_stock_taking_type" :value="scope.row.takingType"/>
          </template>
        </el-table-column>
        <el-table-column label="开始时间" align="center" prop="startTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="结束时间" align="center" prop="endTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="是否盲盘" align="center" prop="blindFlag" >
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.blindFlag"/>
          </template>
        </el-table-column>
        <el-table-column label="是否库存冻结" align="center" prop="frozenFlag" >
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.frozenFlag"/>
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
      <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="confirmSelect">确 定</el-button>
      <el-button @click="showFlag=false">取 消</el-button>
    </div>
  </el-dialog>
  </template>
  
  <script>
  import { listStocktakingplan } from "@/api/mes/wm/stocktakingplan";
  export default {
    name: "Stocktakingplan",
    dicts: ['mes_stock_taking_type','sys_yes_no'],
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
        // 库存盘点方案表格数据
        stocktakingplanList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          planCode: null,        planName: null,        takingType: null,        startTime: null,        endTime: null,        blindFlag: null,        frozenFlag: null,        enableFlag: 'Y',        dataSql: null,        status: null,      },
      };
    },
    created() {
      this.getList();
    },
    methods: {
      handleOpen(id) {
        this.showFlag = true
        this.getList();
        this.selectedId = id
      },
      /** 查询库存盘点方案列表 */
      getList() {
        this.loading = true;
        listStocktakingplan(this.queryParams).then(response => {
          this.stocktakingplanList = response.rows;
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
  