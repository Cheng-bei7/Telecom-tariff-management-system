package sale.ljw.electronictariffmanagementsystem.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sale.ljw.electronictariffmanagementsystem.backend.form.*;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.AdminInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author 86155
 * @description 针对表【admin_info】的数据库操作Service
 * @createDate 2022-09-03 15:20:36
 */
public interface AdminInfoService extends IService<AdminInfo> {

    String loginByAdmin(LoginAdminFrom loginFrom, HttpServletRequest request, HttpServletResponse response);

    String findAllAdmin(FindAdminInformationByAdmin adminInformation);

    String deleteAdminById(Integer adminId);

    String passwordReset(idsInt ids);

    String addAdminInformation(AdminInformationByAdmin adminInformation);

    String getAllRole();

    String editAdminInformation(EditAdminInformationByAdmin adminInformation);

    String changePassword(ChangePassword password,HttpServletRequest req,HttpServletResponse resp);
}
