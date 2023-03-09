<template>
  <div style="margin: 20px;">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-upload ref="leftUpload" :file-list="leftUploadfileList" action="#" :show-file-list="false"
          :on-change="leftHandleChange" accept=".tap_Original,.tap_MMC_NH6300,.tap_MMC_NV7000,.tap_V655"
          :auto-upload="false" :limit="1">
          <el-button slot="trigger" size="medium" type="primary" icon="el-icon-upload">上传左边的NC代码</el-button>
        </el-upload>
      </el-col>
      <el-col :span="6">
        <el-input placeholder="左NC代码" v-model="leftFileName" disabled style="margin-bottom: 10px;">
        </el-input>
      </el-col>
      <el-col :span="6">
        <el-upload ref="rightUpload" :file-list="rightUploadfileList" action="#" :show-file-list="false"
          :on-change="rightHandleChange" accept=".tap_Original,.tap_MMC_NH6300,.tap_MMC_NV7000,.tap_V655"
          :auto-upload="false" :limit="1">
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
        :output-format="diff.outputFormat" :draw-file-list="diff.drawFileList"
        :render-nothing-when-empty="diff.renderNothingWhenEmpty" :diff-style="diff.diffStyle" :file-name="diff.fileName"
        :is-show-no-change="diff.isShowNoChange" />
    </el-row>
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
      leftUploadfileList: [],
      rightUploadfileList: [],
      leftFileName: "",
      rightFileName: "",
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
  
  