package sale.ljw.electronictariffmanagementsystem.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sale.ljw.electronictariffmanagementsystem.backend.form.AddServiceByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.form.EditServiceByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.form.FindServiceByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.ServiceProp;

/**
 * @author 86155
 * @description 针对表【service】的数据库操作Service
 * @createDate 2022-09-08 10:32:14
 */
public interface ServiceService extends IService<ServiceProp> {

    String findServiceByPage(FindServiceByAdmin service);

    String suspendBusinessAccountById(Integer serviceId);

    String deleteAccountById(Integer serviceId);

    String editAccountServiceById(EditServiceByAdmin service);

    String addAccountService(AddServiceByAdmin service);
}
