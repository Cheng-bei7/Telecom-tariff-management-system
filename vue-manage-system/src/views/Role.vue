<template>
<section>
  <div class="role-wrap">
    <!--角色列表-->
    <div class="costList">
      <el-button color="#626aef" type="primary" @click="AddPopup()">添加</el-button>
      <el-table :data="roleList" highlight-current-row style="width: 100%" >
        <el-table-column prop="roleId" label="角色ID"></el-table-column>
        <el-table-column prop="name" label="角色名称"></el-table-column>
        <el-table-column  label="拥有的权限"  prop="moduleName">
          <template v-slot="scope">
            <span v-for="(s,index) in scope.row.moduleInfos" >
              {{s.name}}<span v-if="index!=scope.row.moduleInfos.length-1">,</span>
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="tools" label="功能" width="200px">
          <template v-slot="scope">
            <el-button size="mini" @click="updatePopups(scope.$index, scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="deleteRole(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!--修改弹窗-->
    <el-dialog v-model="updategetmessage" title="修改角色">
      <el-form :model="roleLists" label-width="100px" :rules="roles" ref="updateWrapper">
        <el-form-item label="角色名称:" prop="name">
          <el-input v-model="roleLists.name"></el-input>
        </el-form-item>
        <el-form-item  label="设置权限:" prop="moduleInfos">
          <el-select  v-model="roleLists.moduleInId" multiple placeholder="选择权限" style="width: 240px">
            <el-option v-for="item in moduleLists" :key="item.moduleId" :label="item.name" :value="item.moduleId"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="UpdateRole">保存修改</el-button>
          <el-button @click="updategetmessage=false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!--添加弹窗-->
    <el-dialog v-model="addgetmessage">
      <el-form :model="addRoles" label-width="100px" :rules="roles" ref="addWrapper">
        <el-form-item label="角色名称:" prop="name">
          <el-input v-model="addRoles.name"></el-input>
        </el-form-item>
        <el-form-item  label="设置权限:" prop="moduleInfos">
          <el-select  v-model="addRoles.moduleInfos" multiple placeholder="选择权限" style="width: 240px">
            <el-option v-for="item in moduleLists" :key="item.moduleId" :label="item.name" :value="item.moduleId"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="AddRole">立即保存</el-button>
          <el-button @click="addgetmessage=false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-col :span="24" class="toolbar">
      <el-pagination layout="prev, pager, next" @current-change="pageChange" :page-size="10" :total="total" background
                     style="float:right;">
      </el-pagination>
    </el-col>
  </div>
</section>
</template>

<script>
import {AddRoles, DeleteRoleModule, GetRoleList, GetRoleModule, UpdateRole} from "../api/api_zxm";
import {ElMessage, ElMessageBox} from "element-plus";
export default {
  data(){
    return{
      roles:{
        name:[
          { required: true, message: "请输入角色名称" }
        ],
        moduleInfos:[
          { required: true, message: "请选择角色权限" }
        ]
      },
      page:1,
      total:0,
      roleList:[] ,
      roleLists:{
        moduleInfos: {},
        name:'',
        roleId:'',
        moduleInId:[]
      },
      moduleInfo:[],
      items:[],
      addgetmessage:false,
      updategetmessage:false,
      updateRoleinfo:{},
      moduleLists:[],
      moduleId:'',
      moduleName:'',
      addRoles:{},
    }
  },
  methods:{
    //添加弹窗
    AddPopup() {
      this.addRoles=[];
      this.addgetmessage = !this.addgetmessage
    },
    updatePopups(index,row) {
      this.updategetmessage = true;
      this.roleLists=row;
      let ids=[];
      let moduleInfos = this.roleLists.moduleInfos;
      for (let i = 0; i <moduleInfos.length; i++) {
        ids.push(moduleInfos[i].moduleId)
      }
      this.roleLists.moduleInId=ids;
    },
    RoleList(){
       let configs={
         headers:{
           token: sessionStorage.getItem('AppointToken')
         }
       };
       GetRoleList(this.page,configs).then((res) => {
         this.listLoading = false;
         if (res.data.statusCode === 'C200') {
            this.total = res.data.result.total;
            this.roleList = res.data.result.list;
         } else {
           ElMessage.error(res.data.msgId);
         }
       });
     },
    //分页
    pageChange(val) {
      this.page = val;
      this.CostAdminById();
    },
   //多选选择器
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
    //修改
    UpdateRole(){
      this.$refs["updateWrapper"].validate(valid => {
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
                let para={
                  name:this.roleLists.name,
                  id:this.roleLists.roleId,
                  moduleId:this.roleLists.moduleInId
                };
                let configs={
                  headers:{
                    token: sessionStorage.getItem('AppointToken')
                  }
                };
                // alert(JSON.stringify(para.moduleId))
                UpdateRole(para,configs).then((res) => {
                  this.listLoading = false;
                  if (res.data.statusCode === 'C200') {
                    ElMessage.success(res.data.msgId);
                    this.updategetmessage=false;
                    this.RoleList()
                  } else {
                    ElMessage.error(res.data.msgId);
                  }
                });
              })
            }
      })
    },
    AddRole(){
      this.$refs["addWrapper"].validate(valid => {
            if (valid) {
              let para ={
                name:this.addRoles.name,
                moduleId:this.addRoles.moduleInfos
              };
              let configs={
                headers:{
                  token: sessionStorage.getItem('AppointToken')
                }
              };
              AddRoles(para,configs).then((res) => {
                this.listLoading = false;
                if (res.data.statusCode === 'C200') {
                  ElMessage.success(res.data.msgId);
                  this.addgetmessage=false;
                  this.RoleList()
                } else {
                  ElMessage.error(res.data.msgId);
                }
              });
            }
      })
    },
    deleteRole(index, row){
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
            DeleteRoleModule(row.roleId,configs).then((res) =>{
              this.listLoading = true;
              if (res.data.statusCode === 'C200') {
                this.RoleList();
                ElMessage.success(res.data.msgId);
              } else{
                ElMessage.error(res.data.msgId);
              }
            })
          })
          .catch(() => {
          })
    }

  },
  mounted() {
    this.RoleList();
    this.ModuleList();
  }
}
</script>

<style scoped>
.toolbar{
  margin: 10px 500px;
}
</style>