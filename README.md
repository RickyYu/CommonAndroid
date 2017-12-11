# 1 代码规范
## 1.1     Class 命名
 任何类名都应该使用 UpperCamelCase （驼峰法）命名, 例如:

      AndroidActivity, NetworkHelper, UserFragment, PerActivity

## 1.2 变量名。

 -  变量名：静态常量使用大写字母，使用下划线“_”分词。
 -  非静态成员变量、局部变量、首字母小写，驼峰式分词。
 -  Activity、Fragment、Adapter、View 的子类的成员变量：m开头、驼峰式分词。
 -  方法名：首字母小写，驼峰式分词。

# 1.3 成员变量定义顺序（建议）

- 公用静态常量
- 公用静态变量
- 私用静态常量
- 私用静态变量
- 私有非静态变量

## 1.4 禁止”魔术数”：

    switch(i) {
      case 1:
        mManager.updloadTag();
      break;
        mManager.uploadTabMore();
      case 2:
        // do something
      break;
       // ...
      case 3:
       // ....
      break;
    }

代码中的 case 后的数字即为魔术数，应该使用有明示意义的常量代替。

## 1.5 禁止忽略异常：

  public void setUserId(String id) {
      try {
          mUserId = Integer.parseInt(id);
      } catch (NumberFormatException e) { }
  }


## 1.6 避免UI线程IO：

 不要在主线程执行IO或网络的操作 ( 卡顿工具检查主线程的方法时间设置阀值，超过用子线程去执行 )。

# 1.7 非静态匿名内部类请用WeakReference 方式持有外部对象的引用

    Runnable timeCallback = new Runnable () {
      if(mRef ! = null) {
        Activity activity = mRef.get();
        if(activity != null) {
         // do stuff
        }
      }

    }


# 1.8 长度限制

-  一行代码的长度：不要超过160个字符。
-  一个方法的长度：不要超过：80行。
- 一个文件的长度不超过：1000行。
- 一个方法的参数列表不要超过：7个。
- if 嵌套层次：不要超过4层。
- 无用代码
- 禁止无用的import。
- 禁止import ＊。
- 禁止无用变量。

# 2  Resource 命名

## 2.1 Activity、Fragment、Dialog的布局命名：activity/fragment/dialog+模块，小写字母使用下划线 ”_ ”分词。
 例如：

     activity_main, fragment_user,dialog_login_input.xml


## 2.2 控件布局命名：模块名＋布局类型，小写字母使用下划线 ”_ ”分词。例如：goods_list_item。

## 2.3 图片资源

不再使用的布局资源及时删除。

## 2.2 图片

图片以ic_为前缀，与功能、状态一起命名。例如：

    ic_accept

其他 drawable 文件应该使用相应的前缀，例如：

|类型    |	前缀	    |例如   |
|----- |---- |---- |
|Selector	|selector_	|selector_button_cancel|
|Background	|bg_	|bg_rounded_button|
|Circle	|circle_	|circle_white|
|Progress	|progress_	|progress_circle_purple|
|Divider	|divider_	|divider_grey|

## 2.3 字符串命名

相同英文含义，小写字母使用下划线 ”_ ”分词。

## 2.4 其他
避免使用"px"作为单位。

# 3 发布规范

#### [<APP名称>_<主版本号>.<子版本号>.<日期版本号>_<希腊字母版本号>.apk如：Aux_1.0.0.171211_beta.apk](http://www.baidu.com)

## 3.1     版本命名的规范与原则
#### （1）verisonCode
    是作为一个内部版本号，必须是整型。用来区分版本的新旧，版本号越大，代表距当前越近的发布版本。这个数字不是给用户使用的，是给开发者内部使用的。
 #### （2）versionName 
    是向用户展示的版本号，必须是字符串，这个版本号就是我们可以用来遵循规范的位置，可以作为版本比较的，判断是否需要提示更新、是否需要强制更新的依据。 
## 3.2     希腊字母所代表的版本阶段介绍
* Alpha版：也叫α版，此版本主要是以实现软件功能为主，通常只在软件开发者内部交流，一般而言，该版本软件的Bug较多，需要继续修改。

- Beta版：此版本相对于α版已经有了很大的改进，消除了严重的错误，但还是存在着一些缺陷，需要经过多次测试来进一步消除，此版本主要的修改对像是软件的UI。

- RC版：此版本已经相当成熟了，基本上不存在导致错误的BUG，与即将发行的正式版相差无几，测试人员基本通过的版本。

- Release版：此版本意味着“最终版本”、“上线版本”，在前面版本的一系列测试版之后，终归会有一个正式版本，是最终交付用户使用的一个版本。该版本有时也称为标准版。一般情况下，Release不会以单词形式出现在软件封面上，取而代之的是符号(R)。

## 3.3     版本号修改规则


 #### （1）主版本号
    主版本号(1)：当功能模块有较大的变动，比如增加多个模块或者整体架构发生变化。此版本号由项目决定是否修改。

  #### （2）子版本号
    子版本号（0）：当功能有一定的增加或变化，比如增加了对权限控制、增加自定义视图等功能。此版本号由项目决定是否修改。

  #### （3）阶段版本号
    阶段版本号（0）一般是：Bug 修复或是一些小的变动，要经常发布修订版，时间间隔不限，修复一个严重的bug即可发布一个修订版。此版本号由项目经理决定是否修改。
 #### （4）日期版本号
    日期版本号（170517）：用于记录修改项目的当前日期，每天对项目的修改都需要更改日期版本号。此版本号由开发人员决定是否修改。
 #### （5）希腊字母版本号
    希腊字母版本号（beta）:此版本号用于标注当前版本的软件处于哪个开发阶段，当软件进入到另一个阶段时需要修改此版本号。此版本号由项目决定是否修改。



&copy; 2017

