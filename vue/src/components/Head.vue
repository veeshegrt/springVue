<template>
  <div>
  <el-header style="font-size: 12px;border-bottom: 1px solid #cccc;line-height: 60px;display: flex">
    <div style="flex: 1;font-size: 20px;">
            <span :class=icon style="cursor: pointer" @click="collapseAside">
          </span>
    </div>
    <el-dropdown style="width: 100px;">
      <span>王小虎</span> <i class="el-icon-arrow-down"></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item>
        <router-link to="/login">退出</router-link>
        </el-dropdown-item>
        <el-dropdown-item>个人信息</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </el-header>
  </div>
</template>

<script>
export default {
  name: "Head",
  props:{
    icon:String,
    collapseAside:Boolean
  }
}
</script>

<style scoped>

</style>
