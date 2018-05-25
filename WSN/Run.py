#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Mar 17 10:31:36 2018

@author: glz
"""

import time 
import os
import json
with open('config.json','r') as f:
    config = json.loads(f.read())
username = config['username']
frequency = config['frequency']
os.system('python3 wait.py ')
while 1:
    os.system('python3 AddToDatabase.py ' + username)
    os.system('python3 Main.py ' + username)
    print('完成一次循环')
    time.sleep(frequency)
