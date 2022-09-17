package sale.ljw.electronictariffmanagementsystem.backend.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import sale.ljw.electronictariffmanagementsystem.backend.pojo.RoleInfo;

import java.util.ArrayList;
import java.util.Date;

@Data
@ApiModel(value = "管理员界面展示数据")
public class AdminDisplayData {

    private Integer adminId;

    private String adminCode;

    private String name;

    private String telephone;

    private String email;

    private Date enrolldateCoding;

    private String enrolldate;

    private ArrayList<RoleInfo> roleInfo;
}
