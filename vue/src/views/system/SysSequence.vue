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
              <el-button @click="handleViewVClick(scope.row)"
                         type="text"
                         size="small">日志查看</el-button>
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
                     customName="sysSequence"
                     @changeColumns="changeColumns" />
    <!--查询条件-->
    <Search :show.sync="showSearch"
            :condition="searchCondition"
            :form="searchForm"
            @ok="setCondition"
            @hidden="hidCondition" />
    <!--新增编辑页面-->
    <CustomForm :show.sync="showForm"
                title="流水码编辑"
                :control="codeControl"
                :model="codeForm"
                :rules="codeRules"
                @ok="saveForm"
                @hidden="hidForm" />
    <!--字典子列表页面-->
    <el-drawer title="流水码日志"
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
            <el-table :data="logTableData"
                      border
                      stripe
                      highlight-current-row
                      ref="multipleItemTable"
                      :height="logTableHeight"
                      @row-click="handleItemRowClick"
                      style="width: 100%">
              <el-table-column type="selection"
                               width="55">
              </el-table-column>
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
            </el-table>
          </el-col>
        </el-row>
        <!--分页插件-->
        <el-row>
          <el-col :span="24">
            <el-pagination @size-change="handleLogSizeChange"
                           @current-change="handleLogCurrentChange"
                           :current-page="logCurrent"
                           :page-sizes="pageSizeOptions"
                           :page-size="logSize"
                           layout="total, sizes, prev, pager, next, jumper"
                           :total="logTotal">
            </el-pagination>
          </el-col>
        </el-row>
        <!--列自定义-->
        <CustomTableCols :defaultCols="itemDefaultColumns"
                         customName="sysSequenceLog"
                         @changeColumns="itemChangeColumns" />
        <!--查询条件-->
        <Search :show.sync="showItemSearch"
                :condition="searchItemCondition"
                :form="searchItemForm"
                @ok="setItemCondition"
                @hidden="showItemSearch=false" />
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
      searchForm: { code: '', name: '' },
      searchCondition: [
        { index: 0, label: '编码', field: 'code', type: 'input', show: true },
        { index: 1, label: '名称', field: 'name', type: 'input', show: true }
      ],
      showSearch: false,
      buttonGroups: [
        { index: 0, label: '查询', method: 'showCondition', icon: 'el-icon-search' },
        { index: 1, label: '新建', method: 'addAndEdit', icon: 'el-icon-plus' },
        // { index: 1, label: '测试', method: 'test', icon: 'el-icon-plus' },
        { index: 5, label: '删除', method: 'delete', icon: 'el-icon-delete' },
        { index: 6, label: '刷新', method: 'searchData', icon: 'el-icon-refresh' }
      ],
      tableColumns: [],
      defaultColumns: [
        { label: '编码', prop: 'code', show: true, fixed: false, sortable: false, width: 200 },
        { label: '名称', prop: 'name', show: true, fixed: false, sortable: false, width: 200 },
        { label: '前缀', prop: 'prefix', show: true, fixed: false, sortable: false },
        { label: '分隔符', prop: 'separator', show: true, fixed: false, sortable: false },
        { label: '日期格式', prop: 'dateFormat', show: true, fixed: false, sortable: false },
        { label: '数字长度', prop: 'numLength', show: true, fixed: false, sortable: false },
        { label: '初始值', prop: 'initValue', show: true, fixed: false, sortable: false }
      ],
      showForm: false,
      codeForm: { id: '', code: '', name: '', prefix: '', separator: '', dateFormat: '', numLength: 4, initValue: 1 },
      codeControl: [
        { label: 'ID', field: 'id', type: 'hidden', show: false, readonly: true },
        { index: 0, label: '编码', field: 'code', type: 'input', show: true, readonly: false },
        { index: 1, label: '名称', field: 'name', type: 'input', show: true, readonly: false },
        { index: 2, label: '前缀', field: 'prefix', type: 'input', show: true, readonly: false },
        { index: 3, label: '分隔符', field: 'separator', type: 'input', show: true, readonly: false },
        {
          index: 4,
          label: '日期格式',
          field: 'dateFormat',
          type: 'select',
          show: true,
          readonly: false,
          options: [{
            value: 'yyyy',
            label: 'YYYY'
          }, {
            value: 'yyMM',
            label: 'YYMM'
          }, {
            value: 'yyyyMM',
            label: 'YYYYMM'
          }, {
            value: 'yyMMdd',
            label: 'YYMMDD'
          }, {
            value: 'yyyyMMdd',
            label: 'YYYYMMDD'
          }]
        },
        { index: 5, label: '数字长度', field: 'numLength', type: 'number', show: true, readonly: false, min: 3, max: 5 },
        { index: 5, label: '初始值', field: 'initValue', type: 'number', show: true, readonly: false, min: 3, max: 100 }
      ],
      codeRules: {
        code: [
          { required: true, message: '请输入编码', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur' }
        ]
      },
      multipleSelection: [],
      visible: false,
      logTableData: [],
      logTableHeight: '400px',
      logCurrent: 1,
      logSize: 10,
      logTotal: 0,
      showDrawer: false,
      sequenceId: null,
      showItemSearch: false,
      showItemForm: false,
      searchItemForm: { createTime: '', createTime: '' },
      searchItemCondition: [
        { index: 0, label: '开始时间', field: 'createTime', type: 'input', show: true },
        { index: 1, label: '结束时间', field: 'createTime', type: 'input', show: true },
      ],
      itemButtonGroups: [
        { index: 0, label: '查询', method: 'showLogCondition', icon: 'el-icon-search' },
        { index: 3, label: '刷新', method: 'searchLog', icon: 'el-icon-refresh' }
      ],
      itemTableColumns: [],
      itemDefaultColumns: [
        { label: '流水编码', prop: 'flowCode', show: true, fixed: false, sortable: false, width: 200 },
        { label: '创建人', prop: 'createBy', show: true, fixed: false, sortable: false, width: 200 },
        { label: '创建时间', prop: 'createTime', show: true, fixed: false, sortable: false }
      ],
    }
  },

  methods: {
    searchData () {
      this.$http
        .get('/api/sysSequence/list', {
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
        code: this.searchForm.code,
        name: this.searchForm.name
      }
      return params
    },
    /**
     * 新增修改数据
     */
    addAndEdit () {
      // 设置账号栏位可编辑
      for (const item in this.codeForm) {
        this.codeForm[item] = ''
      }
      this.codeControl[1].readonly = false
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
      this.codeForm = newData
      this.$http.post('/api/sysSequence/save', this.codeForm).then(res => {
        if (res.code == 0) {
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
    * 测试方法
    */
    test () {
      this.$http.post('/api/sysSequence/test', {}).then(res => {


      }).catch(err => {
        console.log(err.message)
      })
    },
    /**
     * 删除流水码
     */
    handleDelClick (row) {
      // 设置账号栏位不可编辑
      this.$confirm('此操作将永久该流水码, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const idArray = []
        this.$refs.multipleTable.selection.forEach(element => {
          idArray.push(element.id)
        })
        this.$http.post('/api/sysSequence/delete', idArray).then(res => {
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
      for (const item in this.codeForm) {
        this.codeForm[item] = ''
      }
      this.codeControl[1].readonly = true
      this.showForm = true
      this.codeForm.id = row.id
      this.codeForm.code = row.code
      this.codeForm.name = row.name
      this.codeForm.prefix = row.prefix
      this.codeForm.separator = row.separator
      this.codeForm.dateFormat = row.dateFormat
      this.codeForm.numLength = row.numLength
      this.codeForm.initValue = row.initValue
    },
    /**
     * 隐藏编辑表单
     */
    hidForm (val) {
      console.log('hiddiv' + val)
      this.showForm = val
    },
    /**
     * 删除流水码
     */
    delete () {
      if (this.$refs.multipleTable.selection.length <= 0) {
        this.$message.warning('请选择要操作的流水码')
        return
      }
      this.$confirm('此操作将永久删除选择的流水码, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const idArray = []
        this.$refs.multipleTable.selection.forEach(element => {
          idArray.push(element.id)
        })
        this.$http.post('/api/sysSequence/delete', idArray).then(res => {
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
  * 日志自定义列
  */
    itemChangeColumns (val) {
      console.log('changeColumns--' + val)
      this.itemTableColumns = []
      this.$nextTick(() => {
        this.itemTableColumns = val
      })
    },
    // 字典子项点击行触发，选中或不选中复选框
    handleItemRowClick (row, column, event) {
      this.$refs.multipleItemTable.toggleRowSelection(row)
    },
    /**
     * 数据查询
     */
    handleViewVClick (row) {
      this.sequenceId = row.id;
      this.showDrawer = true;
      this.searchLog();
    },
    /**
     * 字典子项查询页面
    */
    showLogCondition () {
      this.showItemSearch = true
    },
    /**
     * 日志查询
     */
    searchLog () {
      this.$http.get('/api/sysSequenceLog/list', {
        params: {
          sequenceId: this.sequenceId
        }
      }).then(res => {
        if (res.code == '0') {
          this.logTableData = res.data.records
          this.logTotal = res.data.total
          this.logCurrent = res.data.current
          this.logSize = res.data.size
        } else {
          this.$message.error(res.msg)
        }
      }).catch(err => {
        console.log(err.message)
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
   * 设置查询条件
   */
    setItemCondition (from) {
      const newData = JSON.parse(JSON.stringify(from))
      this.searchItemForm = newData
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
    handleLogSizeChange (val) {
      console.log(`每页 ${val} 条`)
      this.LogSize = val
      this.searchLog()
    },
    /**
     * table页数变化
     */
    handleLogCurrentChange (val) {
      console.log(`当前页: ${val}`)
      this.LogCurrent = val
      this.searchLog()
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
