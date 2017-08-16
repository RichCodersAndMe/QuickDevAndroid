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

![QDA-MVVM](https://github.com/SDE603/QuickDevAndroid/blob/master/doc_images/QDF-MVVM.png)  

项目各层级间均有明确职责并严格控制权限，仅向上一级提供接口，屏蔽实现细节，上层绝不进行下层实现。
MVVM的数据绑定通过RxJava2的 CompositeDisposable 实现，仅在Java代码中进行数据绑定，拥有较强的定制能力。

项目集成了应用内经常使用但是很少有通用库集成使用的零碎代码，封装了Notification管理，AlertDialog管理，
SharedPreferences管理，RecyclerView基类等组件。

## 项目集成

### 1. 模块导入
作为一个App的骨架，请以导入module的方式集成，将所需分支的Project下载下来后可以看到app和framework两个module
app为framework module中所提供的接口以及基类框架的实现范例，可以作为参考代码使用。
将framework module 导入你的项目，并在app的build.gradle中添加  
```
compile project(':framework')
```
即可完成框架模块引入

### 2. 继承基类
要想使用QuickDevFramework提供的大部分功能，必须继承以下基类  

1. Application类继承QDFApplication，即可使用框架Application类中所提供的全部功能。  
2. app中的Activity继承BaseActivity，注意BaseActivity中实现了对PermissionManager的回调处理，如果子类Activity不继承BaseActivity则有可能无法正确收到权限申请回调。  
3. Fragment继承BaseFragment。  
4. 如果你使用DialogFragment来管理你APP内的dialog，可以让DialogFragment继承BaseDialogFragment。
5. 如果你的项目中有底部弹出Dialog的需求，可以考虑将此类继承BaseBottomDialogFragment，直接实现底部弹出Dialog效果，如果不使用DialogFragment，框架也提供了BottomDialog供业务模块使用
6. 数据管理的DataManager继承BaseDataManger，可以在BaseDataManager中实现通用功能

上述3、4、5的继承主要是为了在框架模块中提供一个基本的业务无关的组件基类，以此向子类提供共有的功能，可以继承也可以不继承，如果这些基类并不符合你的业务功能，请在framework模块中任意删改。
如果需要删改1、2，并使用框架的其它功能，请注意1、2中提供的回调和一些基础实现，并在自己的基类中根据需求实现。

### 3. 继承Style
出于简化开发的目的，我在framework模块的style.xml中添加了基础style并缓存了开发中常见的style配置，如果你需要使用这些配置，请在app的AndroidManifest.xml中直接使用。但是考虑到不同App module可能有不同的配置，推荐的集成方式还是在app的style.xml中配置基础style，继承framework中的style。  
当然了，这个只属于简化开发的代码，集成与否对其它代码没有任何影响  

### 4. app module框架搭建
在完成上述集成后，一个app的业务模块应该已经基本成型了，还剩下的一些搭建工作  

* 网络模块：应用内应该有网络管理类，在此类中使用RetrofitApiBuilder配置Retrofit即可，数据转换等工作请根据需求配置ConvertAdapter等工具类并引入
* 其它框架接入：QuickDevFramework只提供了最基本的数据管理和网络调用框架，以及某些常用代码所必须集成的第三方库，诸如图片管理、数据库ORM、注解以及其它第三方库，请根据需求选择集成到业务 module中还是framework module


完成以上工作后一个app的框架搭建就基本完成


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
