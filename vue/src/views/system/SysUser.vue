<template>
  <div>
    <div>
      <!--按钮列表-->
      <el-button-group class="toolBox">
        <el-button size="small"
                   v-for="(item,index) in buttonGroups"
                   :key="index"
                   @click="dynamicMethod(item.method,item.params)"
                   :icon="item.icon">{{item.label}}</el-button>
      </el-button-group>
    </div>
    <!--数据表格-->
    <el-row>
      <el-col :span="24">
        <el-table :data="tableData"
                  border
                  stripe
                  highlight-current-row
                  ref="multipleTable"
                  :height="tableHeight"
                  @row-click="handleRowClick"
                  style="width: 100%">
          <el-table-column type="selection"
                           width="55">
          </el-table-column>
          <el-table-column type="index"
                           width="65"
                           label="序号"
                           align="center"
                           fixed
                           :show-overflow-tooltip="true"></el-table-column>
          <template v-for="(el,i) in tableColumns">
            <el-table-column :label="el.label"
                             header-align="center"
                             v-if="el.show "
                             :width="el.width || ''"
                             :key="el.prop"
                             :fixed="el.fixed"
                             :prop="el.prop"
                             :sortable="el.sortable"
                             show-overflow-tooltip>
            </el-table-column>
          </template>
          <el-table-column fixed="right"
                           label="操作"
                           width="300">
            <template slot-scope="scope">
              <el-button @click="handleAuthVClick(scope.row)"
                         type="text"
                         size="small">权限查看</el-button>
              <el-button @click="handleRoleClick(scope.row)"
                         type="text"
                         size="small">分配角色</el-button>
              <el-button type="text"
                         @click="handleDelClick(scope.row)"
                         size="small">删除</el-button>
              <el-button type="text"
                         @click="handleResetClick(scope.row)"
                         size="small">密码重置</el-button>
              <el-button type="text"
                         @click="handleEditClick(scope.row)"
                         size="small">编辑</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <!--分页插件-->
    <el-row>
      <el-col :span="24">
        <el-pagination @size-change="handleSizeChange"
                       @current-change="handleCurrentChange"
                       :current-page="current"
                       :page-sizes="pageSizeOptions"
                       :page-size="size"
                       layout="total, sizes, prev, pager, next, jumper"
                       :total="total">
        </el-pagination>
      </el-col>
    </el-row>
    <!--列自定义-->
    <CustomTableCols :defaultCols="defaultColumns"
                     customName="user"
                     @changeColumns="changeColumns" />
    <!--查询条件-->
    <Search :show.sync="showSearch"
            :condition="searchCondition"
            :form="searchForm"
            @ok="setCondition"
            @hidden="showSearch=false" />
    <!--新增编辑页面-->
    <CustomForm :show.sync="showForm"
                title="用户编辑"
                :control="userControl"
                :model="userForm"
                :rules="userRules"
                @ok="saveForm"
                @hidden="showForm=false" />
    <!--分配角色-->
    <el-dialog title="角色分配"
               :visible.sync="visible"
               width="400px"
               @close="visible=false">
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
        <el-button @click="visible=false">取消</el-button>
        <el-button @click="selectAll()"
                   type="primary">全选</el-button>
        <el-button @click="submitRole()"
                   type="primary">确定</el-button>
      </div>
    </el-dialog>
    <!--赋权页面-->
    <treelist :show.sync="showTree"
              title="权限查看"
              ptype="user"
              :pid="userId"
              @ok="showTree=false"
              @hidden="showTree=false" />
  </div>

</template>

<script>
import CustomTableCols from '../../components/CustomTableCols'
import Search from '../../components/Search'
import CustomForm from '../../components/CustomForm'
import treelist from '../../components/TreeList'
export default {
  components: {
    CustomTableCols,
    Search,
    CustomForm,
    treelist
  },
  data () {
    var validateComfirmPwd = (rule, value, callback) => {
      if (this.userForm.cmfPassWord !== value) {
        callback(new Error('确认密码与新密码不一致！'))
      } else {
        callback()
      }
    }
    return {
      initData: {},
      tableData: [],
      tableHeight: '400px',
      current: 1,
      size: 10,
      total: 0,
      pageSizeOptions: [10, 20, 50, 100],
      searchForm: { loginName: '', nickName: '', qq: '', email: '', isEnable: '' },
      searchCondition: [
        { index: 0, label: '账号', field: 'loginName', type: 'input', show: true },
        { index: 1, label: '昵称', field: 'nickName', type: 'input', show: true },
        { index: 2, label: '手机', field: 'mobilePhone', type: 'input', show: true },
        { index: 3, label: 'QQ', field: 'qq', type: 'input', show: true },
        { index: 4, label: '邮箱', field: 'email', type: 'input', show: true },
        { index: 5, label: '有效', field: 'isEnable', type: 'input', show: true }
      ],
      showSearch: false,
      buttonGroups: [
        { index: 0, label: '查询', method: 'showCondition', icon: 'el-icon-search' },
        { index: 1, label: '新建', method: 'addAndEdit', icon: 'el-icon-plus' },
        { index: 2, label: '禁用', method: 'enableUser', params: false, icon: 'el-icon-delete' },
        { index: 3, label: '启用', method: 'enableUser', params: true, icon: 'el-icon-success' },
        { index: 4, label: '分配角色', method: 'setRole', icon: 'el-icon-s-cooperation' },
        { index: 4, label: '密码重置', method: 'restPwd', icon: 'el-icon-refresh' },
        { index: 5, label: '删除', method: 'delete', icon: 'el-icon-delete' },
        { index: 6, label: '刷新', method: 'searchData', icon: 'el-icon-refresh' }
      ],
      tableColumns: [],
      defaultColumns: [
        { label: '账号', prop: 'loginName', show: true, fixed: false, sortable: false, width: 200 },
        { label: '昵称', prop: 'nickName', show: true, fixed: false, sortable: false, width: 200 },
        { label: '手机', prop: 'mobilePhone', show: true, fixed: false, sortable: false },
        { label: '邮箱', prop: 'email', show: true, fixed: false, sortable: false },
        { label: 'QQ', prop: 'qq', show: true, fixed: false, sortable: false },
        { label: '是否有效', prop: 'isEnable', show: true, fixed: false, sortable: false },
        { label: '到期时间', prop: 'expireTime', show: true, fixed: false, sortable: false }
      ],
      showForm: false,
      userForm: { id: '', loginName: '', nickName: '', passWord: '', cmfPassWord: '', mobilePhone: '', qq: '', email: '', isEnable: '' },
      userControl: [
        { label: 'ID', field: 'id', type: 'hidden', show: false, readonly: true },
        { label: '账号', field: 'loginName', type: 'input', show: true, readonly: true },
        { label: '昵称', field: 'nickName', type: 'input', show: true, readonly: false },
        { label: '密码', field: 'passWord', type: 'password', show: true, readonly: false },
        { label: '密码确认', field: 'cmfPassWord', type: 'password', show: true, readonly: false },
        { label: '手机', field: 'mobilePhone', type: 'input', show: true, readonly: false },
        { label: 'QQ', field: 'qq', type: 'input', show: true },
        { label: '邮箱', field: 'email', type: 'input', show: true }
      ],
      userRules: {
        loginName: [
          { required: true, message: '请输入账号', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        nickName: [
          { required: true, message: '请输入昵称', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur' }
        ],
        passWord: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        cmfPassWord: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' },
          { validator: validateComfirmPwd, trigger: 'blur' }
        ],
        mobilePhone: [
          { required: true, message: '请输入手机号', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' }
        ]
      },
      multipleSelection: [],
      visible: false,
      treeData: [],
      treeProps: {
        label: 'roleName'
      },
      checkedKeys: [],
      userId: null,
      showTree: false
    }
  },

  methods: {
    searchData () {
      this.$http
        .get('/api/sysUser/list', {
          params: this._handerParams()
        })
        .then(res => {
          if (res.code == 0) {
            this.tableData = res.data.records
            this.total = res.data.total
            this.current = res.data.current
            this.size = res.data.size
          } else {
            this.$message.error(res.msg)
          }
        })
        .catch(err => {
          console.log(err.message)
        })
    },
    /**
     * 查询条件处理
     */
    _handerParams () {
      const params = {
        current: this.current,
        size: this.size,
        loginName: this.searchForm.loginName,
        nickName: this.searchForm.nickName,
        mobilePhone: this.searchForm.mobilePhone,
        email: this.searchForm.email,
        qq: this.searchForm.qq,
        isEnable: this.searchForm.isEnable
      }
      return params
    },
    /**
     * 新增修改数据
     */
    addAndEdit () {
      // 设置账号栏位可编辑
      for (const item in this.userForm) {
        this.userForm[item] = ''
      }
      this.userControl[1].readonly = false
      this.showForm = true
    },
    /**
     * 是否启用/禁用用户
     */
    enableUser (isEnable) {
      console.log(this.multipleSelection)
      if (this.$refs.multipleTable.selection.length <= 0) {
        this.$message.warning('请选择要操作的用户')
        return
      }
      const idArray = []
      this.$refs.multipleTable.selection.forEach(element => {
        idArray.push(element.id)
      })
      this.$http.post('/api/sysUser/enable?enable=' + isEnable, idArray).then(res => {
        if (res.code == '0') {
          this.$message.success(res.msg)
          this.searchData()
        } else {
          this.$message.error(res.msg)
        }
      }).catch(err => {
        console.log(err.message)
      })
    },

    /**
     * 选择事件
     */
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    // 点击行触发，选中或不选中复选框
    handleRowClick (row, column, event) {
      this.$refs.multipleTable.toggleRowSelection(row)
    },

    /**
     * 动态调用方法
     */
    dynamicMethod (methodname, params) {
      this[methodname](params)
    },
    /**
     * 展示查询条件
     */
    showCondition () {
      this.showSearch = true
      console.log(this.showSearch)
    },
    /**
     * 设置查询条件
     */
    setCondition (from) {
      const newData = JSON.parse(JSON.stringify(from))
      this.searchForm = newData
      this.searchData()
    },

    /**
     * 保存表单
     */
    saveForm (from) {
      const newData = JSON.parse(JSON.stringify(from))
      this.userForm = newData
      this.$http.post('/api/sysUser/save', this.userForm).then(res => {
        if (res.code == '0') {
          this.$message.success(res.msg)
          this.showForm = false
          this.searchData()
        } else {
          this.$message.error(res.msg)
        }
      }).catch(err => {
        console.log(err.message)
      })
    },
    /**
     * 删除用户
     */
    handleDelClick (row) {
      // 设置账号栏位不可编辑
      this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const idArray = []
        this.$refs.multipleTable.selection.forEach(element => {
          idArray.push(element.id)
        })
        this.$http.post('/api/sysUser/delete', idArray).then(res => {
          if (res.code == '0') {
            this.$message.success(res.msg)
            this.searchData()
          } else {
            this.$message.error(res.msg)
          }
        }).catch(err => {
          console.log(err.message)
        })
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    },
    /**
     * 密码重置
     */
    handleResetClick (row) {
      this.$http.post('/api/sysUser/rest-password', [row.id]).then(res => {
        if (res.code == '0') {
          this.$message.success(res.msg)
          this.searchData()
        } else {
          this.$message.error(res.msg)
        }
      }).catch(err => {
        console.log(err.message)
      })
    },
    /**
   * 角色分配
   */
    handleRoleClick (row) {
      this.visible = true
      this.userId = row.id
      this.$http.get('/api/sysRoleUser/role-list-set', { params: { userId: row.id } }).then(res => {
        console.log(res)
        this.treeData = res.data.tree
        this.checkedKeys = res.data.checkIds
      }).catch(err => {
        console.log(err.message)
      })
    },
    /**
     * 修改数据
     */
    handleEditClick (row) {
      // 设置账号栏位不可编辑
      for (const item in this.userForm) {
        this.userForm[item] = ''
      }
      this.userControl[1].readonly = true
      this.showForm = true
      this.userForm.loginName = row.loginName
      this.userForm.nickName = row.nickName
      this.userForm.qq = row.qq
      this.userForm.email = row.email
      this.userForm.mobilePhone = row.mobilePhone
      this.userForm.id = row.id
    },
    /**
     * 授权
     */
    handleAuthClick () {
    },
    /**
     *权限查看
     */
    handleAuthVClick (row) {
      this.userId = row.id
      this.showTree = true
    },
    /**
     * 分配角色
     */
    setRole () {
      if (this.$refs.multipleTable.selection.length <= 0) {
        this.$message.warning('请选择要操作的用户')
      }
      if (this.$refs.multipleTable.selection.length > 1) {
        this.$message.warning('只能选择一个用户')
      }
      const id = this.$refs.multipleTable.selection[0].id
      this.userId = id
      this.visible = true
      this.$http.get('/api/sysRoleUser/role-list-set', { params: { userId: id } }).then(res => {
        console.log(res)
        this.treeData = res.data.tree
        this.checkedKeys = res.data.checkIds
      }).catch(err => {
        console.log(err.message)
      })
    },
    // 树形全选操作
    selectAll () {
      var keys = []
      if (this.treeData.length > this.$refs.tree.getCheckedNodes().length) {
        this.treeData.map(function (item) {
          keys.push(item.id)
        })
        this.$refs.tree.setCheckedKeys(keys)
      } else {
        this.$refs.tree.setCheckedKeys([])
      }
    },

    /**
     * 角色分配提交
     */
    submitRole () {
      const nodes = this.$refs.tree.getCheckedNodes()
      this.$http.post('/api/sysRoleUser/save?userId=' + this.userId, nodes).then(res => {
        this.visible = false
        if (res.code == '0') {
          this.$message.success(res.msg)
          this.searchData()
        } else {
          this.$message.error(res.msg)
        }
      }).catch(err => {
        console.log(err.message)
      })
    },
    /**
     * 重置密码
     */
    restPwd () {
      if (this.$refs.multipleTable.selection.length <= 0) {
        this.$message.warning('请选择要操作的用户')
        return
      }
      const idArray = []
      this.$refs.multipleTable.selection.forEach(element => {
        idArray.push(element.id)
      })
      this.$http.post('/api/sysUser/rest-password', idArray).then(res => {
        if (res.code == '0') {
          this.$message.success(res.msg)
          this.searchData()
        } else {
          this.$message.error(res.msg)
        }
      }).catch(err => {
        console.log(err.message)
      })
    },
    /**
     * 删除用户
     */
    delete () {
      if (this.$refs.multipleTable.selection.length <= 0) {
        this.$message.warning('请选择要操作的用户')
        return
      }
      this.$confirm('此操作将永久删除选择的用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const idArray = []
        this.$refs.multipleTable.selection.forEach(element => {
          idArray.push(element.id)
        })
        this.$http.post('/api/sysUser/delete', idArray).then(res => {
          if (res.code == '0') {
            this.$message.success(res.msg)
            this.searchData()
          } else {
            this.$message.error(res.msg)
          }
        }).catch(err => {
          console.log(err.message)
        })
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    },
    /**
     * 自定义列修改
     */
    changeColumns (val) {
      console.log('changeColumns--' + val)
      this.tableColumns = []
      this.$nextTick(() => {
        this.tableColumns = val
      })
    },
    /**
     * table每页数字变化
     */
    handleSizeChange (val) {
      console.log(`每页 ${val} 条`)
      this.size = val
      this.searchData()
    },
    /**
     * table页数变化
     */
    handleCurrentChange (val) {
      console.log(`当前页: ${val}`)
      this.current = val
      this.searchData()
    }
  },
  created () {
    this.tableHeight = document.documentElement.clientHeight - 240
    //
    this.tableColumns = this.defaultColumns
    this.searchData()
  }
}
</script>
