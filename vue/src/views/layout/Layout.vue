<template>
  <el-container class="home-container">
    <!--头部-->
    <el-header>
      <div>
        <img src="../../assets/logo.png"
             alt />
        <span>后台管理系统</span>
      </div>
      <el-button type="info"
                 @click="logout()">退出</el-button>
    </el-header>

    <el-container>
      <!--左侧菜单-->
      <el-aside :width="isCollapse ? '64px' : '200px'">
        <Menu v-bind:isCollapse="isCollapse" />
        <div class="toggle-button"
             @click="toggleCollapse">
          <i :class="isCollapse?'el-icon-d-arrow-right':'el-icon-d-arrow-left'"></i>
        </div>
      </el-aside>
      <!--右侧主体-->
      <el-main>
        <el-card>
          <router-view></router-view>
        </el-card>
      </el-main>
    </el-container>
  </el-container>
</template>
<script>
// import Header from './Header.vue'
import Menu from './Menu.vue'
export default {
  data () {
    return {
      isCollapse: false
    }
  },
  methods: {
    toggleCollapse () {
      this.isCollapse = !this.isCollapse
    },
    logout () {
      const _this = this
      this.$http
        .post('/api/logout', {})
        .then(res => {
          if (res.code == 0) {
            _this.$store.commit('clear_state')
            const vue = this
            vue.$router.push('/login')
          } else {
            _this.$message.error(res.msg)
          }
        })
        .catch(err => {
          console.log(err.message)
        })
    }
  },
  components: {
    Menu
  }
}
</script>
<style lang="less" scoped>
.home-container {
  height: 100%;
}
.el-header {
  background-color: #20a0ff !important;
  display: flex;
  justify-content: space-between;
  padding-left: 5px;
  align-items: center;
  color: #fff;
  font-size: 20px;
  div {
    display: flex;
    align-items: center;
    span {
      margin-left: 15px;
    }
    img {
      height: 100%;
      height: 50px;
    }
  }
}
.el-aside {
  background-color:white;
  .el-menu {
    border-right: none;
    text-align: left;
  }
}
.el-main {
  background-color: #eaedf1;
  padding: 10px;
}
.toggle-button {
  background-color: white;
  font-size: 10px;
  line-height: 24px;
  color: #000000;
  text-align: center;
  letter-spacing: 0.2em;
  cursor: pointer;
  border-top:solid 1px #cccccc;
  border-bottom:solid 1px #cccccc;
}
.el-tabs__header {
  background-color: red !important;
}
</style>
