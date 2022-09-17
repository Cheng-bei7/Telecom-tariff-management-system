package sale.ljw.electronictariffmanagementsystem.backend.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.electronictariffmanagementsystem.backend.form.AdminInformationByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.form.EditAdminInformationByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.form.FindAdminInformationByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.form.idsInt;
import sale.ljw.electronictariffmanagementsystem.backend.service.AdminInfoService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/adminManage")
public class AdminManageServlet {
    @Autowired
    private AdminInfoService adminInfoService;

    @ApiOperation(value = "查找全部数据")
    @PostMapping("/findAllAdmin")
    public String findAllAdmin(@RequestBody @Valid FindAdminInformationByAdmin adminInformation) {
        return adminInfoService.findAllAdmin(adminInformation);
    }

    @ApiOperation(value = "删除管理员")
    @GetMapping("/deleteAdminById/{adminId}")
    public String deleteAdminById(@PathVariable Integer adminId) {
        return adminInfoService.deleteAdminById(adminId);
    }

    @ApiOperation(value = "密码重置")
    @PostMapping("/passwordReset")
    public String passwordReset(@RequestBody @Valid idsInt ids) {
        return adminInfoService.passwordReset(ids);
    }

    @ApiOperation(value = "获取全部角色")
    @GetMapping("/getAllRole")
    public String getAllRole() {
        return adminInfoService.getAllRole();
    }

    @ApiOperation(value = "添加管理员")
    @PostMapping("/addAdminInformation")
    public String addAdminInformation(@RequestBody @Valid AdminInformationByAdmin adminInformation) {
        return adminInfoService.addAdminInformation(adminInformation);
    }

    @ApiOperation(value = "编辑管理员")
    @PostMapping("/editAdminInformation")
    public String editAdminInformation(@RequestBody @Valid EditAdminInformationByAdmin adminInformation) {
        return adminInfoService.editAdminInformation(adminInformation);
    }
}
