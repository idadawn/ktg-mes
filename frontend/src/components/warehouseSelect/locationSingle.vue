<template>
  <el-dialog title="库区选择"
    v-if="showFlag"
    :visible.sync="showFlag"
    :modal= false
    width="80%"
    center
    >
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="库区名称" prop="locationName">
          <el-input
            v-model="queryParams.locationName"
            placeholder="请输入库区名称"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
  
      <el-table v-loading="loading" :data="locationList" @current-change="handleCurrent" @row-dblclick="handleRowDbClick">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="库区编码" align="center" prop="locationCode" >
          <template slot-scope="scope">
            <el-button
              type="text"
              @click="handleView(scope.row)"
              v-hasPermi="['mes:wm:location:query']"
            >{{scope.row.locationCode}}</el-button>
          </template>
        </el-table-column>
        <el-table-column label="库区名称" align="center" prop="locationName" />
        <el-table-column label="面积" align="center" prop="area" />
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
  import { listLocation } from "@/api/mes/wm/location";
  export default {
    name: "Location",
    props: {
        warehouseId: null,
    },
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
        // 库区设置表格数据
        locationList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          locationCode: null,
          locationName: null,
          warehouseId: null,
          area: null,
          areaFlag: null,
        }
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询库区设置列表 */
      getList() {
        this.loading = true;
        listLocation(this.queryParams).then(response => {
          this.locationList = response.rows;
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
  