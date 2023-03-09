<template>
  <div style="margin: 20px;">
    <el-col :span="6">
      <el-upload ref="leftUpload" :file-list="leftUploadfileList" :action="leftUploadAction"
        :before-upload="leftUploadBeforeUpload">
        <el-button size="small" type="primary" icon="el-icon-upload">点击上传</el-button>
      </el-upload>
    </el-col>
    <el-col :span="6">
      <el-input clearable :style="{ width: '100%' }">
      </el-input>
    </el-col>
    <el-col :span="6">
      <el-upload ref="rightUpload" :file-list="rightUploadfileList" :action="rightUploadAction"
        :before-upload="rightUploadBeforeUpload">
        <el-button size="small" type="primary" icon="el-icon-upload">点击上传</el-button>
      </el-upload>
    </el-col>
    <el-col :span="6">
      <el-input clearable :style="{ width: '100%' }">
      </el-input>
    </el-col>
    <code-diff :old-string="diff.oldStr" :new-string="diff.newStr" :context="diff.context"
      :output-format="diff.outputFormat" :draw-file-list="diff.drawFileList"
      :render-nothing-when-empty="diff.renderNothingWhenEmpty" :diff-style="diff.diffStyle" :file-name="diff.fileName"
      :is-show-no-change="diff.isShowNoChange" />
  </div>
</template>

<script>
import CodeDiff from 'vue-code-diff'

export default {
  name: 'NCCompare',
  components: { CodeDiff },
  props: [],
  data() {
    return {
      field103Action: 'https://jsonplaceholder.typicode.com/posts/',
      field103fileList: [],
      field102Action: 'https://jsonplaceholder.typicode.com/posts/',
      field102fileList: [],
      diff: {
        open: false,
        oldStr: "test",
        newStr: "text",
        // 不同地方上下间隔多少行不隐藏
        context: 100,
        // 展示的方式
        outputFormat: "side-by-side",
        // 展示对比文件列表	
        diffStyle: 'char',
        fileName: '',
        // 当无对比时展示源代码
        isShowNoChange: true,
        // 展示对比文件列表
        drawFileList: false,
        // 当无对比时不渲染
        renderNothingWhenEmpty: false,
      }
    }
  },
  computed: {},
  watch: {},
  created() { },
  mounted() { },
  methods: {
    field103BeforeUpload(file) {
      let isRightSize = file.size / 1024 / 1024 < 2
      if (!isRightSize) {
        this.$message.error('文件大小超过 2MB')
      }
      return isRightSize
    },
    field102BeforeUpload(file) {
      let isRightSize = file.size / 1024 / 1024 < 2
      if (!isRightSize) {
        this.$message.error('文件大小超过 2MB')
      }
      return isRightSize
    },
  }
}

</script>
  
<style scoped lang="scss">
.el-upload__tip {
  line-height: 1.2;
}
</style>
  
  