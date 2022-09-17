package sale.ljw.electronictariffmanagementsystem.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "查找account")
public class FindAccountByAdmin {
    @ApiModelProperty(value = "当前页码")
    @NotNull(message = "当前页码不得为空")
    private Integer page;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "登录名")
    private String loginName;

    @ApiModelProperty(value = "当前状态")
    private String status;
}
