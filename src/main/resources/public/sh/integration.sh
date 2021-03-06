#!/bin/sh

#获取程序名称(尽量使程序名唯一)
APP_SERVER=`echo $(basename $0)|awk -F '.' '{print $1}'`

#jar包存在地址 
APP_DIR=/usr/local/boot/jar

#日志分割工具安装目录（ yum install cronolog安装，which cronolog查看安装目录）
APP_LOG_CUT=/usr/sbin/cronolog
#日志输出地址 
APP_LOG_DIR=/usr/local/boot/log

#程序端口
APP_PORT=6060

#初始化程序进程
PID=0

#查询日志目录，若不存在则创建  
if [ ! -d "$APP_LOG_DIR" ]; then
    echo "${APP_LOG_DIR}文件夹不存在，准备创建!"  
    mkdir -p  "$APP_LOG_DIR"
    echo "${APP_LOG_DIR}文件夹创建成功!"  
fi

#获取程序运行的PID
getPID(){
    #通过程序名获取PID（shell中会查出子程序PID），再通过程序所在位置提取PID
    pidResult=`ps aux | grep $APP_DIR | grep $APP_SERVER | grep -v grep | awk '{print $2}'`
    
    if [ -n "$pidResult" ]; then
        PID=$pidResult
    else
        PID=0
    fi
}

#启动程序
start(){  
    getPID  
    echo "================================================================================================================"  
    if [ $PID -ne 0 ]; then  
        echo "$APP_SERVER already started(PID=$PID)"  
        echo "================================================================================================================"  
    else  

        #获取目录下的文件并筛选最新文件，grep jar筛选关键字jar
		filename=`ls -lt $APP_DIR | grep jar | head -n 1 |awk '{print $9}'`

        #运行程序并重新命名程序名，按时间分割日志输出到指定文件
        exec -a $APP_SERVER java -jar $APP_DIR/$filename | $APP_LOG_CUT $APP_LOG_DIR/$APP_SERVER.log.%Y-%m-%d-%H:%M  >> /dev/null 2>&1 &

        getPID  
        if [ $PID -ne 0 ]; then  
            echo "Start $APP_SERVER successfully (PID=$PID),execute jar:$filename"
            echo "================================================================================================================"  
        else  
            echo "Start $APP_SERVER failed..."
            echo "================================================================================================================"  
        fi  
    fi  
}

#终止程序
stop(){
    getPID  
    echo "================================================================================================================"  
    if [ $PID -ne 0 ]; then  
        echo -n "Stop $APP_SERVER(PID=$PID)..."  
        kill -9 $PID  
        if [ $? -eq 0 ]; then  
            echo "Stop $APP_SERVER successfully (PID=$PID)"  
            echo "================================================================================================================"  
        else  
            echo "Stop $APP_SERVER failed..."  
            echo "================================================================================================================"  
        fi  
        getPID  
        if [ $PID -ne 0 ]; then  
            shutdown  
        fi  
    else  
        echo "$APP_SERVER is not running"  
        echo "================================================================================================================"  
    fi  
}

#(函数)检查程序运行状态 
status(){  
    getPID  
    echo "================================================================================================================"  
    if [ $PID -ne 0 ]; then  
        echo "$APP_SERVER is running (PID=$PID)"  
        echo "================================================================================================================"  
    else  
        echo "$APP_SERVER is not running"  
        echo "================================================================================================================"  
    fi  
}

#这里是输入运行指令,一般都是 ./boot.sh start,$1是用来接收输入的指令
case "$1" in
  start)
        start
    ;;
  stop)
        stop
    ;;
  restart)
        stop
		start
    ;;
  status)
        status
    ;;
  *)
    echo "Usage $0 {start | stop | restart | status}"  
    ;;
esac
#正常运行程序并退出程序  
exit 0

