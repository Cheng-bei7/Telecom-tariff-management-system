package sale.ljw.electronictariffmanagementsystem.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName service
 */
@TableName(value = "service")
@Data
public class ServiceProp implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer serviceId;
    /**
     *
     */
    private Integer accountId;
    /**
     *
     */
    private String unixHost;
    /**
     *
     */
    private String osUsername;
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
    private Integer costId;
}