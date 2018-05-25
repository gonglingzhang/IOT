#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Mar 17 14:34:22 2018

@author: glz
判断传感器获取的数据是否正常，不正常则发送邮件
"""

import sys
import json
sys.path.append("MailNotification")
sys.path.append("MysqlOperation")
from MysqlOperation import QueryData,LogData
from MailNotification import MailNotification
username = sys.argv[1]
try:
    res_ph = QueryData(username,'ph','ph')
    res_oxy = QueryData(username,'oxygen_rate','oxygen_rate')
    res_tem = QueryData(username,'temperature','temperature')
    res_hum = QueryData(username,'humidity','humidity')
except:
    print('nodata')
def IsWarning(lis,parameter,min,max):
    for i in lis:
        if not min < i[0] < max:
            try:
                msg = "%s,%s 数据异常，请留意. 正常值是： %.2f ~ %.2f. 但是现在的值是： %.2f."%(i[1],parameter,min,max,i[0])
                print(msg)
                LogData(username,msg)
                with MailNotification() as mail:
                    mail.send_notification(msg)
            except:
                print('邮件发送失败')
      
if __name__ == "__main__":
    with open('config.json','r') as f:
        config = json.loads(f.read())
    try:
        IsWarning(res_tem,'temperature',float(config['temperature_min']),float(config['temperature_max']))
        IsWarning(res_hum,'humidity',float(config['humidity_min']),float(config['humidity_max']))
        IsWarning(res_ph,'ph',float(config['ph_min']),float(config['ph_max']))
    except:
        print('main.py')