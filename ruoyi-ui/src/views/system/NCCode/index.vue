<template>
    <div style="margin: 20px;">
        <div style="width: 49%; min-height: 1000px; float: left; background-color: aliceblue;">
            <file-upload class="btn btn-primary" v-model="fileList" :multiple="true" :directory="true" :drop="true"
                :drop-directory="true" extensions="tap,pdf" @input-filter="inputFilter" @input-file="inputFile"
                ref="upload">
            </file-upload>
            <el-button size="small" type="primary" @click="onAddFolader" style="margin-top: 13px; margin-left: 20px;">
                <el-dropdown>
                    <span class="el-dropdown-link">
                        点击上传<i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item icon="el-icon-plus" @click.native="onAddFolader">上传文件夹</el-dropdown-item>
                        <el-dropdown-item icon="el-icon-circle-plus" @click.native="onAddFiles">上传文件</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </el-button>
            <el-button @click="transform" size="small" type="primary" style="margin-left: 15px;">转换NC代码</el-button>
            <el-button @click="uploadPdf2" size="small" type="primary" style="margin-left: 15px;">上传pdf文档</el-button>
            <el-table v-loading="toTransForm.loading" :data="toTransForm.tapList" :show-header="false"
                @row-click="toTransFormrowClick" :row-class-name="toTransFormrowClassName" :row-style="toTransFormrowStyle"
                :stripe="false" style=" background: aliceblue !important; margin-top: 20px; border:none;">
                <el-table-column key="tapFileName" prop="file.name" />
            </el-table>
        </div>
        <div style="width: 49%;  min-height: 1000px; float: right; padding-top: 13px; background-color: beige;">
            <el-button @click="compare" size="small" type="warning" style="margin-left: 25px;">比较NC代码</el-button>
            <el-button type="warning" size="small" @click="checkTapNames" style="margin-left: 15px;">查询tap文件</el-button>
            <el-input v-model="tapNameToCheck" placeholder="请输入tap文件名" size="small" @keyup.enter.native="checkTapNames"
                clearable style="width: 35%; margin-left: 15px;"></el-input>
            <el-table v-loading="Transformed.loading" :data="Transformed.tapList" :show-header="false"
                @row-click="TransformedrowClick" :row-class-name="TransformedrowClassName" :row-style="TransformedrowStyle"
                :stripe="false" style=" background: beige !important; margin-top: 20px; border:none;">
                <el-table-column key="tapFileName" prop="tapFileName" />
            </el-table>
        </div>
        <el-dialog title="NC代码比较" :visible.sync="diff.open" :fullscreen="true">
            <code-diff :old-string="diff.oldStr" :new-string="diff.newStr" :context="diff.context"
                :output-format="diff.outputFormat" :diff-style="diff.diffStyle" :file-name="diff.fileName"
                :noDiffLineFeed="diff.noDiffLineFeed" />
        </el-dialog>
    </div>
</template>

<script>
import { CodeDiff } from 'v-code-diff'
import FileUpload from 'vue-upload-component'
import { uploadNcCode, uploadPdf, transFormNcCode, newTapList, compareDownload, uploadToDNC, getTapNames, insertTapNames } from "@/api/system/nccode.js"
export default {
    name: 'NCCode',
    components: { CodeDiff, FileUpload },
    props: [],
    data() {
        return {
            fileList: [],
            pdfFileList: [],
            timer: null,
            diffLeftFile: null,
            diffRightFile: null,
            isTransFormed: false,
            tapNameToCheck: null,
            toTransForm: {
                tapList: [],
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
    computed: {
    },
    watch: {
    },
    created() {
    },
    mounted() { },
    methods: {
        inputFilter(newFile, oldFile, prevent) {
            if (newFile && !oldFile) {
                // Before adding a file
                // 添加文件前

                // Filter system files or hide files
                // 过滤系统文件 和隐藏文件
                if (/(\/|^)(Thumbs\.db|desktop\.ini|\..+)$/.test(newFile.name)) {
                    return prevent()
                }

                // Filter php html js file
                // 过滤 php html js 文件
                if (/\.(php5?|html?|jsx?)$/i.test(newFile.name)) {
                    return prevent()
                }
            }
        },

        inputFile(newFile, oldFile) {
            if (newFile && !oldFile) {
                // add
                // console.log('add', newFile)
                this.isTransFormed = false;
                let arr = this.filterRepetition(this.fileList);
                if (arr.length !== this.fileList.length) {
                    this.$message.warning("已过滤重复与不是以.tap结尾的文件");
                }
                // 上传文件后，自动把文件传给后台，这里做了一个防抖，等待500ms后在传给后台
                if (this.fileList.length > 0) {
                    this.fileList = arr;
                    this.debounce(this.uploadTap, 500);
                    this.getNcList();
                }
                if (this.pdfFileList.length > 0) {
                    this.pdfFileList = this.filterRepetition2(this.pdfFileList);
                }
            }
            if (newFile && oldFile) {
                // update
                // console.log('update', newFile)
            }

            if (!newFile && oldFile) {
                // remove
                // console.log('remove', oldFile)
            }

            // Automatically activate upload
            if (Boolean(newFile) !== Boolean(oldFile) || oldFile.error !== newFile.error) {
                if (!this.$refs.upload.active) {
                    this.$refs.upload.active = true
                }
            }
        },
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
            });
        },
        async onAddFolader() {
            let input = this.$refs.upload.$el.querySelector('input');
            input.click();
        },
        onAddFiles() {
            let input = this.$refs.upload.$el.querySelector('input');
            input.directory = false;
            input.webkitdirectory = false;

            input.onclick = null;
            input.click();
            input.onclick = (e) => {
                input.directory = true;
                input.webkitdirectory = true;
            }
        },
        // 过滤重复
        filterRepetition(arr) {
            let arr1 = []; //存id
            let newArr = []; //存新数组
            for (let i in arr) {
                if (arr1.indexOf(arr[i].file.name) == -1) {
                    if (arr[i].name.endsWith(".tap")) {
                        arr1.push(arr[i].file.name);
                        newArr.push(arr[i]);
                    } else {
                        this.pdfFileList.push(arr[i]);
                    }
                }
            }
            return newArr;
        },
        // 过滤pdf重复
        filterRepetition2(arr) {
            let arr1 = []; //存id
            let newArr = []; //存新数组
            for (let i in arr) {
                if (arr1.indexOf(arr[i].file.name) == -1) {
                    arr1.push(arr[i].file.name);
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
                formData.append('file', file.file);
            });
            uploadNcCode(formData).then(response => {
                if (response.code === 200) {
                    this.$message.success("上传成功！");
                }
            });
        },
        // 修改vue-diff的左边代码
        handleDiffLeft(file) {
            let reader = new FileReader();
            reader.readAsText(file, 'UTF-8')//读取，转换字符编码
            var co = this.changeOldStr;
            reader.onload = function (e) {
                co(e.target.result);//获取数据
            }
        },
        // 当前点击行index
        toTransFormrowClick(row) {
            this.toTransForm.rowIndex = row.index;
            this.diffLeftFile = row.file.name;
            this.handleDiffLeft(row.file);
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
                if (!this.toTransForm.tapNames.includes(file.file.name)) {
                    this.toTransForm.tapNames.push(file.file.name);
                }
            });
            if (this.toTransForm.tapNames.length === 0) {
                this.$message.error("没有可以转换的tap文件，请上传需要转换的tap文件！");
                return;
            }
            this.$message.warning("转换NC代码中......");
            transFormNcCode(this.toTransForm.tapNames).then(response => {
                if (response.code === 200) {
                    this.$message.success("转换NC代码成功！");
                    this.getTapList(this.fileList);
                    this.$message.info("正在将NC代码上传到DNC！");
                    this.uploadDNC(response.code);
                }
            });
        },
        uploadPdf2() {
            this.debounce(this.uploadPdf, 500);
        },
        async uploadPdf() {
            let formData = new FormData();
            this.pdfFileList.forEach(file => {
                formData.append('file', file.file);
            });
            uploadPdf(formData).then(response => {
                if (response.code === 200) {
                    this.$message.success("上传PDF文档成功！");
                }
            });
        },
        compare() {
            if (this.diffLeftFile === null || this.diffRightFile === null) {
                this.$message.warning("请选中需要比较的NC代码后比较!");
                return;
            }
            compareDownload(this.diffRightFile).then(response => {
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
        uploadDNC(code) {
            if (code == 200) {
                uploadToDNC(this.toTransForm.tapNames).then(response => {
                    if (response.code == 200) {
                        this.$message.success("上传到DNC成功!");
                        insertTapNames(this.toTransForm.tapNames);
                    }
                })
            } else {
                this.$message.error("转换NC代码失败，无法上传到DNC！");
            }
        },
        checkTapNames() {
            getTapNames(this.tapNameToCheck).then(response => {
                if (response === true) {
                    this.$message.success("此tap文件已转换，且存在DNC中！");
                } else {
                    this.$message.warning("此tap文件不存在DNC中！");
                }
            })
        }
    }
}
</script>
<style lang="scss">
.el-upload__tip {
    line-height: 1.2;
}

.el-dropdown {
    font-size: 12px;
    font-weight: 400;
}

.el-dropdown-link {
    cursor: pointer;
    color: #FFFFFF;
}

.el-icon-arrow-down {
    font-size: 12px;
}
</style>