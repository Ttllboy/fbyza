<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
<!--      <el-form-item label="巡检用户名" prop="userName">-->
<!--        <el-input-->
<!--          v-model="queryParams.userName"-->
<!--          placeholder="请输入巡检用户名"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="巡检用户密码" prop="userPassword">-->
<!--        <el-input-->
<!--          v-model="queryParams.userPassword"-->
<!--          placeholder="请输入巡检用户密码"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="巡检用户角色" prop="userRole">
        <el-select v-model="queryParams.userRole" placeholder="请选择巡检用户角色" clearable>
          <el-option
            v-for="dict in dict.type.check_role"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="巡检用户名称" prop="nickName">
        <el-input
          v-model="queryParams.nickName"
          placeholder="请输入巡检用户名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="巡检用户科室" prop="userDeptArr">
        <!-- <el-input
          v-model="queryParams.userDept"
          placeholder="请输入巡检用户科室"
          clearable
          @keyup.enter.native="handleQuery"
        /> -->
        <el-select v-model="queryParams.userDeptArr" multiple clearable>
          <el-option
          v-for="item in listPlace"
          :key="item.id"
          :label="item.placeName"
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
          v-hasPermi="['zayy:checkUser:add']"
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
          v-hasPermi="['zayy:checkUser:edit']"
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
          v-hasPermi="['zayy:checkUser:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['zayy:checkUser:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="checkUserList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" >
        <template slot-scope="scope">
          <span>{{(queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1}}</span>
        </template>
      </el-table-column>
      <el-table-column label="巡检用户名" align="center" prop="userName" />
      <el-table-column label="巡检用户密码" align="center" prop="userPassword" />
      <el-table-column label="巡检用户角色" align="center" prop="userRole">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.check_role" :value="scope.row.userRole"/>
        </template>
      </el-table-column>
      <el-table-column label="巡检用户名称" align="center" prop="nickName" />
      <el-table-column label="巡检用户科室" align="center" prop="placeName" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['zayy:checkUser:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['zayy:checkUser:remove']"
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

    <!-- 添加或修改人员管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="巡检用户名" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入巡检用户名" />
        </el-form-item>
        <el-form-item label="巡检用户密码" prop="userPassword">
          <el-input v-model="form.userPassword" placeholder="请输入巡检用户密码" />
        </el-form-item>
        <el-form-item label="巡检用户角色" prop="userRole">
          <el-select v-model="form.userRole" placeholder="请选择巡检用户角色">
            <el-option
              v-for="dict in dict.type.check_role"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="巡检用户名称" prop="nickName">
          <el-input v-model="form.nickName" placeholder="请输入巡检用户名称" />
        </el-form-item>
        <el-form-item label="巡检用户科室" prop="userDeptArr">
          <!-- <el-input v-model="form.userDept" placeholder="请输入巡检用户科室" /> -->
          <el-select v-model="form.userDeptArr" multiple clearable @change="changeDeptArr" v-if="deptArr">
            <el-option
            v-for="item in listPlace"
            :key="item.id"
            :label="item.placeName"
            :value="item.id + ''"></el-option>
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
import { listCheckUser, getCheckUser, delCheckUser, addCheckUser, updateCheckUser } from "@/api/zayy/checkUser";
import { listFbyDept } from "@/api/zayy/fbyDept";
import { listCheckPlace } from "@/api/zayy/checkPlace";

export default {
  name: "CheckUser",
  dicts: ['check_role'],
  data() {
    return {
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
      // 人员管理表格数据
      checkUserList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: null,
        userPassword: null,
        userRole: null,
        nickName: null,
        userDeptArr: []
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      deptArr: true
    };
  },
  created() {
    this.getList();
  },
  methods: {
    changeDeptArr() {
      this.deptArr = false;
      this.deptArr = true;
    },
    /** 查询人员管理列表 */
    getList() {
      this.loading = true;
      this.queryParams.userDept = this.queryParams.userDeptArr.join(",")
      listCheckUser(this.queryParams).then(response => {
        this.total = response.total;
        let obj = {
          pageNum: 1,
          pageSize: 1000
        }
        listCheckPlace(obj).then(res => {
          response.rows.forEach(item => {
            item.placeName = []
          })
          this.loading = false;
          this.listPlace = res.rows
          res.rows.forEach(item => {
            response.rows.forEach(k => {
              if(!!k.userDept) {
                k.userDept.split(",").forEach(arr => {
                  if(arr == item.id) {
                    k.placeName.push(item.placeName)
                  }
                })                
              }
            })
          })
          response.rows.forEach(item => {
            item.placeName = item.placeName.join(",")
          })          
          this.checkUserList = response.rows;
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
        userName: null,
        userPassword: null,
        userRole: null,
        nickName: null,
        userDeptArr: [],
        userDept: null
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
      this.title = "添加人员管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCheckUser(id).then(response => {
        this.form = response.data;
        this.form.userDeptArr = this.form.userDept.split(",");
        this.open = true;
        this.title = "修改人员管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.userDept = this.form.userDeptArr.join(",");
          if (this.form.id != null) {
            updateCheckUser(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCheckUser(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除人员管理编号为"' + ids + '"的数据项？').then(function() {
        return delCheckUser(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('zayy/checkUser/export', {
        ...this.queryParams
      }, `checkUser_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
