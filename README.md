## 主要技术栈
- spring boot
- mybatis
- Thymeleaf
- redis
- bootstrap
- mysql
- elasticsearch
 
 ## 项目描述
 码农问答社区是一个类ealsticsearch中文社区的问答社区，采用Spring boot+Thymeleaf,项目涉及的企业级应用包括redis,elasticsearch,未来会加入rabbitMQ做消息服务。  
 使用github OAuth2作为第三方登录入口  
 使用ealsticsearch作为检索引擎  
 使用redis作为缓存引擎
 
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
        
        
               
 
            
        