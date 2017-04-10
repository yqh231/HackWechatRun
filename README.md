**具体分析在此，欢迎pull/request -->**[HackWechatRun](https://evilmass.cc/2017/03/30/HackWechatRun/)

# Python

    pip install requests

1. 电脑用Fiddler抓包，安卓可用Packet Capture(免root)
2. 在`hackWechatRun.py`里修改uid和pc_value
3. 输入想刷的步数，Have Fun～

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
- [ ] Java GUI

