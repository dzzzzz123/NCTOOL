<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="刀具ID" prop="toolId">
        <el-input v-model="queryParams.toolId" placeholder="请输入刀具ID" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="刀具描述" prop="description">
        <el-input v-model="queryParams.description" placeholder="请输入刀具描述" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['system:pocket:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['system:pocket:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['system:pocket:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['system:pocket:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="pocketList" @selection-change="handleSelectionChange"
      @cell-click="handleCellClick">
      <el-table-column type="selection" align="center" />
      <el-table-column label="刀具ID" align="center" prop="toolId" />
      <el-table-column label="刀具描述" align="center" prop="description" />
      <el-table-column label="参数" align="center" prop="parameter">
        <el-button size="medium" type="primary">点击查看参数</el-button>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="medium" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['system:pocket:edit']">修改</el-button>
          <el-button size="medium" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['system:pocket:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改刀具加工参数对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="刀具ID" prop="toolId">
          <el-input v-model="form.toolId" placeholder="请输入刀具ID" />
        </el-form-item>
        <el-form-item label="刀具描述" prop="description">
          <el-input v-model="form.description" placeholder="请输入刀具描述" />
        </el-form-item>
        <el-form-item v-if="title != '添加刀具加工参数'" label="参数" prop="parameter">
          <vue-json-pretty v-model:data="form.parameter" :editable=true />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 查看所有参数对话框 -->
    <el-dialog :title="parameter.title" :visible.sync="parameter.open">
      <div>
        <vue-json-pretty :data=parameter.data :deep=parameter.deep />
      </div>
    </el-dialog>
  </div>
</template>



<script>
import { listPocket, getPocket, delPocket, addPocket, updatePocket } from "@/api/system/pocket";
import VueJsonPretty from 'vue-json-pretty';
import 'vue-json-pretty/lib/styles.css';

export default {
  name: "Pocket",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 刀具加工参数表格数据
      pocketList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        toolId: null,
        description: null,
        parameter: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        toolId: [
          { required: true, message: "刀具ID不能为空", trigger: "blur" }
        ],
        description: [
          { required: true, message: "刀具描述不能为空", trigger: "blur" }
        ],
      },
      parameter: {
        title: "",
        open: false,
        data: {},
        deep: 10
      }
    };
  },
  components: {
    VueJsonPretty
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询刀具加工参数列表 */
    getList() {
      this.loading = true;
      listPocket(this.queryParams).then(response => {
        this.pocketList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        toolId: null,
        description: null,
        parameter: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.toolId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加刀具加工参数";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const toolId = row.toolId || this.ids
      getPocket(toolId).then(response => {
        this.form = response.data;
        this.form.parameter = _.pick(row.parameter, ['FeedRate', 'PeckDepth', 'SpindelSpeed']);
        this.open = true;
        this.title = "修改刀具加工参数";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          console.info(this.form);
          if (this.form.parameter != null) {
            updatePocket(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPocket(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const toolIds = row.toolId || this.ids;
      this.$modal.confirm('是否确认删除刀具ID为"' + toolIds + '"的数据项？').then(function () {
        return delPocket(toolIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/pocket/export', {
        ...this.queryParams
      }, `pocket_${new Date().getTime()}.xlsx`)
    },
    handleCellClick(row, column, cell, event) {
      if (column.label === "参数") {
        this.openJsonView(_.pick(row.parameter, ['FeedRate', 'PeckDepth', 'SpindelSpeed']));
      }
    },
    openJsonView(parameters) {
      this.parameter.title = "参数列表";
      this.parameter.open = true;
      this.parameter.data = parameters;
    }
  }
};
</script>
