# !/usr/bin/env/python3
# -*- coding:utf-8 -*-

import uuid
import os
import requests
from hashlib import md5
from time import sleep

users = {}


def createPC():
    data = {}
    UUID = uuid.uuid4()  # Ramdom UUID => Random PC
    pc = "an" + md5(bytes(str(UUID).encode('utf-8'))).hexdigest()
    return pc

def getAuthCode(phone_number):  # 若返回：{"ret":"60秒内请勿重复发送","errorCode":-300002}，则等待下一个验证码
    url = 'https://walk.ledongli.cn/rest/users/auth_code/v3';
    login = requests.request('post', url, data={'phone': phone_number}, verify=False)
    if "{\"ret\":\"60秒内请勿重复发送\",\"errorCode\":-300002}" in login.text:
        print(u'60秒内请勿重复发送, 请稍后再试')
        exit(0)
    return login.text

    
def mobileLogin(phone_number):
    url = 'https://walk.ledongli.cn/rest/users/login/v3?uid=70589504'
    playload = {
        'phone': str(phone_number),
        'verify_code': str(users['auth_code']),
        'pc': users['pc'],
        'is_old_user': '0',
        'type': '2'
    }
    
    login = requests.request('post', url, data=playload, verify=False)
    return login.text
        

if __name__ == '__main__':
    phone_number = int(input('Please input phone number: '))
    users['pc'] = createPC()
    getAuthCode(phone_number)
    print('Wait...')
    sleep(3)
    users['auth_code'] = input('Please input auth_code: ')
    mobileLogin(phone_number)
    print('Now, your valuable PC is: ', users['pc'])
