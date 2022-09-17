package sale.ljw.electronictariffmanagementsystem.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(value = "修改密码")
public class ChangePassword {
    @ApiModelProperty(value = "旧密码")
    @NotBlank(message = "旧密码不得为空")
    private String oldPassword;
    @ApiModelProperty(value = "新密码")
    @NotBlank(message = "新密码不得为空")
    @Pattern(regexp = "^[a-z0-9_-]{5,30}$", message = "密码格式错误")
    private String newPassword;
}
