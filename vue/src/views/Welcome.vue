<template>
  <div>
    <h1>Welcom to Vue App</h1>
  </div>
</template>
<script>
export default {
  data () {
    return {
      userList: []
    }
  },
  methods: {
    loadUser () {
      const _this = this
      this.$http
        .get('/api/sysUser/list', {})
        .then(res => {
          if (res.status == '200') {
            const data = res.data
            if (data.code == 0) {
              _this.data.userList = data.data
            } else {
            }
          } else {
          }
        })
        .catch(err => {
          console.log(err.message)
        })
    }
  },
  created () {
    this.loadUser()
  }
}
</script>
