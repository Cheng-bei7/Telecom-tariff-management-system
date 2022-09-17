package sale.ljw.electronictariffmanagementsystem.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;

@Data
@ApiModel(value = "管理员信息")
public class AdminInformationByAdmin {
    @ApiModelProperty(value = "管理员账号")
    @NotBlank(message = "管理员账号不得为空")
    private String adminCode;
    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不得为空")
    @Pattern(regexp = "^[a-z0-9_-]{5,30}$", message = "密码格式错误")
    private String password;
    @ApiModelProperty(value = "管理员姓名")
    @NotBlank(message = "管理员姓名不得为空")
    private String name;
    @ApiModelProperty(value = "电话")
    @NotBlank(message = "电话不得为空")
    @Pattern(regexp = "^1[3456789]\\d{9}$", message = "电话格式错误")
    private String telephone;
    @ApiModelProperty(value = "电子邮件")
    @NotBlank(message = "电子邮件不得为空")
    @Pattern(regexp = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$", message = "邮件格式错误")
    private String email;
    @ApiModelProperty(value = "角色列表")
    @Size(min = 1)
    private ArrayList<Integer> roleIds;
}
