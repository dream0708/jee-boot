<p align="center" style="size: 25px">
  Spring-Boot-JEE-Api 脚手架
</p>

<p align="center">  
  <a href="https://github.com/spring-projects/spring-boot">
    <img alt="spring boot version" src="https://img.shields.io/badge/spring%20boot-2.1.5.RELEASE-brightgreen">
  </a>
</p>

## 简介

jee-boot-api 是一个基于`SpringBoot`，快速构建`RESTful API`工程的脚手架， 能够满足90%公司90%的需求，支持多数据源配置、分布式事务、多Redis配置、分布式调度、分布式缓存配置等；快速生成各模块的基础代码，极大的提升了开发效率，使团队代码风格保持统一。

## 特征
- 使用 Druid Spring Boot Starter 集成 Druid 数据库连接池与监控
- 多Redis集成配置（支持Jedis、Lettuce)
- 集成 Spring Boot 常用开发组件集
- 集成 Mybatis Plus、实现单表业务零SQL（多数据源配置）
- 集成 Atomikos 支持分布式事务、以及支持多数据源配置（待集成）
- 集成Jetcache分布式缓存(最强大的缓存工具，没有之一)
- 完备的请求日志体系 （按需打印， 定位排查、 敏感词过滤）
- 基于CompletableFuture异步编码封装
- 统一异常处理 (完美支持异步，目前没有发现有类似的）
- 统一响应结果封装(最佳的封装，没有之一)
- 基于Fastjson升级报文返回（灵活处理日期、浮点数、字典类型等)
- 自实现的会话管理 （最简单最使用最灵活  尤其适合前后分离）
- 自实现RBAC权限管理框架(Shiro、Security配置太复杂，学习成本太高）
- 分布式任务调度(满足80%业务需求)
- 文档编写工具apidoc （ 最佳前后交互文档 没有之一 Swagger效率太低，有代码入侵)
- 强大参数校验功能 代码美观（NotNull , NotBlank , Max , Min , Size 等）
- 日志组件log4j2最全样例
## 支持我 给我动力
<table>
    <tr>
        <td><img src="https://gitee.com/dream0708/jee-boot/blob/master/doc/pay.jpg"/></td>
        <td><img src="https://gitee.com/dream0708/jee-boot/blob/master/doc/qq.jpg"/></td>
    </tr>
</table> 

### 项目环境 
中间件 | 版本 |  备注
-|-|-
JDK | 1.8+ | JDK1.8及以上 |
MySQL | 5.6+ | 5.6及以上，如果使用Druid的分布式驱动，暂不支持8.0+ |

### 技术选型
技术 | 版本 |  备注
-|-|-
Spring Boot | 2.1.3.RELEASE | 最新发布稳定版 |
Mybatis | 3.5.2 | 持久层框架 |
Mybatis Plus | 3.2.0 | Mybatis增强框架 |
Jta-Atomikos | 2.1.9.RELEASE | 分布式事务管理（下个版本推出) |
Alibaba Druid | 1.1.20 | 数据源 |
FastJson | 1.2.70 | JSON处理工具集 |
commons-lang3 | 3.9 | 常用工具包 |
Jetcache | 2.6.0.M3 | 分布式缓存工具 |
lombok | 1.18.10 | 注解生成Java Bean等工具 |
apidoc | 0.25.0 | 文档编写工具 |
## 快速开始
1. 克隆项目:`git clone https://gitee.com/dream0708/jee-boot.git <Br/>
      git clone https://github.com/dream0708/jee-boot.git `
2. 构建数据库，多数据源可以先创建两个数据库(数据库加本人联系获取）
3. 修改module jee-boot-api本地环境配置文件`application-local.application`
4. 进入项目目录 jee-boot-api目录 运行makedoc.bat (前提是要安装好apidoc ，出现汉化问题可以联系我 私有秘方)
5. 运行App
6. 访问接口文档 http://localhost:8092/jee/apidoc/index.html

## 文档截图
![Alt text](https://gitee.com/dream0708/jee-boot/blob/master/doc/1.jpg)
![Alt text](https://gitee.com/dream0708/jee-boot/blob/master/doc/2.jpg)
![Alt text](https://gitee.com/dream0708/jee-boot/blob/master/doc/3.jpg)
![Alt text](https://gitee.com/dream0708/jee-boot/blob/master/doc/4.jpg)
![Alt text](https://gitee.com/dream0708/jee-boot/blob/master/doc/5.jpg)

## 开发建议
- 开发规范可以参考阿里巴巴Java开发手册（[最新版下载](https://github.com/alibaba/p3c))
- 公司内部可以把统一异常处理、统一响应结果、鉴权工具类等公共模块封装打包；项目需要用到时，引入工具包。
- 代码生成建议使用IDEA MybatisCodeHelperNew插件

## 相关技术文档
- Spring Boot（[查看官方文档](https://spring.io/projects/spring-boot/)）
- MyBatis（[查看官方中文文档](http://www.mybatis.org/mybatis-3/zh/index.html)）
- Mybatis Plus（[查看官方文档](https://mybatis.plus/guide/)）
- Druid Spring Boot Starter（[查看官方中文文档](https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter/)）
- FastJson（[查看官方中文文档](https://github.com/alibaba/fastjson/wiki/Quick-Start-CN)）
- Apidoc（[查看官方文档](https://apidocjs.com/)）


## License
开源分享，感谢支持 给我动力 GitHub  [Star](https://github.com/dream0708/jee-boot/stargazers) & [Fork](https://github.com/dream0708/jee-boot/network/members) <Br/>
开源分享，感谢支持 给我动力 Gitee  [Star](https://gitee.com/dream0708/jee-boot/stargazers) & [Fork](https://gitee.com/dream0708/jee-boot/members) <Br/>
