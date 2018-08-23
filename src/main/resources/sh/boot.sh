#!/bin/bash

#获取程序名称
APP_SERVER=`echo $(basename $0)|awk -F '.' '{print $1}'`

#jar包存在地址 
APP_DIR=/usr/local/haoy/boot/jar

#日志输出地址 
APP_LOG_DIR=/usr/local/haoy/boot/log
#日志文件名
APP_LOG_NAME="boot-"$(date "+%Y-%m-%d")".log"

#初始化全局变量tradePortalPID,用于标识交易前置系统的PID,0表示未启动  
tradePortalPID=0

#查询日志目录，若不存在则创建  
if [ ! -d "$APP_LOG_DIR" ]; then
    echo "${APP_LOG_DIR}文件夹不存在，准备创建!"  
    mkdir -p  "$APP_LOG_DIR"
    echo "${APP_LOG_DIR}文件夹创建成功!"  
fi

#获取程序运行的PID
getTradeProtalPID(){
    javaps=`ps aux|grep java|grep $APP_SERVER|grep -v grep | awk '{print $2}'`
    if [ -n "$javaps" ]; then
        tradePortalPID=$javaps
    else
        tradePortalPID=0
    fi
} 

#启动程序
start(){  
    getTradeProtalPID  
    echo "================================================================================================================"  
    if [ $tradePortalPID -ne 0 ]; then  
        echo "$APP_SERVER already started(PID=$tradePortalPID)"  
        echo "================================================================================================================"  
    else  

        #日志输出到指定文件
        java -jar $APP_DIR/integration-0.0.1.jar >>$APP_LOG_DIR/$APP_LOG_NAME &
		
        getTradeProtalPID  
        if [ $tradePortalPID -ne 0 ]; then  
            echo "Strat $APP_SERVER successfully (PID=$tradePortalPID)"  
            echo "================================================================================================================"  
        else  
            echo "Strat $APP_SERVER failed..."  
            echo "================================================================================================================"  
        fi  
    fi  
}

#终止程序
stop(){
    getTradeProtalPID  
    echo "================================================================================================================"  
    if [ $tradePortalPID -ne 0 ]; then  
        echo -n "Stop $APP_SERVER(PID=$tradePortalPID)..."  
        kill -9 $tradePortalPID  
        if [ $? -eq 0 ]; then  
            echo "Stop $APP_SERVER successfully (PID=$tradePortalPID)"  
            echo "================================================================================================================"  
        else  
            echo "Stop $APP_SERVER failed..."  
            echo "================================================================================================================"  
        fi  
        getTradeProtalPID  
        if [ $tradePortalPID -ne 0 ]; then  
            shutdown  
        fi  
    else  
        echo "$APP_SERVER is not running"  
        echo "================================================================================================================"  
    fi  
}

#(函数)检查程序运行状态 
status(){  
    getTradeProtalPID  
    echo "================================================================================================================"  
    if [ $tradePortalPID -ne 0 ]; then  
        echo "$APP_SERVER is running (PID=$tradePortalPID)"  
        echo "================================================================================================================"  
    else  
        echo "$APP_SERVER is not running"  
        echo "================================================================================================================"  
    fi  
}

# 这里是输入运行指令,一般都是 ./boot.sh start,$1是用来接收输入的指令   
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


