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
              <el-button @click="handleRunClick(scope.row)"
                         type="text"
                         size="small">执行</el-button>
              <el-button @click="handleEnableClick(scope.row)"
                         type="text"
                         size="small">开启</el-button>
              <el-button @click="handleDisableClick(scope.row)"
                         type="text"
                         size="small">暂停</el-button>
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
                     customName="sysjob"
                     @changeColumns="changeColumns" />
    <!--查询条件-->
    <Search :show.sync="showSearch"
            :condition="searchCondition"
            :form="searchForm"
            @ok="setCondition"
            @hidden="hidCondition" />
    <!--新增编辑页面-->
    <CustomForm :show.sync="showForm"
                title="任务编辑"
                :control="jobControl"
                :model="jobForm"
                :rules="jobRules"
                @ok="saveForm"
                @hidden="hidForm" />
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
      searchForm: { jobName: '' },
      searchCondition: [
        { index: 0, label: '任务名称', field: 'jobName', type: 'input', show: true }
      ],
      showSearch: false,
      buttonGroups: [
        { index: 0, label: '查询', method: 'showCondition', icon: 'el-icon-search' },
        { index: 1, label: '新建', method: 'addAndEdit', icon: 'el-icon-plus' },
        { index: 5, label: '删除', method: 'delete', icon: 'el-icon-delete' },
        { index: 1, label: '执行', method: 'start', icon: 'el-icon-caret-right' },
        { index: 1, label: '启用', method: 'enable', icon: 'el-icon-video-play' },
        { index: 1, label: '禁用', method: 'disable', icon: 'el-icon-video-pause' },
        { index: 1, label: '全部启用', method: 'enableAll', icon: 'el-icon-video-play' },
        { index: 1, label: '全部禁用', method: 'disableAll', icon: 'el-icon-video-pause' },
        { index: 6, label: '刷新', method: 'searchData', icon: 'el-icon-refresh' }
      ],
      tableColumns: [],
      defaultColumns: [
        { label: '任务名称', prop: 'jobName', show: true, fixed: false, sortable: false, width: 200 },
        { label: '任务分组', prop: 'jobGroup', show: true, fixed: false, sortable: false, width: 200 },
        { label: '任务描述', prop: 'description', show: true, fixed: false, sortable: false },
        { label: '类路径', prop: 'jobClassName', show: true, fixed: false, sortable: false },
        { label: '执行频率', prop: 'cronExpression', show: true, fixed: false, sortable: false },
        { label: '状态', prop: 'triggerState', show: true, fixed: false, sortable: false }
      ],
      showForm: false,
      jobForm: { id: '', jobName: '', jobGroup: '', description: '', jobClassName: '', cronExpression: '', jobDataParam: '', oldJobName: '', oldJobGroup: '' },
      jobControl: [
        { label: 'ID', field: 'id', type: 'hidden', show: false, readonly: true },
        { index: 0, label: '任务名称', field: 'jobName', type: 'input', show: true, readonly: false },
        { index: 1, label: '任务分组', field: 'jobGroup', type: 'input', show: true, readonly: false },
        { index: 2, label: '任务描述', field: 'description', type: 'input', show: true, readonly: false },
        { index: 3, label: '类路径', field: 'jobClassName', type: 'input', show: true, readonly: false },
        { index: 4, label: '执行频率', field: 'cronExpression', type: 'input', show: true, readonly: false },
        // { index: 4, label: '执行参数', field: 'jobDataParam', type: 'input', show: true, readonly: false },
        { index: 4, label: '任务名称', field: 'oldJobName', type: 'hidden', show: false, readonly: false },
        { index: 4, label: '任务分组', field: 'oldJobGroup', type: 'hidden', show: false, readonly: false }
      ],
      jobRules: {
        jobName: [
          { required: true, message: '请输入任务名称', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        jobGroup: [
          { required: true, message: '请输入任务分组', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur' }
        ],
        jobClassName: [
          { required: true, message: '请输入任类路径', trigger: 'blur' }
        ],
        cronExpression: [
          { required: true, message: '请输入执行频率', trigger: 'blur' }
        ]
      },
      multipleSelection: [],
      visible: false
    }
  },

  methods: {
    searchData () {
      this.$http
        .get('/api/quartz/list', {
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
      for (const item in this.jobForm) {
        this.jobForm[item] = ''
      }
      this.jobControl[1].readonly = false
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
      this.jobForm = newData
      this.$http.post('/api/quartz/save', this.jobForm).then(res => {
        if (res.code == 0) {
          this.showForm = false; this.searchData()
        } else {
          this.$message.error(res.msg)
        }
      }).catch(err => {
        console.log(err.message)
      })
    },
    /**
     * 执行任务
     */
    handleRunClick (row) {
      // 设置账号栏位不可编辑
      this.$http.post('/api/quartz/start', [row]).then(res => {
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
   * 启用JOB
   */
    handleEnableClick (row) {
      // 设置账号栏位不可编辑
      this.$http.post('/api/quartz/resume', [row]).then(res => {
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
   * 禁用Job
   */
    handleDisableClick (row) {
      // 设置账号栏位不可编辑
      this.$http.post('/api/quartz/pause', [row]).then(res => {
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
     * 删除任务
     */
    handleDelClick (row) {
      // 设置账号栏位不可编辑
      this.$confirm('此操作将永久该任务, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http.post('/api/quartz/delete', [row]).then(res => {
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
      for (const item in this.jobForm) {
        this.jobForm[item] = ''
      }
      this.showForm = true
      this.jobForm.jobName = row.jobName
      this.jobForm.jobGroup = row.jobGroup
      this.jobForm.description = row.description
      this.jobForm.jobClassName = row.jobClassName
      this.jobForm.cronExpression = row.cronExpression
      // this.jobForm.jobDataParam = row.jobDataParam
      this.jobForm.oldJobName = row.oldJobName
      this.jobForm.oldJobGroup = row.oldJobGroup
    },
    /**
     * 隐藏编辑表单
     */
    hidForm (val) {
      console.log('hiddiv' + val)
      this.showForm = val
    },
    /**
     * 删除任务
     */
    delete () {
      if (this.$refs.multipleTable.selection.length <= 0) {
        this.$message.warning('请选择要操作的任务')
        return
      }
      this.$confirm('此操作将永久删除选择的任务, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const delitems = []
        this.$refs.multipleTable.selection.forEach(element => {
          delitems.push(element)
        })
        this.$http.post('/api/quartz/delete', delitems).then(res => {
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
     * 执行任务
     */
    start () {
      if (this.$refs.multipleTable.selection.length <= 0) {
        this.$message.warning('请选择要操作的任务')
        return
      }
      this.$http.post('/api/quartz/start', this.$refs.multipleTable.selection).then(res => {
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
   * 启用任务
   */
    enable () {
      if (this.$refs.multipleTable.selection.length <= 0) {
        this.$message.warning('请选择要操作的任务')
        return
      }
      this.$http.post('/api/quartz/resume', this.$refs.multipleTable.selection).then(res => {
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
   * 启用全部
   */
    enableAll () {
      this.$http.get('/api/quartz/resume-all', {}).then(res => {
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
    * 启用任务
    */
    disable () {
      if (this.$refs.multipleTable.selection.length <= 0) {
        this.$message.warning('请选择要操作的任务')
        return
      }
      this.$http.post('/api/quartz/pause', this.$refs.multipleTable.selection).then(res => {
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
    * 禁用全部
    */
    disableAll () {
      this.$http.get('/api/quartz/pause-all', {}).then(res => {
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
