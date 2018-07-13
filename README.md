# Android-MagicAdapter
With the help of databinding to achieve the simplest universal adapter.<p>
在 Android 开发中，使用 shape 标签可以很方便的帮我们构建资源文件，跟传统的 png 图片相比：
* shape 标签可以帮助我们有效减小 apk 安装包大小。
* 在不同手机的适配上面，shape 标签也表现的更加优秀。   

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
真的是不看不知道，一看吓一跳。原来我们项目中大量存在的 shape 文件其实都是大同小异的，涉及到最常见的 shape 变化：圆角，描边，填充以及渐变。
进一步分析，我们又发现：
* 有些时候填充颜色是相同的，只不过圆角半径不同，我们就得新增一个 shape 文件。
* 有些时候圆角半径是相同的，只不过填充颜色不同，我们又得新增一个 shape 文件。
* 有些时候两个负责不同业务模块的同事，各自新增一个同样样式的 shape 文件。   

等等一些情况，让我们陷入了 shape 文件的无限新增与维护中。我们不禁要思考，有没有办法可以把这些 shape 统一起来管理呢？xml 书写出来的代码最终不都是会对应一个内存中的对象吗？我们能不能从管理 shape 文件过度到管理一个对象呢？

**Talk is cheap. Show me the code**

第一步，我们需要确定 shape 标签对应的类到底是哪一个？第一反应就是 ShapeDrawable，顾名思义嘛。然后残酷的事实告诉我们其实是 GradientDrawable 这兄弟。浏览 GradientDrawable 类的方法结构，从中我们也找到了setColor()、setCornerRadius()、setStroke() 等目标方法。好吧，不管怎样，先找到正主了。

第二步，继续思考如何来设计这个通用控件，主要从以下几个方面进行了考虑：
* shape 的应用场景有可能是文字标签，也有可能是响应按钮，所以需要文本和按钮两种样式，两者的主要区别在于按钮样式在普通状态下和按压状态下都具有阴影。
* 为了提升用户体验，设计了通用控件的按压动效。针对 5.0 以上的用户开启按压水波纹效果，针对 5.0 以下的用户开启按压变色效果。
结合以上两点，通用控件的实现考虑直接继承 AppCompatButton 进行扩展。
* 具体的业务场景中，通用控件的使用还有可能伴随着 drawable，并且要求 drawable 和文字一起居中显示。其实这个问题本来是不需要单独考虑的，但是 Android 有个坑，在一个按钮控件中设置 drawable 以后，默认是贴着控件边缘显示的，所以这个坑需要单独填。
* 自定义控件属性支持 shape 模式、填充颜色、按压颜色、描边颜色、描边宽度、圆角半径、按压动效是否开启、渐变开始颜色、渐变结束颜色、渐变方向、drawable 方位。

第三步，思路已经梳理清楚了，那就开撸。
```android
class CommonShapeButton @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {
```
这里实现了继承 AppCompatButton 进行扩展，默认样式 defStyleAttr 传递的是 0，那么 CommonShapeButton 的默认表现形式就是文本样式。

如果想要采用按钮样式，则需要先自定义一个按钮样式，原因是系统按钮的样式自带了 minWidth、minHeight 以及 padding，在具体业务中会影响到我们的按钮显示，所以在自定义按钮样式中重置了这三个属性：
```xml
<!-- 自定义按钮样式 -->
<style name="CommonShapeButtonStyle" parent="@style/Widget.AppCompat.Button">
    <item name="android:minWidth">0dp</item>
    <item name="android:minHeight">0dp</item>
    <item name="android:padding">0dp</item>
</style>
```
有了自定义按钮样式，那么想要 CommonShapeButton 采用按钮样式，则采用如下形式：
```xml
<com.blue.view.CommonShapeButton
    style="@style/CommonShapeButtonStyle"
    android:layout_width="300dp"
    android:layout_height="50dp"/>
```
到这里就可以实现简单的文本样式和按钮样式的切换了。
接下来我们就要进行关键的 shape 渲染了：
```android {.line-numbers}
override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    // 初始化normal状态
    with(normalGradientDrawable) {
        // 渐变色
        if (mStartColor != Color.parseColor("#FFFFFF") && mEndColor != Color.parseColor("#FFFFFF")) {
            colors = intArrayOf(mStartColor, mEndColor)
            when (mOrientation) {
                0 -> orientation = GradientDrawable.Orientation.TOP_BOTTOM
                1 -> orientation = GradientDrawable.Orientation.LEFT_RIGHT
            }
        }
        // 填充色
        else {
            setColor(mFillColor)
        }
        when (mShapeMode) {
            0 -> shape = GradientDrawable.RECTANGLE
            1 -> shape = GradientDrawable.OVAL
            2 -> shape = GradientDrawable.LINE
            3 -> shape = GradientDrawable.RING
        }
        cornerRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, mCornerRadius.toFloat(), resources.displayMetrics)
        // 默认的透明边框不绘制,否则会导致没有阴影
        if (mStrokeColor != Color.parseColor("#00000000")) {
            setStroke(mStrokeWidth, mStrokeColor)
        }
    }

    // 是否开启点击动效
    background = if (mActiveEnable) {
        // 5.0以上水波纹效果
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            RippleDrawable(ColorStateList.valueOf(mPressedColor), normalGradientDrawable, null)
        }
        // 5.0以下变色效果
        else {
            // 初始化pressed状态
            with(pressedGradientDrawable) {
                setColor(mPressedColor)
                when (mShapeMode) {
                    0 -> shape = GradientDrawable.RECTANGLE
                    1 -> shape = GradientDrawable.OVAL
                    2 -> shape = GradientDrawable.LINE
                    3 -> shape = GradientDrawable.RING
                }
                cornerRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, mCornerRadius.toFloat(), resources.displayMetrics)
                setStroke(mStrokeWidth, mStrokeColor)
            }

            // 注意此处的add顺序，normal必须在最后一个，否则其他状态无效
            // 设置pressed状态
            stateListDrawable.apply {
                addState(intArrayOf(android.R.attr.state_pressed), pressedGradientDrawable)
                // 设置normal状态
                addState(intArrayOf(), normalGradientDrawable)
            }
        }
    } else {
        normalGradientDrawable
    }
}
```
这里的代码有点长，别着急，我们来慢慢分析一下：
* 首先是选择在 onMeasure 方法中做shape渲染
* 其次对 normarlGradientDrawable 设置当前是渐变色渲染还是填充色渲染，渐变色渲染还需要单独控制渲染的方向
* 然后对 normarlGradientDrawable 设置 shape 模式、圆角以及描边
* 最后对CommonShapeButton设置background。如果没有开启点击特效，则直接返回normarlGradientDrawable。如果开启了点击特效，那么 5.0 以上启用水波纹效果，5.0 以下启用变色效果。在变色效果的设置中同样初始化了 pressedGradientDrawable 的 shape 属性，并且依次添加进了 stateListDrawable 用作背景显示

到这里就可以实现了用自定义属性控制shape渲染显示 CommonShapeButton 的背景了，这里贴上全部的属性：
```xml
<declare-styleable name="CommonShapeButton">
    <attr name="csb_shapeMode" format="enum">
        <enum name="rectangle" value="0" />
        <enum name="oval" value="1" />
        <enum name="line" value="2" />
        <enum name="ring" value="3" />
    </attr>
    <attr name="csb_fillColor" format="color" />
    <attr name="csb_pressedColor" format="color" />
    <attr name="csb_strokeColor" format="color" />
    <attr name="csb_strokeWidth" format="dimension" />
    <attr name="csb_cornerRadius" format="dimension" />
    <attr name="csb_activeEnable" format="boolean" />
    <attr name="csb_drawablePosition" format="enum">
        <enum name="left" value="0" />
        <enum name="top" value="1" />
        <enum name="right" value="2" />
        <enum name="bottom" value="3" />
    </attr>
    <attr name="csb_startColor" format="color" />
    <attr name="csb_endColor" format="color" />
    <attr name="csb_orientation" format="enum">
        <enum name="TOP_BOTTOM" value="0" />
        <enum name="LEFT_RIGHT" value="1" />
    </attr>
</declare-styleable>
```
接下来我们还需要进行最后的工作，解决在一个 button 中添加 drawable 不居中显示的问题
```android
override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
    super.onLayout(changed, left, top, right, bottom)
    // 如果xml中配置了drawable则设置padding让文字移动到边缘与drawable靠在一起
    // button中配置的drawable默认贴着边缘
    if (mDrawablePosition > -1) {
        compoundDrawables?.let {
            val drawable: Drawable? = compoundDrawables[mDrawablePosition]
            drawable?.let {
                // 图片间距
                val drawablePadding = compoundDrawablePadding
                when (mDrawablePosition) {
                // 左右drawable
                    0, 2 -> {
                        // 图片宽度
                        val drawableWidth = it.intrinsicWidth
                        // 获取文字宽度
                        val textWidth = paint.measureText(text.toString())
                        // 内容总宽度
                        contentWidth = textWidth + drawableWidth + drawablePadding
                        val rightPadding = (width - contentWidth).toInt()
                        // 图片和文字全部靠在左侧
                        setPadding(0, 0, rightPadding, 0)
                    }
                // 上下drawable
                    1, 3 -> {
                        // 图片高度
                        val drawableHeight = it.intrinsicHeight
                        // 获取文字高度
                        val fm = paint.fontMetrics
                        // 单行高度
                        val singeLineHeight = Math.ceil(fm.descent.toDouble() - fm.ascent.toDouble()).toFloat()
                        // 总的行间距
                        val totalLineSpaceHeight = (lineCount - 1) * lineSpacingExtra
                        val textHeight = singeLineHeight * lineCount + totalLineSpaceHeight
                        // 内容总高度
                        contentHeight = textHeight + drawableHeight + drawablePadding
                        // 图片和文字全部靠在上侧
                        val bottomPadding = (height - contentHeight).toInt()
                        setPadding(0, 0, 0, bottomPadding)
                    }
                }
            }

        }
    }
    // 内容居中
    gravity = Gravity.CENTER
    // 可点击
    isClickable = true
}
```
我们继续来分析这里的代码：
* 首先渲染的效率，我们选择在 onLayout 方法中计算一些数值
* 其次由于我们是支持上下左右四个方向的 drawable，所以需要在 xml 中指定属性 drawablePosition
* 然后判断是否设置了 drawable 并且 drawable 获取不为空
* 然后判断 drawable 左右方位，则计算图片的宽度和文字的宽度，然后根据内容的总宽度把 button 的内容全部贴左边缘显示
* 最后判断 drawable 在上下方位，则计算图片的高度和文字的高度，然后根据内容的总高度把 button 的内容全部贴上边缘显示

到这里就做好了让 drawable 居中显示的准备工作，我们继续往下走：
```java
override fun onDraw(canvas: Canvas) {
    // 让图片和文字居中
    when {
        contentWidth > 0 && (mDrawablePosition == 0 || mDrawablePosition == 2) -> canvas.translate((width - contentWidth) / 2, 0f)
        contentHeight > 0 && (mDrawablePosition == 1 || mDrawablePosition == 3) -> canvas.translate(0f, (height - contentHeight) / 2)
    }
    super.onDraw(canvas)
}
```
接下来我们就是在 onDraw 方法中，利用在 onLayout 方法中计算的数值，平移 button 的内容，从而实现让 drawable 和文字一起居中显示。

到这里我们就完成了 CommonShapeButton 的全部设计和实现，以下是效果图：
![show.gif](https://upload-images.jianshu.io/upload_images/13146984-5a68b24e619495f5.gif?imageMogr2/auto-orient/strip)
最后再附上：[源代码传送门](https://github.com/michaelxs/CommonShapeButton) 喜欢就 star 一下呗。
