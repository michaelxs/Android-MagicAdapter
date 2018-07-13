# Android-MagicAdapter
With the help of databinding to achieve the simplest universal adapter.<p>
在 Android 开发中，使用 shape 标签可以很方便的帮我们构建资源文件，跟传统的 png 图片相比：
* shape 标签可以帮助我们有效减小 apk 安装包大小
* 在不同手机的适配上面，shape 标签也表现的更加优秀   

关于 shape 标签如何使用，在网上一搜一大把，笔者就不在这里赘述了，今天我们要讨论的是 shape 标签泛滥成灾以后带来的后果。这里先给大家看一个维护超过了 5 年的项目的 drawable 目录
![image.png](https://upload-images.jianshu.io/upload_images/13146984-4f75cc68d837278c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
请注意右侧标红的滚动条，有没有感觉很酸爽，在这个目录下的文件现在已经超过了 500 个，并且还在不停的增加。我们分析这个目录下的 xml 构成，发现主要由两种类型构成：selector 和 shape。selector 这里略过不提，重点关注 shape，发现 shape 文件已经超过了 200 个并且还在不停的增加。我们再带着好奇的心态随便点开几个 shape 看一看
```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">

    <solid android:color="#66000000" />
    <corners android:radius="15dp" />

</shape>
```
```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <gradient
        android:startColor="#0f000000"
        android:endColor="#00000000"
        android:angle="270"
        />
</shape>
```
```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android" >

    <solid android:color="#fbfbfd" />
    <stroke
        android:width="1px"
        android:color="#dad9de" />
    
    <corners
        android:radius="10dp" />

</shape>
```
