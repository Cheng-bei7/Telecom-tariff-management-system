package sale.ljw.electronictariffmanagementsystem.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.ArrayList;

@Data
@ApiModel(value = "修改权限列表")
public class EditRoleInfoByAdmin {
    @ApiModelProperty(value = "权限名称")
    @NotBlank(message = "权限名称不得为空")
    private String name;

    @ApiModelProperty(value = "权限id")
    @NotNull(message = "权限id不得为空")
    private Integer id;

    @ApiModelProperty(value = "权限列表")
    @Size(min = 1)
    private ArrayList<Integer> moduleId;
}
