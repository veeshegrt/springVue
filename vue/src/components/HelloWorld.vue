<template>
  <div style="height: 100%;">
    <el-container style="height: 100%; border: 1px solid #eee">
      <Aside :collapse="collapse" :side-width="sideWidth"/>
      <el-container>
        <el-header style="font-size: 12px;border-bottom: 1px solid #cccc;line-height: 60px;display: flex">
          <div style="flex: 1;font-size: 20px;">
            <span :class=icon style="cursor: pointer" @click="collapseAside">
          </span>
          </div>
          <el-dropdown style="width: 100px;">
            <span>{{user.username}}</span> <i class="el-icon-arrow-down"></i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <span style="text-decoration: none" @click="logout">退出</span>
              </el-dropdown-item>
              <el-dropdown-item>
                <router-link to="/person" style="text-decoration: none;color: #000000">个人信息</router-link></el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-header>
        <el-breadcrumb style="margin-top: 10px;margin-left: 20px" separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>{{currentPathName}}</el-breadcrumb-item>
        </el-breadcrumb>
        <el-main>
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Aside from "./Aside";

export default {
  name: 'HelloWorld',
  components:{
    Aside
  },
  created() {
    this.user = JSON.parse(localStorage.getItem("user"))
  },
  computed:{
    currentPathName(){
      return this.$store.state.currentPathName;
    }
  },
  data() {
    return {
      collapse:false,
      sideWidth: 200,
      dialogFormVisible:false,
      icon:'el-icon-s-unfold',
      user:{}
    }
  },

  methods: {
    collapseAside(){
      this.collapse = !this.collapse
      if(this.collapse){
        this.sideWidth = 64
        this.icon = 'el-icon-s-fold'
      }else {
        this.sideWidth = 200
        this.icon = 'el-icon-s-unfold'
      }
    },
    logout(){
      this.$router.push("/login").then(res=>{
        localStorage.removeItem("user")
        this.$message.success("退出成功")
      })
    }
  }
}
</script>


<style scoped>
.el-aside::-webkit-scrollbar {
  display: none;
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
</style>
