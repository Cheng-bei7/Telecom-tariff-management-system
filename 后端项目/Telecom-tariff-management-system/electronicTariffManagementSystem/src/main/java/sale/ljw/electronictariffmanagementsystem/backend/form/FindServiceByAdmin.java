package sale.ljw.electronictariffmanagementsystem.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(value = "查找业务账号")
public class FindServiceByAdmin {
    @ApiModelProperty(value = "当前页码")
    @NotNull(message = "当前页码不得为空")
    private Integer page;

    @ApiModelProperty(value = "os账号")
    private String osName;

    @ApiModelProperty(value = "服务器Ip")
    private String ip;

    @ApiModelProperty(value = "身份证号")
    private String idcardNo;

    @ApiModelProperty(value = "状态")
    @Pattern(regexp = "0|1|2", message = "当前状态格式错误")
    private String status;
}
