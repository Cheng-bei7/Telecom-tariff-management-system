<template>
  <section>
    <div class="service-wrap">
      <div class="service-tools">
        <el-col :span="8" class="select">
          <el-input v-model="input.osUsername" placeholder="请输入OS账号" clearable></el-input>
        </el-col>
        <el-col :span="8" class="select">
          <el-input v-model="input.unisHost" placeholder="请输入服务器IP" clearable></el-input>
        </el-col>
        <el-col :span="8" class="select" clearable>
          <el-input v-model="input.accountIdCard" placeholder="请输入身份证" clearable></el-input>
        </el-col>
        <el-select v-model="input.status" class="m-2 select" clearable placeholder="请选择">
          <el-option v-for="item in choosestatus" :key="item.id"  :label="item.name" :value="item.id"/>
        </el-select>
        <el-button icon="el-icon-search" @click="AdminService" circle class="selectbutton"></el-button>
        <!--添加按钮-->
        <el-button @click="AddPopup" class="service-add">添加</el-button>
      </div>
      <!--添加弹出的页面内容-->
      <el-dialog v-model="addgetmessage">
        <el-form :model="addservice"  label-width="100px">
          <el-form-item label="登录密码:" prop="loginPasswd">
            <el-input v-model="addservice.loginPasswd"/>
          </el-form-item>
          <el-form-item label="OS账号:" prop="osUsername">
            <el-input v-model="addservice.osUsername"/>
          </el-form-item>
          <el-form-item label="套餐信息:" prop="name">
            <el-select v-model="addservice.costId" class="m-2 select"  clearable placeholder="请选择" >
              <el-option v-for="item in costlist" :key="item.cost_id"  :label="item.name" :value="item.cost_id"/>
            </el-select>
          </el-form-item>
          <el-form-item label="当前状态:" prop="status">
            <el-tooltip :content="'Switch value: ' + value" placement="top" >
              <el-switch
                  v-model="addservice.status"
                  style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
                  active-value="0"
                  inactive-value="1"
              />
            </el-tooltip>
          </el-form-item>
          <el-form-item label="主机IP:" prop="unixHost">
            <el-input v-model="addservice.unixHost"/>
          </el-form-item>
          <el-form-item label="账务身份证号:" prop="accountIdCard">
            <el-input v-model="addservice.accountIdCard"/>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="ServiceAdd">立即创建</el-button>
            <el-button @click="addgetmessage=false">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
      <!--修改弹出的页面内容 -->
      <el-dialog v-model="updategetmessage">
        <el-form :model="updateservice"  label-width="100px" >
          <el-form-item label="OS账号:" prop="osUsername">
            <el-input v-model="updateservice.osUsername"/>
          </el-form-item>
          <el-form-item label="套餐信息:" prop="name">
          <el-select v-model="updateservice.costId" class="m-2 select"  clearable placeholder="请选择" >
            <el-option v-for="item in costlist" :key="item.cost_id"  :label="item.name" :value="item.cost_id"/>
          </el-select>
          </el-form-item>
          <el-form-item label="当前状态:" prop="status">
            <el-tooltip :content="'Switch value: ' + value" placement="top" >
              <el-switch
                  v-model="updateservice.status"
                  style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
                  active-value="0"
                  inactive-value="1"
              />
            </el-tooltip>
          </el-form-item>
          <el-form-item label="主机IP:" prop="unisHost">
            <el-input v-model="updateservice.unisHost"/>
          </el-form-item>
          <el-form-item label="账务身份证号:" prop="accountIdCard">
            <el-input v-model="updateservice.accountIdCard"/>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="ServiceUpdate">立即修改</el-button>
            <el-button @click="updategetmessage=false">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
      <!--账务列表-->
      <div class="serviceList">
        <el-table :data="serviceDate" highlight-current-row style="width: 100%" @selection-change="selsChange">
          <el-table-column width="50" type="selection">
          </el-table-column>
          <el-table-column prop="serviceId" label="业务ID" width="80px"></el-table-column>
          <el-table-column prop="accountId" label="账务账号ID"></el-table-column>
          <el-table-column prop="accountIdCard" label="身份证" width="180px" ></el-table-column>
          <el-table-column prop="realName" label="姓名" width="100px"></el-table-column>
          <el-table-column prop="osUsername" label="OS账号" width="100px"></el-table-column>
          <el-table-column prop="status" label="状态" width="100px">
            <template v-slot="scope">
              <el-tag class="ml-2" v-if="serviceDate[scope.$index].status==='0'" type="success">开通</el-tag>
              <el-tag class="ml-2" v-if="serviceDate[scope.$index].status==='1'" type="danger">暂停</el-tag>
              <el-tag class="ml-2" v-if="serviceDate[scope.$index].status==='2'" type="danger">删除</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="unisHost" label="服务器IP" width="120px"></el-table-column>
          <el-table-column prop="name" label="资费" width="100px">
            <template #default="scope">
              <el-popover effect="light" trigger="hover" placement="top" width="auto">
                <template #default>
                  <div>套餐名称: {{ scope.row.name }}</div>
                  <div>套餐详情: {{ scope.row.descr }}</div>
                </template>
                <template #reference>
                  <el-tag>{{ scope.row.name }}</el-tag>
                </template>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column prop="pause" label="是否开通" width="80px">
            <template v-slot="scope">
              <el-button type="primary" style="font-size:20px" @click=PauseService(scope.row) icon="el-icon-video-pause" circle v-if="scope.row.status=='0'"/>
              <el-button icon="el-icon-video-play"  style="font-size:20px" @click=PauseService(scope.row) circle v-if="scope.row.status=='1'"/>
            </template>
          </el-table-column>
          <el-table-column prop="tools" label="操作" width="160px">
            <template v-slot="scope">
              <el-button size="mini"  :disabled="scope.row.status == '2'" @click="updatePopup(scope.$index, scope.row)">编辑</el-button>
              <el-button size="mini" type="danger" :disabled="scope.row.status == '2'" @click="ServiceDelete(scope.$index, scope.row)">删除</el-button>
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
  AddService,
  DeleteServiceById, GetCostList,
  PauseServiceById,
  ServiceAdmin,
  UpdateService
} from "../api/api_zxm";

export default {
  data(){
    return{
      addgetmessage:false,
      updategetmessage:false,
      page: 1,
      total: 0,
      name:'',
      serviceId:'',
      accountIdCard:'',
      accountId:'',
      unisHost:'',
      osUsername:'',
      loginName:'',
      loginPasswd:'',
      status:'',
      createDate:'',
      pauseDate:'',
      closeDate:'',
      costId:'',
      descr:'',
      idcardNo:'',
      serviceDate:[],
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
      osName:'',
      ip:'',
      input: {},
      updateservice:{},
      addservice:{},
      costlist:{},
      cost_id:'',
      unixHost:'',
    }
  },
  methods:{
    AddPopup() {
      this.addgetmessage = !this.addgetmessage
    },
    updatePopup(index,row) {
      this.updateservice={};
      this.updateservice=row;
      this.updategetmessage = !this.updategetmessage
    },
    //查询
    AdminService(){
      this.listLoading = true;
      // alert(this.input.status)
      let configs={
        headers:{
          token: sessionStorage.getItem('AppointToken')
        }
      };
      var para = {
        page: this.page,
        osName: this.input.osUsername,
        ip: this.input.unisHost,
        idcardNo: this.input.accountIdCard,
      }
      if(this.input.status){
        para.status = this.input.status
      }
      ServiceAdmin(para,configs).then((res) => {
        this.listLoading = false;
        if (res.data.statusCode === 'C200') {
          this.total = res.data.result.total;
          this.serviceDate = res.data.result.list;
          // ElMessage.success(res.data.msgId);
        } else {
          ElMessage.error(res.data.msgId);
        }
      });
    },
    //暂停
    PauseService(row){
      let configs={
        headers:{
          token: sessionStorage.getItem('AppointToken')
        }
      };
      let cstatus = row.status == 1 ? '开通' : '暂停'
      this.$confirm(`是否${cstatus}此接口状态？`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        PauseServiceById(row.serviceId,configs).then((res) =>{
          this.listLoading = true;
          if (res.data.statusCode === 'C200') {
            this.AdminService();
            ElMessage.success(res.data.msgId);
          } else{
            ElMessage.error(res.data.msgId);
          }
        })
      })
          .catch(() => {
          })
    },
    //添加业务信息
    ServiceAdd(){
      // alert(JSON.stringify(this.addservice))
      let configs={
        headers:{
          token: sessionStorage.getItem('AppointToken')
        }
      };
      AddService(this.addservice,configs).then((res)=>{
        if (res.data.statusCode === 'C200') {
          this.AddPopup();
          this.AdminService();
          ElMessage.success(res.data.msgId);
        } else{
          ElMessage.error(res.data.msgId);
        }
      })
    },
    //删除业务账号
    ServiceDelete(index, row){
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
            DeleteServiceById(row.serviceId,configs).then((res) =>{
              this.listLoading = true;
              if (res.data.statusCode === 'C200') {
                this.AdminService();
                ElMessage.success(res.data.msgId);
              } else{
                ElMessage.error(res.data.msgId);
              }
            })
          })
          .catch(() => {
          })
    },
    //编辑业务账号
    ServiceUpdate(){
      ElMessageBox.confirm(
          '确定修改此账号吗?',
          '修改',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      ).then(() => {
            let para = {
              serviceId:this.updateservice.serviceId,
              accountId: this.updateservice.accountId,
              osUsername: this.updateservice.osUsername,
              costId: this.updateservice.costId,
              status: this.updateservice.status,
              unixHost: this.updateservice.unisHost,
              accountIdCard: this.updateservice.accountIdCard,
            };
            let configs={
              headers:{
                token: sessionStorage.getItem('AppointToken')
              }
            };
             // alert(JSON.stringify(para))
            UpdateService(para,configs).then((res)=>{
              if (res.data.statusCode === 'C200') {
                this.AdminService();
                this.updategetmessage=false;
                ElMessage.success(res.data.msgId);
              } else{
                ElMessage.error(res.data.msgId);
              }
            })
          })
    },
    //cost下拉列表
    CostGetList(){
      let configs={
        headers:{
          token: sessionStorage.getItem('AppointToken')
        }
      };
      GetCostList(configs).then((res)=>{
        if (res.data.statusCode === 'C200') {
          this.costlist=res.data.result;
        } else{
          ElMessage.error(res.data.msgId);
        }
      })
    },

    //分页
    pageChange(val) {
      this.page = val;
      this.AdminService();
    },
  },
  mounted() {
    this.AdminService();
    this.CostGetList();
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
.service-add{
  background-color: #409EFF;
  color: #fff;
}
.toolbar{
  margin: 10px 500px;
}
</style>