<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="任务ID" prop="taskId">
        <el-input
          v-model="queryParams.taskId"
          placeholder="请输入任务ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户" prop="userId">
        <!-- <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户ID"
          clearable
          @keyup.enter.native="handleQuery"
        /> -->
        <el-select
        v-model="queryParams.userId"
        clearable>
          <el-option
          v-for="item in userArr"
          :key="item.id"
          :value="item.id"
          :label="item.nickName"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="发布时间" prop="releaseTime">
        <el-date-picker clearable
          v-model="queryParams.releaseTime"
          type="datetime"
          value-format="yyyy-MM-dd HH:mm:ss"
          placeholder="请选择发布时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="截止时间" prop="deadline">
        <el-date-picker clearable
          v-model="queryParams.deadline"
          type="datetime"
          value-format="yyyy-MM-dd HH:mm:ss"
          placeholder="请选择截止时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="完成时间" prop="finishTime">
        <el-date-picker clearable
          v-model="queryParams.finishTime"
          type="datetime"
          value-format="yyyy-MM-dd HH:mm:ss"
          placeholder="请选择完成时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="科室地点" prop="deptId">
        <el-select
        v-model="queryParams.deptId"
        clearable>
          <el-option
          v-for="item in placeArr"
          :key="item.id"
          :value="item.id"
          :label="item.placeName"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="完成情况" prop="isNot">
        <!-- <el-input
          v-model="queryParams.isNot"
          placeholder="请输入完成情况0未完成1已完成"
          clearable
          @keyup.enter.native="handleQuery"
        /> -->
        <el-select
        v-model="queryParams.isNot"
        clearable>
          <el-option label="未完成" value=0></el-option>  
          <el-option label="已完成" value=1></el-option>  
        </el-select>
      </el-form-item>
      <el-form-item label="发布状态" prop="taskType">
        <!-- <el-input
          v-model="queryParams.taskType"
          placeholder="请输入发布状态0未发布1已发布"
          clearable
          @keyup.enter.native="handleQuery"
        /> -->
        <el-select
        v-model="queryParams.taskType"
        clearable>
          <el-option label="未发布" value=0></el-option>  
          <el-option label="已发布" value=1></el-option>  
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
          v-hasPermi="['zayy:checkTask:add']"
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
          v-hasPermi="['zayy:checkTask:edit']"
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
          v-hasPermi="['zayy:checkTask:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['zayy:checkTask:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleExportR"
        >发布任务</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="checkTaskList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="任务ID" align="center" prop="taskId" />
      <el-table-column label="用户ID" align="center" prop="nickName" />
      <el-table-column label="发布时间" align="center" prop="releaseTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.releaseTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="截止时间" align="center" prop="deadline" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.deadline, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="完成时间" align="center" prop="finishTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.finishTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="科室地点" align="center" prop="placeName" />
      <el-table-column label="完成情况" align="center" prop="isNot">
        <template slot-scope="scope">
          <span v-if="scope.row.isNot == 0">未完成</span>
          <span v-if="scope.row.isNot == 1">已完成</span>
        </template>
      </el-table-column>
      <el-table-column label="发布状态" align="center" prop="taskType">
        <template slot-scope="scope">
          <span v-if="scope.row.taskType == 0">未发布</span>
          <span v-if="scope.row.taskType == 1">已发布</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['zayy:checkTask:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['zayy:checkTask:remove']"
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

    <!-- 添加或修改巡检任务对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="任务ID" prop="taskId">
          <el-input v-model="form.taskId" placeholder="请输入任务ID" />
        </el-form-item>
        <el-form-item label="用户" prop="userId">
          <!-- <el-input v-model="form.userId" placeholder="请输入用户ID" /> -->
          <el-select
          v-model="form.userId"
          clearable>
            <el-option
            v-for="item in userArr"
            :key="item.id"
            :value="item.id"
            :label="item.nickName"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="发布时间" prop="releaseTime">
          <el-date-picker clearable
            v-model="form.releaseTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择发布时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="截止时间" prop="deadline">
          <el-date-picker clearable
            v-model="form.deadline"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择截止时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="完成时间" prop="finishTime">
          <el-date-picker clearable
            v-model="form.finishTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择完成时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="科室地点" prop="deptId">
          <!-- <el-input v-model="form.deptId" placeholder="请输入科室地点" /> -->
          <el-select
          v-model="form.deptId"
          clearable>
            <el-option
            v-for="item in placeArr"
            :key="item.id"
            :value="item.id + ''"
            :label="item.placeName"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="完成情况" prop="isNot">
          <!-- <el-input v-model="form.isNot" placeholder="请输入完成情况0未完成1已完成" /> -->
          <el-select
          v-model="form.isNot"
          clearable>
            <el-option label="未完成" value="0"></el-option>  
            <el-option label="已完成" value="1"></el-option>  
          </el-select>
        </el-form-item>
        <el-form-item label="发布状态" prop="taskType">
          <!-- <el-input v-model="form.taskType" placeholder="请输入发布状态0未发布1已发布" /> -->
          <el-select
          v-model="form.taskType"
          clearable>
            <el-option label="未发布" value="0"></el-option>  
            <el-option label="已发布" value="1"></el-option>  
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="发布任务" :visible.sync="openR" width="500px" append-to-body>
      <el-form ref="formR" :model="formR" :rules="rulesR" label-width="80px">
        <el-form-item label="用户ID" prop="userIDArr">
          <el-select
          v-model="formR.userIDArr"
          clearable
          placeholder="所有人"
          multiple>
            <el-option
            v-for="item in userArr"
            :key="item.id"
            :value="item.id"
            :label="item.nickName"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="开始时间" prop="startDate"> 
          <el-date-picker
          v-model="formR.startDate"
          type="datetime"
          placeholder="选择日期时间"></el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endDate">
          <el-date-picker
          v-model="formR.endDate"
          type="datetime"
          placeholder="选择日期时间"></el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormR">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { listCheckTask, getCheckTask, delCheckTask, addCheckTask, updateCheckTask, releaseTask } from "@/api/zayy/checkTask";
import { listCheckUser } from "@/api/zayy/checkUser";
import { listCheckPlace } from "@/api/zayy/checkPlace";

export default {
  name: "CheckTask",
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
      // 巡检任务表格数据
      checkTaskList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      openR: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        taskId: null,
        userId: null,
        releaseTime: null,
        deadline: null,
        finishTime: null,
        deptId: null,
        isNot: null,
        taskType: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      formR: {
        userIDArr: [],
        startDate: "",
        endDate: ""
      },
      rulesR: {
        startDate: [
          { required: true, message: "请选择开始时间", trigger: "change" }
        ],
        endDate: [
          { required: true, message: "请选择结束时间", trigger: "change" }
        ]
      },
      userArr: [],
      placeArr: []
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询巡检任务列表 */
    getList() {
      this.loading = true;
      listCheckPlace({pageNum:1, pageSize: 1000}).then(res => {
        this.placeArr = res.rows
        listCheckUser({pageNum:1, pageSize: 1000}).then(res => {
          this.userArr = res.rows
            listCheckTask(this.queryParams).then(response => {
              this.userArr.forEach(item => {
                response.rows.forEach(k => {
                  if(item.id == k.userId) {
                    k.nickName = item.nickName
                  }
                })
              })
              this.placeArr.forEach(item => {
                response.rows.forEach(k => {
                  if(item.id == k.deptId) {
                    k.placeName = item.placeName
                  }
                })
              })
              this.checkTaskList = response.rows;
              this.total = response.total;
              this.loading = false;
            });        
        })
      })
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.openR = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        taskId: null,
        userId: null,
        releaseTime: null,
        deadline: null,
        finishTime: null,
        deptId: null,
        isNot: null,
        taskType: null
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
      this.title = "添加巡检任务";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCheckTask(id).then(response => {
        response.data.isNot += ''
        response.data.taskType += ''
        this.form = response.data;
        this.open = true;
        this.title = "修改巡检任务";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCheckTask(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCheckTask(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    submitFormR() {
      this.$refs["formR"].validate(valid => {
        if(valid) {
          let obj = this.formR
          if(!this.formR.userIDArr.length) {
            obj.userId = '0'
          } else {
            obj.userId = this.formR.userIDArr.join(",")
          }
          releaseTask(obj).then(res => {
            this.$modal.msgSuccess("发布任务成功")
            this.openR = false
            this.getList()
          })
        } else {
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除巡检任务编号为"' + ids + '"的数据项？').then(function() {
        return delCheckTask(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('zayy/checkTask/export', {
        ...this.queryParams
      }, `checkTask_${new Date().getTime()}.xlsx`)
    },
    handleExportR() {
      this.openR = true;
      this.formR = {
        userIDArr: [],
        startDate: "",
        endDate: ""
      }
    }
  }
};
</script>
