package sale.ljw.electronictariffmanagementsystem.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;

@Data
@ApiModel(value = "添加角色")
public class AddRoleInfoByAdmin {
    @ApiModelProperty(value = "权限名称")
    @NotBlank(message = "权限名称不得为空")
    private String name;

    @ApiModelProperty(value = "权限列表")
    @Min(1)
    private ArrayList<Integer> moduleId;
}
