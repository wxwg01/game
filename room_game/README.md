# JgServer

#### 项目介绍
JgServer，使用java语言开发，基于Netty、Spring、Mybatis和Redis等框架开发的服务端容器，支持Tcp，Socket，WebSocket(SSL)，HTTP(S)。
支持对各种通讯协议进行定制，可以用于开发游戏后端(意为JavaGameServer，最初用于开发Java游戏服务器的一套完整架构)。
项目代码简洁，注释丰富，上手容易，扩展方便。
项目使用框架：Spring，Netty，Mybatis，Redis，junit，log4j等。
已包含一些常规的工具：消息处理(包括json字符串、二进制byte消息)、事件机制、心跳检测、日志管理、定时任务、持久层框架、数据库连接池等。

#### 源码框架
1.common    常用方法
2.domain    实体类
3.event     操作事件处理
4.net       提供socket连接，负责维护客户端连接
    channel     连接相关
    codec       数据解析
    concurrent  抽象类
    croe        
5.server    服务启动与服务注册与调用维护
6.service   业务处理

#### 软件框架
1. 使用框架：Spring，Netty，Mybatis，Redis，junit，log4j等。
2. 编译器：IDEA
3. JDK版本：1.8

#### 配置文件
  1. spring.datasource：数据库配置文件
  2. spring.redis：redis配置

#### 运行前提
1.  - protocolType=TCP
     - protocolType=WEBSOCKET
2. 

#### 安装教程
  1. 在编译器中直接运行com.tiger.Application.main
            
  2. jar启动
  	  1. 打包
          -	使用maven打包，assembly