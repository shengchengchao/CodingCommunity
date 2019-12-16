## 论坛

## 资料

## 工具

## 疑难点
   [github OAuth](https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/)
   1. 关于github OAuth认证，这是一种调用githun第三方认证的技术，github中有API与文档的说明
         - 大致过程分为几个步骤
         - 用户点击登录按钮，调用登录的过程，页面跳转到认证页面.
         - 请求页面包含相应的认证信息authorize
         - 用户接受请求后，会回调之前的url同时附带一个code信息。
         - 之后再次访问github ,调用accesstoken 接口 会携带一个access_token对象，其中携带code
         - github 返回一个access_token，
         - 客户端向github调用user接口 返回一个user对象，其中返回一个access_token
         = access_token 正确会返回一个user 信息
         - 客户端自己页面的展示  更新session状态。
            
        