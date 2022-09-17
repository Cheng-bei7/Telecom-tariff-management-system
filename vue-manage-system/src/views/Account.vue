<template>
  <section>
    <div class="account-wrap">
      <div class="account-tools">
        <el-col :span="8" class="select">
          <el-input v-model="input.idCard" placeholder="请输入身份证号"></el-input>
        </el-col>
        <el-col :span="8" class="select">
          <el-input v-model="input.name" placeholder="请输入姓名"></el-input>
        </el-col>
        <el-col :span="8" class="select">
          <el-input v-model="input.loginName" placeholder="请输入登录名"></el-input>
        </el-col>
        <el-select v-model="input.status" class="m-2 select" clearable placeholder="请选择">
          <el-option v-for="item in choosestatus" :key="item.id"  :label="item.name" :value="item.id"/>
        </el-select>
        <el-button icon="el-icon-search" @click="AccountAdminById" circle class="selectbutton"></el-button>
        <!--添加按钮-->
        <el-button @click="AddPopup" class="account-add">添加</el-button>
      </div>
            <!--添加弹出的页面内容-->
            <el-dialog v-model="addgetmessage">
              <el-form :model="addaccount" :rules="rulesForm" label-width="100px">
                <el-form-item label="推荐人ID:" prop="recommenderId">
                  <el-input v-model="addaccount.recommenderId"/>
                </el-form-item>
                <el-form-item label="登录名:" prop="loginName">
                  <el-input v-model="addaccount.loginName"/>
                </el-form-item>
                <el-form-item label="登陆密码:" prop="loginPasswd">
                  <el-input v-model="addaccount.loginPasswd"/>
                </el-form-item>
                <el-form-item label="当前状态:" prop="status">
                  <el-tooltip :content="'Switch value: ' + value" placement="top" >
                    <el-switch
                        v-model="addaccount.status"
                        style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
                        active-value="0"
                        inactive-value="1"
                    />
                  </el-tooltip>
                </el-form-item>
                <el-form-item label="真实姓名:" prop="realName">
                  <el-input v-model="addaccount.realName"/>
                </el-form-item>
                <el-form-item label="性别:" prop="gender">
                  <el-radio-group v-model="addaccount.gender" @change="typeChoose">
                    <el-radio label="0" @click="gender1">男</el-radio>
                    <el-radio label="1" @click="gender2">女</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item label="身份证号:" prop="idcardNo">
                  <el-input v-model="addaccount.idcardNo"/>
                </el-form-item>
                <el-form-item prop="birthdate" label="出生日期">
                  <el-date-picker
                      v-model="addaccount.birthdate"
                      type="date"
                      placeholder="选择时间"
                  />
                </el-form-item>
                <el-form-item label="职业:" prop="occupation">
                  <el-input v-model="addaccount.occupation"/>
                </el-form-item>
                <el-form-item label="电话号码:" prop="telephone">
                  <el-input v-model="addaccount.telephone"/>
                </el-form-item>
                <el-form-item label="电子邮件:" prop="email">
                  <el-input v-model="addaccount.email"/>
                </el-form-item>
                <el-form-item label="邮编编码:" prop="zipcode">
                  <el-input v-model="addaccount.zipcode"/>
                </el-form-item>
                <el-form-item label="qq号:" prop="qq">
                  <el-input v-model="addaccount.qq"/>
                </el-form-item>
                <el-form-item label="邮编地址">
                  <el-input type="textarea" v-model="addaccount.mailaddress"></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="AddAccount">立即创建</el-button>
                  <el-button @click="addgetmessage=false">取消</el-button>
                </el-form-item>
              </el-form>
            </el-dialog>
            <!--修改弹出的页面内容 -->
            <el-dialog v-model="updategetmessage">
              <el-form :model="updateaccount" :rules="rulesForm" label-width="100px" ref="editForm">
                <el-form-item label="账务ID:" prop="accountId">
                  <el-input v-model="updateaccount.accountId"/>
                </el-form-item>
                <el-form-item label="推荐人ID:" prop="recommenderId">
                  <el-input v-model="updateaccount.recommenderId"/>
                </el-form-item>
                <el-form-item label="登录名:" prop="loginName">
                  <el-input v-model="updateaccount.loginName"/>
                </el-form-item>
                <el-form-item label="登陆密码:" prop="loginPasswd">
                  <el-input v-model="updateaccount.loginPasswd"/>
                </el-form-item>
                <el-form-item label="当前状态:" prop="status">
                  <el-tooltip :content="'Switch value: ' + value" placement="top" >
                    <el-switch
                        v-model="updateaccount.status"
                        style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
                        active-value="0"
                        inactive-value="1"
                    />
                  </el-tooltip>
                </el-form-item>
                <el-form-item label="真实姓名:" prop="realName">
                  <el-input v-model="updateaccount.realName"/>
                </el-form-item>
                <el-form-item label="身份证号:" prop="idcardNo">
                  <el-input v-model="updateaccount.idcardNo"/>
                </el-form-item>
                <el-form-item prop="birthdate" label="出生日期">
                  <el-date-picker
                      v-model="updateaccount.birthdate"
                      type="date"
                      placeholder="选择时间"
                  />
                </el-form-item>
                <el-form-item label="性别:" prop="gender">
                  <el-radio-group v-model="updateaccount.gender">
                    <el-radio label="0" @click="gender1">男</el-radio>
                    <el-radio label="1" @click="gender2">女</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item label="职业:" prop="occupation">
                  <el-input v-model="updateaccount.occupation"/>
                </el-form-item>
                <el-form-item label="电话号码:" prop="telephone">
                  <el-input v-model="updateaccount.telephone"/>
                </el-form-item>
                <el-form-item label="电子邮件:" prop="email">
                  <el-input v-model="updateaccount.email"/>
                </el-form-item>
                <el-form-item label="邮编编码:" prop="zipcode">
                  <el-input v-model="updateaccount.zipcode"/>
                </el-form-item>
                <el-form-item label="qq号:" prop="qq">
                  <el-input v-model="updateaccount.qq"/>
                </el-form-item>
                <el-form-item label="邮编地址">
                  <el-input type="textarea" v-model="updateaccount.mailaddress"></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="AccountUpdate">立即修改</el-button>
                  <el-button @click="updategetmessage=false">取消</el-button>
                </el-form-item>
              </el-form>
            </el-dialog>
            <!--账务列表-->
            <div class="accountList">
              <el-table :data="accountDate" highlight-current-row style="width: 100%" @selection-change="selsChange">
                <el-table-column width="50" type="selection">
                </el-table-column>
                <el-table-column prop="accountId" label="账号ID" width="80px"></el-table-column>
                <el-table-column prop="realName" label="姓名" width="150px"></el-table-column>
                <el-table-column prop="idcardNo" label="身份证"></el-table-column>
                <el-table-column prop="loginName" label="登录名" width="100px"></el-table-column>
                <el-table-column prop="status" label="状态" width="100px">
                  <template v-slot="scope">
                    <el-tag class="ml-2" v-if="accountDate[scope.$index].status==='0'" type="success">开通</el-tag>
                    <el-tag class="ml-2" v-if="accountDate[scope.$index].status==='1'" type="danger">暂停</el-tag>
                    <el-tag class="ml-2" v-if="accountDate[scope.$index].status==='2'" type="danger">删除</el-tag>
                  </template>
                </el-table-column>
                <el-table-column prop="createDate" label="创建时间" width="200px"></el-table-column>
                <!--          <el-table-column prop="last_login_time" label="上次登录时间" width="150px"></el-table-column>-->
                <el-table-column prop="pause" label="是否开通" width="80px">
                  <template v-slot="scope">
                      <el-button type="primary" style="font-size:20px" @click=PauseAccount(scope.row) icon="el-icon-video-pause" circle v-if="scope.row.status=='0'"/>
                      <el-button icon="el-icon-video-play"  style="font-size:20px" @click=PauseAccount(scope.row) circle v-if="scope.row.status=='1'"/>
                  </template>
                </el-table-column>
                <el-table-column prop="tools" label="操作" width="250px">
                  <template v-slot="scope">
                    <el-button size="mini"  :disabled="scope.row.status == '2'" @click="updatePopup(scope.$index, scope.row)">编辑</el-button>
                    <el-button size="mini" type="danger" :disabled="scope.row.status == '2'" @click="AccountDelete(scope.$index, scope.row)">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
    </div>
    <el-col :span="24" class="toolbar">
      <el-pagination layout="prev, pager, next" @current-change="pageChange" :page-size="10" :total="total" background
                     style="float:right;">
      </el-pagination>
    </el-col>

  </section>
</template>

<script>
import {ElMessage, ElMessageBox} from "element-plus";
import {
  AccountAdd,
  AccountAdmin, AccountById,
  DeleteAccountById,
  PauseAccountById, UpdateAccount
} from "../api/api_zxm";
export default {
  data() {
    return {
      controltool:'false',
      addgetmessage:false,
      updategetmessage:false,
      accountId: '',
      realName: '',
      idcardNo: '',
      recommenderId:'',
      loginPasswd:'',
      birthdate:'',
      gender:'',
      occupation:'',
      telephone:'',
      email:'',
      zipcode:'',
      qq:'',
      mailaddress:'',
      testVersion: true,
      page: 1,
      total: 0,
      name: '',
      loginName: '',
      idCard: '',
      status: '',
      cstatus:'',
      accountDate: [],
      addaccount:{},
      updateaccount:{},
      input: {},
      update:{},
      sels:[],
      choosestatus: [{
        id: '0',
        name: '开通',
      }, {
        id: '1',
        name: '暂停',
      }, {
        id: '2',
        name: '删除',
      }],
      rulesForm:{
        loginName: [{
          required: true,
          message: "请输入登录名",
          trigger: "blur",
        }],
        loginPasswd:[{
          required: true,
          message: "请输入密码",
          trigger: "blur",
        }],
        status: [{
          required: true,
          message: "请选择状态",
          trigger: "blur",
        }],
        realName: [{
          required: true,
          message: "请输入真实姓名",
          trigger: "blur",
        }],
        idcardNo: [{
          required: true,
          message: "请输入身份证号",
          trigger: "blur",
        },{
          pattern: '(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)', message: '请输入正确的格式'
        }],
        telephone: [{
          required: true,
          message: "请输入电话号码",
          trigger: "blur",
        },{
          pattern: '(^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$)', message: '请输入正确的格式'
        }],
      }

    }
  },
  methods: {
    //添加弹窗
    AddPopup() {
      this.addparam={
        status:1
      };
      this.addgetmessage = !this.addgetmessage
    },
    updatePopup(index,row) {
      let configs={
        headers:{
          token: sessionStorage.getItem('AppointToken')
        }
      };
      AccountById(row.accountId,configs).then((res)=>{
        if (res.data.statusCode === 'C200') {
          this.updateaccount=res.data.result;
          // alert(JSON.stringify(this.updateaccount))
        } else{
          ElMessage.error(res.data.msgId);
        }
      })
      this.updateaccount={};
      this.updateaccount=row;
      this.updategetmessage = !this.updategetmessage
    },
    //获取账务信息
    AccountAdminById() {
      this.listLoading = true;
      // alert(this.input.status)
      var para = {
        page: this.page,
        name: this.input.name,
        loginName: this.input.loginName,
        idCard: this.input.idCard,
        status: this.input.status,
      }
      let configs={
        headers:{
          token: sessionStorage.getItem('AppointToken')
        }
      };
      // aler(para.status)
      AccountAdmin(para,configs).then((res) => {
        this.listLoading = false;
        if (res.data.statusCode === 'C200') {
          this.total = res.data.result.total;
          this.accountDate = res.data.result.list;
          // ElMessage.success(res.data.msgId);
        } else {
          ElMessage.error(res.data.msgId);
        }
      });
    },
    //添加
    AddAccount(){
      // alert(JSON.stringify(this.addaccount))
      let configs={
        headers:{
          token: sessionStorage.getItem('AppointToken')
        }
      };
      AccountAdd(this.addaccount,configs).then((res)=>{
        if (res.data.statusCode === 'C200') {
          this.AddPopup();
          this.AccountAdminById();
          ElMessage.success(res.data.msgId);
        } else{
          ElMessage.error(res.data.msgId);
        }
      })

    },
    //暂停
    PauseAccount(row){
      let cstatus = row.status == 1 ? '开通' : '暂停'
      let configs={
        headers:{
          token: sessionStorage.getItem('AppointToken')
        }
      };
      this.$confirm(`是否${cstatus}此接口状态？`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        PauseAccountById(row.accountId,configs).then((res) =>{
          this.listLoading = true;
          if (res.data.statusCode === 'C200') {
            this.AccountAdminById();
            ElMessage.success(res.data.msgId);
          } else{
            ElMessage.error(res.data.msgId);
          }
        })
      })
          .catch(() => {
          })
    },
    //单个删除
    AccountDelete(index, row){
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
            DeleteAccountById(row.accountId,configs).then((res) =>{
              this.listLoading = true;
              if (res.data.statusCode === 'C200') {
                this.AccountAdminById();
                ElMessage.success(res.data.msgId);
              } else{
                ElMessage.error(res.data.msgId);
              }
            })
          })
          .catch(() => {
          })
    },
    //编辑
    AccountUpdate(){
      this.$refs.editForm.validate((valid) => {
            if (valid) {
              this.$confirm('确认提交吗？', '提示', {}).then(() => {
                // alert(JSON.stringify(this.updateaccount))
                let para = {
                  accountId: this.updateaccount.accountId,
                  loginPasswd: this.updateaccount.loginPasswd,
                  recommenderId: this.updateaccount.recommenderId,
                  loginName: this.updateaccount.loginName,
                  status: this.updateaccount.status,
                  realName: this.updateaccount.realName,
                  idcardNo: this.updateaccount.idcardNo,
                  birthdate: this.updateaccount.birthdate,
                  gender: this.updateaccount.gender,
                  occupation: this.updateaccount.occupation,
                  telephone: this.updateaccount.telephone,
                  email: this.updateaccount.email,
                  mailaddress: this.updateaccount.mailaddress,
                  zipcode: this.updateaccount.zipcode,
                  qq: this.updateaccount.qq
                };
                let configs={
                  headers:{
                    token: sessionStorage.getItem('AppointToken')
                  }
                };
                UpdateAccount(para,configs).then((res)=>{
                  if (res.data.statusCode === 'C200') {
                    this.AccountAdminById();
                    this.updategetmessage=false;
                    ElMessage.success(res.data.msgId);
                  } else{
                    ElMessage.error(res.data.msgId);
                  }
                })
              })
            }})
    },
    //分页
    pageChange(val) {
      this.page = val;
      this.AccountAdminById();
    },
  },
  mounted() {
    this.AccountAdminById();
  }
}
</script>

<style scoped>
.select {
  margin: 10px 5px;
}

.m-2 {
  margin-top: 10px;
  margin-left: 5px;
}

.selectbutton {
  margin-left: 5px;
}
.account-add{
  background-color: #409EFF;
  color: #fff;
}
.toolbar{
  margin: 10px 500px;
}
</style>