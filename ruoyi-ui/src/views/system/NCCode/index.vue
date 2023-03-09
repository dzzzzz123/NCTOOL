<template>
    <div style="margin: 20px;">
        <div style="width: 49%; min-height: 1000px; float: left; background-color: aliceblue;">
            <el-upload class="upload-demo" action="#" :on-exceed="handleExceed" :on-change="handleChange"
                :file-list="fileList" :show-file-list="false" accept=".tap_Original" style="margin: 10px;" multiple
                :limit="50" :auto-upload="false">
                <el-button slot="trigger" size="small" type="primary">点击上传</el-button>
                <el-button @click="transform" size="small" type="primary" style="margin-left: 20px;">转换NC代码</el-button>
                <el-table v-loading="toTransForm.loading" :data="toTransForm.tapList" :show-header="false"
                    @row-click="toTransFormrowClick" :row-class-name="toTransFormrowClassName"
                    :row-style="toTransFormrowStyle" :stripe="false"
                    style=" background: aliceblue !important; margin-top: 20px; border:none;">
                    <el-table-column key="tapFileName" prop="name" />
                </el-table>
            </el-upload>
        </div>
        <div style="width: 49%;  min-height: 1000px; float: right; padding-top: 13px; background-color: beige;">
            <el-button @click="refresh" size="small" type="warning" style="margin-right: 20px;">刷新列表</el-button>
            <el-button @click="compare" size="small" type="warning" style="margin-right: 20px;">比较NC代码</el-button>
            <el-button @click="uploadToDNC" size="small" type="warning">上传到DNC</el-button>
            <el-table v-loading="Transformed.loading" :data="Transformed.tapList" :show-header="false"
                @row-click="TransformedrowClick" :row-class-name="TransformedrowClassName" :row-style="TransformedrowStyle"
                :stripe="false" style=" background: beige !important; margin-top: 20px; border:none;">
                <el-table-column key="tapFileName" prop="tapFileName" />
            </el-table>
        </div>
        <el-dialog title="NC代码比较" :visible.sync="diff.open" :fullscreen="true">
            <code-diff :old-string="diff.oldStr" :new-string="diff.newStr" :context="diff.context"
                :output-format="diff.outputFormat" :draw-file-list="diff.drawFileList"
                :render-nothing-when-empty="diff.renderNothingWhenEmpty" :diff-style="diff.diffStyle"
                :file-name="diff.fileName" :is-show-no-change="diff.isShowNoChange" />
        </el-dialog>
    </div>
</template>
 
<script>
import CodeDiff from 'vue-code-diff'
import { uploadNcCode, transFormNcCode, newTapList, compareDownload } from "@/api/system/nccode.js"
export default {
    name: 'NCCode',
    components: { CodeDiff },
    props: [],
    data() {
        return {
            fileList: [],
            timer: null,
            diffLeftFile: null,
            diffRightFile: null,
            isTransFormed: false,
            toTransForm: {
                tapList: null,
                loading: false,
                rowIndex: null,
                tapNames: [],
            },
            Transformed: {
                tapList: null,
                loading: false,
                rowIndex: null,
            },
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
    computed: {
    },
    watch: {
    },
    created() {
    },
    mounted() { },
    methods: {
        getNcList() {
            this.toTransForm.loading = true;
            this.toTransForm.tapList = this.fileList;
            this.toTransForm.loading = false;
        },
        getTapList() {
            this.Transformed.loading = true;
            this.Transformed.tapList = null;
            newTapList(this.toTransForm.tapNames).then(response => {
                this.Transformed.tapList = response;
                this.Transformed.loading = false;
            }
            );
        },
        handleChange(file, fileList) {
            this.isTransFormed = false;
            let arr = this.filterRepetition(fileList);
            if (arr.length !== fileList.length) {
                this.$message.warning("上传重复文件，已过滤重复文件");
            }
            this.fileList = arr;
            // 上传文件后，自动把文件传给后台，这里做了一个防抖，等待500ms后在传给后台
            this.debounce(this.uploadTap, 500);
            this.getNcList();
        },
        handleExceed(files, fileList) {
            this.$message.warning(`当前限制选择 50 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
        },
        // 过滤重复
        filterRepetition(arr) {
            let arr1 = []; //存id
            let newArr = []; //存新数组
            for (let i in arr) {
                if (arr1.indexOf(arr[i].name) == -1) {
                    arr1.push(arr[i].name);
                    newArr.push(arr[i]);
                }
            }
            return newArr;
        },
        // element上传多个文件时，会把每个文件做个单独请求
        // 这里的方法是请求最后一次
        debounce(fn, waits) {
            if (this.timer) {
                clearTimeout(this.timer);
                this.timer = null;
            }
            this.timer = setTimeout(() => {
                fn.apply(this, arguments); // 把参数传进去
            }, waits);
        },
        async uploadTap() {
            let formData = new FormData();
            this.fileList.forEach(file => {
                formData.append('file', file.raw);
            });
            uploadNcCode(formData).then(response => {
                if (response.code === 200) {
                    this.$message.success("上传成功！");
                }
            });
        },
        refresh() {
            this.getTapList();
        },
        // 修改vue-diff的左边代码
        handleDiffLeft(file) {
            let reader = new FileReader();
            reader.readAsText(file.raw, 'UTF-8')//读取，转换字符编码
            var co = this.changeOldStr;
            reader.onload = function (e) {
                let val = e.target.result;//获取数据
                co(val);
            }
        },
        // 当前点击行index
        toTransFormrowClick(row) {
            this.toTransForm.rowIndex = row.index;
            this.diffLeftFile = row.name;
            this.handleDiffLeft(row);
        },
        // 把每行的 index 放入 row 中
        toTransFormrowClassName({ row, rowIndex }) {
            row.index = rowIndex
        },
        // 如果当前行index 是点击行，就改变颜色
        toTransFormrowStyle({ row, rowIndex }) {
            if (this.toTransForm.rowIndex === rowIndex) {
                return {
                    'background-color': 'aliceblue !important'
                }
            }
        },
        // 当前点击行index
        TransformedrowClick(row) {
            this.Transformed.rowIndex = row.index;
            this.diffRightFile = row.tapFileName;
        },
        // 把每行的 index 放入 row 中
        TransformedrowClassName({ row, rowIndex }) {
            row.index = rowIndex
        },
        // 如果当前行index 是点击行，就改变颜色
        TransformedrowStyle({ row, rowIndex }) {
            if (this.Transformed.rowIndex === rowIndex) {
                return {
                    'background-color': 'beige !important'
                }
            }
        },
        transform() {
            if (this.isTransFormed) {
                this.$message.warning("NC代码已转换，请勿重复转换！");
                return;
            }
            this.isTransFormed = true;
            this.fileList.forEach(file => {
                if(!this.toTransForm.tapNames.includes(file.name)){
                    this.toTransForm.tapNames.push(file.name);
                }
            });
            if (this.toTransForm.tapNames.length === 0) {
                this.$message.warning("没有可以转换的tap文件，请上传需要转换的tap文件！");
                return;
            }
            this.$message.warning("转换NC代码!");
            transFormNcCode(this.toTransForm.tapNames).then(response => {
                if (response.code === 200) {
                    this.$message.success("转换NC代码成功！");
                    this.getTapList(this.fileList);
                }
            });
        },
        compare() {
            if (this.diffLeftFile === null || this.diffRightFile === null) {
                this.$message.warning("请选中需要比较的NC代码后比较!");
                return;
            }
            compareDownload(this.diffLeftFile, this.diffRightFile).then(response => {
                this.changeNewStr(response);
            });
            this.diff.open = true;
        },
        changeNewStr(str) {
            this.diff.newStr = str;
        },
        changeOldStr(str) {
            this.diff.oldStr = str;
        },
        uploadToDNC() {
            this.$message.warning("上传到DNC!");
        },
    }
}
</script>
<style lang="scss">
.el-upload__tip {
    line-height: 1.2;
}
</style>