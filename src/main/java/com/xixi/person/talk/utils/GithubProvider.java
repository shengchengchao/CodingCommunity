package com.xixi.person.talk.utils;

import com.alibaba.fastjson.JSON;
import com.xixi.person.talk.dto.AccessTokendto;
import com.xixi.person.talk.dto.GithubUserdto;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @Auther: xixi-98
 * @Date: 2019/12/15 20:21
 * @Description: provider工具类
 */
@Component
@Slf4j
public class GithubProvider {

    /**
    * @Description:  使用okHTTP来模拟客户端访问github浏览器获得accesstoken 信息   令牌
    * @Param: accessTokendto
    * @return: accesstoken
    * @Author: xixi
    * @Date: 2019/12/16
    */
    public String getAccessToken(AccessTokendto accessTokendto){
            MediaType mediaType = MediaType.get("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();
            System.out.println(JSON.toJSONString(accessTokendto));
            String s = JSON.toJSONString(accessTokendto);
            RequestBody body = RequestBody.create(mediaType,s);

            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
                System.out.println(string);
                String token = string.split("&")[0].split("=")[1];
                return token;
            } catch (Exception e) {
                log.error("getAccessToken error,{}", accessTokendto, e);
            }
            return null;
    }

    /**
    * @Description: 根据 accesstoken令牌 中获得的数据从github中获得到对应的用户信息。 还是使用okhttp的 get请求
    * @Param:
    * @return:
    * @Author: xixi
    * @Date: 2019/12/16
    */
    public GithubUserdto getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://api.github.com/user?access_token="+accessToken)
                    .build();
            try  {
                Response response = client.newCall(request).execute();
                String string = response.body().string();
                //json的转换 使用fastjson
                GithubUserdto githubUserdto = JSON.parseObject(string, GithubUserdto.class);
                System.out.println("githubUserdto :"+githubUserdto.toString());
                return githubUserdto;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
    }
}
