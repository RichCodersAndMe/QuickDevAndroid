# QuickDevAndroid
a development framework for rapid development of android apps  
用于快速开发Android应用的基础框架模块  

## 介绍
本项目最大的作用通过 import module 的方式将框架导入 project 并引入 app module 中，即可完成框架搭建。
因此此项目适合新建项目时使用  

* 框架模式：MVVM
* 依托组件：[android-architecture-components](https://developer.android.com/topic/libraries/architecture/index.html)
* 异步管理：RxJava2
* 网络调用：Retrofit2、OkHttp3

框架的整体结构如图  

![QDA-MVVM](https://github.com/SDE603/QuickDevAndroid/tree/master/doc_images/QDF-MVVM.png)  

项目各层级间均有明确职责并严格控制权限，仅向上一级提供接口，屏蔽实现细节，上层绝不进行下层实现。
MVVM的数据绑定通过RxJava2的 CompositeDisposable 实现，仅在Java代码中进行数据绑定，拥有较强的定制能力。

项目集成了应用内经常使用但是很少有通用库集成使用的零碎代码，封装了Notification管理，AlertDialog管理，
SharedPreferences管理，RecyclerView基类等组件。

## LICENSE

    MIT License
    
    Copyright (c) 2017 SDE603
    
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
