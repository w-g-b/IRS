IntranetRequestSystem
======
内网调用系统（IRS）是通过Socket实现的一套可以通过公网调用局域网内部主机服务（软件）的系统。可分为三个部分：
 * 远程服务器（即公网下的主机，主要用于转发信息）
 * 本地服务器（完成客户端发送的请求任务，比如调用本地主机完成打印任务（打印机连接在本地主机上）
 * 客户端（发送请求给服务器，请求包括需要调用的本地服务器名称以及需要处理的数据） 
 
内网调用系统调用源码实现简单，可以说什么都做不了，只是实现了公网转发数据而已；但也可以说什么都可以做，主要是看本地服务器以及客户端的功能，开发者可以个性化配置本地服务器去执行自己开发的工具来实现任意功能（当然这也需要重新定义客户端发送的数据）

使用
======
1. 运行远程服务端  
![](https://github.com/w-g-b/IntranetRequestSystem/blob/master/images/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20190407165211.png)
2. 运行本地服务端  
![](https://github.com/w-g-b/IntranetRequestSystem/blob/master/images/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20190407165259.png)
3. 打开客户端，发送消息  
客户端开发好之后，对本地服务器进行配置，然后就只需这么三步，实现各种各样的功能

可实现功能
======
_待续_

Todo List
======
* [ ] 本地服务器实现配置文件内的功能  
* [ ] 丰富客户端功能，实现如发送命令，传输文件等交互功能  
* [ ] 本地服务器和客户端之前添加信息传递功能  
* [ ] 部分异常没有处理  
* [ ] 服务器增加处理已断开服务器的功能  
* [ ] 服务器增加交互功能，如可以增删改查目前连接的服务器  

License
=======

    Copyright 2013 Square, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
