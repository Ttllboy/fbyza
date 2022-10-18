<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="巡检员" prop="userId">
        <!-- <el-input
          v-model="queryParams.userId"
          placeholder="请输入巡检员"
          clearable
          @keyup.enter.native="handleQuery"
        /> -->
        <el-select v-model="queryParams.userId" clearable>
          <el-option
          v-for="item in listUser"
          :key="item.id"
          :label="item.nickName"
          :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="记录时间" prop="recordTime">
        <el-date-picker clearable
          v-model="queryParams.recordTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择记录时间">
        </el-date-picker>
      </el-form-item> -->
      <el-form-item label="记录开始时间" prop="startDate">
        <el-date-picker clearable
          v-model="queryParams.startDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择记录时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="记录结束时间" prop="endDate">
        <el-date-picker clearable
          v-model="queryParams.endDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择记录时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="巡检地点" prop="checkPlace">
        <el-select clearable v-model="queryParams.checkPlace">
          <el-option
          v-for="item in listPlace"
          :key="item.placeId"
          :label="item.placeName"
          :value="item.placeId"></el-option>
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="巡检记录" prop="recordId">
        <el-input
          v-model="queryParams.recordId"
          placeholder="请输入巡检记录"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="异常等级" prop="abnormalLev">
        <!-- <el-input
          v-model="queryParams.abnormalLev"
          placeholder="请输入异常等级"
          clearable
          @keyup.enter.native="handleQuery"
        /> -->
        <el-select v-model="queryParams.abnormalLev" placeholder="请选择" clearable>
          <el-option label="黄色" value="0"></el-option>
          <el-option label="橙色" value="1"></el-option>
          <el-option label="红色" value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="事件状态" prop="eventType">
        <!-- <el-input
          v-model="queryParams.eventType"
          placeholder="请输入事件状态0处理中1已办结2超时未办"
          clearable
          @keyup.enter.native="handleQuery"
        /> -->
        <el-select v-model="queryParams.eventType" placeholder="请选择" clearable>
          <el-option label="未处理" value="0"></el-option>
          <el-option label="处理中" value="1"></el-option>
          <el-option label="已办结" value="2"></el-option>
          <el-option label="超时未办" value="3"></el-option>
          <el-option label="驳回" value="4"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <!-- <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['zayy:checkRecordAbnormal:add']"
        >新增</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['zayy:checkRecordAbnormal:edit']"
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
          v-hasPermi="['zayy:checkRecordAbnormal:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['zayy:checkRecordAbnormal:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="checkRecordAbnormalList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="巡检员" align="center" prop="userName" />
      <el-table-column label="记录时间" align="center" prop="recordTime" width="170">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.recordTime, '{y}-{m}-{d} {h}:{m}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="巡检地点" align="center" prop="placeName" />
      <el-table-column label="巡检记录" align="center" prop="recordId" />
      <el-table-column label="详情描述" align="center" prop="checkContent" />
      <el-table-column label="异常等级" align="center" prop="abnormalLev">
        <template slot-scope="scope">
          <el-button v-if="scope.row.abnormalLev == 0" size="mini" type="warning" plain>黄色</el-button>
          <el-button v-if="scope.row.abnormalLev == 1" size="mini" type="warning">橙色</el-button>
          <el-button v-if="scope.row.abnormalLev == 2" size="mini" type="danger">红色</el-button>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="特殊备注" align="center" prop="remarkSpecial" />
      <el-table-column label="事件状态" align="center" prop="eventType">
        <template slot-scope="scope">
          <span v-if="scope.row.eventType == 0">未处理</span>
          <span v-if="scope.row.eventType == 1">处理中</span>
          <span v-if="scope.row.eventType == 2">已办结</span>
          <span v-if="scope.row.eventType == 3">超时未办</span>
          <span v-if="scope.row.eventType == 4">驳回</span>
        </template>
      </el-table-column>
      <el-table-column label="处理方法" align="center" prop="handleMethod" />
      <el-table-column label="处理结果" align="center" prop="handleResult" />
      <el-table-column label="操作" align="center" width="190">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-search"
            @click="handleUpdate(scope.row, true)"
            v-hasPermi="['zayy:checkItemDept:edit']"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row, false)"
            v-hasPermi="['zayy:checkRecordAbnormal:edit']"
          >处理异常</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['zayy:checkRecordAbnormal:remove']"
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

    <!-- 添加或修改巡检异常对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="95px">
        <el-form-item label="巡检员" prop="userId">
          <!-- <el-input v-model="form.userId" placeholder="请输入巡检员" /> -->
          <el-select v-model="form.userId" clearable :disabled="updateDisabled">
            <el-option
            v-for="item in listUser"
            :key="item.id"
            :label="item.nickName"
            :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="记录时间" prop="recordTime">
          <el-date-picker clearable
            v-model="form.recordTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择记录时间"
            :disabled="updateDisabled">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="巡检地点" prop="checkPlace">
          <!-- <el-input v-model="form.checkPlace" placeholder="请输入巡检地点" /> -->
          <el-select clearable v-model="queryParams.checkPlace" :disabled="updateDisabled">
            <el-option
            v-for="item in listPlace"
            :key="item.placeId"
            :label="item.placeName"
            :value="item.placeId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="巡检记录" prop="recordId">
          <el-input :disabled="updateDisabled" v-model="form.recordId" placeholder="请输入巡检记录" />
        </el-form-item>
        <el-form-item label="详情描述" prop="checkContent">
          <el-input :disabled="updateDisabled" v-model="form.checkContent" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item v-if="updateDisabled" label="巡检图片" prop="imgs">
          <el-image class="imgs" v-for="item in abnormalList.imgs" :key="item.id" :src="item.item_img" :preview-src-list="[item.item_img]"></el-image>
        </el-form-item>
        <el-form-item label="异常等级" prop="abnormalLev">
          <!-- <el-input v-model="form.abnormalLev" placeholder="请输入异常等级" /> -->
          <el-select :disabled="updateDisabled" v-model="form.abnormalLev" placeholder="请选择" clearable>
            <el-option label="黄色" value="0"></el-option>
            <el-option label="橙色" value="1"></el-option>
            <el-option label="红色" value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="reamrk">
          <el-input :disabled="updateDisabled" v-model="form.reamrk" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="特殊备注" prop="remarkSpecial">
          <el-input :disabled="updateDisabled" v-model="form.remarkSpecial" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="异常项" prop="abnormalItems">
          <div v-for="(item, index) in abnormalList.abnormalItems" :key="item.id">
            {{ index + 1 }}.
            <span>{{ item.item_name }}</span>
            <el-button v-if="item.abnormal_lev == 0" size="mini" type="warning" plain>黄色警报</el-button>
            <el-button v-if="item.abnormal_lev == 1" size="mini" type="warning">橙色警报</el-button>
            <el-button v-if="item.abnormal_lev == 2" size="mini" type="danger">红色警报</el-button>
            <br>
          </div>
          <span v-if="abnormalList.abnormalItems && !abnormalList.abnormalItems.length">无</span>
        </el-form-item>
        <el-form-item label="异常图片" prop="abnormalImgs">
          <el-image class="imgs" v-for="item in abnormalList.abnormalImgs" :key="item.id" :src="item.item_img" :preview-src-list="[item.item_img]"></el-image>
        </el-form-item>
        <el-form-item label="处理方法" prop="handleMethod">
          <el-input :disabled="disabled" v-model="form.handleMethod" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="处理结果" prop="handleResult">
          <el-input :disabled="disabled" v-model="form.handleResult" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="事件状态" prop="eventType">
          <!-- <el-input v-model="form.eventType" placeholder="请输入事件状态0处理中1已办结2超时未办" /> -->
          <el-select :disabled="disabled" v-model="form.eventType" placeholder="请选择" clearable>
            <el-option label="未处理" value="0"></el-option>
            <el-option label="处理中" value="1"></el-option>
            <el-option label="已办结" value="2"></el-option>
            <el-option label="超时未办" value="3"></el-option>
            <el-option label="驳回" value="4"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" v-if="!disabled">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCheckRecordAbnormal, getCheckRecordAbnormal, delCheckRecordAbnormal, addCheckRecordAbnormal, updateCheckRecordAbnormal, getDetail } from "@/api/zayy/checkRecordAbnormal";
import { listCheckUser } from "@/api/zayy/checkUser";
import { listCheckPlace } from "@/api/zayy/checkPlace";

export default {
  name: "CheckRecordAbnormal",
  data() {
    return {
      disabled: false,
      updateDisabled: false,
      abnormalList: {},
      listUser: [],
      listPlace: [],
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
      // 巡检异常表格数据
      checkRecordAbnormalList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        recordTime: null,
        checkPlace: null,
        recordId: null,
        checkContent: null,
        handleMethod: null,
        handleResult: null,
        abnormalLev: null,
        eventType: null
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
    /** 查询巡检异常列表 */
    getList() {
      this.loading = true;
      listCheckRecordAbnormal(this.queryParams).then(response => {
        let obj = {
          pageNum: 1,
          pageSize: 1000
        }
        listCheckUser(obj).then(res => {
          this.listUser = res.rows;
          res.rows.forEach(item => {
            response.rows.forEach(k => {
              if(k.userId == item.id) {
                k.userName = item.nickName
              }
            })
          })
          listCheckPlace(obj).then(res => {
            this.listPlace = res.rows;
            res.rows.forEach(item => {
              response.rows.forEach(k => {
                if(k.checkPlace == item.placeId) {{
                  k.placeName = item.placeName
                }}
              })
            })
            this.checkRecordAbnormalList = response.rows;
            this.total = response.total;
            this.loading = false;
          })
        })
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
        userId: null,
        recordTime: null,
        checkPlace: null,
        recordId: null,
        checkContent: null,
        handleMethod: null,
        handleResult: null,
        abnormalLev: null,
        eventType: null
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
      this.updateDisabled = false
      this.disabled = false
      this.title = "添加巡检异常";
    },
    /** 修改按钮操作 */
    handleUpdate(row, type) {
      this.reset();
      this.disabled = type
      this.updateDisabled = true
      const id = row.id || this.ids
      getCheckRecordAbnormal(id).then(response => {
        response.data.abnormalLev += ""
        response.data.eventType += ""
        getDetail({ recordId: row.recordId }).then(res => {
          this.abnormalList = res
          this.form = response.data;
          this.open = true;
        })
        if(type) {
          this.title = "巡检异常详情"
        } else {
          this.title = "巡检处理异常";
        }
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            this.form.roleId = JSON.parse(sessionStorage.getItem("USER_INFO")).roles[0].roleId
            updateCheckRecordAbnormal(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCheckRecordAbnormal(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除巡检异常编号为"' + ids + '"的数据项？').then(function() {
        return delCheckRecordAbnormal(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('zayy/checkRecordAbnormal/export', {
        ...this.queryParams
      }, `checkRecordAbnormal_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
