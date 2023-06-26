<template>
  <div style="margin: 20px;">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-upload ref="leftUpload" :file-list="leftUploadfileList" action="#" :show-file-list="false"
          :on-change="leftHandleChange" :auto-upload="false" :limit="1">
          <el-button slot="trigger" size="medium" type="primary" icon="el-icon-upload">上传左边的NC代码</el-button>
        </el-upload>
      </el-col>
      <el-col :span="6">
        <el-input placeholder="左NC代码" v-model="leftFileName" disabled style="margin-bottom: 10px;">
        </el-input>
      </el-col>
      <el-col :span="6">
        <el-upload ref="rightUpload" :file-list="rightUploadfileList" action="#" :show-file-list="false"
          :on-change="rightHandleChange" :auto-upload="false" :limit="1">
          <el-button slot="trigger" size="medium" type="primary" icon="el-icon-upload">上传右边的NC代码</el-button>
        </el-upload>
      </el-col>
      <el-col :span="6">
        <el-input placeholder="右NC代码" v-model="rightFileName" disabled>
        </el-input>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <code-diff :old-string="diff.oldStr" :new-string="diff.newStr" :context="diff.context"
        :output-format="diff.outputFormat" :diff-style="diff.diffStyle" :file-name="diff.fileName"
        :noDiffLineFeed="diff.noDiffLineFeed" />
    </el-row>
  </div>
</template>

<script>
import { CodeDiff } from 'v-code-diff'

export default {
  name: 'NCCompare',
  components: { CodeDiff },
  props: [],
  data() {
    return {
      leftUploadfileList: [],
      rightUploadfileList: [],
      leftFileName: "",
      rightFileName: "",
      diff: {
        oldStr: "test",
        newStr: "text",
        // 不同地方上下间隔多少行不隐藏
        context: 100,
        // 展示的方式
        outputFormat: "side-by-side",
        // 每行中对比差异级别
        diffStyle: 'word',
        // 展示对比文件列表	
        fileName: '',
        // 不 diff windows 换行符(CRLF)与 linux 换行符(LF)
        noDiffLineFeed: true,
        // 移除字符串前后空白字符
        trim: true
      }
    }
  },
  computed: {},
  watch: {},
  created() { },
  mounted() { },
  methods: {
    leftHandleChange(file, fileList) {
      this.leftFileName = file.name;
      var co = this.changeOldStr;
      var reader = new FileReader();
      reader.readAsText(file.raw, "UTF-8");
      reader.onload = function (e) {
        co(e.target.result);
      }
    },
    rightHandleChange(file, fileList) {
      this.rightFileName = file.name;
      var co = this.changeNewStr;
      var reader = new FileReader();
      reader.readAsText(file.raw, "UTF-8");
      reader.onload = function (e) {
        co(e.target.result);
      }
    },
    changeNewStr(str) {
      this.diff.newStr = str;
    },
    changeOldStr(str) {
      this.diff.oldStr = str;
    },
  }
}

</script>
  
<style scoped lang="scss">
.el-upload__tip {
  line-height: 1.2;
}
</style>
  
  