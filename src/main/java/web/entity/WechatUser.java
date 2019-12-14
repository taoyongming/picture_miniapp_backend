package web.entity;

import lombok.Data;

import java.util.Date;

@Data
public class WechatUser {
    private Integer id;

    private String sessionKey;

    private String token;

    private String nickname;

    private String avatarUrl;

    private Integer gender;

    private String country;

    private String province;

    private String city;

    private String mobile;

    private String openId;

    private String unionId;

    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;


}