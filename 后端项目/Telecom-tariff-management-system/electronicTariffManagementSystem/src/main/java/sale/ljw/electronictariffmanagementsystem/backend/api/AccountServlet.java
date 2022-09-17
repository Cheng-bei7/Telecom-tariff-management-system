package sale.ljw.electronictariffmanagementsystem.backend.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.electronictariffmanagementsystem.backend.form.AddAccountByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.form.EditAccountByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.form.FindAccountByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.service.AccountService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/accountByAdmin")
public class AccountServlet {
    @Autowired
    private AccountService accountService;

    @ApiOperation(value = "查询全部账户账号")
    @PostMapping("/findAllAccount")
    public String findAllAccount(@RequestBody @Valid  FindAccountByAdmin account) {
        return accountService.findAllAccount(account);
    }


    @ApiOperation(value = "暂停账号")
    @GetMapping("/suspendAccount/{id}")
    public String suspendAccount(@PathVariable String id) {
        return accountService.suspendAccount(id);
    }

    @ApiOperation(value = "删除账号")
    @GetMapping("/deleteById/{id}")
    public String deleteById(@PathVariable String id) {
        return accountService.deleteById(id);
    }

    @ApiOperation(value = "添加账号")
    @PostMapping("/addAccount")
    public String addAccount(@RequestBody @Valid AddAccountByAdmin account) {
        return accountService.addAccount(account);
    }

    @ApiOperation(value = "获取当前获取行数")
    @GetMapping("/getAccountOneById/{accountId}")
    public String findAccountOneById(@PathVariable Integer accountId) {
        return accountService.findAccountOneById(accountId);
    }

    @ApiOperation(value = "根据身份证号获取推荐人id")
    @GetMapping("/getReferrerIdByIdCard/{idCard}")
    public String findReferrerIdByIdCard(@PathVariable String idCard) {
        return accountService.findReferrerIdByIdCard(idCard);
    }

    @ApiOperation(value = "更新账户")
    @PostMapping("/updateAccountById")
    public String updateAccountById(@RequestBody @Valid EditAccountByAdmin account) {
        return accountService.updateAccountById(account);
    }
}
