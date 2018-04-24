## commonnetview
网络请求时候的加载View,以及加载错误和暂无数据View

## Smaple
```
    <com.zhudi.commonnetview.NetView
        android:id="@+id/netView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:emptyContentTextColor="#ff3d78c8"
        app:emptyImageHeight="200dip"
        app:emptyImageWidth="200dip"
        app:emptyTitleTextColor="#ff818181"
        app:errorContentTextColor="#ff3d78c8"
        app:errorTitleTextColor="#ff818181">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Hello World!" />
    </com.zhudi.commonnetview.NetView>

    private NetView netView;

    netView.showLoading();  //加载中
    netView.showContent();  //显示内容
    //错误显示
    netView.showError(0, "网络错误", "", new View.OnClickListener() {
        @Override
        public void onClick(View v) {
             Toast.makeText(MainActivity.this,"点击了",Toast.LENGTH_LONG).show();
        }
    });
    //空显示
    netView.showEmpty(0,"暂无数据","",null);

    //自定义布局
    loadingLayout //自定义加载过程界面
    emptyLayout   //自定义空界面
    errorLayout   //自定义异常界面

    //自定义布局可以设置以下属性
    emptyUseClickId    //空界面的可点击View Id
    emptyUseImageId    //空界面的图片Id
    emptyUseTitleId    //空界面的title Id
    emptyUseMessageId  //空界面的content Id

    errorUseClickId    //错误界面的可点击View Id
    errorUseImageId    //错误界面的图片Id
    errorUseTitleId    //错误界面的title Id
    errorUseMessageId  //错误界面的content Id

    //默认布局可以设置以下属性
    loadingProgressBarWidth  //加载控件的宽
    loadingProgressBarHeight //加载控件的高
    emptyImageWidth          //空界面的图片宽
    emptyImageHeight         //空界面的图片高
    emptyTitleTextSize       //空界面的title字体大小
    emptyContentTextSize     //空界面的content字体大小
    emptyTitleTextColor      //空界面的title字体颜色
    emptyContentTextColor    //空界面的content字体颜色
    errorImageWidth          //错误界面的图片宽
    errorImageHeight         //错误界面的图片高
    errorTitleTextSize       //错误界面的title字体大小
    errorContentTextSize     //错误界面的content字体大小
    errorTitleTextColor      //错误界面的title字体颜色
    errorContentTextColor    //错误界面的content字体颜色
```

## Getting started
```
compile 'com.zhudi.commonnetview:commonnetview:1.0.0'
```

## Build
```
git clone https://github.com/506967734/commonNetView.git
```

## Bugs and Feedback

        For bugs, questions and discussions please use the Github Issues.<br>
        OR .
        Write Email to zhudi19911107@gmail.com

## LICENSE
```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
