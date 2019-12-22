package com.xixi.person.talk.provider;

import com.alibaba.fastjson.JSON;
import com.xixi.person.talk.dto.AccessTokendto;
import com.xixi.person.talk.dto.GithubUserdto;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @Auther: xixi-98
 * @Date: 2019/12/15 20:21
 * @Description: provider工具类
 */
@Component
public class GithubProvider {

    /**
    * @Description:  使用okHTTP来模拟客户端访问github浏览器 获得accesstoken 信息
    * @Param: accessTokendto
    * @return: accesstoken
    * @Author: xixi
    * @Date: 2019/12/16
    */
    public String getAccessToken(AccessTokendto accessTokendto){
        MediaType Type = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(Type,JSON.toJSONString(accessTokendto));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
                //使用正则表达式，对其进行分割，获得需要的token,其实只要对于& 进行分割就可以了 例 49d0a2818a7676c0527b67057071df9571bb09b9
                String token = string.split("&")[0].split("=")[1];
                return token;
            } catch (IOException e) {
            }
            return null;
    }

    /**
    * @Description: 根据 accesstoken 中获得的数据从github中获得到对于的用户信息。 还是使用okhttp的 get请求
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

                return githubUserdto;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
    }
}
