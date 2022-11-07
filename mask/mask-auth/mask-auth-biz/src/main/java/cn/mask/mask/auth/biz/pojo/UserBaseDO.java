package cn.mask.mask.auth.biz.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author hezhiling
 * @version 1.0
 * @date 2022-10-29 20:54:19
 */
@Data
@TableName(value = "user_base")
public class UserBaseDO {
    @TableId
    private String userId;

    private String username;

    private Integer gender;

    private Date bitrh;

    private String tel;

    private String email;

    private String avatarUrl;

    private String status;

    private String crteAppId;

    private String crteType;

    private String crtorId;

    private String crtorName;

    private Date crteTime;

    private String updterId;

    private String updterName;

    private Date updtTime;
}
