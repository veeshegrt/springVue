<template>
<div>
  <span><el-input style="width: 200px" placeholder="请输入名称" v-model="username"></el-input>
    <el-input style="width: 200px" placeholder="请输入邮箱"></el-input>
    <el-input style="width: 200px" placeholder="请输入地址"></el-input>
    <el-button type="primary" @click="search">搜索</el-button>
  </span>
  <div style="margin-top: 5px"><el-button type="primary" @click="add">新增</el-button>
    <el-button type="danger" @click="batchDel">批量删除</el-button>
    <el-upload action="http://localhost:9090/sysUser/import" :show-file-list="false" style="display: inline-block" :on-success="handleImport">
      <el-button type="primary">导入</el-button>
    </el-upload>
    <el-button type="primary" @click="exp">导出</el-button></div>
  <el-table :data="tableData" @selection-change="handleSelectionChange">
    <el-table-column
      type="selection"
      width="55">
    </el-table-column>
    <el-table-column prop="id" label="ID" width="50">
    </el-table-column>
    <el-table-column prop="username" label="用户名">
    </el-table-column>
    <el-table-column prop="nickname" label="昵称">
    </el-table-column>
    <el-table-column prop="email" label="邮箱">
    </el-table-column>
    <el-table-column prop="phone" label="电话">
    </el-table-column>
    <el-table-column prop="address" label="地址">
    </el-table-column>
    <el-table-column  label="操作" >
      <template slot-scope="scope">
        <el-button type="success" icon="el-icon-edit" @click="edit(scope.row)">修改</el-button>
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
  <el-dialog title="个人信息" :visible.sync="dialogFormVisible">
    <el-form :model="form">
      <el-form-item label="姓名">
        <el-input v-model="form.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="form.nickname" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="form.phone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="地址">
        <el-input v-model="form.address" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</div>
</template>

<script>
import request from "../util/request";

export default {
  name: "User",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      username: '',
      collapse: false,
      sideWidth: 200,
      multipleSelection: [],
      dialogFormVisible: false,
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
      request.get("/getData",{params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          username:this.username
        }
      }).then(res=>{
        this.tableData = res.records;
        this.total = res.total
      })
    },
    add(){
      this.dialogFormVisible = true
    },
    save(){
      request.post("/save",this.form).then(res=>{
        if(res){
          this.dialogFormVisible = false
          this.$message.success("保存成功")
        }else {
          this.dialogFormVisible = false
          this.$message.error("保存失败")
        }
        this.load()
      })
    },
    del(id){
      request.post("/delete/"+id).then(res=>{
          this.load()
        }
      )
    },
    batchDel(){
      let ids = this.multipleSelection.map(v=>v.id)
      request.post("/batchDel",ids).then(res=>{
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
    edit(row){
      this.dialogFormVisible = true
      this.form = row
    },
    handleSizeChange(val){
      this.pageSize = val;
      this.load();
    },
    handleCurrentChange(val){
      this.pageNum = val;
      this.load();
    },
    exp(){
      window.open("http://localhost:9090/sysUser/export")
    },
    handleImport(){
      this.$message.success("导入成功")
      this.load()
    }
  }
}
</script>

<style scoped>

</style>
