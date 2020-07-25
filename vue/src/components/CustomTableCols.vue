<template>
  <div class="colEditBox">
    <el-button class="colEdit"
               size="small"
               @click="showCustom=true">
      <slot>
        <i class="fa fa-gear"></i>
        列设置
      </slot>
    </el-button>
    <el-dialog title="列设置"
               top="5%"
               @close="cancelCustom"
               :modal="true"
               size="small"
               custom-class="colPop"
               append-to-body
               :visible.sync="showCustom">
      <!-- 工具栏 -->
      <el-button-group class="toolBox">
        <el-button @click="moveFirst"
                   :disabled="cur===-1 || cur===0">
          <i class="fa fa-fast-forward fa-rotate-270"></i>
          移至顶部
        </el-button>
        <el-button @click="up(cur)"
                   :disabled="cur===-1 || cur===0">
          <i class="fa fa-arrow-up"></i>
          上移
        </el-button>
        <el-button @click="down(cur)"
                   :disabled="cur===-1 || cur===(columns.length-1)">
          <i class="fa fa-arrow-down"></i>
          下移
        </el-button>
        <el-button @click="moveLast"
                   :disabled="cur===-1 || cur===(columns.length-1)">
          <i class="fa fa-fast-forward fa-rotate-90"></i>
          移至末尾
        </el-button>
      </el-button-group>
      <el-table :height="320"
                :data="columns"
                :row-class-name="rowClass"
                @row-click="curRow"
                border
                v-loading="saveLoading">
        <el-table-column prop="label"
                         label="列名"></el-table-column>
        <!-- <el-table-column :prop="cus" label="自定义列名"></el-table-column> -->
        <el-table-column label="列宽(0或空为自适应)"
                         width="200">
          <template slot-scope="scope">
            <el-input-number :controls="false"
                             :min="0"
                             :max="9999"
                             v-model="scope.row.width"></el-input-number>
          </template>
        </el-table-column>
        <el-table-column label="是否显示">
          <template slot-scope="scope">
            <el-checkbox v-model="scope.row.show"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="是否冻结">
          <template slot-scope="scope">
            <el-checkbox v-model="scope.row.fixed"></el-checkbox>
          </template>
        </el-table-column>
        <el-table-column label="排序">
          <template slot-scope="scope">
            <el-button size="large"
                       @click="up(scope.$index)"
                       :disabled="scope.$index===0"
                       type="text"><i class="el-icon-caret-top"></i></el-button>
            <el-button size="large"
                       @click="down(scope.$index)"
                       :disabled="scope.$index===(columns.length-1)"
                       type="text"><i class="el-icon-caret-bottom"></i></el-button>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer"
           class="dialog-footer">
        <el-button @click="showCustom=false">取 消</el-button>
        <el-button type="primary"
                   @click="saveCustom(false)">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import md5 from 'js-md5'
export default {
  props: {
    // 列自定义json
    defaultCols: {
      type: Array,
      require: true
    },
    // 增加命名，方便一个页面有多个table区分
    customName: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      cur: -1, // 选中的行
      showCustom: false,
      columns: [],
      saveLoading: false,
      colsSource: [], // 接口返回的自定义列的原始值
      id: null, // 当前自定义列的id
      key: 'table_' + this.customName + '_' + this.$route.path.replace(/\//g, '-'),
      keyDefault: 'tableDefault_' + this.customName + '_' + this.$route.path.replace(/\//g, '-')
    }
  },
  methods: {
    curRow (val) {
      const i = this.columns.indexOf(val)
      this.cur = i
    },
    rowClass (row, index) {
      return index === this.cur ? 'current-row' : ''
    },
    // 上移
    up (i) {
      const row = JSON.parse(JSON.stringify(this.columns[i]))
      const row2 = JSON.parse(JSON.stringify(this.columns[i - 1]))
      this.columns.splice(i, 1, row2)
      this.columns.splice(i - 1, 1, row)
      if (this.cur !== -1) this.cur -= 1
    },
    // 下移
    down (i) {
      const row = JSON.parse(JSON.stringify(this.columns[i]))
      const row2 = JSON.parse(JSON.stringify(this.columns[i + 1]))
      this.columns.splice(i, 1, row2)
      this.columns.splice(i + 1, 1, row)
      if (this.cur !== -1) this.cur += 1
    },
    // 置顶
    moveFirst () {
      const obj = this.columns.splice(this.cur, 1)
      this.columns.unshift(obj[0])
      this.cur = 0
    },
    // 置尾
    moveLast () {
      const obj = this.columns.splice(this.cur, 1)
      this.columns.push(obj[0])
      this.cur = this.columns.length - 1
    },
    // 保存列设置
    saveCustom (columns) {
      const md5Cols = md5(JSON.stringify(this.defaultCols))
      const column = columns || this.columns
      const params = {
        modelCode: this.key,
        tableColumns: JSON.stringify(column),
        columnValue: md5Cols
      }
      if (this.id) params.id = this.id
      this.saveLoading = true
      this.$http.post('/api/sysUserColumn/save', params).then(
        res2 => {
          this.colsSource = JSON.parse(JSON.stringify(column))
          this.$emit('changeColumns', this.filterColumns())
          this.saveLoading = false
          this.showCustom = false
        },
        () => {
          this.saveLoading = false
        }
      )
    },
    // 取消列设置
    cancelCustom () {
      this.columns = JSON.parse(JSON.stringify(this.colsSource))
    },
    // 过滤col，将为0的宽度置为空
    filterColumns () {
      const arr = JSON.parse(JSON.stringify(this.columns))
      arr.map(n => {
        if (n.width === 0) n.width = ''
      })
      return arr
    }
  },
  created () {
    console.log(this.defaultCols)
    const md5Cols = md5(JSON.stringify(this.defaultCols))
    this.$http
      .get('/api/sysUserColumn/get', { params: { modelCode: this.key } })
      .then(res => {
        console.log(res)
        this.id = res.data ? res.data.id : null
        if (res.data && res.data.columnValue === md5Cols) {
          // 如果自定义列代码未修改
          this.columns = JSON.parse(res.data.tableColumns)
          this.colsSource = JSON.parse(JSON.stringify(this.columns)) // 记录原始的值
          this.$emit('changeColumns', this.filterColumns())
        } else {
          this.columns = JSON.parse(JSON.stringify(this.defaultCols))
          this.colsSource = JSON.parse(JSON.stringify(this.columns)) // 记录原始的值
          this.saveCustom(this.defaultCols) // 如果无记录，则保存默认配置
        }
      })
  }
}
</script>
<style>
.colEditBox {
  float: left;
  margin-right: 10px;
}
.colEdit {
  z-index: 2;
  position: relative;
  top: -32px;
}
</style>
