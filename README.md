**具体分析在此，欢迎pull/request -->**[HackWechatRun](https://evilmass.cc/2017/03/30/HackWechatRun/)

# Python

1. 乐动力APP需绑定**微信和手机**

        python mobile_login.py 
    随机生成一个PC后，输入手机号和得到的验证码其生效
        
2. 在`hackWechatRun.py`的users里填入uid和第一步获得的PC

3. 输入步数
    
        python hackWechatRun.py

**多用户**：重复第一步得到PC后填入相应数据到`hackWechatRun.py`即可


# PHP
网页版填入相应数据提交即可

多用户版本添加到定时任务（crontab）可实现每日自动刷步数

    php -f multi-users.php >> log.txt

# Java
需要[okhttp](https://github.com/square/okhttp)

# To Do Lists
- [x] Python实现
- [x] PHP实现
- [x] Java实现
- [x] 添加输入功能
- [x] CSS页面美化（PHP）
- [x] 多用户支持（PHP）
- [x] 解决PC VALUE
- [ ] 找到在线接收短信验证码的API或平台（不绑定手机刷步数）
- [ ] Java GUI

