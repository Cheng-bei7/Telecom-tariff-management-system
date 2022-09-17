package sale.ljw.electronictariffmanagementsystem.backend.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.electronictariffmanagementsystem.backend.form.AddServiceByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.form.EditServiceByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.form.FindServiceByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.service.ServiceService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/accountServiceByAdmin")
public class AccountServiceServlet {
    @Autowired
    private ServiceService serviceService;

    @ApiOperation(value = "查找业务账号")
    @PostMapping("/findServiceByPage")
    public String findServiceByPage(@RequestBody @Valid FindServiceByAdmin service) {
        return serviceService.findServiceByPage(service);
    }

    @ApiOperation(value = "暂停业务账号")
    @GetMapping("/suspendBusinessAccountById/{serviceId}")
    public String suspendBusinessAccountById(@PathVariable Integer serviceId) {
        return serviceService.suspendBusinessAccountById(serviceId);
    }

    @ApiOperation(value = "删除账号")
    @GetMapping("/deleteAccountById/{serviceId}")
    public String deleteAccountById(@PathVariable Integer serviceId) {
        return serviceService.deleteAccountById(serviceId);
    }

    @ApiOperation(value = "修改业务账号")
    @PostMapping("/editAccountServiceById")
    public String editAccountServiceById(@RequestBody @Valid EditServiceByAdmin service) {
        return serviceService.editAccountServiceById(service);
    }

    @ApiOperation(value = "添加业务账号")
    @PostMapping("/addAccountService")
    public String addAccountService(@RequestBody @Valid AddServiceByAdmin service) {
        return serviceService.addAccountService(service);
    }
}
