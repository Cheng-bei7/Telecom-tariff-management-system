package sale.ljw.electronictariffmanagementsystem.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sale.ljw.electronictariffmanagementsystem.backend.form.AddAccountByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.form.EditAccountByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.form.FindAccountByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.Account;

/**
 * @author 86155
 * @description 针对表【account】的数据库操作Service
 * @createDate 2022-09-06 08:59:52
 */
public interface AccountService extends IService<Account> {

    String findAllAccount(FindAccountByAdmin account);

    String suspendAccount(String id);

    String deleteById(String id);

    String addAccount(AddAccountByAdmin account);

    String findAccountOneById(Integer accountId);

    String findReferrerIdByIdCard(String idCard);

    String updateAccountById(EditAccountByAdmin account);
}
