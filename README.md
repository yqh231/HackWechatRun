**具体分析在此，欢迎pull/request -->**[HackWechatRun](https://evilmass.cc/2017/03/30/HackWechatRun/)

# Python

* 乐动力APP需要**绑定微信设备**
* **pc格式**：an+22位任意数字或字母
* **绑定手机**：`mobile_login.py `注释掉最后一行`mobileLogin()`并调用`mobileBind()`即可
* **登录验证**：输入手机号和验证码使伪造的pc生效

* **多用户**：重复下面第一步填入相应数据到`hackWechatRun.py`即可
1. 在`mobile_login.py `的users里填入uid和你喜欢的pc :)

            python mobile_login.py


2. 在`hackWechatRun.py`的users里填入uid和第一步的pc，输入步数即可
 
            python hackWechatRun.py

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
- [x] 找到在线接收短信验证码的API或平台（不绑定手机刷步数）
- [x] Java GUI