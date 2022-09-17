package sale.ljw.electronictariffmanagementsystem.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(value = "添加账号")
public class AddServiceByAdmin {
    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不得为空")
    private String loginPasswd;

    @ApiModelProperty(value = "os账号")
    @NotBlank(message = "os账号不得为空")
    private String osUsername;

    @ApiModelProperty(value = "cost账号")
    @NotNull(message = "cost账号不得为空")
    private Integer costId;

    @ApiModelProperty(value = "当前状态")
    @NotBlank(message = "当前状态不得为空")
    @Pattern(regexp = "0|1", message = "状态格式错误")
    private String status;

    @ApiModelProperty(value = "主机ip")
    @NotBlank(message = "主机ip不得为空")
    @Pattern(regexp = "\\d+\\.\\d+\\.\\d+\\.\\d+", message = "主机地址格式错误")
    private String unixHost;

    @ApiModelProperty(value = "账户身份证号")
    @NotBlank(message = "账户身份证号不得为空")
    @Pattern(regexp = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)", message = "身份证格式错误")
    private String accountIdCard;
}
