# Android-MagicAdapter
[[点击查看中文版]](https://www.jianshu.com/p/8be99ce68780)<p>
It's a super lightweight library that RecycleView adapter can be used quickly.<p>
![show.gif](https://upload-images.jianshu.io/upload_images/13146984-80c23921483ca64c.gif?imageMogr2/auto-orient/strip)
## Feature
* Based on Kotlin
* Based on Databinding
* No longer need to maintain Adapter, there is only one MagicAdapter globally
* No longer need to maintain ViewHolder
* Support multiple item view types
* Provide some basic callbacks
* Easily expand headers and footers
* Easily expand RecycleView animation and events
* No reflection, high efficiency
* Super lightweight, less than 34 KB
## Setup
1.Add jcenter repository to root's build.gradle
```gradle
allprojects {
    repositories {
        ...
        jcenter()
    }
}
```
2.Turn on databinding in app's build.gradle
```gradle
android {
    ...
    dataBinding {
        enabled = true
    }
}
```
3.Add dependencies in app's build.gradle (please use the latest version)
```gradle
dependencies {
    ...
    implementation 'com.xuyefeng:magicadapter:1.0.0'
}
```
## Usage
1.Create a RecycleView item layout file image_item_layout.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>

        <variable
            name="item"
            type="com.blue.helloadapter.ImageItem" />
    </data>

    <RelativeLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="10dp">

        <ImageView
            android:id="@+id/iv"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_centerInParent="true" />
    </RelativeLayout>
</layout>
```
Please note that the name of the XXXItem declared here must be `item`
****
2.Create a XXXItem corresponding to the item layout file, such ad ImageItem
```kotlin
class ImageItem(
        val resId: Int
) : BaseItem() {

    override fun getLayout(): Int = R.layout.image_item_layout

    override fun onBinding(binding: ViewDataBinding) {
        (binding as ImageItemLayoutBinding).apply {
            iv.setImageResource(resId)
            iv.setOnClickListener {
                Toast.makeText(iv.context, "click image on ${getViewHolder()?.adapterPosition}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
```
Here is a description of the BaseItem:
* Can get the Adapter of RecycleView binding
* Can get the ViewHolder corresponding to item
* Can get the Databinding corresponding to item
****
3.Create a TextItem and a ButtonItem in the same way, and then you can add data.
```kotlin
val adapter = MagicAdapter()
adapter.addItem(ImageItem(R.drawable.s1))
adapter.addItem(TextItem())
adapter.addItem(ButtonItem())
recyclerView.adapter = adapter
```
****
4.If you need a callback, you can do it like this
```kotlin
adapter.onItemClickListener = object : OnItemClickListener {
    override fun onItemClick(holder: ItemViewHolder)
          val position = holder.adapterPosition
          val item = holder.item
          val binding = holder.binding
          ...
    }
}
```
It’s over here, adding adapters to a RecycleView is as simple as that.
## License
[Apache-2.0](https://opensource.org/licenses/Apache-2.0)
