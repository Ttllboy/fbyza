<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="事件ID" prop="eventId">
        <el-input
          v-model="queryParams.eventId"
          placeholder="请输入事件ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="事件源编号" prop="srcIndex">
        <el-input
          v-model="queryParams.srcIndex"
          placeholder="请输入事件源编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="事件源名称" prop="srcName">
        <el-input
          v-model="queryParams.srcName"
          placeholder="请输入事件源名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="事件类型" prop="eventType">
        <el-input
          v-model="queryParams.eventType"
          placeholder="请输入事件类型"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="脉冲超时时间" prop="timeout">
        <el-input
          v-model="queryParams.timeout"
          placeholder="请输入脉冲超时时间"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="事件发生时间" prop="happenTime">
        <el-date-picker clearable
          v-model="queryParams.happenTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择事件发生时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="事件发生的事件源父设备" prop="srcParentIdex">
        <el-input
          v-model="queryParams.srcParentIdex"
          placeholder="请输入事件发生的事件源父设备"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['zayy:recordSmogAlarm:add']"
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
          v-hasPermi="['zayy:recordSmogAlarm:edit']"
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
          v-hasPermi="['zayy:recordSmogAlarm:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['zayy:recordSmogAlarm:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="recordSmogAlarmList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="事件ID" align="center" prop="eventId" />
      <el-table-column label="事件源编号" align="center" prop="srcIndex" />
      <el-table-column label="事件源类型" align="center" prop="srcType" />
      <el-table-column label="事件源名称" align="center" prop="srcName" />
      <el-table-column label="事件类型" align="center" prop="eventType" />
      <el-table-column label="事件状态" align="center" prop="status" />
      <el-table-column label="脉冲超时时间" align="center" prop="timeout" />
      <el-table-column label="事件发生时间" align="center" prop="happenTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.happenTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="事件发生的事件源父设备" align="center" prop="srcParentIdex" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['zayy:recordSmogAlarm:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['zayy:recordSmogAlarm:remove']"
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

    <!-- 添加或修改烟感报警对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="事件ID" prop="eventId">
          <el-input v-model="form.eventId" placeholder="请输入事件ID" />
        </el-form-item>
        <el-form-item label="事件源编号" prop="srcIndex">
          <el-input v-model="form.srcIndex" placeholder="请输入事件源编号" />
        </el-form-item>
        <el-form-item label="事件源名称" prop="srcName">
          <el-input v-model="form.srcName" placeholder="请输入事件源名称" />
        </el-form-item>
        <el-form-item label="事件类型" prop="eventType">
          <el-input v-model="form.eventType" placeholder="请输入事件类型" />
        </el-form-item>
        <el-form-item label="脉冲超时时间" prop="timeout">
          <el-input v-model="form.timeout" placeholder="请输入脉冲超时时间" />
        </el-form-item>
        <el-form-item label="事件发生时间" prop="happenTime">
          <el-date-picker clearable
            v-model="form.happenTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择事件发生时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="事件发生的事件源父设备" prop="srcParentIdex">
          <el-input v-model="form.srcParentIdex" placeholder="请输入事件发生的事件源父设备" />
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
import { listRecordSmogAlarm, getRecordSmogAlarm, delRecordSmogAlarm, addRecordSmogAlarm, updateRecordSmogAlarm } from "@/api/zayy/recordSmogAlarm";

export default {
  name: "RecordSmogAlarm",
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
      // 烟感报警表格数据
      recordSmogAlarmList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        eventId: null,
        srcIndex: null,
        srcType: null,
        srcName: null,
        eventType: null,
        status: null,
        timeout: null,
        happenTime: null,
        srcParentIdex: null
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
    /** 查询烟感报警列表 */
    getList() {
      this.loading = true;
      listRecordSmogAlarm(this.queryParams).then(response => {
        this.recordSmogAlarmList = response.rows;
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
        eventId: null,
        srcIndex: null,
        srcType: null,
        srcName: null,
        eventType: null,
        status: 0,
        timeout: null,
        happenTime: null,
        srcParentIdex: null
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
      this.title = "添加烟感报警";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRecordSmogAlarm(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改烟感报警";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRecordSmogAlarm(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRecordSmogAlarm(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除烟感报警编号为"' + ids + '"的数据项？').then(function() {
        return delRecordSmogAlarm(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('zayy/recordSmogAlarm/export', {
        ...this.queryParams
      }, `recordSmogAlarm_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
