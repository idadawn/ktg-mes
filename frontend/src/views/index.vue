<template>
  <div class="dashboard-editor-container">

    <panel-group @handleSetLineChartData="handleSetLineChartData" />

    <el-row :gutter="32" style="padding:16px 16px 0;margin-bottom:32px;">
        <el-card>
            <div slot="header" class="clearfix">
                <span>生产进度</span>
            </div>
          <el-table
            v-loading="loading"
            :data="workorderList"
            row-key="workorderId"
            default-expand-all
            :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
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
            <el-table-column label="订单编号" align="center" prop="sourceCode" />
            <el-table-column label="工单名称" align="center" prop="workorderName" :show-overflow-tooltip="true"/>
            <el-table-column label="产品编号" width="120" align="center" prop="productCode" />
            <el-table-column label="产品名称" align="center" prop="productName" :show-overflow-tooltip="true"/>
            <el-table-column label="规格型号" width="140" align="center" prop="productSpc" :show-overflow-tooltip="true"/>
            <el-table-column label="单位" width="140" align="center" prop="unitOfMeasure" />
            <el-table-column label="工单数量" width="100" align="center" prop="quantity" />
            <el-table-column label="已生产数量" width="100" align="center" prop="quantityProduced" />
            <el-table-column label="需求日期" align="center" prop="requestDate">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.requestDate, '{y}-{m}-{d}') }}</span>
              </template>
            </el-table-column>
            <el-table-column label="工序进度" align="center" width="350">
              <template slot-scope="scope">
                  <div style="display: flex;width: 100%;overflow:auto;">
                  <div v-for="(item, index) in scope.row.routeHomg" style="flex-shrink: 0;">
                    <div style="display: flex">
                      <div style="line-height: 70px; text-align: center;color: #5e75ff;flex-shrink:0;" v-if="index != 0">--------</div>
                      <div style="display: flex; flex-direction: column;margin: 0 5px">
                        <el-progress :height="70" :width="70" type="circle" :percentage="parseFloat(((item.completeNumber/item.total)*100).toFixed(2))" :color="colors"></el-progress>
                        <el-tooltip class="item" effect="dark" :content="item.processName" placement="bottom">
                          <span style="display: inline-block;width: 55px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap">{{ item.processName }}</span>
                        </el-tooltip>
                      </div>
                    </div>
                  </div>
                </div>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
    </el-row>
    <el-row :gutter="16" style="display: flex; flex-wrap: wrap;">
      <el-col :span="24" style="display: flex; flex-wrap: wrap;">
        <div v-for="(item, index) in chartList" :key="index" style="margin-bottom: 32px; padding: 0 8px;">
          <el-card style="width: calc(33.333% - 16px); min-width: 400px; box-sizing: border-box;">
            <div slot="header" class="clearfix"> {{ item.chartName }}</div>
            <div style="padding: 16px;">
              <Chart :chartOptions="item.options" :api="item.api"></Chart>
            </div>
          </el-card>
        </div>
      </el-col>
    </el-row>


  </div>
</template>

<script>
import {getHomeList} from "@/api/mes/pro/workorder";
import {getMyCharts} from "@/api/mes/report/chart";
import Chart from "./mes/report/chart/chart.vue"
import PanelGroup from './dashboard/PanelGroup'
import LineChart from './dashboard/LineChart'
import RaddarChart from './dashboard/RaddarChart'
import PieChart from './dashboard/PieChart'
import BarChart from './dashboard/BarChart'

const lineChartData = {
  newVisitis: {
    expectedData: [100, 120, 161, 134, 105, 160, 165],
    actualData: [120, 82, 91, 154, 162, 140, 145]
  },
  messages: {
    expectedData: [200, 192, 120, 144, 160, 130, 140],
    actualData: [180, 160, 151, 106, 145, 150, 130]
  },
  purchases: {
    expectedData: [80, 100, 121, 104, 105, 90, 100],
    actualData: [120, 90, 100, 138, 142, 130, 130]
  },
  shoppings: {
    expectedData: [130, 140, 141, 142, 145, 150, 160],
    actualData: [120, 82, 91, 154, 162, 140, 130]
  }
}

export default {
  name: 'Index',
  components: {
    Chart,
    PanelGroup,
    LineChart,
    RaddarChart,
    PieChart,
    BarChart
  },
  data() {
    return {
        loading: true,
        queryParams: {status:'CONFIRMED'},
        workorderList: [],
        chartList: [],
        lineChartData: lineChartData.newVisitis,
      colors: [
        {color: '#f56c6c', percentage: 20},
        {color: '#e6a23c', percentage: 40},
        {color: '#5cb87a', percentage: 60},
        {color: '#1989fa', percentage: 80},
        {color: '#6f7ad3', percentage: 100}
      ]
    }
  },
  created(){
      this.getList();
      this.getCharts();
  },
  methods: {
    getList() {
      this.loading = true;
      getHomeList(this.queryParams).then(response => {
        this.workorderList = response.data;
        this.loading = false;
      });
    },
    getCharts(){
      getMyCharts().then(response => {
        this.chartList = response.data.map(item => {
            if(typeof item.options === 'string'){
              try{
                item.options = JSON.parse(item.options);
              } catch(e) {
                console.log(e);
                item.options = {};
              }
            }
            return item;
          });;
      });
    },
    handleSetLineChartData(type) {
      this.lineChartData = lineChartData[type]
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

.el-card {
  flex: 1 1 calc(33.333% - 16px); /* 每个卡片占据三分之一的宽度 */
  margin-bottom: 32px;
  box-sizing: border-box;
  min-width: 400px;
}

@media (max-width: 1200px) {
  .el-card {
    flex: 1 1 calc(50% - 16px); /* 在较小的屏幕上每个卡片占据一半的宽度 */
  }
}

@media (max-width: 768px) {
  .el-card {
    flex: 1 1 100%; /* 在更小的屏幕上每个卡片占据整行的宽度 */
  }
}


@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
