1. 远程服务器配置说明
{
  "port": 4567 //配置服务器端口
}
2. 本地服务器配置说明
{
  "lsName": "Printer",       //本地服务器名称，必须有，否则无法找到该服务器
  "rsIp": "127.0.0.1",      //远程服务器地址
  "rsPort": 4567,           //远程服务器端口
  "isExecutable": true,     //是否是可以执行，如果为true，直接调用Runtime.getRuntime().exec()执行本地客户端传输过来的数据
  "ifSave":true,            //是否保存文件，true则根据文件名和文件后缀保存，如果文件名和后缀不存在，则随机生成名字保存
  "fileName":"aaa",         //ifSave为false时不生效，为空时随机生成文件名      
  "fileSuffix":"png",        //ifSave为false时不生效，为空时不添加后缀
  "exeCommandWithMsg": "java Print",  //把客户端传过来的数据作为参数加到命令后面，如果此项为空则忽略该选项
  "exeCommandDirectly": "java Print", //执行命令，为空时，忽略该选项
  "exeCommandWithFile": "java Print" //把文件名作为参数加到命令后面，如果ifSave为false,则效果和exeCommandDirectly一样
}
3. 客户端开发注意事项
  * 需要先发送服务端名称到服务器，然后在发送具体传输的数据
  * 数据前面和结尾需要加上标记字节，以便分析文件开头和结尾
4. 其他相关说明
  * PathUtils.class.getResources("")获取到的位置为该类(.class)所在的位置
  * PathUtils.class.getResources("/")获取到的位置为生成的字节码文件的根目录
  * 在java中直接使用使用文件为项目的根目录，如File file = File("aaa.png");指的是项目根目录的位置