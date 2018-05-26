# IOT
### 简介

老师分配的毕业设计，硬件0基础边学习边和同学探讨，断断续续2个多月最终实现，代码有点乱，还没时间重构。 想想自己学硬件时候的苦，留下来这份教程希望能够帮助入门IOT的童鞋,顺便备份一下自己的毕设。
本系统实现的功能是无线获取传感器（Ph，温度和湿度）数据，存入数据库实时将数据显示在网页中供用户查看。由于时间有限，技术有限，功能比较少，但是传感器数据无线获取的问题已经解决了。

### 硬件
开发板使用的是ESP8266 NodeMCU，首先买来的NodeMCU需要进行固件烧写，（引用 https://github.com/nodemcu/nodemcu-flasher）。 软件已经上传：nodemcu-flasher-master，bin文件可以选择nodemcu-flasher-master/Win64/nodemcu-master-18-modules-2018-04-18-05-38-02-float.bin， 一些基本传感器需要用到的库都已经在里面了，还支持WIFI。

在这里我使用的是Arduino软件往NodeMCU中烤录代码。Ardunio环境配置参考http://www.geek-workshop.com/thread-26170-1-1.html。 （直接点击进不去，需要将链接复制粘贴访问） Arduino代码在NodeMCU_code中。一些需要的头文件也在该目录中，记得将这些头文件放到 arduino-1.8.5/libraries/下。
![Image text](https://github.com/gonglingzhang/IOT/blob/master/%E7%A1%AC%E4%BB%B6%E8%BF%9E%E6%8E%A5%E5%9B%BE.png)
由于多个传感器代码写在一个NodeMCU中有些传感器连接会异常，读不出来数据，所以我将3个传感器放到了2个NodeMCU中。连接图同理可以参考上图进行连接。
### 软件
#### 无线组网
我使用的python来获取数据，顺便进行阈值检查，判断是否发邮件，在WSN/config.json中配置一些用户信息，接受邮件方，传感器阈值和NodeMCU的Web服务器地址，
在WSN/MailNotification/config.json中配置发送邮件方。

![Image text](https://github.com/gonglingzhang/IOT/blob/master/web.png)

主要实现的是数据传输，就没有美化页面，凑合着看。。。。
#### 用户层
用户层面使用的JAVA WEB进行的开发，代码在Sensor文件夹下（编辑器是IDEA）部分截图如下所示：
![Image text](https://github.com/gonglingzhang/IOT/blob/master/2018-05-25%2022-58-41%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE.png)
用户登陆进去显示了5处传感器安置点，每个安置点可以放一套传感器，如果传感器数据过高则显示红色，过低则显示蓝色。鼠标放上去会现实最近一次的传感器数据，点击不同的位置可以查看不同位置的各传感器的历史数据，如下图：
![Image text](https://github.com/gonglingzhang/IOT/blob/master/2018-05-25%2022-59-01%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE.png)

用户也可以自己配置传感器阈值（显示红色/蓝色）

![Image text](https://github.com/gonglingzhang/IOT/blob/master/2018-05-25%2022-59-18%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE.png)

这个系统只是达到了最基础的标准，用户层这边有很多地方需要完善：
1.实现用户自定义上传鱼塘图片和传感器定位的自由拖拽；
2.增加专家建议功能；
3.实现历史数据的自由查看（比如间隔多长时间/选取多少条数据）。

### 另外：
如果你愿意使用Arduino作为ESP8266 NodeMCU编辑器的话，那么这个开源项目你一定要知道：
https://github.com/esp8266/Arduino 
它的上一层目录是关于esp8266的很多东东，有兴趣也可以了解一下。
ESPlorer是我推荐你使用的编辑器。
当然聪明机智的你一定不会忘记去官网查看他们的文档。
