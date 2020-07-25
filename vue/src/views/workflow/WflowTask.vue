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
              <el-button type="text"
                         @click="handleEditClick(scope.row)"
                         size="small">业务单据</el-button>
              <el-button type="text"
                         @click="handleViewProcessClick(scope.row)"
                         size="small">查看流程图</el-button>
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
                     customName="wlfowTask"
                     @changeColumns="changeColumns" />
    <!--查询条件-->
    <Search :show.sync="showSearch"
            :condition="searchCondition"
            :form="searchForm"
            @ok="setCondition"
            @hidden="hidCondition" />
    <!--流程图-->
    <el-dialog title="流程图"
               :visible.sync="showView">
      <img :src="imgSrc"
           style="margin-top: -150px;margin-bottom: 30px;" />
      <br />
    </el-dialog>
  </div>

</template>

<script>
import CustomTableCols from '../../components/CustomTableCols'
import Search from '../../components/Search'
export default {
  components: {
    CustomTableCols,
    Search
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
      searchForm: { name: '', key: '' },
      searchCondition: [
        { index: 0, label: '流程名称', field: 'name', type: 'input', show: true },
        { index: 1, label: '流程key', field: 'key', type: 'input', show: true }
      ],
      showSearch: false,
      buttonGroups: [
        { index: 0, label: '查询', method: 'showCondition', icon: 'el-icon-search' },
        { index: 1, label: '发起测试流程', method: 'startProcess', icon: 'el-icon-video-play' },
        { index: 1, label: '同意', method: 'pass', icon: 'el-icon-check' },
        { index: 2, label: '拒绝', method: 'reject', params: false, icon: 'el-icon-close' },
        { index: 6, label: '刷新', method: 'searchData', icon: 'el-icon-refresh' }
      ],
      tableColumns: [],
      defaultColumns: [
        { label: '任务id', prop: 'id', show: true, fixed: false, sortable: false, width: 200 },
        { label: '当前签核节点', prop: 'name', show: true, fixed: false, sortable: false, width: 200 },
        { label: '当前签核人', prop: 'assignee', show: true, fixed: false, sortable: false },
        { label: '流程分类', prop: 'category', show: true, fixed: false, sortable: false },
        { label: '业务单据 ', prop: 'businessKey', show: true, fixed: false, sortable: false },
        { label: '描述', prop: 'description', show: true, fixed: false, sortable: false }
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
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
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
      showView: false,
      imgSrc: ''
    }
  },

  methods: {
    searchData () {
      this.$http
        .get('/api/wflowTask/list', {
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
        name: this.searchForm.name,
        key: this.searchForm.key
      }
      return params
    },
    /**
     * 开启流程
     */
    startProcess () {
      this.$http.get('/api/wflowTask/start', {}).then(res => {
        this.searchData()
      }).catch(err => {
        console.log(err.message)
      })
    },
    /**
     * 审批通过
     */
    pass () {
      const idArray = []
      this.$refs.multipleTable.selection.forEach(element => {
        idArray.push(element.id)
      })
      this.$http.post('/api/wflowTask/complete', idArray).then(res => {
        if (res.code == '0') {
          this.searchData()
        } else { this.$message.error(res.msg) }
      }).catch(err => {
        console.log(err.message)
      })
    },
    /**
     * 审批通过
     */
    reject () {
      const idArray = []
      this.$refs.multipleTable.selection.forEach(element => {
        idArray.push(element.id)
      })
      this.$http.post('/api/wflowTask/reject', idArray).then(res => {
        if (res.code == '0') {
          this.searchData()
        } else { this.$message.error(res.msg) }
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
      this.userForm = newData
      this.$http.post('/api/wflowDefine/save', this.userForm).then(res => {
        this.searchData()
      }).catch(err => {
        console.log(err.message)
      })
    },
    /**
     * 删除用户
     */
    handleDelClick (row) {
      // 设置账号栏位不可编辑
      this.$confirm('此操作将永久删除该流程定义, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const idArray = []
        this.$refs.multipleTable.selection.forEach(element => {
          idArray.push(element.deploymentId)
        })
        this.$http.post('/api/wflowDefine/delete', idArray).then(res => {
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
   * 查看流程图
   */
    handleViewProcessClick (row) {
      // 设置账号栏位不可编辑
      this.showView = true
      this.imgSrc = '/api/wflowChart/traceprocess?processInstanceId=' + row.processInstanceId
    },
    /**
     * 隐藏编辑表单
     */
    hidForm (val) {
      console.log('hiddiv' + val)
      this.showForm = val
    },
    deploy () {
      this.$http.post('/api/wflowDefine/deploy', {}).then(res => {
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
        this.$message.warning('请选择要操作的数据')
        return
      }
      this.$confirm('此操作将永久删除选择的流程定义, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const idArray = []
        this.$refs.multipleTable.selection.forEach(element => {
          idArray.push(element.deploymentId)
        })
        this.$http.post('/api/wflowDefine/delete', idArray).then(res => {
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
