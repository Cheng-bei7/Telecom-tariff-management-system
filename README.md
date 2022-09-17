# 电信资费系统
## 系统概述
本系统为电信资费系统，系统前端由本人完成，后端项目可从此处下载或去https://github.com/Larimari1314/Telecom-tariff-management-system.git地址下载
系统共分为首页、角色管理、管理员管理、资费管理、账务管理、业务管理六个模块
权限介绍：用户登陆后系统将根据自身权限展示部分界面、同时后端会根据用户的请求进行验证，如果验证失败，则拦截用户本次访问，前端则根据对应的消息码跳转页面。
对应权限展示界面不同
![image](https://user-images.githubusercontent.com/104745406/190838092-84b3ebde-7b6c-4a08-bcf5-ea98d23496ae.png)
![image](https://user-images.githubusercontent.com/104745406/190838121-d78a178e-c881-40c6-a987-49a832aebf63.png)
无权限访问跳转无权限界面
![image](https://user-images.githubusercontent.com/104745406/190838157-2a1812b1-1800-4914-bf47-a54ce369d40d.png)
![image](https://user-images.githubusercontent.com/104745406/190838184-265c0b1b-d4ac-42e3-84ef-c3d771bad9b6.png)
其他界面对当前信息进行增改查操作

## 使用技术
前端：vue+element+axios+node.js+jQuery等
后端：springBoot+springMVC+mysql+mybatis+mybatis plus+redis等

后端委托Larimari1314完成，github地址：https://github.com/Larimari1314 ，csdn地址：https://blog.csdn.net/weixin_45677046?spm=1000.2115.3001.5343
