<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="科室名称" prop="deptId">
        <!-- <el-input
          v-model="queryParams.deptId"
          placeholder="请输入科室名称"
          clearable
          @keyup.enter.native="handleQuery"
        /> -->
        <el-select v-model="queryParams.deptId" clearable>
          <el-option
          v-for="item in listPlace"
          :key="item.placeId"
          :label="item.placeName"
          :value="item.placeId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="巡检内容" prop="itemId">
        <!-- <el-input
          v-model="queryParams.itemId"
          placeholder="请输入巡检内容"
          clearable
          @keyup.enter.native="handleQuery"
        /> -->
        <el-select v-model="queryParams.itemId" clearable>
          <el-option
          v-for="item in listItem"
          :key="item.id"
          :label="item.itemName"
          :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['zayy:checkItemDept:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['zayy:checkItemDept:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['zayy:checkItemDept:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['zayy:checkItemDept:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="checkItemDeptList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="科室名称" align="center" prop="deptName" />
      <el-table-column label="巡检内容" align="center" prop="itemName" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['zayy:checkItemDept:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['zayy:checkItemDept:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改巡检配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="科室名称" prop="deptId">
          <!-- <el-input v-model="form.deptId" placeholder="请输入科室名称" /> -->
          <el-select v-model="form.deptId" clearable>
            <el-option
            v-for="item in listPlace"
            :key="item.placeId"
            :label="item.placeName"
            :value="item.placeId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="巡检内容" prop="itemId">
          <!-- <el-input v-model="form.itemId" placeholder="请输入巡检内容" /> -->
          <el-select v-model="form.itemId" clearable>
            <el-option
            v-for="item in listItem"
            :key="item.id"
            :label="item.itemName"
            :value="item.id"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCheckItemDept, getCheckItemDept, delCheckItemDept, addCheckItemDept, updateCheckItemDept } from "@/api/zayy/checkItemDept";
import { listFbyDept } from "@/api/zayy/fbyDept";
import { listCheckItem } from "@/api/zayy/checkItem";
import { listCheckPlace } from "@/api/zayy/checkPlace";

export default {
  name: "CheckItemDept",
  data() {
    return {
      listPlace: [],
      listItem: [],
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
      // 巡检配置表格数据
      checkItemDeptList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deptId: null,
        itemId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询巡检配置列表 */
    getList() {
      this.loading = true;
      listCheckItemDept(this.queryParams).then(response => {
        let obj = {
          pageNum: 1,
          pageSize: 100
        }
        listFbyDept(obj).then(res => {
          listCheckItem(obj).then(res => {
            this.listItem = res.rows
            res.rows.forEach(item => {
              response.rows.forEach(k => {
                if(k.itemId == item.id) {{
                  k.itemName = item.itemName
                }}
              })
            })
            this.checkItemDeptList = response.rows;
            this.total = response.total;
            this.loading = false;
          });
          res.rows.forEach(item => {
            response.rows.forEach(k => {
              if(k.deptId == item.id) {{
                k.deptName = item.deptName
              }}
            })
          })
        });
        listCheckPlace(obj).then(res => {
          this.listPlace = res.rows;
        });
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
        id: null,
        deptId: null,
        itemId: null
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加巡检配置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCheckItemDept(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改巡检配置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCheckItemDept(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCheckItemDept(this.form).then(response => {
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
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除巡检配置编号为"' + ids + '"的数据项？').then(function() {
        return delCheckItemDept(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('zayy/checkItemDept/export', {
        ...this.queryParams
      }, `checkItemDept_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
