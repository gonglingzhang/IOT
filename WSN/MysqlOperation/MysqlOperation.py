#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Mar 17 13:14:06 2018

@author: glz
数据库的一些操作
"""

import pymysql
import json
with open('config.json','r') as f:
    config = json.loads(f.read())
position = config['position']
def StoreData(username,table,parameter,device,para_value):
    conn = pymysql.connect(host='localhost', user = 'root', password = 'root', database = 'SensorData')
    cursor = conn.cursor() 
    sql = 'INSERT INTO '+table+'(username,device,'+parameter+',position) VALUES (%s,%s,%s,'+position+')'
    cursor.execute(sql,(username,device,para_value))
    conn.commit()
    cursor.close()
    conn.close()
def QueryData(username,table,parameter):
    conn = pymysql.connect(host='localhost',user = 'root', password = 'root', database = 'SensorData')
    cursor = conn.cursor()
    sql = 'SELECT '+ parameter +',device FROM '+ table +' WHERE position='+position+' AND username = '+ "'"+username +"'"+' ORDER BY time DESC LIMIT 4' #query newest data from database
    cursor.execute(sql)
    res = cursor.fetchall()
    cursor.close()
    conn.close()
    return res

def LogData(username,content):
    conn = pymysql.connect(host='localhost', user = 'root', password = 'root', database = 'SensorData')
    cursor = conn.cursor() 
    sql = 'INSERT INTO logging(username,log) VALUES (%s,%s)'
    cursor.execute(sql,(username,content))
    conn.commit()
    cursor.close()
    conn.close()
if __name__ == "__main__":
    print('MysqlOperation.py')
