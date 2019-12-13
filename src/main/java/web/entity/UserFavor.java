package web.entity;

import java.util.Date;

public class UserFavor {
    private Integer id;

    private String openid;

    private String imageMd5;

    private Boolean isfavor;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getImageMd5() {
        return imageMd5;
    }

    public void setImageMd5(String imageMd5) {
        this.imageMd5 = imageMd5 == null ? null : imageMd5.trim();
    }

    public Boolean getIsfavor() {
        return isfavor;
    }

    public void setIsfavor(Boolean isfavor) {
        this.isfavor = isfavor;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}