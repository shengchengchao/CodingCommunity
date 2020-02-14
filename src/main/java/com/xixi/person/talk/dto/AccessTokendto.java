package com.xixi.person.talk.dto;

import lombok.Data;

/**
 * @Author: xixi-98
 * @Date: 2019/12/15 20:21
 * @Description: AccessToken传输类
 */

public class AccessTokendto {
    private String clientId;
    private String clientSecret;
    private String code;
    private String redirectUri;
    private String state;

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public AccessTokendto(String clientId, String clientSecret, String code, String redirectUri, String state) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.code = code;
        this.redirectUri = redirectUri;
        this.state = state;
    }

    @Override
    public String toString() {
        return "AccessTokendto{" +
                "clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", code='" + code + '\'' +
                ", redirectUri='" + redirectUri + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
