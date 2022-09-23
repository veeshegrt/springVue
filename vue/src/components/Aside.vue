<template>
<div>
  <el-aside width="sideWidth + 'px'" style="background-color: rgb(2,144,255);height: 100%">
    <el-menu :default-openeds="['1', '3']" style="height: 100%;overflow-x: hidden;"
             background-color="rgb(48,65,86)"
             text-color="#fff"
             active-text-color="#ffd04b"
             :collapse-transition="false"
             :collapse=collapse
              router>
      <div style="height: 60px;line-height: 60px;text-align: center">
        <img src="../assets/logo.png" style="width: 20px;position:relative;top: 5px">
        <b style="color: white;margin-left: 3px" v-show="!collapse">后台管理系统</b>
      </div>
      <el-menu-item index="/home">
        <i class="el-icon-house"></i>
        <span slot="title">主页</span>
      </el-menu-item>
      <el-submenu index="">
        <template slot="title"><i class="el-icon-menu"></i>
          <span>系统管理</span></template>
        <el-menu-item-group>
          <el-menu-item index="/user">用户管理</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
    </el-menu>
  </el-aside>
</div>
</template>

<script>
export default {
  name: "Aside",

  props:{
    collapse:Boolean,
    sideWidth:Number
  },
  data(){
    return{

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
