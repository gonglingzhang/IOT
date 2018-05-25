#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Thu May 10 14:34:48 2018

@author: glz
访问页面100次 以获取电压稳定时候的ph
"""
import requests
import json
with open('config.json','r') as f:
    config = json.loads(f.read())
url_sen0161_ph = config['sen0161_ph']
url_dht11_humidity = config['dht11_humidity']
url_dht11_temperature = config['dht11_temperature']
url_ds18b20_temperature = config['ds18b20_temperature']
for time in range(0,80):
    print(requests.get(url_dht11_humidity).text)
    print(requests.get(url_dht11_temperature).text)
    print(requests.get(url_ds18b20_temperature).text)
    print(requests.get(url_sen0161_ph).text)
    print('time: ',time,'\n')

