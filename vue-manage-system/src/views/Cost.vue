<template>
  <section>
  <div class="cost-wrap">
    <div class="cost-tools">
      <el-col :span="8">
        <el-input v-model="input" placeholder="请输入查询内容"></el-input>
      </el-col>
        <el-button icon="el-icon-search" @click="CostAdminById" circle></el-button>
       <!--批量删除-->
        <el-button type="danger" @click="moreRemove" :disabled="this.sels.length===0" class="moreDelete">批量删除</el-button>
        <!--添加按钮-->
        <el-button @click="AddPopup" class="cost-add">添加</el-button>
    </div>
       <!-- 添加弹出的页面内容 -->
       <el-dialog v-model="addgetmessage">
        <el-form :model="addparam" :rules="rules" label-width="80px">
          <el-form-item label="资费名称:" prop="name">
            <el-input v-model="addparam.name"/>
          </el-form-item>
          <el-form-item label="资费类型:" prop="costType">
            <el-radio-group v-model="addparam.costType" @change="typeChoose">
              <el-radio label="0" @click="type1">包月</el-radio>
              <el-radio label="1" @click="type2">套餐</el-radio>
              <el-radio label="2" @click="type3">计时</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="基本时长:"  class="duration">
            <el-input v-model="addparam.baseDuration" :disabled="typechoose1"></el-input>
          </el-form-item>
          <el-form-item label="基本费用:"  class="basecost">
            <el-input v-model="addparam.baseCost" :disabled="typechoose2"></el-input>
          </el-form-item>
          <el-form-item label="单位费用:"  class="unitcost">
            <el-input v-model="addparam.unitCost" :disabled="typechoose3"></el-input>
          </el-form-item>
          <el-form-item label="资费说明">
            <el-input type="textarea" v-model="addparam.descr"></el-input>
          </el-form-item>
          <el-form-item prop="startime" label="开通时间">
            <el-date-picker
                v-model="addparam.startime"
                type="date"
                placeholder="选择时间"
            />
          </el-form-item>
          <el-form-item label="状态:" prop="status">
            <el-tooltip :content="'Switch value: ' + value" placement="top" >
              <el-switch
                  v-model="addparam.status"
                  style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
                  active-value="0"
                  inactive-value="1"
              />
            </el-tooltip>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="AddCost">立即创建</el-button>
            <el-button @click="addgetmessage=false">取消</el-button>
          </el-form-item>
        </el-form>
    </el-dialog>
       <!-- 修改弹出的页面内容 -->
       <el-dialog v-model="updategetmessage">
      <el-form ref="form" :model="updateinfo" label-width="80px" :rules="rules">
        <el-form-item label="资费ID:" prop="cost_id">
          <el-input v-model="updateinfo.cost_id"></el-input>
        </el-form-item>
        <el-form-item label="资费名称:" prop="name">
          <el-input v-model="updateinfo.name"></el-input>
        </el-form-item>
        <el-form-item label="资费类型:" prop="cost_type" >
          <el-radio-group v-model="updateinfo.cost_type" @change="typeChoose">
            <el-radio label="0" @click="type1">包月</el-radio>
            <el-radio label="1" @click="type2">套餐</el-radio>
            <el-radio label="2" @click="type3">计时</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="基本时长:" >
          <el-input v-model="updateinfo.base_duration" :disabled="typechoose1"></el-input>
        </el-form-item>
        <el-form-item label="基本费用:" >
          <el-input v-model="updateinfo.base_cost" :disabled="typechoose2"></el-input>
        </el-form-item>
        <el-form-item label="单位费用:" >
          <el-input v-model="updateinfo.unit_cost" :disabled="typechoose3"></el-input>
        </el-form-item>
        <el-form-item label="资费说明">
          <el-input type="textarea" v-model="updateinfo.descr"></el-input>
        </el-form-item>
        <el-form-item prop="startimeCoding" label="开通时间">
          <el-date-picker
              v-model="updateinfo.startimeCoding"
              type="date"
              placeholder="选择时间"
          />
        </el-form-item>
        <el-tooltip :content="'Switch value: ' + value" placement="top">
          <el-switch
              v-model="updateinfo.status"
              style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
              active-value="0"
              inactive-value="1"
          />
        </el-tooltip>
        <el-form-item>
          <el-button type="primary" @click="CostUpdate">保存修改</el-button>
          <el-button @click="updategetmessage=false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
       <!--资费列表-->
       <div class="costList">
      <el-table :data="tableData" highlight-current-row style="width: 100%"  @selection-change="selsChange">
        <el-table-column width="50" type="selection" >
        </el-table-column>
        <el-table-column prop="cost_id" label="资费ID" width="70px"></el-table-column>
        <el-table-column prop="name" label="资费名称" width="100px" ></el-table-column>
        <el-table-column prop="cost_type" label="资费类型">
          <template v-slot="scope">
            <el-tag class="ml-2" v-if="tableData[scope.$index].cost_type==='0'" type="success">包月</el-tag>
            <el-tag class="ml-3" v-if="tableData[scope.$index].cost_type==='1'" type="success">套餐</el-tag>
            <el-tag class="ml-4" v-if="tableData[scope.$index].cost_type==='2'" type="success">计时</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="base_duration" label="基本时长" ></el-table-column>
        <el-table-column prop="base_cost" label="基本费用" ></el-table-column>
        <el-table-column prop="unit_cost" label="单位费用"></el-table-column>
        <el-table-column prop="createtime" label="创建时间" width="150px"></el-table-column>
        <el-table-column prop="startime" label="开通时间" width="150px"></el-table-column>
        <el-table-column prop="status" label="状态" width="100px">
          <template v-slot="scope">
            <el-tag class="ml-2" v-if="tableData[scope.$index].status==='0'" type="success">已开通</el-tag>
            <el-tag class="ml-2" v-if="tableData[scope.$index].status==='1'" type="danger">未开通</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="descr" label="描述" ></el-table-column>
        <el-table-column prop="tools" label="功能" width="200px">
          <template v-slot="scope">
            <el-button size="mini" @click="updatePopup(scope.$index, scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="CostDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-col :span="24" class="toolbar">
      <el-pagination layout="prev, pager, next" @current-change="pageChange" :page-size="10" :total="total" background
                     style="float:right;">
      </el-pagination>
    </el-col>

  </div>

  </section>
</template>

<script>
import {ElMessage} from "element-plus";
import {CostAdmin, CostAdd, UpdateCost, DeleteCostById} from "../api/api_zxm";
export default {
  data() {
    return{
      testVersion:true,
      page:1,
      total: 0,
      tableData:[],
      addgetmessage:false,
      updategetmessage:false,
      costId:'',
      name:'',
      baseDuration:'',
      baseCost:'',
      unitCost:'',
      status:'',
      descr:'',
      createtime:'',
      startime:'',
      costType:'',
      disabled:false,
      addparam: {
        status:'1'
      },
      input:'',
      updateinfo:{},
      rules:{

      },
      sels:[],
      listLoading: false,
      typechoose1:'false',
      typechoose2:'false',
      typechoose3:'false'

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
      this.updateinfo={};
      this.updateinfo=row;
      this.updategetmessage = !this.updategetmessage
    },
    //获取资费信息
    CostAdminById(){
      // this.testVersion=false;
      var para={
        page:this.page,
        packageName:this.input
      }
      let configs={
        headers:{
          token: sessionStorage.getItem('AppointToken')
        }
      };
      CostAdmin(para,configs).then((res) => {
          this.listLoading = false;
          if (res.data.statusCode === 'C200') {
            // ElMessage.success(res.data.msgId);
            this.total = res.data.result.total;
            this.tableData = res.data.result.list;
          } else {
            ElMessage.error(res.data.msgId);
          }
        });
    },
    //添加资费信息
    AddCost(){
       // alert(JSON.stringify(this.addparam))
      let configs={
        headers:{
          token: sessionStorage.getItem('AppointToken')
        }
      };
       CostAdd(this.addparam,configs).then((res)=>{
         if (res.data.statusCode === 'C200') {
           this.AddPopup();
           this.CostAdminById();
           ElMessage.success(res.data.msgId);
         } else {
           ElMessage.error(res.data.msgId);
         }
       })

    },
    //修改信息
    CostUpdate(){
      let para = {
        costId: this.updateinfo.cost_id,
        name: this.updateinfo.name,
        baseDuration: this.updateinfo.base_duration,
        baseCost: this.updateinfo.base_cost,
        unitCost: this.updateinfo.unit_cost,
        status: this.updateinfo.status,
        descr: this.updateinfo.descr,
        startime: this.updateinfo.startimeCoding,
        costType: this.updateinfo.cost_type

      };
      let configs={
        headers:{
          token: sessionStorage.getItem('AppointToken')
        }
      };
      UpdateCost(para,configs).then((res)=>{
        if (res.data.statusCode === 'C200') {
          this.CostAdminById();
          this.updategetmessage=false;
          ElMessage.success(res.data.msgId);
        } else{
          ElMessage.error(res.data.msgId);
        }
      })
    },
    //删除
    CostDelete(index, row){
      let para = {"ids":[row.cost_id]};
      let configs={
        headers:{
          token: sessionStorage.getItem('AppointToken')
        }
      };
      DeleteCostById(para,configs).then((res) =>{
        this.listLoading = true;
        if (res.data.statusCode === 'C200') {
          this.CostAdminById();
          ElMessage.success(res.data.msgId);

        } else{
          ElMessage.error(res.data.msgId);
        }
      })
    },
    //批量删除
    selsChange(sels) {
      this.sels = sels;
    },
    moreRemove(){
        var ids = this.sels.map(item => item.cost_id);
        // alert(ids);
        this.$confirm('确认删除选中记录吗？', '提示', {
          type: 'warning'
        }).then(() => {
          this.listLoading = true;
          let para = {"ids": ids};
          let configs={
            headers:{
              token: sessionStorage.getItem('AppointToken')
            }
          };
          DeleteCostById(para,configs).then((res) => {
            this.listLoading = true;
            if (res.data.statusCode === 'C200') {
              this.CostAdminById();
              ElMessage.success(res.data.msgId);
            } else{
              ElMessage.error(res.data.msgId);
            }
          })
        })
      },
    //分页
    pageChange(val) {
      this.page = val;
      this.CostAdminById();
    },
    //套餐选择
    typeChoose(val){
      if(val==='0'){
         this.typechoose1 = true;
         this.typechoose2 = false;
         this.typechoose3 = true;
      }else if(val==='1'){
        this.typechoose1 = false;
        this.typechoose2 = false;
        this.typechoose3 = false;
      }else if(val==='2'){
        this.typechoose1 = true;
        this.typechoose2 = true;
        this.typechoose3 = false;
      }else if(val===''){
        this.typechoose1 = false;
        this.typechoose2 = false;
        this.typechoose3 = false;
      }
    }


  },
  mounted() {
    this.CostAdminById();
  }
}
</script>

<style scoped>
.moreDelete{
   float: right;
}
.cost-add{
  background-color: #409EFF;
  color: #fff;
}
.cost-tools{
  margin-top: 10px;
  margin-bottom: 10px;
}
.toolbar{
  margin: 10px 500px;
}
</style>