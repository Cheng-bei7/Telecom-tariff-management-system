package sale.ljw.electronictariffmanagementsystem.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "登陆选项")
public class LoginAdminFrom {
    @ApiModelProperty(value = "登录凭证")
    @NotBlank(message = "登录凭证不得为空")
    private String loginName;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不得为空")
    private String password;
}
