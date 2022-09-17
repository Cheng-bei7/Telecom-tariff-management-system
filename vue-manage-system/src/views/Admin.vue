<template>
  <section>
      <div class="admin-wrap">
        <div class="admin-tools">
          <el-col :span="8" class="select" clearable>
            <el-input v-model="input.roleName" placeholder="请输入角色" clearable></el-input>
          </el-col>
            <el-select class="select"  v-model="input.moduleId" multiple placeholder="选择权限" style="width: 240px">
              <el-option v-for="item in moduleLists" :key="item.moduleId" :label="item.name" :value="item.moduleId"/>
            </el-select>
          <el-button icon="el-icon-search" @click="adminFind" circle class="selectbutton"></el-button>
         <!--添加按钮-->
          <el-button @click="AddPopup" class="cost-add">添加</el-button>
        </div>
        <!--添加弹窗-->
        <el-dialog v-model="addgetmessage">
          <el-form :model="addAdmin" label-width="100px" :rules="rules" ref="addAdminWrapper">
            <el-form-item label="姓名:" prop="name">
              <el-input v-model="addAdmin.name"></el-input>
            </el-form-item>
            <el-form-item label="管理员账号:" prop="adminCode">
              <el-input v-model="addAdmin.adminCode"></el-input>
            </el-form-item>
            <el-form-item label="密码:" prop="password">
              <el-input v-model="addAdmin.password"></el-input>
            </el-form-item>
            <el-form-item label="重复密码:" prop="repeatPassword">
              <el-input v-model="addAdmin.repeatPassword"></el-input>
            </el-form-item>
            <el-form-item label="电话:" prop="telephone">
              <el-input v-model="addAdmin.telephone"></el-input>
            </el-form-item>
            <el-form-item label="Email:" prop="email">
              <el-input v-model="addAdmin.email"></el-input>
            </el-form-item>
            <el-form-item  label="设置权限:" prop="moduleId">
              <el-select  v-model="addAdmin.moduleId" multiple placeholder="选择权限" style="width: 240px">
                <el-option v-for="item in roleLists" :key="item.roleId" :label="item.name" :value="item.roleId"/>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="adminAdd">立即保存</el-button>
              <el-button @click="addgetmessage=false">取消</el-button>
            </el-form-item>
          </el-form>
        </el-dialog>
        <!--修改弹窗-->
        <el-dialog v-model="updategetmessage" title="编辑用户信息">
          <el-form :model="updateAdminInformationList" label-width="100px" :rules="rules" ref="adminUpdateWrapper">
              <el-form-item label="姓名:" prop="name">
                <el-input v-model="updateAdminInformationList.name"></el-input>
              </el-form-item>
              <el-form-item label="管理员账号:" prop="adminCode">
                <el-input v-model="updateAdminInformationList.adminCode"></el-input>
              </el-form-item>
              <el-form-item label="电话:" prop="telephone">
                <el-input v-model="updateAdminInformationList.telephone"></el-input>
              </el-form-item>
              <el-form-item label="Email:" prop="email">
                <el-input v-model="updateAdminInformationList.email"></el-input>
              </el-form-item>
              <el-form-item  label="设置权限:" prop="moduleId">
                <el-select  v-model="updateAdminInformationList.roleIds" multiple placeholder="选择权限" style="width: 240px">
                  <el-option v-for="item in roleLists" :key="item.roleId" :label="item.name" :value="item.roleId"/>
                </el-select>
              </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="adminUpdate">修改保存</el-button>
              <el-button @click="updategetmessage=false">取消</el-button>
            </el-form-item>
          </el-form>

        </el-dialog>
        <!--人员列表-->
        <div class="adminList">
          <el-table :data="adminDate" highlight-current-row style="width: 100%" >
            <el-table-column prop="adminId" label="管理员ID" width="80px"></el-table-column>
            <el-table-column prop="name" label="姓名" width="100px"></el-table-column>
            <el-table-column prop="adminCode" label="登录名" width="100px" ></el-table-column>
            <el-table-column prop="telephone" label="电话" width="180px"></el-table-column>
            <el-table-column prop="email" label="电子邮件"></el-table-column>
            <el-table-column prop="enrolldate" label="授权日期" width="180px"></el-table-column>
            <el-table-column  label="拥有的权限" width="100px" prop="roleName">
                  <template #default="scope">
                    <el-popover effect="light" trigger="hover" placement="top" width="auto">
                      <template #default>
                        权限名称:
                        <span v-for="(s,index) in scope.row.roleInfo">
                         {{ s.name }}<span v-if="index!=scope.row.roleInfo.length-1">,</span>
                        </span>
                      </template>
                      <template #reference>
                        <el-tag>{{ scope.row.roleInfo[0].name }}</el-tag>
                      </template>
                    </el-popover>
                  </template>
            </el-table-column>
            <el-table-column prop="tools" label="操作" width="160px">
              <template v-slot="scope">
                <el-button size="mini"   @click="updatePopup(scope.$index, scope.row)">编辑</el-button>
                <el-button size="mini" type="danger"  @click="adminDelete(scope.$index, scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
  </section>
</template>

<script>
import {
  AddAdmin,
  AddRoles,
  AdminList,
  AdminRole,
  DeleteAdmin, DeleteRoleModule,
  GetRoleModule, UpdateAdmin,
} from "../api/api_zxm";
import {ElMessage, ElMessageBox} from "element-plus";

export default {
  data(){
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
        // password 是表单上绑定的字段
      } else if (value !== this.addAdmin.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return{
      rules:{
        name:[
          { required: true, message: "请输入姓名" }
        ],adminCode:[
          { required: true, message: "请输入登录姓名" }
        ],password:[
          { required: true, message: "请输入密码" },
          { pattern: /^[a-z0-9_-]{5,30}$/, message: '格式不正确' }
        ],telephone:[
          { required: true, message: "请输入电话号码" },
          { pattern: /^1[3456789]\d{9}$/, message: '格式不正确' }
        ],email:[
          { required: true, message: "请输入邮箱" },
          { pattern: /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/, message: '格式不正确' }
        ],
        repeatPassword: [
          { required: true, validator: validatePass2, trigger: 'blur' }
        ]
      },
      addgetmessage:false,
      updategetmessage:false,
      moduleLists:[],
      roleLists:[],
      moduleId:[],
      name:'',
      input: {},
      adminDate:[],
      adminName:'',
      adminId:'',
      adminCode:'',
      telephone:'',
      email:'',
      enrolldate:'',
      roleName:'',
      page:1,
      total:0,
      addAdmin:{},
      password:'',
      updateAdminInformationList:{
        roleIds:[],
        name:'',
        adminCode:'',
        telephone:'',
        email:'',
        roleInfo:{},
            }
    }
  },
  methods:{
    //添加弹窗
    AddPopup() {
      this.addAdmin={};
      this.addgetmessage = !this.addgetmessage
    },
    //获取所有权限信息
    ModuleList(){
      let configs={
        headers: {
          token: sessionStorage.getItem('AppointToken')
        }};
      GetRoleModule(configs).then((res)=>{
        if (res.data.statusCode === 'C200') {
          this.moduleLists=res.data.result;
        } else{
          ElMessage.error(res.data.msgId);
        }
      })
    },
    //多项选择器角色
    RoleList(){
      let configs={
        headers: {
          token: sessionStorage.getItem('AppointToken')
        }
      };
      AdminRole(configs).then((res)=>{
        if (res.data.statusCode === 'C200') {
          this.roleLists=res.data.result;
        } else{
          ElMessage.error(res.data.msgId);
        }
      })
    },
    //获取全部信息
    adminFind(){
      this.listLoading = true;
      // alert(this.input.status)
      let configs={
        headers:{
          token: sessionStorage.getItem('AppointToken')
        }
      };
      var para = {
        page: this.page,
        moduleId:this.input.moduleId,
        roleName:this.input.roleName,
      }
      AdminList(para,configs).then((res) => {
        this.listLoading = false;
        if (res.data.statusCode === 'C200') {
          this.total = res.data.result.total;
          this.adminDate = res.data.result.list;
          // ElMessage.success(res.data.msgId);
        } else {
          ElMessage.error(res.data.msgId);
        }
      });
    },
    adminDelete(index, row){
      let configs={
        headers:{
          token: sessionStorage.getItem('AppointToken')
        }
      };
      ElMessageBox.confirm(
          '确定删除此账号吗?',
          '删除',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      )
          .then(() => {
            DeleteAdmin(row.adminId,configs).then((res) =>{
              this.listLoading = true;
              if (res.data.statusCode === 'C200') {
                this.adminFind();
                ElMessage.success(res.data.msgId);
              } else{
                ElMessage.error(res.data.msgId);
              }
            })
          })
          .catch(() => {
          })
    },
    //添加
    adminAdd(){
      this.$refs["addAdminWrapper"].validate(valid => {
            if (valid) {
              ElMessageBox.confirm(
                  '确定添加账号吗?',
                  '修改',
                  {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                  }
              ).then(() => {
                let para ={
                  adminCode:this.addAdmin.adminCode,
                  password:this.addAdmin.password,
                  name:this.addAdmin.name,
                  telephone:this.addAdmin.telephone,
                  email:this.addAdmin.email,
                  roleIds:this.addAdmin.moduleId
                };
                // alert(JSON.stringify(para))
                let configs={
                  headers:{
                    token: sessionStorage.getItem('AppointToken')
                  }
                };
                AddAdmin(para,configs).then((res) => {
                  this.listLoading = false;
                  if (res.data.statusCode === 'C200') {
                    ElMessage.success(res.data.msgId);
                    this.addgetmessage=false;
                    this.adminFind()
                  } else {
                    ElMessage.error(res.data.msgId);
                  }
                });
              })
            }
      })
    },
    //修改弹窗
    updatePopup(index,row){
      this.updategetmessage=true;
      this.updateAdminInformationList={};
      this.updateAdminInformationList=row;
      let ids=[];
      let roleInfo = this.updateAdminInformationList.roleInfo;
      for (let i = 0; i <roleInfo.length; i++) {
        ids.push(roleInfo[i].roleId)
      }
      this.updateAdminInformationList.roleIds=ids;
    },
    //修改
    adminUpdate(){
      this.$refs["adminUpdateWrapper"].validate(valid => {
            if (valid) {
              ElMessageBox.confirm(
                  '确定修改此账号吗?',
                  '修改',
                  {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                  }
              ).then(() => {
                let para ={
                  adminId:this.updateAdminInformationList.adminId,
                  adminCode:this.updateAdminInformationList.adminCode,
                  name:this.updateAdminInformationList.name,
                  telephone:this.updateAdminInformationList.telephone,
                  email:this.updateAdminInformationList.email,
                  roleIds:this.updateAdminInformationList.roleIds,
                };
                let configs={
                  headers:{
                    token: sessionStorage.getItem('AppointToken')
                  }
                };
                // alert(JSON.stringify(para))
                UpdateAdmin(para,configs).then((res) => {
                  this.listLoading = false;
                  if (res.data.statusCode === 'C200') {
                    ElMessage.success(res.data.msgId);
                    this.updategetmessage=false;
                    this.adminFind()
                  } else {
                    ElMessage.error(res.data.msgId);
                  }
                });
                  })
            }
      })
    },
    //分页
    pageChange(val) {
      this.page = val;
      this.RoleList();
    },
  },
  mounted() {
    this.ModuleList();
    this.adminFind();
    this.RoleList();
  }

}
</script>

<style scoped>
.select {
  margin: 10px 5px;
}
.selectbutton {
  margin-left: 5px;
}
</style>