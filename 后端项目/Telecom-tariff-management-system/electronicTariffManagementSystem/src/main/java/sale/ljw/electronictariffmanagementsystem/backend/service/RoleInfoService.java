package sale.ljw.electronictariffmanagementsystem.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sale.ljw.electronictariffmanagementsystem.backend.form.AddRoleInfoByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.form.EditRoleInfoByAdmin;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.RoleInfo;

/**
 * @author 86155
 * @description 针对表【role_info】的数据库操作Service
 * @createDate 2022-09-13 10:58:51
 */
public interface RoleInfoService extends IService<RoleInfo> {

    String findAllRoleModule(Integer page);

    String deleteRoleInfoById(Integer id);

    String editRoleInfo(EditRoleInfoByAdmin editRoleInfo);

    String addRoleInfo(AddRoleInfoByAdmin roleInfo);
}
