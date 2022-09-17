package sale.ljw.electronictariffmanagementsystem.backend.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.electronictariffmanagementsystem.backend.form.ChangePassword;
import sale.ljw.electronictariffmanagementsystem.backend.form.LoginAdminFrom;
import sale.ljw.electronictariffmanagementsystem.backend.service.AdminInfoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/loginAdmin")
public class LoginServlet {
    @Autowired
    private AdminInfoService adminInfoService;

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public String loginByAdmin(@RequestBody @Valid LoginAdminFrom loginFrom, HttpServletRequest request, HttpServletResponse response) {
        return adminInfoService.loginByAdmin(loginFrom, request, response);
    }

    @ApiOperation(value = "修改密码")
    @PostMapping("/changePassword")
    public String changePassword(@RequestBody ChangePassword password,HttpServletRequest req,HttpServletResponse resp) {
        return adminInfoService.changePassword(password,req,resp);
    }
}
