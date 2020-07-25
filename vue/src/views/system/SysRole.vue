<template>
  <div>
    <div>
      <!--按钮列表-->
      <el-button-group class="toolBox">
        <el-button size="small"
                   v-for="(item,index) in buttonGroups"
                   :key="index"
                   @click="dynamicMethod(item.method)"
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
                  :height="tableHeight"
                  style="width: 100%"
                  @row-click="handleRowClick"
                  ref="multipleTable">
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
                             show-overflow-tooltip>
            </el-table-column>
          </template>
          <el-table-column fixed="right"
                           label="操作"
                           width="200">
            <template slot-scope="scope">
              <el-button type="text"
                         @click="handleAuthClick(scope.row)"
                         size="small">赋权</el-button>
              <el-button type="text"
                         @click="handleDeleteClick(scope.row)"
                         size="small">删除</el-button>
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
                     customName="sysrole"
                     @changeColumns="changeColumns" />
    <!--查询条件-->
    <Search :show.sync="showSearch"
            :condition="searchCondition"
            :form="searchForm"
            @ok="setCondition"
            @hidden="showSearch=false" />
    <!--新增编辑页面-->
    <CustomForm :show.sync="showForm"
                title="角色编辑"
                :control="roleControl"
                :model="roleForm"
                :rules="roleRules"
                @ok="saveForm"
                @hidden="showForm=false" />
    <!--赋权页面-->
    <treelist :show.sync="showTree"
              title="角色赋权"
              ptype="role"
              :pid="roleId"
              @ok="saveTree"
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
    return {
      tableData: [],
      tableHeight: '400px',
      current: 1,
      size: 10,
      total: 0,
      pageSizeOptions: [10, 20, 50, 100],
      searchForm: { roleCode: '', roleName: '' },
      searchCondition: [
        { index: 0, label: '角色代码', field: 'roleCode', type: 'input', show: true },
        { index: 1, label: '角色名称', field: 'roleName', type: 'input', show: true }
      ],
      showSearch: false,
      buttonGroups: [
        { index: 0, label: '查询', method: 'showCondition', icon: 'el-icon-search' },
        { index: 1, label: '新建', method: 'add', icon: 'el-icon-plus' },
        { index: 3, label: '删除', method: 'delete', icon: 'el-icon-delete' },
        { index: 4, label: '赋权', method: 'setAuth', icon: 'el-icon-success' },
        { index: 5, label: '刷新', method: 'searchData', icon: 'el-icon-refresh' }
      ],
      tableColumns: [],
      defaultColumns: [
        { label: '角色代码', prop: 'roleCode', show: true, fixed: false, sortable: true },
        { label: '角色名称', prop: 'roleName', show: true, fixed: false, sortable: true },
        { label: '创建时间', prop: 'createTime', show: true, fixed: false, sortable: true },
        { label: '创建人', prop: 'createBy', show: true, fixed: false, sortable: true }
      ],
      showForm: false,
      roleForm: { id: '', roleCode: '', roleName: '' },
      roleControl: [
        { label: 'ID', field: 'id', type: 'hidden', show: false, readonly: true },
        { label: '角色代码', field: 'roleCode', type: 'input', show: true, readonly: true },
        { label: '角色名称', field: 'roleName', type: 'input', show: true, readonly: false }
      ],
      roleRules: {
        roleCode: [
          { required: true, message: '请输入角色代码', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        roleName: [
          { required: true, message: '请输入角色名称', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur' }
        ]
      },
      multipleSelection: [],
      showTree: false,
      roleId: null
    }
  },

  methods: {
    searchData () {
      this.$http
        .get('/api/sysRole/list', {
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
            this.tableData = []
            this.total = 0
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
        roleCode: this.searchForm.roleCode,
        roleNmae: this.searchForm.roleName
      }
      return params
    },
    /**
     * 动态调用方法
     */
    dynamicMethod (methodname) {
      this[methodname]()
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
     * 新增修改数据
     */
    add () {
      // 设置账号栏位可编辑
      for (const item in this.roleForm) {
        this.roleForm[item] = ''
      }
      this.roleControl[1].readonly = false
      this.showForm = true
    },
    /**
   * 保存表单
   */
    saveForm (from) {
      const newData = JSON.parse(JSON.stringify(from))
      this.roleForm = newData
      this.$http.post('/api/sysRole/save', this.roleForm).then(res => {
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
     * 删除所选角色
     */
    delete () {
      if (this.$refs.multipleTable.selection.length <= 0) {
        this.$message.warning('请选择要操作的角色')
        return
      }
      this.$confirm('此操作将永久删除该角色, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const idArray = []
        this.$refs.multipleTable.selection.forEach(element => {
          idArray.push(element.id)
        })
        this.$http.post('/api/sysRole/delete', idArray).then(res => {
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
     * 修改数据
     */
    handleEditClick (row) {
      // 设置账号栏位不可编辑
      for (const item in this.roleForm) {
        this.roleForm[item] = ''
      }
      this.roleControl[1].readonly = true
      this.showForm = true
      this.roleForm.id = row.id
      this.roleForm.roleCode = row.roleCode
      this.roleForm.roleName = row.roleName
    },
    /**
     * 删除数
     */
    handleDeleteClick (row) {
      this.$confirm('此操作将永久删除该角色, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const params = [row.id]
        this.$http.post('/api/sysRole/delete', params).then(res => {
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
   * 展示授权页面
   */
    setAuth () {
      if (this.$refs.multipleTable.selection.length <= 0) {
        this.$message.warning('请选择要操作的角色')
      }
      if (this.$refs.multipleTable.selection.length > 1) {
        this.$message.warning('只能选择一个角色')
      }
      const id = this.$refs.multipleTable.selection[0].id
      this.roleId = id
      this.showTree = true
    },
    /**
     * 展示授权页面
     */
    handleAuthClick (row) {
      this.roleId = row.id
      this.showTree = true
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
    },
    /**
     *赋权保存
    */
    saveTree (nodes) {
      const newData = JSON.parse(JSON.stringify(nodes))
      newData.forEach(item => {
        item.childrens = null
      })
      console.log(newData)
      this.$http.post('/api/sysRoleFunc/save?roleId=' + this.roleId, newData).then(res => {
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
    // 点击行触发，选中或不选中复选框
    handleRowClick (row, column, event) {
      this.$refs.multipleTable.toggleRowSelection(row)
    },
    /**
     * 隐藏权限赋权框
     */
    hidTree (val) {
      this.showTree = val
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
