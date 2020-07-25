<template>
  <div id="app">
    <router-view />
  </div>
</template>
<script>
export default {
  methods: {
    saveState () {
      localStorage.setItem('state', JSON.stringify(this.$store.state))
    }
  },
  created () {
    this.saveState()
    // 在页面加载时读取sessionStorage里的状态信息
    if (localStorage.getItem('state')) {
      this.$store.replaceState(Object.assign({}, this.$store.state, JSON.parse(localStorage.getItem('state'))))
    }
    // 在页面刷新时将vuex里的信息保存到sessionStorage里
    window.addEventListener('beforeunload', () => {
      localStorage.setItem('state', JSON.stringify(this.$store.state))
    })
  }
}
</script>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
