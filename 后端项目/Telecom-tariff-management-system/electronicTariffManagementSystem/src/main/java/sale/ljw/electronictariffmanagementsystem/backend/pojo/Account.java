package sale.ljw.electronictariffmanagementsystem.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName account
 */
@TableName(value = "account")
@Data
public class Account implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer accountId;
    /**
     *
     */
    private Integer recommenderId;
    /**
     *
     */
    private String loginName;
    /**
     *
     */
    private String loginPasswd;
    /**
     *
     */
    private String status;
    /**
     *
     */
    private Date createDate;
    /**
     *
     */
    private Date pauseDate;
    /**
     *
     */
    private Date closeDate;
    /**
     *
     */
    private String realName;
    /**
     *
     */
    private String idcardNo;
    /**
     *
     */
    private Date birthdate;
    /**
     *
     */
    private String gender;
    /**
     *
     */
    private String occupation;
    /**
     *
     */
    private String telephone;
    /**
     *
     */
    private String email;
    /**
     *
     */
    private String mailaddress;
    /**
     *
     */
    private String zipcode;
    /**
     *
     */
    private String qq;
    /**
     *
     */
    private Date lastLoginTime;
    /**
     *
     */
    private String lastLoginIp;
}