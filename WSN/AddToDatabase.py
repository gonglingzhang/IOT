#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Mar 16 10:46:39 2018

@author: glz
从web服务器读取传感器获取的数据，并存入数据库，同时根据获取的数据判断传感器接触是否良好。
"""
import sys
import json
sys.path.append('MailNotification')
sys.path.append('MysqlOperation')
from MailNotification import MailNotification
from MysqlOperation import StoreData,LogData
username = sys.argv[1]
import requests
with open('config.json','r') as f:
    config = json.loads(f.read())
try:
    ds18b20_temperature = requests.get(config['ds18b20_temperature']).text
    dht11_temperature = requests.get(config['dht11_temperature']).text
    dht11_humidity = requests.get(config['dht11_humidity']).text
    sen0161_ph = requests.get(config['sen0161_ph']).text
except:
    err_msg = "无法连接到web服务器，请检查网络连接。"
    with MailNotification() as mail:
        LogData(username,err_msg)
        mail.send_notification(err_msg)
    
def judgedata(sensor,table,parameter):  
    msg = "%s 传感器异常，请检查." % sensor.split(':')[0]
    try:
        if -40.0 < float(sensor.split(':')[1]) < 100.0: #判断传感器接触是否有问题
            StoreData(username,table,parameter,sensor.split(':')[0].split('_')[0],float(sensor.split(':')[1]))
        else:
            with MailNotification() as mail:
                LogData(username,msg)
                StoreData(username,table,parameter,sensor.split(':')[0].split('_')[0],float(sensor.split(':')[1]))
                mail.send_notification(msg)
    except:    
        with MailNotification() as mail:
            LogData(username,msg)
            mail.send_notification(msg)
for i in ds18b20_temperature.split('\n'):
    judgedata(i,'temperature','temperature') 
for i in dht11_temperature.split('\n'):
    judgedata(i,'temperature','temperature') 
for i in dht11_humidity.split('\n'):
    judgedata(i,'humidity','humidity')
judgedata(sen0161_ph.split('\n')[1],'ph','ph') 
