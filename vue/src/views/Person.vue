<template>
<div>
  <el-card style="width: 720px">
    <el-form :model="form" style="width: 480px">
      <el-form-item label="姓名">
        <el-input :disabled="true" v-model="form.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="昵称">
        <el-input :disabled="true" v-model="form.nickname" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input :disabled="true" v-model="form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="电话">
        <el-input :disabled="true" v-model="form.phone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="地址">
        <el-input :disabled="true" v-model="form.address" autocomplete="off"></el-input>
      </el-form-item>
      <el-button style="margin-left: 620px" @click="$router.push('/user')">确定</el-button>
    </el-form>
  </el-card>
</div>
</template>

<script>
import request from "../util/request";

export default {
  name: "Person",
  data(){
    return{
      form: {
        username: '',
        nickname: '',
        email: '',
        phone: '',
        address: ''
      },
    }
  },
  created() {
    const user = JSON.parse(localStorage.getItem("user"))
    this.request.post("/find/"+user.username).then(res=>{
      this.form = res.data;
    })
  }
}
</script>

<style scoped>

</style>
