# RabbitMQ

## 新建用户

> Linux/Unix上注意用户权限

```cmd
rabbitmqctl  add_user [用户名] [密码]
```

## 用户授权

> 直接授予administrator角色，生产环境请酌情赋予角色

```cmd
set_user_tags [用户名] administrator
```

## 访问权限

> 对于vhost根路径的权限

```cmd
rabbitmqctl  set_permissions -p / [用户名] '.*' '.*' '.*'
```

# 参考文章

- [RabbitMQ ：Spring AMQP 快速入门](https://www.jianshu.com/p/935746eb37b2)
- [RabbitMQ ：RabbitMQ 与 AMQP路由](https://www.jianshu.com/p/65906181393e)
- [RabbitMQ：RabbitAdmin 与 RabbitTemplate 使用](https://www.jianshu.com/p/e647758a7c50)
- [RabbitMQ：消息发送确认 与 消息接收确认（ACK）](https://www.jianshu.com/p/2c5eebfd0e95)

