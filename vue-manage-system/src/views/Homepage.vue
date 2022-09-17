<template>
  <section>
    <div class="homepage-wrap">
       <div class="home-picture">
         <el-carousel :interval="4000" type="card" height="280px">
           <el-carousel-item v-for="item in 6" :key="item">
             <h3 text="2xl" justify="center">{{ item }}</h3>
           </el-carousel-item>
         </el-carousel>
       </div>
    </div>
    <div class="homepage-show">
      <el-tabs type="border-card">
        <el-tab-pane label="系统公告">
          欢迎您使用本电信资费系统<br>
          本电信资费系统当前可以正常使用<br>
          如果遇到相关问题请联系相关工作人员<br>
        </el-tab-pane>
        <el-tab-pane label="使用说明">
          通过点击左侧提供的相关业务<br>
          进行业务操作<br>
          如遇到问题无法解决<br>
          请第一时间联系工作人员<br>
        </el-tab-pane>
        <el-tab-pane label="新品概述">
          本月上新，神州行，包年电话卡，价格实惠<br>
          现正打折销售，优惠仅此一个月<br>
          详情请查看资费界面<br>
        </el-tab-pane>
        <el-tab-pane label="关于">
          关于本电信资费系统，作用于用户和管理员的一体系统<br>
          方便了用户了解自己资费的信息<br>
          也方便了工作人员的工作<br>
        </el-tab-pane>
      </el-tabs>
    </div>
  </section>
</template>

<script>
export default {
 data(){
   return{

   }
 },
  methods:{

  }
}
</script>

<style scoped>
.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
</style>