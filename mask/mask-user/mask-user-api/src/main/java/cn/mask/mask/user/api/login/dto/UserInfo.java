package cn.mask.mask.user.api.login.dto;

import lombok.Data;

/**
 * @author hezhiling
 */
@Data
public class UserInfo {
    private String id;
    private String nickName;
    private String avatarUrl;
    private String country;
    private String province;
    private String city;
    private String language;
    private Byte gender;
}