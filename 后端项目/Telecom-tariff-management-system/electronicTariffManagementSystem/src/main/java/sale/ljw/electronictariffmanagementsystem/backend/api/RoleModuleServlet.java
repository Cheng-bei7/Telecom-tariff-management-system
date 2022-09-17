package sale.ljw.electronictariffmanagementsystem.backend.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.electronictariffmanagementsystem.backend.form.AddRoleInfoByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.form.EditRoleInfoByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.service.ModuleInfoService;
import sale.ljw.electronictariffmanagementsystem.backend.service.RoleInfoService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/roleModule")
public class RoleModuleServlet {
    @Autowired
    private RoleInfoService roleInfoService;

    @Autowired
    private ModuleInfoService moduleInfoService;

    @ApiOperation(value = "查找权限数据")
    @GetMapping("/findAllRoleModule/{page}")
    public String findAllRoleModule(@PathVariable Integer page) {
        return roleInfoService.findAllRoleModule(page);
    }

    @ApiOperation(value = "获取具体权限列表")
    @GetMapping("/findAllModuleInfo")
    public String findAllModuleInfoList() {
        return moduleInfoService.findAllModuleInfoList();
    }

    @ApiOperation(value = "删除管理权限")
    @GetMapping("/deleteRoleInfoById/{id}")
    public String deleteRoleInfoById(@PathVariable Integer id) {
        return roleInfoService.deleteRoleInfoById(id);
    }

    @ApiOperation(value = "修改角色权限")
    @PostMapping("/editRoleInfo")
    public String editRoleInfo(@RequestBody @Valid EditRoleInfoByAdmin editRoleInfo) {
        return roleInfoService.editRoleInfo(editRoleInfo);
    }

    @ApiOperation(value = "添加角色权限")
    @PostMapping("/addRoleInfo")
    public String addRoleInfo(@RequestBody @Valid  AddRoleInfoByAdmin roleInfo) {
        return roleInfoService.addRoleInfo(roleInfo);
    }

}
