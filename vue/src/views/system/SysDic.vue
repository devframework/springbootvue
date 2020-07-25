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
              <el-button @click="handleItemClick(scope.row)"
                         type="text"
                         size="small">字典配置</el-button>
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
                     customName="sysdic"
                     @changeColumns="changeColumns" />
    <!--查询条件-->
    <Search :show.sync="showSearch"
            :condition="searchCondition"
            :form="searchForm"
            @ok="setCondition"
            @hidden="showSearch=false" />
    <!--新增编辑页面-->
    <CustomForm :show.sync="showForm"
                title="字典编辑"
                :control="dicControl"
                :model="dicForm"
                :rules="dicRules"
                @ok="saveForm"
                @hidden="showForm=false" />

    <!--字典子列表页面-->
    <el-drawer title="字典列表"
               :visible.sync="showDrawer"
               custom-class="demo-drawer"
               ref="drawer"
               size="40%">
      <div class="demo-drawer__content">
        <div>
          <!--按钮列表-->
          <el-button-group class="toolBox">
            <el-button size="small"
                       v-for="(item,index) in itemButtonGroups"
                       :key="index"
                       @click="dynamicMethod(item.method,item.params)"
                       :icon="item.icon">{{item.label}}</el-button>
          </el-button-group>
        </div>
        <!--数据表格-->
        <el-row>
          <el-col :span="24">
            <el-table :data="itemTableData"
                      border
                      stripe
                      highlight-current-row
                      ref="multipleItemTable"
                      :height="itemTableHeight"
                      @row-click="handleItemRowClick"
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
              <template v-for="(el,i) in itemTableColumns">
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
                               label="操作">
                <template slot-scope="scope">
                  <el-button type="text"
                             @click="handleDicItemDelClick(scope.row)"
                             size="small">删除</el-button>
                  <el-button type="text"
                             @click="handleDicItemEditClick(scope.row)"
                             size="small">编辑</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
        </el-row>
        <!--分页插件-->
        <el-row>
          <el-col :span="24">
            <el-pagination @size-change="handleItemSizeChange"
                           @current-change="handleItemCurrentChange"
                           :current-page="itemCurrent"
                           :page-sizes="pageSizeOptions"
                           :page-size="itemSize"
                           layout="total, sizes, prev, pager, next, jumper"
                           :total="itemTotal">
            </el-pagination>
          </el-col>
        </el-row>
        <!--列自定义-->
        <CustomTableCols :defaultCols="itemDefaultColumns"
                         customName="sysdic_item"
                         @changeColumns="itemChangeColumns" />
        <!--查询条件-->
        <Search :show.sync="showItemSearch"
                :condition="searchItemCondition"
                :form="searchItemForm"
                @ok="setItemCondition"
                @hidden="showItemSearch=false" />
        <!--新增编辑页面-->
        <CustomForm :show.sync="showItemForm"
                    title="字典编辑"
                    :control="dicItemControl"
                    :model="dicItemForm"
                    :rules="dicItemRules"
                    @ok="saveDicItemForm"
                    @hidden="showItemForm=false" />
      </div>
    </el-drawer>
  </div>

</template>

<script>
import CustomTableCols from '../../components/CustomTableCols'
import Search from '../../components/Search'
import CustomForm from '../../components/CustomForm'
export default {
  components: {
    CustomTableCols,
    Search,
    CustomForm
  },
  data () {
    return {
      initData: {},
      tableData: [],
      tableHeight: '400px',
      current: 1,
      size: 10,
      total: 0,
      pageSizeOptions: [10, 20, 50, 100],
      searchForm: { dicCode: '', dicName: '' },
      searchCondition: [
        { index: 0, label: '字典编号', field: 'dicCode', type: 'input', show: true },
        { index: 1, label: '字典名称', field: 'dicName', type: 'input', show: true }
      ],
      showSearch: false,
      buttonGroups: [
        { index: 0, label: '查询', method: 'showCondition', icon: 'el-icon-search' },
        { index: 1, label: '新建', method: 'addAndEdit', icon: 'el-icon-plus' },
        { index: 3, label: '删除', method: 'delete', icon: 'el-icon-delete' },
        { index: 4, label: '刷新', method: 'searchData', icon: 'el-icon-refresh' }
      ],
      tableColumns: [],
      defaultColumns: [
        { label: '字典编号', prop: 'dicCode', show: true, fixed: false, sortable: false, width: 200 },
        { label: '字典名称', prop: 'dicName', show: true, fixed: false, sortable: false, width: 200 },
        { label: '字典描述', prop: 'description', show: true, fixed: false, sortable: false },
        { label: '创建人', prop: 'createByName', show: true, fixed: false, sortable: false },
        { label: '创建时间', prop: 'createTime', show: true, fixed: false, sortable: false }
      ],
      showForm: false,
      dicForm: { id: '', dicCode: '', dicName: '', description: '' },
      dicControl: [
        { label: 'ID', field: 'id', type: 'hidden', show: false, readonly: true },
        { label: '字典编号', field: 'dicCode', type: 'input', show: true, readonly: true },
        { label: '字典名称', field: 'dicName', type: 'input', show: true, readonly: false },
        { label: '描述', field: 'description', type: 'input', show: true, readonly: false }
      ],
      dicRules: {
        dicCode: [
          { required: true, message: '请输入字典编号', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        dicName: [
          { required: true, message: '请输入字典名称', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur' }
        ]
      },
      multipleSelection: [],
      // 字典子项部分
      itemTableData: [],
      itemTableHeight: '400px',
      itemCurrent: 1,
      itemSize: 10,
      itemTotal: 0,
      dicId: '',
      showDrawer: false,
      showItemSearch: false,
      showItemForm: false,
      searchItemForm: { dicItemCode: '', dicItemName: '', dicItemValue: '' },
      searchItemCondition: [
        { index: 0, label: '编码', field: 'dicItemCode', type: 'input', show: true },
        { index: 1, label: '名称', field: 'dicItemName', type: 'input', show: true },
        { index: 2, label: '值', field: 'dicItemValue', type: 'input', show: true }
      ],
      itemButtonGroups: [
        { index: 0, label: '查询', method: 'showItemCondition', icon: 'el-icon-search' },
        { index: 1, label: '新建', method: 'addAndEditDicItem', icon: 'el-icon-plus' },
        { index: 2, label: '删除', method: 'deleteDicItem', icon: 'el-icon-delete' },
        { index: 3, label: '刷新', method: 'searchDicItemData', icon: 'el-icon-refresh' }
      ],
      itemTableColumns: [],
      itemDefaultColumns: [
        { label: '编码', prop: 'dicItemCode', show: true, fixed: false, sortable: false, width: 200 },
        { label: '名称', prop: 'dicItemName', show: true, fixed: false, sortable: false, width: 200 },
        { label: '值', prop: 'dicItemValue', show: true, fixed: false, sortable: false }
      ],
      dicItemForm: { id: '', dicId: '', dicItemCode: '', dicItemName: '', dicItemValue: '', description: '' },
      dicItemControl: [
        { label: 'ID', field: 'id', type: 'hidden', show: false, readonly: true },
        { label: '编码', field: 'dicItemCode', type: 'input', show: true, readonly: true },
        { label: '名称', field: 'dicItemName', type: 'input', show: true, readonly: false },
        { label: '值', field: 'dicItemValue', type: 'input', show: true, readonly: false },
        { label: '描述', field: 'description', type: 'input', show: true, readonly: false }
      ],
      dicItemRules: {
        dicItemCode: [
          { required: true, message: '请输入字典编号', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        dicItemName: [
          { required: true, message: '请输入字典名称', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur' }
        ],
        dicItemValue: [
          { required: true, message: '请输入字典名称', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur' }
        ]
      },
      multipleItemSelection: []
    }
  },

  methods: {
    /**
     *数据字典查询
     */
    searchData () {
      this.$http
        .get('/api/sysDic/list', {
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
        dicId: this.dicId,
        dicCode: this.showItemSearch.dicCode,
        dicName: this.showItemSearch.dicName
      }
      return params
    },
    /**
     * 新增修改数据
     */
    addAndEdit () {
      // 设置账号栏位可编辑
      for (const item in this.dicForm) {
        this.dicForm[item] = ''
      }
      this.dicControl[1].readonly = false
      this.showForm = true
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
     * 隐藏查询条件
     */
    hidCondition (val) {
      console.log('hiddiv' + val)
      this.showSearch = val
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
      this.dicForm = newData
      this.$http.post('/api/sysDic/save', this.dicForm).then(res => {
        if (res.code == '0') {
          this.searchData()
          this.$message.success(res.msg)
          this.showForm = false
        } else {
          this.$message.error(res.msg)
        }
      }).catch(err => {
        console.log(err.message)
      })
    },
    /**
     * 删除字典
     */
    handleDelClick (row) {
      // 设置账号栏位不可编辑
      this.$confirm('此操作将永久删除该数据字典 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const idArray = []
        this.$refs.multipleTable.selection.forEach(element => {
          idArray.push(element.id)
        })
        this.$http.post('/api/sysDic/delete', idArray).then(res => {
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
      for (const item in this.dicForm) {
        this.dicForm[item] = ''
      }
      this.dicControl[1].readonly = true
      this.showForm = true
      this.dicForm.dicCode = row.dicCode
      this.dicForm.dicName = row.dicName
      this.dicForm.description = row.description
      this.dicForm.id = row.id
    },
    /**
     * 字典子项管理
     */
    handleItemClick (row) {
      this.dicId = row.id
      this.showDrawer = true
      this.searchDicItemData()
    },
    // 字典子项点击行触发，选中或不选中复选框
    handleItemRowClick (row, column, event) {
      this.$refs.multipleItemTable.toggleRowSelection(row)
    },
    /**
     * 设置查询条件
     */
    setItemCondition (from) {
      const newData = JSON.parse(JSON.stringify(from))
      this.searchItemForm = newData
    },
    /**
   * 删除字典
   */
    delete () {
      if (this.$refs.multipleTable.selection.length <= 0) {
        this.$message.warning('请选择要操作的字典')
        return
      }
      this.$confirm('此操作将永久删除选择的字典, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const idArray = []
        this.$refs.multipleTable.selection.forEach(element => {
          idArray.push(element.id)
        })
        this.$http.post('/api/sysDic/delete', idArray).then(res => {
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
     * 字典子项查询
     */
    searchDicItemData () {
      this.$http
        .get('/api/sysDicItem/list', {
          params: this._itemHanderParams()
        })
        .then(res => {
          if (res.code == 0) {
            this.itemTableData = res.data.records
            this.itemTotal = res.data.total
            this.itemCurrent = res.data.current
            this.itemSize = res.data.size
          } else {
            this.$message.error(res.msg)
          }
        })
        .catch(err => {
          console.log(err.message)
        })
    },
    /**
     * 字典子项查询条件处理
     */
    _itemHanderParams () {
      const params = {
        current: this.itemCurrent,
        size: this.itemSize,
        dicId: this.dicId,
        dicItemCode: this.searchItemForm.dicItemCode,
        dicItemName: this.searchItemForm.dicItemName
      }
      return params
    },
    /**
   * 新增修改数据
   */
    addAndEditDicItem () {
      // 设置账号栏位可编辑
      for (const item in this.dicItemForm) {
        this.dicItemForm[item] = ''
      }
      this.dicItemForm.dicId = this.dicId
      this.dicItemControl[1].readonly = false
      this.showItemForm = true
    },
    /**
  * 删除字典
  */
    deleteDicItem () {
      if (this.$refs.multipleItemTable.selection.length <= 0) {
        this.$message.warning('请选择要操作的字典')
        return
      }
      this.$confirm('此操作将永久删除选择的子项, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const idArray = []
        this.$refs.multipleItemTable.selection.forEach(element => {
          idArray.push(element.id)
        })
        this.$http.post('/api/sysDicItem/delete', idArray).then(res => {
          if (res.code == '0') {
            this.$message.success(res.msg)
            this.searchDicItemData()
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
    * 保存表单
    */
    saveDicItemForm (from) {
      const newData = JSON.parse(JSON.stringify(from))
      this.dicItemForm = newData
      this.$http.post('/api/sysDicItem/save', this.dicItemForm).then(res => {
        if (res.code == '0') {
          this.$message.success(res.msg)
          this.searchDicItemData()
          this.showItemForm = false
        } else {
          this.$message.error(res.msg)
        }
      }).catch(err => {
        console.log(err.message)
      })
    },
    /**
    * 字典子项自定义列修改
    */
    itemChangeColumns (val) {
      console.log('changeColumns--' + val)
      this.itemTableColumns = []
      this.$nextTick(() => {
        this.itemTableColumns = val
      })
    },
    /**
     * 删除字典子项
     */
    handleDicItemDelClick (row) {
      // 设置账号栏位不可编辑
      this.$confirm('此操作将永久删除该数据子项 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const idArray = []
        this.$refs.multipleItemTable.selection.forEach(element => {
          idArray.push(element.id)
        })
        this.$http.post('/api/sysDicItem/delete', idArray).then(res => {
          if (res.code == '0') {
            this.$message.success(res.msg)
            this.searchDicItemData()
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
     * 修改字典子项数据
     */
    handleDicItemEditClick (row) {
      // 设置账号栏位不可编辑
      for (const item in this.dicItemForm) {
        this.dicItemForm[item] = ''
      }
      this.dicItemControl[1].readonly = true
      this.showItemForm = true
      this.dicItemForm.dicItemCode = row.dicItemCode
      this.dicItemForm.dicItemName = row.dicItemName
      this.dicItemForm.dicId = row.dicId
      this.dicItemForm.dicItemValue = row.dicItemValue
    },
    /**
     * 字典子项查询页面
    */
    showItemCondition () {
      this.showItemSearch = true
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
    * table每页数字变化
    */
    handleItemSizeChange (val) {
      console.log(`每页 ${val} 条`)
      this.itemSize = val
      // this.searchData()
    },
    /**
     * table页数变化
     */
    handleItemCurrentChange (val) {
      console.log(`当前页: ${val}`)
      this.itemCurrent = val
      // this.searchData()
    }
  },
  created () {
    this.tableHeight = document.documentElement.clientHeight - 240
    this.itemTableHeight = document.documentElement.clientHeight - 240
    this.tableColumns = this.defaultColumns
    this.itemTableColumns = this.itemDefaultColumns
    this.searchData()
  }
}
</script>
<style lang="less" scoped>
.el-drawer__header {
  text-align: left;
}
.el-drawer__body {
  padding: 10px !important;
}
</style>
