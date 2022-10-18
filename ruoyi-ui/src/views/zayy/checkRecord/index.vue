<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="巡检员名字" prop="userId">
        <!-- <el-input
          v-model="queryParams.userId"
          placeholder="请输入巡检员名字"
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
        <!-- <el-input
          v-model="queryParams.checkPlace"
          placeholder="请输入巡检地点"
          clearable
          @keyup.enter.native="handleQuery"
        /> -->
        <el-select clearable v-model="queryParams.checkPlace">
          <el-option
          v-for="item in listPlace"
          :key="item.placeId"
          :label="item.placeName"
          :value="item.placeId"></el-option>
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="巡检记录ID" prop="recordId">
        <el-input
          v-model="queryParams.recordId"
          placeholder="请输入巡检记录ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
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
          v-hasPermi="['zayy:checkRecord:add']"
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
          v-hasPermi="['zayy:checkRecord:edit']"
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
          v-hasPermi="['zayy:checkRecord:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['zayy:checkRecord:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="checkRecordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" >
        <template slot-scope="scope">
          <span>{{(queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1}}</span>
        </template>
      </el-table-column>
      <el-table-column label="巡检员名字" align="center" prop="nickName" />
      <el-table-column label="记录时间" align="center" prop="recordTime" width="180">
      </el-table-column>
      <el-table-column label="巡检地点" align="center" prop="placeName" />
      <el-table-column label="巡检记录ID" align="center" prop="recordId" />
      <el-table-column label="详情描述" align="center" prop="checkContent">
        <template slot-scope="scope">
          <div v-html="scope.row.checkContent"></div>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
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
            v-hasPermi="['zayy:checkRecord:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['zayy:checkRecord:remove']"
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

    <!-- 添加或修改巡检记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="巡检员名字" prop="userId">
          <!-- <el-input :disabled="disabled" v-model="form.userId" placeholder="请输入巡检员名字" /> -->
          <el-select :disabled="disabled" v-model="form.userId" clearable>
            <el-option
            v-for="item in listUser"
            :key="item.id"
            :label="item.nickName"
            :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="记录时间" prop="recordTime">
          <el-date-picker
            :disabled="disabled"
            clearable
            v-model="form.recordTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择记录时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="巡检地点" prop="checkPlace">
          <!-- <el-input :disabled="disabled" v-model="form.checkPlace" placeholder="请输入巡检地点" /> -->
          <el-select :disabled="disabled" v-model="form.checkPlace">
            <el-option
            v-for="item in listPlace"
            :key="item.placeId"
            :label="item.placeName"
            :value="item.placeId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="巡检记录ID" prop="recordId" v-if="disabled || updateDisabled">
          <el-input :disabled="true" v-model="form.recordId" placeholder="请输入巡检记录ID" />
        </el-form-item>
        <el-form-item label="巡检项" prop="items" v-if="disabled &&  updateDisabled">
          <div v-for="(item, index) in form.items" :key="item.id">
            <span>{{ item.item_name }}</span>
            :
            <el-button type="primary" plain size="mini">{{ item.item_if ? '否' : '是' }}</el-button> 
            <br>       
          </div>
          <span v-if="form.items && !form.items.length">无</span>
        </el-form-item>
        <el-form-item label="巡检图片" prop="imgs" v-if="disabled && updateDisabled">
          <el-image class="imgs" v-for="item in form.imgs" :key="item.id" :src="item.item_img" :preview-src-list="[item.item_img]"></el-image>
        </el-form-item>
        <el-form-item label="详情描述">
          <editor readonly v-model="form.checkContent" :min-height="192"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-if="!disabled" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCheckRecord, getCheckRecord, delCheckRecord, addCheckRecord, updateCheckRecord, getDetail } from "@/api/zayy/checkRecord";
import { listCheckUser } from "@/api/zayy/checkUser";
import { listCheckPlace } from "@/api/zayy/checkPlace";

export default {
  name: "CheckRecord",
  data() {
    return {
      listUser: [],
      listPlace: [],
      updateDisabled: false,
      disabled: false,
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
      // 巡检记录表格数据
      checkRecordList: [],
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
        checkContent: null
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
    /** 查询巡检记录列表 */
    getList() {
      this.loading = true;
      listCheckRecord(this.queryParams).then(response => {
        let obj = {
          pageNum: 1,
          pageSize: 1000
        }
        listCheckUser(obj).then(res => {
          this.listUser = res.rows
          listCheckPlace(obj).then(res => {
            this.listPlace = res.rows;
            res.rows.forEach(item => {
              response.rows.forEach(k => {
                if(k.checkPlace == item.placeId) {{
                  k.placeName = item.placeName
                }}
              })
            })
            this.checkRecordList = response.rows;
            this.total = response.total;
            this.loading = false;
          });
          res.rows.forEach(item => {
            response.rows.forEach(k => {
              if(k.userId == item.id) {{
                k.nickName = item.nickName
              }}
            })
          })
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
        userId: null,
        recordTime: null,
        checkPlace: null,
        recordId: null,
        checkContent: null
      };
      this.disabled = false;
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
      this.updateDisabled = false
      this.open = true;
      this.title = "添加巡检记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row, type) {
      this.reset();
      this.disabled = type
      this.updateDisabled = true
      const id = row.id || this.ids
      const recordId = row.recordId
      getCheckRecord(id).then(response => {
        getDetail({ recordId: recordId }).then(res => {
          this.form = response.data;
          this.form.items = res.items
          this.form.imgs = res.imgs
          this.open = true;
          this.title = "修改巡检记录";
        })
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCheckRecord(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCheckRecord(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除巡检记录编号为"' + ids + '"的数据项？').then(function() {
        return delCheckRecord(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('zayy/checkRecord/export', {
        ...this.queryParams
      }, `checkRecord_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

<style scoped>
.el-button + .el-button {
  margin-left: 0;
}
.imgs {
  width: 320px;
}
</style>
