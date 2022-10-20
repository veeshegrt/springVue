<template>
  <div>
  <span><el-input style="width: 200px" placeholder="请输入名称" v-model="fileName"></el-input>
    <el-button type="primary" @click="search">搜索</el-button>
  </span>
    <div style="margin-top: 5px">
      <el-button type="danger" @click="batchDel">批量删除</el-button>
      <el-upload action="http://localhost:9090/file/upload" :show-file-list="false" style="display: inline-block" :on-success="handleImport">
        <el-button type="primary" icon="el-icon-upload">上传</el-button>
      </el-upload>
    </div>
    <el-table :data="tableData" @selection-change="handleSelectionChange">
      <el-table-column
        type="selection"
        width="55">
      </el-table-column>
      <el-table-column prop="id" label="ID" width="50">
      </el-table-column>
      <el-table-column prop="fileName" label="文件名">
      </el-table-column>
      <el-table-column prop="size" label="文件大小(Kb)">
      </el-table-column>
      <el-table-column prop="enable" label="是否启用">
        <template slot-scope="scope">
        <el-switch
          v-model="scope.row.enable"
          active-color="#13ce66"
          inactive-color="#ff4949">
        </el-switch>
        </template>
      </el-table-column>
      <el-table-column  label="操作" >
        <template slot-scope="scope">
          <el-button type="primary" icon="el-icon-download" @click="download(scope.row.url)">下载</el-button>
          <el-popconfirm
            confirm-button-text='好的'
            cancel-button-text='不用了'
            icon="el-icon-info"
            icon-color="red"
            title="确定删除吗？"
            @confirm="del(scope.row.id)"
          >
            <el-button type="danger" icon="el-icon-delete" slot="reference">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pageNum"
      :page-sizes="[5, 10, 15, 20]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
  </div>
</template>

<script>
import request from "../util/request";

export default {
  name: "Files",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      fileName: '',
      collapse: false,
      sideWidth: 200,
      multipleSelection: [],
      value: true,
      form: {
        username: '',
        nickname: '',
        email: '',
        phone: '',
        address: ''
      },
      icon: 'el-icon-s-unfold'
    }
  },
  created(){
    this.load();
  },
  methods:{
    load(){
      request.get("/file/getFiles",{params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          fileName:this.fileName
        }
      }).then(res=>{
        this.tableData = res.data.records;
        this.total = res.data.total;
      })
    },
    del(id){
      request.post("/sysUser/delete/"+id).then(res=>{
          this.load()
        }
      )
    },
    batchDel(){
      let ids = this.multipleSelection.map(v=>v.id)
      request.post("/sysUser/batchDel",ids).then(res=>{
        if(res){
          this.$message.success("删除成功")
        }else {
          this.$message.error("删除失败")
        }
        this.load()
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    search(){
      this.load();
    },
    handleSizeChange(val){
      this.pageSize = val;
      this.load();
    },
    handleCurrentChange(val){
      this.pageNum = val;
      this.load();
    },
    handleImport(){
      this.$message.success("导入成功")
      this.load()
    },
    download(url){
      window.open(url)
    }
  }
}
</script>

<style scoped>

</style>
