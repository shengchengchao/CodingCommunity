package com.xixi.pereson.talk.Model;

/**
 * @Auther: xixi-98
 * @Date: 2019/12/16 17:00
 * @Description:
 */
public class Users {

    private Integer id;
    private String accountid;
    private String name;
    private String toekn;
    private Long createdate;
    private Long updatedate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToekn() {
        return toekn;
    }

    public void setToekn(String toekn) {
        this.toekn = toekn;
    }

    public Long getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Long createdate) {
        this.createdate = createdate;
    }

    public Long getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Long updatedate) {
        this.updatedate = updatedate;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", accountid='" + accountid + '\'' +
                ", name='" + name + '\'' +
                ", toekn='" + toekn + '\'' +
                ", createdate=" + createdate +
                ", updatedate=" + updatedate +
                '}';
    }
}
