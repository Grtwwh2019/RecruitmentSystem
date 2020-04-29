version1.0-项目总结
========
数据库
----
`数据库设计存在冗余`    
* 1.用户-角色的权限关系    
因为学习了RBAC权限管理模型，于是想在项目上实践。但是到最后写代码的时候，感觉有点刻意了。    

* 2.公告方面    
公告模块，一则公告应该对应的是一个群体，而不是一个用户。不然的话，面对上千上万的用户也需要单独发公告的话，管理员的工作量是非常大的，并且不利于数据的保存。   

* 3.企业账号与公司的关系    
企业账号应该与公司关联上，一个企业账号对应一间公司，去除用户上营业执照的属性。在企业账号注册时，应该要求填写公司信息，并且保存在数据库。    

* 4.新用户的简历初始化   
一个用户新注册以后，应该为其初始化一份对应的简历。   

后端
---
`代码冗余`
* 1.登录、角色问题   
在登录方面，每一个接口至少都有一个if语句判断用户是否登录；在权限方面，每一个接口都有一个if语句判断用户是否拥有对应的角色；这种全局的登录状态和角色权限判断可以利用AOP来实现，并且简化代码。   

* 2.项目的分层不够清晰明确   
对不同的，但名称类似的service没有进行分层；   
不用的service使用了同一条sql，如果以后要对某一个service的业务逻辑进行更新，那么有可能会导致调用这条sql的其他的service产生报错。   

* 3.对于spring boot与各种功能的整合有点乱
多处地方仍使用的是原生的代码或者是spring MVC进行处理；例如验证码的获取、文件的上传等等。   

* 4.部分业务逻辑的思路不合理    
例如生成返回的简历信息，不应该是new一个新的实例来生成，而是应该用已有的数据加上新修改的数据来生成。否则的话会出现返回的简历信息有空缺，从而导致数据库与缓存的数据不一致的问题。

* 5.缓存与数据库的问题   
没有在写代码前思考数据不一致的问题；也未认真了解过，需要加强这方面的知识。   

* 6.没有进行测试    
todo：对系统进行压测，获取系统各方面的性能并思考可优化的地方。   

前端
---
* 1.系统初始化架构   
整个项目的结构很乱，并且对于全局的变量没有做一个集中处理；在未来需要修改的时候，要一个一个的改。

* 2.没有牢记vue的特点：数据渲染页面   
对于页面的变化处理较为生硬，没有做到灵活处理，导致用户体验感会很差。

todo
---
* 1.实现新用户注册以后，后端自动发送一封邮件到新用户的邮箱   
step1.先使用多线程的方式实现邮件的发送；   
step2.使用rabbitMQ改进。   

* 2.修改系统的用户-角色-权限关系   
step1.重新设计用户-角色-权限的基本表以及关系表；    
step2.整合spring security；    
step3.前端根据数据库权限表动态获取路由。   

* 3.更新用户找回密码（修改手机号、邮箱）的方式   
step1.通过邮件、手机号码发送验证码；
step2.用户填写验证码；
step3.比对发出的验证码和填写的验证码，然后判断是否允许找回密码。   

* 4.尽可能减少if-else的使用   

* 5.重新设计属于自己的前端页面（difficult）


