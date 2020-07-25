<template>
  <el-dialog :title="formTitle"
             :visible.sync="visible"
             :close-on-click-modal="false"
             append-to-body
             @close="cancel">
    <el-form :inline="true"
             label-width="80px"
             :rules="formRules"
             :model="formModel"
             ref="formView">
      <el-form-item v-for="(item,index) in controlArray"
                    :key="index"
                    v-show="item.show"
                    :label="item.label"
                    :prop="item.field">
        <!-- 文本框 -->
        <el-input v-if="item.type==='input'"
                  v-model="formModel[item.field]"
                  :disabled="item.readonly"></el-input>
        <el-input v-if="item.type===null"
                  v-model="formModel[item.field]"
                  :disabled="item.readonly"></el-input>
        <!--密码框-->
        <el-input v-if="item.type==='password'"
                  v-model="formModel[item.field]"
                  type="password"
                  :disabled="item.readonly"></el-input>
        <!--隐藏域-->
        <el-input v-if="item.type==='hidden'"
                  v-model="formModel[item.field]"
                  type="hidden"
                  :disabled="item.readonly"></el-input>
        <!--数字输入框-->
        <el-input-number v-if="item.type==='number'"
                         v-model="formModel[item.field]"
                         controls-position="right"
                         :min="formModel[item.min]"
                         :max="formModel[item.max]"></el-input-number>
        <!--下拉框-->
        <el-select v-if="item.type==='select'"
                   v-model="formModel[item.field]"
                   placeholder="请选择"
                   :disabled="item.readonly">
          <el-option v-for="option in item.options"
                     :key="option.value"
                     :label="option.label"
                     :value="option.value"
                     :disabled="option.disabled">
          </el-option>
        </el-select>
        <!--图表选择下拉框-->
        <el-popover v-if="item.type==='icon-select'"
                    placement="bottom"
                    width="300"
                    trigger="click">
          <el-input slot="reference"
                    v-model="formModel[item.field]"
                    :disabled="item.readonly"
                    readonly="readonly"></el-input>
          <div ref="iconSelect"
               class="icon-content">
            <i class="el-icon-edit"
               @click="formModel[item.field]='el-icon-edit'"></i>
            <i class="el-icon-share"
               @click="formModel[item.field]='el-icon-share'"></i>
            <i class="el-icon-delete"
               @click="formModel[item.field]='el-icon-delete'"></i>
            <i class="el-icon-s-tools"
               @click="formModel[item.field]='el-icon-s-tools'"></i>
            <i class="el-icon-setting"
               @click="formModel[item.field]='el-icon-setting'"></i>
            <i class="el-icon-user-solid"
               @click="formModel[item.field]='el-icon-user-solid'"></i>
            <i class="el-icon-user"
               @click="formModel[item.field]='el-icon-user'"></i>
            <i class="el-icon-phone-outline"
               @click="formModel[item.field]='el-icon-phone-outline'"></i>
            <i class="el-icon-more-outline"
               @click="formModel[item.field]='el-icon-more-outline'"></i>
            <i class="el-icon-star-off"
               @click="formModel[item.field]='el-icon-star-off'"></i>
            <i class="el-icon-s-goods"
               @click="formModel[item.field]='el-icon-s-goods'"></i>
            <i class="el-icon-warning"
               @click="formModel[item.field]='el-icon-warning'"></i>
            <i class="el-icon-question"
               @click="formModel[item.field]='el-icon-question'"></i>
            <i class="el-icon-info"
               @click="formModel[item.field]='el-icon-info'"></i>
            <i class="el-icon-s-help"
               @click="formModel[item.field]='el-icon-s-help'"></i>
            <i class="el-icon-picture"
               @click="formModel[item.field]='el-icon-picture'"></i>
            <i class="el-icon-camera-solid"
               @click="formModel[item.field]='el-icon-camera-solid'"></i>
            <i class="el-icon-upload"
               @click="formModel[item.field]='el-icon-upload'"></i>
            <i class="el-icon-s-cooperation"
               @click="formModel[item.field]='el-icon-cooperation'"></i>
            <i class="el-icon-s-promotion"
               @click="formModel[item.field]='el-icon-s-promotion'"></i>
            <i class="el-icon-s-platform"
               @click="formModel[item.field]='el-icon-s-platform'"></i>
            <i class="el-icon-s-order"
               @click="formModel[item.field]='el-icon-s-order'"></i>
            <i class="el-icon-s-management"
               @click="formModel[item.field]='el-icon-s-management'"></i>
            <i class="el-icon-s-release"
               @click="formModel[item.field]='el-icon-s-release'"></i>
            <i class="el-icon-s-open"
               @click="formModel[item.field]='el-icon-s-open'"></i>
            <i class="el-icon-s-check"
               @click="formModel[item.field]='el-icon-s-check'"></i>
            <i class="el-icon-menu"
               @click="formModel[item.field]='el-icon-menu'"></i>
            <i class="el-icon-s-flag"
               @click="formModel[item.field]='el-icon-s-flag'"></i>
            <i class="el-icon-s-comment"
               @click="formModel[item.field]='el-icon-s-comment'"></i>
            <i class="el-icon-s-claim"
               @click="formModel[item.field]='el-icon-s-claim'"></i>
          </div>
        </el-popover>
        <!--复选框-->
        <!--日期选择器-->
        <el-date-picker v-if="item.type==='date'"
                        v-model="formModel[item.field]"
                        type="date"
                        placeholder="选择日期">
        </el-date-picker>
        <!--文件上传-->
        <el-upload v-if="item.type==='upload'"
                   :headers="importHeaders"
                   action="/api/sysUpload/upload"
                   name="multipartfiles"
                   :on-change="handleChange"
                   :file-list="formModel[item.field]['fileList']">
          <el-button size="small"
                     type="primary"
                     name="flie">点击上传</el-button>
          <div slot="tip"
               class="el-upload__tip">{{formModel[item.field]['acceptPrompt']}}</div>
        </el-upload>
      </el-form-item>
    </el-form>
    <div slot="footer"
         class="dialog-footer">
      <el-button @click="cancel()">取消</el-button>
      <el-button @click="reset()">重置</el-button>
      <el-button @click="ok()"
                 type="primary">提交</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  data () {
    return {
      visible: false,
      formTitle: '',
      controlArray: [],
      formModel: {},
      formRules: {},
      fileList: [],
      importHeaders: { Authorization: 'Bearer ' + localStorage.getItem('token') }
    }
  },
  props: {
    show: {
      type: Boolean,
      default: false
    },
    control: {
      type: Array
    },
    model: {
      type: Object
    },
    title: {
      type: String
    },
    rules: {
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
    title: {
      immediate: true,
      handler (title) {
        this.formTitle = title
      }
    },
    control: {
      immediate: true,
      handler (control) {
        this.controlArray = control
      }
    },
    model: {

      immediate: true,
      handler (model) {
        this.formModel = model
      }
    },
    rules: {

      immediate: true,
      handler (rules) {
        this.formRules = rules
      }
    }
  },
  methods: {
    handleChange (file, fileList) {
      this.fileList = fileList.slice(-3)
    },
    /**
     * dialog 关闭事件
     */
    close () {
      this.visible = false
      this.$emit('hidden', this.visible)
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
     * 重置当前表单
     */
    reset () {
      for (const item in this.formModel) {
        this.formModel[item] = ''
        if (this.formModel[item].fileList) {
          this.formModel[item].fileList = []
        }
      }
    },
    /**
     * 调用父页面查询方法
     */
    ok () {
      this.$refs.formView.validate((valid) => {
        if (valid) {
          this.$emit('ok', this.formModel)
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
.el-form--inline {
  text-align: left;
}
.icon-content {
  i {
    font-size: 25px;
    padding: 10px;
    cursor: pointer;
  }
}
</style>>
