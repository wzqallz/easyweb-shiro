# easyweb-shiro

## 简介

> Java后台管理系统开发平台，前后端不分离版本。

<br>

* 演示地址：[http://47.98.107.251:8089/](http://47.98.107.251:8089/login)
* 演示账号：admin &emsp;&emsp; 密码：admin 

&emsp;前后端分离版本项目地址：[EasyWeb](https://gitee.com/whvse/EasyWeb)，分离版本适用于前端人员充足，
能做到分离开发、分离部署的公司。

## 更新日志

- **2018-07-22 增加树形表格**

    - 增加树形表格

- **2018-07-18 前后台都进行完善**

    - 重新加入q.js，改为路由单页面版，路由比iframe可实现的功能更多，iframe会带来很多问题。
    
    - shiro加入处理ajax请求，如果是ajax请求登录过期和没有权限返回json数据，并且admin.js封装req会自动处理此类json。
    - 依然保留iframe功能，可自由选择页面使用iframe还是路由，参考演示地址的druid监控，使用iframe方式。
    - 与分离版不同的是，这一版针对不分离做了很多改进的地方，使用起来功能更分离版一样强大，用法却简单很多。

- **2018-07-17 发布纯iframe版**

    - 去掉mvvm，去掉q.js，纯iframe版，在gitee的release中可以下载此版本。
    - 此版本为过渡版本，最终由于iframe的一些问题，比如弹窗遮罩层不能全屏、子页面中不能跳转tab等放弃采用iframe版


## 使用技术

### 不分离版本
描述 | 框架 
:---|:---
核心框架 | Spring、Spring Boot、Spring MVC
持久层 | MyBatis、MyBatis-Plus、Druid
权限框架 | Shiro、jjwt（用于app接口）
模板引擎 | [beetl](http://ibeetl.com/guide)

### 前端
描述 | 框架 
:---|:---
核心框架 | [Layui](http://www.layui.com/)、[jQuery](http://jquery.cuishifeng.cn/)
路由框架 | [Q.js](https://github.com/itorr/q.js) (纯js轻量级路由框架)
mvvm框架 | [pandyle.js](https://gitee.com/pandarrr/pandyle) (专为jquery编写的mvvm)
主要特色 | 单页面 / 响应式 / 简约 / 极易上手

> 开发工具为IDEA，数据库文件存放在项目的`src/main/resources/static`目录下。


## 项目结构

### 后台接构
```text
|-src
   |-main
      |-java
      |    |-com.wf.ew
      |              |-common     // 核心模块
      |              |     |-config      // 存放SpringBoot配置类
      |              |     |     |-MyBatisPlusConfig.java      // MyBatisPlus配置
      |              |     |     |-SwaggerConfig.java          // Swagger2配置
      |              |     |
      |              |     |-exception   // 自定义异常类，统一异常处理器
      |              |     |-shiro       // shiro配置模块
      |              |     |-oauth       // app接口权限配置模块
      |              |     |-utils       // 工具类包
      |              |     |-BaseController.java    // controller基类
      |              |     |-JsonResult.java        // 结果集封装
      |              |     |-PageResult.java        // 分页结果集封装
      |              |
      |              |-system      // 系统管理模块
      |              |-api         // app接口模块
      |              |-xxxxxx      // 其他业务模块
      |              |
      |              |-EasyWebApplication.java     // SpringBoot启动类
      |              
      |-resources
            |-mapper     // mapper文件
            |    |-system
            |
            |-static     // css、js、图片文件
            |-templates  // html文件
            |
            |-application.properties  // 配置文件
```


## 快速上手
### 后台快速上手

**如何添加自己的业务代码：**

&emsp;&emsp;跟common、system同级建一个包，名字为你的业务模块名称，然后下面依次建
controller、dao、model、service、service.impl等包，然后再resource/mapper下面也
建一个模块文件夹，里面放mapper的xml文件。

- `mapper.xml` 扫描路径是`classpath:mapper/**/*Mapper.xml`
- `druid` 的service扫描路径是 `com.wf.ew.*.service.*`
- `mapper` 的扫描路径是 `com.wf.ew.*.dao` ，<br>
   位于 `common/config/MybatisPlusConfig.java`


### 前端快速上手

&emsp;前端页面详细开发文档：[https://whvse.gitee.io/easywebpage/docs/](https://whvse.gitee.io/easywebpage/docs/)


## 项目截图

![登录](https://ws1.sinaimg.cn/large/006a7GCKgy1fstc7m6zggj30vq0jn0vb.jpg) 

![用户管理](https://ws1.sinaimg.cn/large/006a7GCKgy1fstc7ldhlbj315y0q6415.jpg)

![角色管理](https://ws1.sinaimg.cn/large/006a7GCKgy1fstc7lye0jj30vq0i8gmv.jpg)

![登录日志](https://ws1.sinaimg.cn/large/006a7GCKgy1fstc7logerj30vq0i8js2.jpg)
