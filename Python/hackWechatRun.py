# !/usr/bin/env python3
# -*- coding:utf-8 -*-

__author__ = "Evilmass"
__version__ = "1.1"
__email__ = "evilmass1ve@gmail.com"

import requests
import datetime
import json
import time
from hashlib import md5


users = {
    # 'uid1': 'pc_value1',
    # 'uid2': 'pc_value2',
    # 多用户以此类推
}
data ={}


def formatDate():  # 生成时间戳
    nowtime = datetime.datetime.now()
    date = time.strftime('%Y-%m-%d')
    strtemp_date = date + ' 00:00:00'
    ledongli_date = time.strptime(strtemp_date, '%Y-%m-%d %H:%M:%S')
    finaldate = int(time.mktime(ledongli_date))
    return finaldate


def generateKey():  # 生成校验值
    Str = 'ldl_pro@' + str(formatDate()) + '#' + str(steps) + '$' + str(data['distance']) + '^' + str(data['calories']) + '&' + str(data['duration'])
    key = md5(bytes(Str.encode('utf-8'))).hexdigest()
    return key


def upload(uid, pc):  # POST
    url = 'http://walk.ledongli.cn/rest/dailystats/upload/v3?uid=' + str(uid)
    data['calories'] = data['duration'] = data['distance'] = data['steps'] = steps
    data['date'] = formatDate()
    data['key'] = generateKey()
    playload = {
        'pc': pc,
        'stats': json.dumps([data])
    }
    rsp = requests.request("POST", url=url, data=playload)
    return rsp.text


if __name__ == '__main__':
    steps = int(input(u'请输入步数：'))  # 上限为98800
    for uid, pc in users.items():
        print(upload(uid, pc)) #返回 {"ret":[],"errorCode":0} 代表成功
    