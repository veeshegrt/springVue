<template>
  <div>
    <el-row :gutter="20" style="margin-bottom: 30px">
      <el-col :span="6">
        <el-card style="color: #F56C6C">
          <div><i class="el-icon-user-solid"></i>总用户数</div>
          <div style="text-align: center">2000</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #E6A23C">
          <div><i class="el-icon-money"></i>销售额</div>
          <div style="text-align: center">2000</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #67C23A">
          <div><i class="el-icon-truck">
          </i> 机器总数</div>
          <div style="text-align: center">2000</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #409EFF">
          <div><i class="el-icon-s-shop"></i> 门店数</div>
          <div style="text-align: center">2000</div>
        </el-card>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <div id="main" style="height: 520px;width: 760px"></div>
      </el-col>
      <el-col :span="12">
        <div id="pi" style="height: 520px;width: 760px"></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: "Home",
  data(){
    return {
      yData:[],
      piData:[]
    }
  },
  created() {
    this.request.get("/sysUser/echartsData").then(res=>{
      this.yData = res.data[1]
      var chartDom = document.getElementById('main');
      var myChart = echarts.init(chartDom);
      var option;
      option = {
        title: {
          text: '用户数统计',
          subtext: '柱线图',
          left: 'center',
        },
        xAxis: {
          type: 'category',
          data: ['第一季度', '第二季度', '第三季度', '第四季度']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: this.yData,
            type: 'bar'
          },
          {
            data: this.yData,
            type: 'line'
          }
        ]
      };

      myChart.setOption(option);

      this.piData = res.data[0]
      var piDom = document.getElementById('pi');
      var piChart = echarts.init(piDom);
      var piOption;
      piOption = {
        title: {
          text: '用户数比例',
          subtext: '饼图',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            type: 'pie',
            radius: '70%',
            label: {
              normal: {
                show: true,
                position: "inside",
                formatter: '{b}({d}%)'
              }
            },
            data: this.piData,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      piChart.setOption(piOption)
    })
  }
}
</script>

<style scoped>

</style>
