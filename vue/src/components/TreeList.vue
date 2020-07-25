<template>
  <el-dialog :title="formTitle"
             :visible.sync="visible"
             width="400px"
             :close-on-click-modal="false"
             @close="cancel">
    <el-tree node-key="id"
             style="height:400px!important;overflow-y: auto;"
             :data="treeData"
             :props="treeProps"
             ref="tree"
             show-checkbox
             :highlight-current="true"
             :default-checked-keys="checkedKeys"
             :default-expand-all="true"></el-tree>
    <div slot="footer"
         class="dialog-footer">
      <el-button @click="cancel()">取消</el-button>
      <el-button @click="ok()"
                 type="primary">确定</el-button>
    </div>
  </el-dialog>

</template>

<script>
export default {
  data () {
    return {
      visible: false,
      formTitle: '赋权',
      tableHeight: '400px',
      treeData: [],
      treeProps: {
        label: 'menuName',
        children: 'childrens'
      },
      type: 'role',
      roleId: null,
      checkedKeys: []
    }
  },
  props: {
    show: {
      type: Boolean,
      default: false
    },
    title: {
      type: String
    },
    ptype: {
      type: String
    },
    pid: {
      type: String
    }
  },
  watch: {
    // 监听show,visible 随着show变化而变化
    show: {
      immediate: true,
      handler (show) {
        this.visible = show
      }
    },
    title: {
      immediate: true,
      handler (title) {
        this.formTitle = title
      }
    },
    model: {

      immediate: true,
      handler (model) {
        this.formModel = model
      }
    },
    type: {
      immediate: true,
      handler (ptype) {
        this.type = ptype
      }
    },
    pid: {
      immediate: true,
      handler (pid) {
        this.roleId = pid
        if (this.roleId) {
          this.loadTree()
        }
      }
    }
  },
  methods: {
    loadTree () {
      if (this.ptype == 'role') {
        this.$http.get('/api/sysMenu/tree-func-list', { params: { roleId: this.roleId } }).then(res => {
          console.log(res)
          this.treeData = res.data.tree
          this.checkedKeys = res.data.checkIds
        }).catch(err => {
          console.log(err.message)
        })
      } else {
        this.$http.get('/api/sysMenu/tree-func-user-list', { params: { userId: this.roleId } }).then(res => {
          console.log(res)
          this.treeData = res.data.tree
          this.checkedKeys = res.data.checkIds
        }).catch(err => {
          console.log(err.message)
        })
      }
    },
    currentChange (nodedata, node) {
      console.log(nodedata)
    },
    /**
     * 隐藏dialog查询框
     * 回调父页面 修改显示隐藏值
     */
    cancel () {
      this.visible = false
      this.$emit('hidden', this.visible)
    },
    /**
     * 调用父页面查询方法
     */
    ok () {
      const nodes = this.$refs.tree.getCheckedNodes().concat(this.$refs.tree.getHalfCheckedNodes())
      this.visible = false
      this.$emit('hidden', this.visible)
      this.$emit('ok', nodes)
    }
  },
  created () {

  }
}
</script>

<style>
</style>
