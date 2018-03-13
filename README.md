SelList 列表选择器
=========

## 效果预览图

![](/gif/listselect.gif)

## 如何使用它

> Step 1.先在 build.gradle(Project:XXXX) 的 repositories 添加::

	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
> Step 2. 然后在 build.gradle(Module:app) 的 dependencies 添加:

	dependencies {
	       compile 'com.github.luocc:listselect:1.0'
	}

    使用方法: 
        FragmentContentActivity.startForResultActivity(this, true, "请选择名称", strList);
    (如果更多需求，可以下载源码然后进行更改)

## 近期更新日志

    v1.0 版本                : 实现了基本功能
    v2.0 版本                : 添加城市列表选择功能 （待实现）

    
