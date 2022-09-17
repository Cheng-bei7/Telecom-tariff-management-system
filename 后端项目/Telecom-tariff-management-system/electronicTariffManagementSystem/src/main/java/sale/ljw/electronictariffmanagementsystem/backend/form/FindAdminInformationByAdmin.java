package sale.ljw.electronictariffmanagementsystem.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
@ApiModel(value = "查找管理员信息")
public class FindAdminInformationByAdmin {

    @ApiModelProperty(value = "当前页码")
    private Integer page;

    @ApiModelProperty(value = "权限id")
    private ArrayList<Integer> moduleId;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

}
