## 在线预览
[问答社区](http://39.107.47.5:8018/index)

## 主要技术栈
- spring boot
- mybatis
- Thymeleaf
- redis
- bootstrap
- mysql
- elasticsearch
- RabbitMQ
 
 ## 项目描述
 [码农问答社区](http://39.107.47.5:8018/index)是一个类ealsticsearch中文社区的问答社区，采用Spring boot+Thymeleaf,项目涉及的企业级应用包括redis,elasticsearch。  
 使用github OAuth2作为第三方登录入口  
 使用ealsticsearch作为检索引擎  
 使用redis作为缓存引擎  
 使用定时器来完成热门问题的搜索
 
## 疑难点
   [github OAuth](https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/)
   1. 关于github OAuth认证，这是一种调用github第三方认证的技术，github中有API与文档的说明
         - 大致过程分为几个步骤
         - 用户点击登录按钮，调用登录的过程，页面跳转到认证页面.
         - 请求页面包含相应的认证信息authorize
         - 用户接受请求后，会回调之前的url同时附带一个code信息。
         - 之后再次访问github ,调用accesstoken 接口 会携带一个access_token对象，其中携带code
         - github 返回一个access_token，
         - 客户端向github调用user接口 返回一个user对象，其中返回一个access_token
         = access_token 正确会返回一个user 信息
         - 客户端自己页面的展示  更新session状态。
   2. 关于发布页面以及显示的逻辑
       - 第一：需要确认用户是否存在登录的情况，未登录要跳转index页面做登录处理，考虑优化为拦截器，Spring security
       -  第二：在用户登录的情况下，对于发布内容是否为空进行校验，考虑使用spring booot自带的表单校验注解
       - 第三：将数据插入到数据库中，完成后跳转index页面，显示问题
       -  第四：在显示时，我们需要一个用户github的头像来作为图片，所以需要从user表中进行数据的获取
       -  第五：采用业务装配的方式，在service中将对应的question与user类进行组合，需要找一个不变的字段来进行连接（creator_id），不推荐使用外键
       -  第六： 将获得数据进行显示
       - 遇到的问题：登录时没有进行相同用户校验，导致相同用户都被插入到数据库中，在进行与questuion对象进行绑定时，查询出多条数据
       - 解决：对于登录逻辑进行更改，用户进行登录时，先与数据库进行比对，看是否存在相同数据，相同的话，需要对于不同的记录进行更新
       不存在要进行用户的新增。
   3.  对于count(1)与count（*） 以及count(列名) 的问题
        -  第一 对于null而言，count(1) 与count(*) 都不会忽略列为null的情况，而对于count(列名)而言，
        会忽略列为null的情况    
        - 当表只有一个字段时，count(*)最快 
        - 当列名为主键时，count(列名)比count(1)快，反之亦然
        - 当存在多列且没有主键时，count(1) 效率比count(*)更高，
   4. 关于编辑自己问题的页面逻辑 
       -  首先在我的问题页面点击问题标题，可以跳转，先经过拦截器判断是否登录
       -  编辑问题页面与发布页面为相同页面，不同的是编辑问题有一个id的隐藏域，我们首先需要判断登录状态，
       -  其次根据id判断是在发布问题，还是更新问题，其次判断该id的创建者是否为登录用户
       -  最后对问题进行修改与新增  
##### 异常处理
1.    要完成对于异常的处理，最少需要一个集合QuestionErrorCodeEnum.class对于所有可能发生的异常进行包装
2.    一个自定义的异常类继承了RuntimeException.class
3.    一个类MyExceptionHandler添加@ControllerAdvice，自定义一个方法handler添加 @ExceptionHandler 来捕获所有发生的异常，返回值是返回给客户端的类。 该注解在捕获异常时会优先寻找最合适的异常。
ExceptionHandlerMethodResolver.getMappedMethod寻找匹配度最高的
4. 一个类来返回给客户端具体的异常对象。
5.    在可能出现异常的位置抛出自定义异常类
## Linux运行步骤
- 开启es  su - elasticsearch /usr/local/tmp/elasticsearch-6.8.2/bin/elasticsearch
- 编译 mvn compile package
- 运行 java -jar -Dspring.profiles.active=production target/talk-0.0.1-SNAPSHOT.jar    
## 更新记录   
##### 2020-2-5更新
本次更新版本：v2020-2-5  
  使用nginx完成图片的上传服务   
  

  ##### 2020-2-14更新
  本次更新版本：v2020-2-14   
  使用定时器完成热门话题
  ##### 2020-2-21更新
  本次更新版本：v2020-2-21   
  完善elasticsearch搜索，热门问题支持搜索
   ##### 2020-2-22更新
   本次更新版本：v2020-2-22   
   添加多个模块，支持热度搜索             
 ##### 2020-2-22更新
   本次更新版本：v2020-2-24   
   修复无法分页的问题。
            
        