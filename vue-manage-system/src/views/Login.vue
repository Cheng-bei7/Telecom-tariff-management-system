<template>
  <div class="login-wrap">
    <div class="panda">
      <div class="ear left"></div>
      <div class="ear right"></div>
      <div class="face">
        <div class="eye-shadow left"></div>
        <div class="eye-white left">
          <div class="eye-ball"></div>
        </div>
        <div class="eye-shadow right"></div>
        <div class="eye-white right">
          <div class="eye-ball"></div>
        </div>
        <div class="nose"></div>
        <div class="mouth"></div>
      </div>
      <div class="body"></div>
      <div class="foot left">
        <div class="sole"></div>
      </div>
      <div class="foot right">
        <div class="sole"></div>
      </div>
    </div>
    <div class="ms-login">
      <div class="ms-title">电信资费登录</div>
      <div class="hand left"></div>
      <div class="hand right"></div>
      <el-form :model="param" :rules="rules" ref="param" label-width="0px" class="ms-content">
        <el-form-item prop="loginName">
          <el-input v-model="param.loginName" placeholder="账号">
            <template #prepend>
              <el-button icon="el-icon-user"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" placeholder="密码" v-model="param.password"
                    @keyup.enter="submitForm()" id="password" >
            <template #prepend>
              <el-button icon="el-icon-lock"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="submitForm()">登录</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>
<script>
import {ElMessage} from "element-plus";
import $ from 'jquery'
import {loginByAdmin} from "../api/api_zxm";
export default {
  data: function () {
    return {
      ruleForm2: {
        account: '',
        checkPass: ''
      },
      param: {
        loginName: "",
        password: "",
      },
      rules: {
        loginName: [
          {
            required: true,
            message: "请输入用户名",
            trigger: "blur",
          },
        ],
        password: [
          {
            required: true, message: "请输入密码",
            trigger: "blur"
          },
        ],
      },
      level:[],
    }

  },
  methods: {
    submitForm() {
      this.$refs.param.validate((valid) => {
        if (valid) {
          sessionStorage.removeItem('AppointToken');
          loginByAdmin(this.param).then((res) => {
            if (res.data.statusCode === 'C200') {
              // alert(res.headers['token'])
              //将token存入页面会话域中
              sessionStorage.setItem("AppointToken",res.headers['token'])
              sessionStorage.setItem("showView",JSON.stringify(res.data.result.showView))
              ElMessage.success(res.data.msgId);
              localStorage.setItem("admin", JSON.stringify(res.data.result.adminInformation));
              localStorage.setItem("loginTime", new Date().getFullYear() + "-" + (new Date().getMonth() + 1) + '-' + new Date().getDate() + '  ' + new Date().getHours() + ':' + new Date().getMinutes() + ':' + new Date().getSeconds())
              window.location.href = 'http://localhost:3000/#/homepage';
            } else {
              ElMessage.error(res.data.msgId);
            }
          })
        } else {
          ElMessage.error("登录失败");
          return false;
        }
      });
    },
    focus(){
      $("#password").focusin(function(){
        // 密码框选中
        $('.ms-login').addClass('up');
      }).focusout(function(){
        // 密码框非选中
        $('.ms-login').removeClass('up');
      })
    },
// 眼球移动
    move(){
      $(document).on('mousemove',function(e){
        let dw=$(document).width() / 10;
        let dh=$(document).height() / 18;
        let x=e.pageX / dw;
        let y=e.pageY / dh;
        $('.eye-ball').css({
          left:x,
          top:y
        })
      })
    },
  },
  mounted(){
    this.focus();
    this.move();
  }
}

</script>

<style scoped>
.login-wrap {
  position: relative;
  width: 100%;
  height: 100%;
  background:linear-gradient(200deg, #72afd3, #96fbc4);
  background-size: 100%;
}

.ms-title {
  width: 100%;
  line-height: 50px;
  text-align: center;
  font-size: 20px;
  color: #000000;
  border-bottom: 1px solid #ddd;
  z-index:4;
}

.ms-login {
  position: absolute;
  left: 50%;
  top:50%;
  width: 350px;
  margin: -100px 0 0 -175px;
  border-radius: 5px;
  background: rgba(255, 255, 255);
  overflow: hidden;
  z-index:3;
  box-shadow: 0 0 15px rgba(0,0,0,0.2);
}

.ms-content {
  padding: 30px 30px;
}

.login-btn {
  text-align: center;
}

.login-btn button {
  width: 100%;
  height: 36px;
  margin-bottom: 10px;
}

.login-tips {
  font-size: 12px;
  line-height: 30px;
  color: #fff;
}
/* 开始画熊猫 */
.panda{
  /* 相对定位 */
  position: relative;
  width: 200px;
  left: 545px;
  top:55px
}
/* 脸部 */
.face{
  width: 200px;
  height: 200px;
  background-color: #fff;
  border-radius: 100%;
  box-shadow: 0 10px 15px rgba(0,0,0,0.15);
  position: relative;
  z-index: 1;
}
/* 耳朵 */
.ear{
  width: 70px;
  height: 70px;
  background-color: #000;
  border-radius: 100%;
  position: absolute;
  top: -10px;
}
.ear.right{
  right: 0;
}
/* 黑眼圈 */
.eye-shadow{
  width: 50px;
  height: 80px;
  background-color: #000;
  border-radius: 50%;
  /* 绝对定位 */
  position: absolute;
  top: 40px;
}
.eye-shadow.left{
  transform: rotate(45deg);
  left: 30px;
}
.eye-shadow.right{
  transform: rotate(-45deg);
  right: 30px;
}
/* 眼白 */
.eye-white{
  width: 30px;
  height: 30px;
  background-color: #fff;
  border-radius: 100%;
  position: absolute;
  top: 68px;
}
.eye-white.left{
  left: 38px;
}
.eye-white.right{
  right: 38px;
}
/* 眼球 */
.eye-ball{
  width: 20px;
  height: 20px;
  background-color: #000;
  border-radius: 100%;
  position: absolute;
  left: 5px;
  top: 5px;
}
/* 鼻子 */
.nose{
  width: 35px;
  height: 20px;
  background-color: #000;
  position: absolute;
  left: 0;
  right: 0;
  margin: auto;
  bottom: 65px;
  border-radius: 42px 42px 60px 60px / 30px 30px 60px 60px;
}
/* 嘴巴 */
.mouth{
  width: 68px;
  height: 25px;
  border-bottom: 7px solid #000;
  border-radius: 50%;
  position: absolute;
  left: 0;
  right: 0;
  margin: auto;
  bottom: 35px;
}
/* 身体 */
.body{
  width: 250px;
  height: 280px;
  background-color: #fff;
  position: relative;
  left: -25px;
  top: -10px;
  border-radius: 100px 100px 100px 100px / 126px 126px 96px 96px;
  box-shadow: 0 5px 10px rgba(0,0,0,0.3);
}
/* 脚 */
.foot{
  width: 82px;
  height: 120px;
  background-color: #000;
  position: absolute;
  bottom: 8px;
  z-index: 4;
  border-radius: 40px 40px 35px 40px / 26px 26px 63px 63px;
  box-shadow: 0 5px 5px rgba(0,0,0,0.2);
}
.foot.left{
  left: -80px;
}
.foot.right{
  right: -80px;
  transform: rotateY(180deg);
}
/* 脚掌-大椭圆 */
.foot::after{
  content: "";
  width: 55px;
  height: 65px;
  background-color: #222;
  position: absolute;
  border-radius: 50%;
  left: 0;
  right: 0;
  margin: auto;
  bottom: 10px;
}
/* 脚掌-三个小椭圆 */
.foot .sole,
.foot .sole::before,
.foot .sole::after{
  width: 20px;
  height: 30px;
  background-color: #222;
  position: absolute;
  border-radius: 50%;
  left: 0;
  right: 0;
  margin: auto;
  top: 8px;
}
.foot .sole::before{
  content: "";
  left: -50px;
}
.foot .sole::after{
  content: "";
  left: 25px;
}
/* 手 */
.hand,
.hand::before,
.hand::after{
  width: 40px;
  height: 30px;
  background-color: #000;
  border-radius: 50px;
  position: absolute;
  top: 70px;
  left: -20px;
}
.hand::before{
  content: "";
  top: 16px;
  left: 0;
}
.hand::after{
  content: "";
  top: 32px;
  left: 0;
}
.hand.right{
  right: -20px;
  left: auto;
}
/* 登录框向上举 */
.up{
  transform: translate(0,-80px);
  transition-duration:1s;
}
</style>
