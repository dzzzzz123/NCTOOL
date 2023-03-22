<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="145px">
      <el-form-item label="刀具描述" prop="toolDescription">
        <el-input v-model="queryParams.toolDescription" placeholder="请输入需要查询的刀具描述" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">查询</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['system:tools:add']">创建新刀具</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['system:tools:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['system:tools:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['system:tools:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="toolsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="NH6300" align="center" prop="nh6300" width="220px" sortable />
      <el-table-column label="NV7000" align="center" prop="nv7000" width="220px" sortable />
      <el-table-column label="MAZAK655" align="center" prop="mazak655" width="220px" sortable />
      <el-table-column label="Tool Description" prop="toolDescription" />
      <el-table-column label="operation" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['system:tools:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['system:tools:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改刀具管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="350px">
        <el-form-item label="刀具在机床NH6300上的位置" prop="nh6300">
          <el-input v-model="form.nh6300" placeholder="请输入刀具在机床NH6300上的位置" />
        </el-form-item>
        <el-form-item label="刀具在机床NV7000上的位置" prop="nv7000">
          <el-input v-model="form.nv7000" placeholder="请输入刀具在机床NV7000上的位置" />
        </el-form-item>
        <el-form-item label="刀具在机床MAZAK655上的位置" prop="mazak655">
          <el-input v-model="form.mazak655" placeholder="请输入刀具在机床MAZAK655上的位置" />
        </el-form-item>
        <el-form-item label="刀具描述" prop="toolDescription">
          <el-input v-model="form.toolDescription" placeholder="请输入刀具描述" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确定</el-button>
        <el-button @click="cancel">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listTools, getTools, delTools, addTools, updateTools } from "@/api/system/tools";

export default {
  name: "Tools",
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
      // 刀具管理表格数据
      toolsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        nh6300: null,
        nv7000: null,
        mazak655: null,
        toolDescription: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        toolDescription: [
          { required: true, message: "刀具描述不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询刀具管理列表 */
    getList() {
      this.loading = true;
      listTools(this.queryParams).then(response => {
        this.toolsList = response.rows;
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
        nh6300: null,
        nv7000: null,
        mazak655: null,
        toolDescription: null,
        delFlag: null
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
      this.title = "添加刀具管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const toolId = row.toolId || this.ids
      getTools(toolId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改刀具管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.toolId != null) {
            updateTools(this.form).then(response => {
              this.$modal.msgSuccess("修改成功！");
              this.open = false;
              this.getList();
            });
          } else {
            addTools(this.form).then(response => {
              this.$modal.msgSuccess("添加成功！");
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
      const toolDescription = row.toolDescription || this.toolDescription;
      this.$modal.confirm('你是否确认删除刀具描述为 "' + toolDescription + '" 的刀具?').then(function () {
        return delTools(toolIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功！");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/tools/export', {
        ...this.queryParams
      }, `tools_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
