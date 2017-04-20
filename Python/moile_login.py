# !/usr/bin/env/python3
# -*- coding:utf-8 -*-

import requests
from hashlib import md5
from time import sleep

requests.packages.urllib3.disable_warnings()  #取消verify=False的SSL Warning
users = {
    'uid': 'your_uid_here',
    'pc': 'an00000000000000000000000000000000'
    }


def getAuthCode(phone_number):
    url = 'https://walk.ledongli.cn/rest/users/auth_code/v3';
    login = requests.request('post', url, data={'phone': phone_number}, verify=False) 
    if "{\"ret\":\"60秒内请勿重复发送\",\"errorCode\":-300002}" in login.text:
        print(u'60秒内请勿重复发送, 请稍后再试')
        exit(0)
    return login.text

    
def mobileLogin(phone_number):
    url = 'https://walk.ledongli.cn/rest/users/login/v3?uid=' + users['uid']
    playload = {
        'phone': str(phone_number),
        'verify_code': str(users['auth_code']),
        'pc': users['pc'],
        'is_old_user': '0',
        'type': '2'
    }
    login = requests.request('post', url, data=playload, verify=False)
    return login.text


def mobileBind(phone_number):  # 更换绑定手机号码
    url = 'https://walk.ledongli.cn/rest/users/bind/v3?uid=' + users['uid']
    playload = {
        'phone': str(phone_number),
        'verify_code': str(users['auth_code']),
        'pc': users['pc'],
        'is_old_user': '0',
        'type': '2'
    }
    bind = requests.request('post', url, data=playload, verify=False)
    return bind.text
        

if __name__ == '__main__':
    phone_number = int(input('Please input phone number:'))
    getAuthCode(phone_number)
    sleep(3)
    users['auth_code'] = input('Please input auth_code:')
    # mobileBind(phone_number)
    print(mobileLogin(phone_number))
    