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

# RabbitMQ的一些关键词与解释

## RabbitMQ Server

也叫broker server，它不是运送食物的卡车，而是一种传输服务。原话是RabbitMQisn’t a food truck, it’s a delivery service. 他的角色就是维护一条从Producer到Consumer的路线，保证数据能够按照指定的方式进行传输。但是这个保证也不是100%的保证，但是对于普通的应用来说这已经足够了。当然对于商业系统来说，可以再做一层数据一致性的guard，就可以彻底保证系统的一致性了。

## Producer 
数据的发送方。
> Createmessages and publish (send) them to a broker server (RabbitMQ).

一个`Message`有两个部分：`payload`（有效载荷）和`label`（标签）。
`payload`顾名思义就是传输的数据。
`label`是`exchange`的名字或者说是一个`tag`，它描述了`payload`，而且`RabbitMQ`也是通过这个`label`来决定把这个`Message`发给哪个`Consumer`。
AMQP仅仅描述了`label`，而`RabbitMQ`决定了如何使用这个`label`的规则。

## Consumer
数据的接收方。
> Consumersattach to a broker server (RabbitMQ) and subscribe to a queue。

把queue比作是一个有名字的邮箱。当有`Message`到达某个邮箱后，`RabbitMQ`把它发送给它的某个订阅者即`Consumer`。
当然可能会把同一个`Message`发送给很多的`Consumer`。
在这个`Message`中，只有`payload`，`label`已经被删掉了。
对于`Consumer`来说，它是不知道谁发送的这个信息的。
就是协议本身不支持。但是当然了如果`Producer`发送的`payload`包含了`Producer`的信息就另当别论了。
对于一个数据从`Producer`到`Consumer`的正确传递，还有三个概念需要明确：`exchanges`, `queues` and `bindings`。
- Exchanges are where producers publish their messages.
- Queues are where the messages end up and are received by consumers
- Bindings are how the messages get routed from the exchange to particular queues.

> Publisher —> exchange — bindings — queue —> Consumer

## Connection

就是一个TCP的连接。`Producer`和`Consumer`都是通过`TCP`连接到`RabbitMQ Server`的。以后我们可以看到，程序的起始处就是建立这个TCP连接。

## Channels

虚拟连接。它建立在上述的TCP连接中。数据流动都是在`Channel`中进行的。也就是说，一般情况是程序起始建立TCP连接，第二步就是建立这个`Channel`。

> 那么，为什么使用Channel，而不是直接使用TCP连接？
> 对于OS来说，建立和关闭TCP连接是有代价的，频繁的建立关闭TCP连接对于系统的性能有很大的影响，而且TCP的连接数也有限制，这也限制了系统处理高并发的能力。
> 但是，在TCP连接中建立Channel是没有上述代价的。对于Producer或者Consumer来说，可以并发的使用多个Channel进行Publish或者Receive。
> 有实验表明，1s的数据可以Publish 10K的数据包。当然对于不同的硬件环境，不同的数据包大小这个数据肯定不一样，但是我只想说明，对于普通的Consumer或者Producer来说，这已经足够了。
> 如果不够用，你考虑的应该是如何细化split你的设计。


# 参考文章

- [RabbitMQ: Spring AMQP 快速入门](https://www.jianshu.com/p/935746eb37b2)
- [RabbitMQ: RabbitMQ 与 AMQP路由](https://www.jianshu.com/p/65906181393e)
- [RabbitMQ: RabbitAdmin 与 RabbitTemplate 使用](https://www.jianshu.com/p/e647758a7c50)
- [RabbitMQ: 消息发送确认 与 消息接收确认（ACK）](https://www.jianshu.com/p/2c5eebfd0e95)
- [RabbitMQ: 学习笔记](https://blog.csdn.net/doc_sgl/article/details/50615496)
- [RabbitMQ: Tutorials](https://www.rabbitmq.com/getstarted.html)

