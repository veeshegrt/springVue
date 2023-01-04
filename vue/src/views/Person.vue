<template>
<div>
  <el-card style="width: 720px">
    <el-form :model="form" style="width: 480px">
      <el-upload
        class="avatar-uploader"
        action="https://jsonplaceholder.typicode.com/posts/"
        :show-file-list="false"
        :before-upload="beforeAvatarUpload">
        <img v-if="form.avatarUrl" :src="form.avatarUrl" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
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
        address: '',
        avatarUrl:''
      },
    }
  },
  created() {
    const user = JSON.parse(localStorage.getItem("user"))
    this.request.post("/sysUser/find/"+user.username).then(res=>{
      this.form = res.data;
    })
  }
}
</script>

<style scoped>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
  text-align: center;
}
</style>
