package cn.mask.mask.users.service.login.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author hezhiling
 */
@Data
@Builder
@AllArgsConstructor
public class UserPO {
    private Integer id;
    private String username;
    private String password;
    private Byte gender;
    private LocalDate birthday;
    private LocalDateTime lastLoginTime;
    private String lastLoginIp;
    private String userLevel;
    private String nickname;
    private String mobile;
    private String avatar;
    private String weiXinOpenid;
    private String sessionKey;
    private String status;
    private LocalDateTime addTime;
    private LocalDateTime updateTime;
    private Boolean deleted;
    private String email;
}