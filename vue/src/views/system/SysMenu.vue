<template>
  <div>
    <el-row>
      <el-col :span="5"
              :height="tableHeight">
        <el-card class="box-card">
          <div slot="header">
            <el-button-group class="toolBox">
              <el-button size="small"
                         v-for="(item,index) in buttonGroups"
                         :key="index"
                         @click="dynamicMethod(item.method)"
                         :icon="item.icon">{{item.label}}</el-button>
            </el-button-group>
          </div>
          <el-tree node-key="id"
                   :style="'height:' + (tableHeight) + 'px;overflow-y: auto;'"
                   :data="treeData"
                   @node-click="nodeclick"
                   :props="treeProps"
                   :render-content="renderContent"
                   :highlight-current="true"></el-tree>
        </el-card>
      </el-col>
      <el-col :span="19"
              style="padding-left:10px">
        <!-- 功能键区 -->
        <div>
          <el-tabs type="border-card"
                   :style="'height:' + (tableHeight+108) + 'px;overflow-y: auto;'">
            <el-tab-pane label="方法管理">
              <el-form :inline="true"
                       class="demo-form-inline"
                       style="text-align: left">
                <el-form-item label="菜单代码">
                  <el-input v-model="menuCode"
                            disabled="disabled"></el-input>
                </el-form-item>
                <el-form-item label="菜单名称">
                  <el-input v-model="menuName"
                            disabled="disabled"></el-input>
                </el-form-item>
              </el-form>
              <el-button-group class="toolBox">
                <el-button size="small"
                           v-for="(item,index) in buttonGroups2"
                           :key="index"
                           @click="dynamicMethod(item.method)"
                           :render-content="renderContent"
                           :icon="item.icon">{{item.label}}</el-button>
              </el-button-group>
              <el-row>
                <el-col :span="24">
                  <el-table :data="tableData"
                            border
                            stripe
                            highlight-current-row
                            ref="multipleTable"
                            :height="tableHeight-110"
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
                                       show-overflow-tooltip>
                      </el-table-column>
                    </template>
                    <el-table-column fixed="right"
                                     label="操作"
                                     width="200">
                      <template slot-scope="scope">
                        <el-button type="text"
                                   @click="handleDelClick(scope.row)"
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
                               customName="sysfunc"
                               @changeColumns="changeColumns" />
            </el-tab-pane>
          </el-tabs>
        </div>
        <!-- 功能键区 -->
      </el-col>
    </el-row>
    <!--新增编辑菜单页面-->
    <CustomForm :show.sync="showForm"
                title="菜单编辑"
                :control="menuControl"
                :model="menuForm"
                ref="tree"
                :rules="menuRules"
                @check-change="handleCheckChange"
                @ok="saveForm"
                @hidden="showForm=false" />
    <!--新增编辑菜单方法-->
    <CustomForm :show.sync="showFunc"
                title="功能编辑"
                :control="funcControl"
                :model="funcForm"
                ref="tree"
                :rules="funcRules"
                @check-change="handleCheckChange"
                @ok="saveFunc"
                @hidden="showFunc=false" />
  </div>

</template>

<script>
import CustomForm from '../../components/CustomForm'
import CustomTableCols from '../../components/CustomTableCols'
export default {
  components: { CustomForm, CustomTableCols },
  data () {
    return {
      tableHeight: '400px',
      treeData: [],
      treeProps: {
        label: 'menuName',
        children: 'childrens'
      },
      current: 1,
      size: 10,
      total: 0,
      pageSizeOptions: [10, 20, 50, 100],
      tableData: [],
      tableColumns: [],
      defaultColumns: [
        { label: '方法代码', prop: 'funcCode', show: true, fixed: false, sortable: true },
        { label: '方法名称', prop: 'funcName', show: true, fixed: false, sortable: true },
        { label: '方法URL', prop: 'funcUrl', show: true, fixed: false, sortable: true },
        { label: '创建时间', prop: 'createTime', show: true, fixed: false, sortable: true },
        { label: '创建人', prop: 'createBy', show: true, fixed: false, sortable: true }
      ],
      buttonGroups: [
        { index: 0, label: '新建', method: 'add', icon: 'el-icon-plus' },
        { index: 1, label: '修改', method: 'edit', icon: 'el-icon-edit' },
        { index: 2, label: '删除', method: 'delete', icon: 'el-icon-delete' },
        { index: 3, label: '刷新', method: 'loadTree', icon: 'el-icon-refresh' }
      ],
      buttonGroups2: [
        { index: 0, label: '新建', method: 'addFunc', icon: 'el-icon-plus' },
        { index: 2, label: '删除', method: 'deleteFunc', icon: 'el-icon-delete' },
        { index: 3, label: '刷新', method: 'searchData', icon: 'el-icon-refresh' }
      ],
      showForm: false,
      menuForm: { id: '', menuCode: '', menuName: '', menuUrl: '', menuIcon: '', parentId: '', sortNo: 0 },
      menuControl: [
        { label: 'ID', field: 'id', type: 'hidden', show: false, readonly: true },
        { label: '菜单代码', field: 'menuCode', type: 'input', show: true, readonly: true },
        { label: '菜单名称', field: 'menuName', type: 'input', show: true, readonly: false },
        { label: '菜单地址', field: 'menuUrl', type: 'input', show: true, readonly: false },
        { label: '菜单图标', field: 'menuIcon', type: 'icon-select', show: true, readonly: false, options: [{ value: '' }] },
        { label: '父级菜单', field: 'parentId', type: 'input', show: true, readonly: true },
        { label: '菜单排序', field: 'sortNo', type: 'input', show: true, readonly: false }
      ],
      menuRules: {
        menuCode: [
          { required: true, message: '请输入菜单代码', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        menuName: [
          { required: true, message: '请输入菜单名称', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur' }
        ]
      },
      selectNode: null,
      menuCode: '',
      menuName: '',
      showFunc: false,
      funcForm: { id: '', menuId: '', funcCode: '', funcName: '', funcUrl: '', funcDesc: '', sortNo: 0 },
      funcControl: [
        { label: 'ID', field: 'id', type: 'hidden', show: false, readonly: true },
        { label: '功能代码', field: 'funcCode', type: 'input', show: true, readonly: true },
        { label: '功能名称', field: 'funcName', type: 'input', show: true, readonly: false },
        { label: '功能地址', field: 'funcUrl', type: 'input', show: true, readonly: false },
        { label: '功能描述', field: 'funcDesc', type: 'input', show: true, readonly: false },
        { label: '排序', field: 'sortNo', type: 'input', show: true, readonly: false }
      ],
      funcRules: {
        menuCode: [
          { required: true, message: '请输入菜单代码', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        menuName: [
          { required: true, message: '请输入菜单名称', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    loadTree () {
      this.$http.get('/api/sysMenu/tree-list', {}).then(res => {
        this.treeData = res.data
      }).catch(err => {
        console.log(err.message)
      })
    },
    renderContent (h, { node, data, store }) {
      return (
        <span class="custom-tree-node">
          <span>
            <span>{node.label}</span>
          </span>
          <span >
            <i style="margin-left:5px" class="el-icon-plus"
              on-click={() => this.openAddAndEdit('add', data)}
            />
            <i style="margin-left:5px" class="el-icon-edit"
              on-click={() => this.openAddAndEdit('edit', data)}
            />
            <i style="margin-left:5px" class="el-icon-delete"
              on-click={() => this.openAddAndEdit('delete', data)}
            />
          </span>
        </span>
      )
    },
    /**
     * 动态调用方法
     */
    dynamicMethod (methodname) {
      this[methodname]()
    },
    /***
     *菜单新增
     */
    add () {
      for (const item in this.menuForm) {
        this.menuForm[item] = ''
      }
      this.menuControl[1].readonly = false
      this.showForm = true
    },
    /**
     *点击节点
     */
    nodeclick (node) {
      this.selectNode = node
      this.menuCode = this.selectNode.menuCode
      this.menuName = this.selectNode.menuName
      this.searchData()
    },
    /**
     * 编辑修改菜单
     */
    edit () {
      if (this.selectNode == null) {
        this.$message.warning('请选择要操作的节点')
        return
      }
      for (const item in this.menuForm) {
        this.menuForm[item] = this.selectNode[item]
      }
      this.menuControl[1].readonly = false
      this.showForm = true
    },
    /**
     * 删除
     */
    delete () {
      if (this.selectNode == null) {
        this.$message.warning('请选择要操作的节点')
        return
      }
      this.$confirm('此操作将永久删除选择的菜单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http.post('/api/sysMenu/delete', this.selectNode).then(res => {
          if (res.code == '0') {
            this.$message.success(res.msg)
            this.loadTree()
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
    /***
   *打开新增编辑
   */
    openAddAndEdit (func, data) {
      switch (func) {
        case 'add':
          for (const item in this.menuForm) {
            this.menuForm[item] = ''
          }
          this.menuForm.parentId = data.id
          this.menuControl[1].readonly = false
          this.showForm = true
          break
        case 'edit':
          for (const item in this.menuForm) {
            this.menuForm[item] = data[item]
          }
          this.menuControl[1].readonly = true
          this.showForm = true
          break
        case 'delete':
          this.$confirm('此操作将永久删除选择的菜单, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.$http.post('/api/sysMenu/delete', data).then(res => {
              if (res.code == '0') {
                this.$message.success(res.msg)
                this.loadTree()
              } else {
                this.$message.error(res.msg)
              }
            }).catch(err => {
              console.log(err.message)
            })
          }).catch(() => {
            this.$message.info('已取消删除')
          })
          break
      }
    },
    /**
     * 节点选中事件
     */
    handleCheckChange () {
      const res = this.$refs.tree.getCheckedNodes()
      const arr = []
      res.forEach((item) => {
        arr.push(item.id)
      })
      console.log(arr)
    },
    /**
     *菜单保存
     */
    saveForm (from) {
      const newData = JSON.parse(JSON.stringify(from))
      this.menuForm = newData
      this.$http.post('/api/sysMenu/save', this.menuForm).then(res => {
        if (res.code == '0') {
          this.$message.success(res.msg)
          this.showForm = false
          this.loadTree()
        } else {
          this.$message.error(res.msg)
        }
      }).catch(err => {
        console.log(err.message)
      })
    },
    /**
   * 隐藏编辑表单
   */
    hidForm (val) {
      this.showForm = val
    },
    /**
     *展示新增页面
     */
    addFunc () {
      if (this.selectNode == null) {
        this.$message.warning('请先选择菜单节点')
        return
      }
      for (const item in this.funcForm) {
        this.funcForm[item] = ''
      }
      this.funcForm.menuId = this.selectNode.id
      this.funcControl[1].readonly = false
      this.showFunc = true
    },
    /**
    *方法保存
    */
    saveFunc (from) {
      const newData = JSON.parse(JSON.stringify(from))
      this.funcForm = newData
      this.$http.post('/api/sysFunc/save', this.funcForm).then(res => {
        if (res.code == '0') {
          this.$message.success(res.msg)
          this.showFunc = false
          this.searchData()
        } else {
          this.$message.error(res.msg)
        }
      }).catch(err => {
        console.log(err.message)
      })
    },
    /**
     * 删除方法
     */
    deleteFunc () {
      if (this.$refs.multipleTable.selection.length <= 0) {
        this.$message.warning('请选择要操作的数据')
        return
      }
      this.$confirm('此操作将永久删除该方法, 是否继续?', '提示', {
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
     * 删除方法
     */
    handleDelClick (row) {
      // 设置账号栏位不可编辑
      this.$confirm('此操作将永久删除该方法, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http.post('/api/sysFunc/delete', [row.id]).then(res => {
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
      for (const item in this.funcForm) {
        this.funcForm[item] = ''
      }
      this.funcControl[1].readonly = false
      this.showFunc = true
      this.funcForm.id = row.id
      this.funcForm.funcCode = row.funcCode
      this.funcForm.funcName = row.funcName
      this.funcForm.menuId = row.menuId
      this.funcForm.funcUrl = row.funcUrl
      this.funcForm.funcDesc = row.funcDesc
      this.funcForm.sortNo = row.sortNo
    },
    /**  deleteFunc
   * 隐藏方法保存
   */
    hidFunc (val) {
      this.showFunc = val
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
    searchData () {
      if (this.selectNode) {
        this.$http.get('/api/sysFunc/menu-func-list', { params: this._handerParams() }).then(res => {
          if (res.code == '0') {
            this.tableData = res.data.records
            this.total = res.data.total
            this.current = res.data.current
            this.size = res.data.size
          } else {
            this.$message.error(res.msg)
          }
        }).catch(err => {
          console.log(err.message)
        })
      }
    },
    /**
   * 查询条件处理
   */
    _handerParams () {
      const params = {
        current: this.current,
        size: this.size,
        menuId: this.selectNode.id
      }
      return params
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
    this.tableColumns = this.defaultColumns
    this.loadTree()
  }
}
</script>

<style>
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding-right: 8px;
}
</style>
