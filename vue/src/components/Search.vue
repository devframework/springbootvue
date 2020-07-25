<template>
  <el-dialog title="查询条件"
             :visible.sync="visible"
             :close-on-click-modal="false"
             append-to-body
             @close="cancel">
    <el-form :inline="true"
             label-width="80px"
             :model="conditionForm">
      <el-form-item v-for="(item,index) in conditionArray"
                    :key="index"
                    :label="item.label">
        <span></span>
        <!-- 文本框 -->
        <el-input v-if="item.type==='input'"
                  v-model="conditionForm[item.field]"></el-input>
        <el-input v-if="item.type===null"
                  v-model="conditionForm[item.field]"></el-input>
        <!--下拉框-->
        <el-select v-if="item.type==='select'"
                   v-model="conditionForm[item.field]"
                   placeholder="请选择">

        </el-select>
        <!--复选框-->
        <!--日期选择器-->
        <el-date-picker v-if="item.type==='date'"
                        v-model="conditionForm[item.field]"
                        type="date"
                        placeholder="选择日期">
        </el-date-picker>
      </el-form-item>
    </el-form>
    <div slot="footer"
         class="dialog-footer">
      <el-button @click="cancel()">取消</el-button>
      <el-button @click="reset()">清空</el-button>
      <el-button @click="ok()"
                 type="primary">查询</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  data () {
    return {
      visible: false,
      conditionArray: [],
      conditionForm: {}
    }
  },
  props: {
    show: {
      type: Boolean,
      default: false
    },
    condition: {
      type: Array
    },
    form: {
      type: Object
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
    condition: {
      immediate: true,
      handler (condition) {
        this.conditionArray = condition
      }
    },
    form: {
      immediate: true,
      handler (form) {
        this.conditionForm = form
      }
    }
  },
  methods: {
    /**
     * 隐藏dialog查询框
     * 回调父页面 修改显示隐藏值
     */
    cancel () {
      this.visible = false
      this.$emit('hidden', this.visible)
    },
    /**
     * 重置当前表单
     */
    reset () {
      for (const item in this.conditionForm) {
        this.conditionForm[item] = ''
      }
    },
    /**
     * 调用父页面查询方法
     */
    ok () {
      this.visible = false
      this.$emit('hidden', this.visible)
      this.$emit('ok', this.conditionForm)
    }
  }
}
</script>

<style>
</style>
