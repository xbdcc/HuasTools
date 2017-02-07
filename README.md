# HuasTools

"文理小助手”改版界面AI全新设计，整体采用嵌套滑动和点击切换效果。结合学校教务系统，数字图书馆，阳光服务里几大对我们学生有用的功能为一体的APP。 因数据都是通过抓取网页信息截取下来，毕业后没怎么维护了，可能有些数据因网页变化显示会有缺陷，但总体不会有太大影响。此APP免费给文理学子使用，现将源码也分享出来，仅供提供便利和学习，请勿拿来作商业用途。
系统总体采用MVP模式，基于Dagger2+RxJava+Retrofit2进行开发，使用了Jpush推送和Bugly等第三方SDK。

## 开发背景
由于手机越来越普及，很多网页上的东西也越来越趋向于移动化。而我们学校旧的教务系统在手机上打开不兼容登不进去。因此查成绩选课和评教等功能都需要在电脑上登录进去完成。而每到期末选课的时候机房总是爆满，有的还要去外面网吧占机子。亲眼目睹从二楼机房排到三楼楼梯壮观的场面。
而超级课程表刚开始火，很多人都不登教务系统而直接用超级课程表查课表了。但是选课和评教功能上面没有。而选课都想在短时间内抢到自己想选的课，晚了的话可能名额都没有了。于是想着要开发一款集教务系统查成绩、查课表、评教、选课四大功能为一体的APP。后续版本重新设计界面加上数字图书馆和阳光服务两大模块里的功能。

## 新版本界面设计图

因此次项目为个人项目，之前版本只是简单的想着实现几个功能用用就行了，后续加上了一些功能后觉得需要重新设计下界面好点，又不好意思麻烦美工的同学设计界面，于是自己学着设计界面。简单学了两个PS,AI的一些基础的知识后选定用AI画界面图。后期学校启用新的教务系统后有些功能有所改变，所以后期实现的版本界面也有小的变动，但大体都是根据这次版本设计的界面图来实现的。

<div  align="center">    
<img src="http://xbdcc.github.io/images/xiaobudian/huastools/1%E9%A6%96%E9%A1%B5.jpg" width="32%" />
<img src="http://xbdcc.github.io/images/xiaobudian/huastools/2%E6%95%99%E5%8A%A1%E7%B3%BB%E7%BB%9F_%E7%99%BB%E5%BD%95.jpg" width="32%" />
<img src="http://xbdcc.github.io/images/xiaobudian/huastools/3%E6%95%99%E5%8A%A1%E7%B3%BB%E7%BB%9F_%E6%88%90%E7%BB%A9.jpg" width="32%" />
</div>

<div  align="center">    
<img src="http://xbdcc.github.io/images/xiaobudian/huastools/4%E6%95%99%E5%8A%A1%E7%B3%BB%E7%BB%9F_%E6%88%90%E7%BB%A9%E8%AF%A6%E6%83%85.jpg" width="32%" />
<img src="http://xbdcc.github.io/images/xiaobudian/huastools/5%E6%95%99%E5%8A%A1%E7%B3%BB%E7%BB%9F_%E8%AF%BE%E8%A1%A8.jpg" width="32%" />
<img src="http://xbdcc.github.io/images/xiaobudian/huastools/6%E6%95%99%E5%8A%A1%E7%B3%BB%E7%BB%9F_%E8%AF%BE%E8%A1%A8%E8%AF%A6%E6%83%85.jpg" width="32%" />
</div>

<div  align="center">    
<img src="http://xbdcc.github.io/images/xiaobudian/huastools/7%E6%95%99%E5%8A%A1%E7%B3%BB%E7%BB%9F_%E9%80%89%E8%AF%BE.jpg" width="32%" />
<img src="http://xbdcc.github.io/images/xiaobudian/huastools/9%E6%95%99%E5%8A%A1%E7%B3%BB%E7%BB%9F_%E9%80%89%E8%AF%BE%E7%9B%91%E6%8E%A7%E7%AE%A1%E7%90%86.jpg" width="32%" />
<img src="http://xbdcc.github.io/images/xiaobudian/huastools/10%E6%95%99%E5%8A%A1%E7%B3%BB%E7%BB%9F_%E9%80%89%E8%AF%BE%E7%9B%91%E6%8E%A7%E8%AE%BE%E7%BD%AE.jpg" width="32%" />
</div>

<div  align="center">    
<img src="http://xbdcc.github.io/images/xiaobudian/huastools/13%E6%95%B0%E5%AD%97%E5%9B%BE%E4%B9%A6%E9%A6%86_%E7%99%BB%E5%BD%95.jpg" width="32%" />
<img src="http://xbdcc.github.io/images/xiaobudian/huastools/14%E6%95%B0%E5%AD%97%E5%9B%BE%E4%B9%A6%E9%A6%86_%E5%A4%96%E7%BD%91%E6%9F%A5%E4%B9%A6.jpg" width="32%" />
<img src="http://xbdcc.github.io/images/xiaobudian/huastools/15%E6%95%B0%E5%AD%97%E5%9B%BE%E4%B9%A6%E9%A6%86_%E5%A4%96%E7%BD%91%E7%BB%AD%E5%80%9F.jpg" width="32%" />
</div>

<div  align="center">    
<img src="http://xbdcc.github.io/images/xiaobudian/huastools/19%E9%98%B3%E5%85%89%E6%9C%8D%E5%8A%A1_%E5%9B%9E%E5%A4%8D%E8%AF%A6%E6%83%85.jpg" width="32%" />
<img src="http://xbdcc.github.io/images/xiaobudian/huastools/20%E9%98%B3%E5%85%89%E6%9C%8D%E5%8A%A1_%E8%AF%B4%E8%AF%9D.jpg" width="32%" />
<img src="http://xbdcc.github.io/images/xiaobudian/huastools/21%E4%B8%AA%E4%BA%BA.jpg" width="32%" />
</div>

这里贴其中的一部分图，全部设计图可点击博文([毕业设计项目文理小助手2.版界面设计](http://xbdcc.github.io/2016/02/17/%E6%AF%95%E4%B8%9A%E8%AE%BE%E8%AE%A1%E9%A1%B9%E7%9B%AE%E6%96%87%E7%90%86%E5%B0%8F%E5%8A%A9%E6%89%8B2-%E7%89%88%E7%95%8C%E9%9D%A2%E8%AE%BE%E8%AE%A1/))查阅。

主要用到的开源库和第三方SDK
---
Dagger2、RxJava、RxAndroid、Retrofit2、Butterknife、Bugly、Jpush、CircleButton、Jsoup

## 版本说明
2015.2 v1.0 新增查课表、查阳光教育服务中心信息。<br>
2015.4 v1.0.1 新增查数字图书馆书籍信息。<br>
2015.8 v1.0.2 新增教务系统登录手机抢课等其他功能。<br>
2015.9.1 v1.1 新增检测更新功能。<br>
2015.9.2 v1.1.2 添加百度云推送功能。<br>
2015.9.3 v1.1.3 添加推送更新版本信息，优化一些bug。<br>
2016.1.8 v2.0 界面全新改版，获取新教务系统的数据。<br>
2016.5.21 v3.0 全新开发，代码重构，改为MVP设计模式开发，重新实现功能，使用流行的开源框架。<br>
2016.6.11 v3.1 新增教务系统评教功能，修复已知bug。<br>
2016.6.21 v3.2 新增教务系统选课功能，修复已知bug。   

## 其他说明
近期看了下APP登录教务系统登录不进去，打开教务系统网站发现现在页面登录功能变了需要加图片验证码了。由于近期比较忙没时间修复这个问题，大家想要看时可以在代码里跳过登录部分，其他功能大致还没变。有什么好的想法也希望提出改进。





License
=======

    Copyright (c) 2017 小不点 <caochang1994@gmail.com>


    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

