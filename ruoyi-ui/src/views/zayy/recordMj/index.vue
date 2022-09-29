<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备编号" prop="deviceNo">
        <el-input
          v-model="queryParams.deviceNo"
          placeholder="请输入设备编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="姓名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="身份证" prop="userIdcard">
        <el-input
          v-model="queryParams.userIdcard"
          placeholder="请输入身份证"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="健康码" prop="userHealthcode">
        <el-input
          v-model="queryParams.userHealthcode"
          placeholder="请输入健康码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开门结果" prop="openResult">
        <!-- <el-input
          v-model="queryParams.openResult"
          placeholder="请输入开门结果"
          clearable
          @keyup.enter.native="handleQuery"
        /> -->
        <el-select
        v-model="queryParams.openResult"
        clearable
        placeholder="请选择开门结果">
          <el-option
          v-for="item in openResultList"
          :key="item.value"
          :label="item.label"
          :value="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="通过时间" prop="accessTime">
        <el-date-picker clearable
          v-model="queryParams.accessTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择通过时间">
        </el-date-picker>
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
          v-hasPermi="['zayy:recordMj:add']"
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
          v-hasPermi="['zayy:recordMj:edit']"
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
          v-hasPermi="['zayy:recordMj:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['zayy:recordMj:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="recordMjList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="设备编号" align="center" prop="deviceNo" />
      <el-table-column label="通道类型" align="center" prop="accessType" />
      <el-table-column label="人脸图片" align="center" prop="userFaceImg" />
      <el-table-column label="姓名" align="center" prop="userName" />
      <el-table-column label="身份证" align="center" prop="userIdcard" />
      <el-table-column label="体温" align="center" prop="userTemp" />
      <el-table-column label="健康码" align="center" prop="userHealthcode" />
      <el-table-column label="开门方式" align="center" prop="openType" />
      <el-table-column label="开门结果" align="center" prop="openResult">
        <template slot-scope="scope">
          <span v-if="scope.row.openResult == 1">开门成功</span>
          <span v-if="scope.row.openResult == 2">人证核验失败</span>
          <span v-if="scope.row.openResult == 3">温度异常</span>
          <span v-if="scope.row.openResult == 4">健康码无效</span>
          <span v-if="scope.row.openResult == 5">不在有限期</span>
          <span v-if="scope.row.openResult == 9">其他失败</span>
        </template>
      </el-table-column>
      <el-table-column label="通过时间" align="center" prop="accessTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.accessTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['zayy:recordMj:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['zayy:recordMj:remove']"
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

    <!-- 添加或修改门禁管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="设备编号" prop="deviceNo">
          <el-input v-model="form.deviceNo" placeholder="请输入设备编号" />
        </el-form-item>
        <el-form-item label="人脸图片" prop="userFaceImg">
          <el-input v-model="form.userFaceImg" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="姓名" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="身份证" prop="userIdcard">
          <el-input v-model="form.userIdcard" placeholder="请输入身份证" />
        </el-form-item>
        <el-form-item label="体温" prop="userTemp">
          <el-input v-model="form.userTemp" placeholder="请输入体温" />
        </el-form-item>
        <el-form-item label="健康码" prop="userHealthcode">
          <el-input v-model="form.userHealthcode" placeholder="请输入健康码" />
        </el-form-item>
        <el-form-item label="开门结果" prop="openResult">
          <!-- <el-input v-model="form.openResult" placeholder="请输入开门结果" /> -->
          <el-select
          v-model="form.openResult"
          clearable
          placeholder="请选择开门结果">
            <el-option
            v-for="item in openResultList"
            :key="item.value"
            :label="item.label"
            :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="通过时间" prop="accessTime">
          <el-date-picker clearable
            v-model="form.accessTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择通过时间">
          </el-date-picker>
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
import { listRecordMj, getRecordMj, delRecordMj, addRecordMj, updateRecordMj } from "@/api/zayy/recordMj";

export default {
  name: "RecordMj",
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
      // 门禁管理表格数据
      recordMjList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deviceNo: null,
        accessType: null,
        userName: null,
        userIdcard: null,
        userHealthcode: null,
        openType: null,
        openResult: null,
        accessTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      openResultList: [
        { value: 1, label: "开门成功" },
        { value: 2, label: "人证核验失败" },
        { value: 3, label: "温度异常" },
        { value: 4, label: "健康码无效" },
        { value: 5, label: "不在有效期" },
        { value: 9, label: "其他失败" }
      ]
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询门禁管理列表 */
    getList() {
      this.loading = true;
      listRecordMj(this.queryParams).then(response => {
        this.recordMjList = response.rows;
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
        id: null,
        deviceNo: null,
        accessType: null,
        userFaceImg: null,
        userName: null,
        userIdcard: null,
        userTemp: null,
        userHealthcode: null,
        openType: null,
        openResult: null,
        accessTime: null,
        createTime: null
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
      this.title = "添加门禁管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRecordMj(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改门禁管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRecordMj(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRecordMj(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除门禁管理编号为"' + ids + '"的数据项？').then(function() {
        return delRecordMj(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('zayy/recordMj/export', {
        ...this.queryParams
      }, `recordMj_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
