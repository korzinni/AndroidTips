 #[STATUS BAR](https://developer.android.com/reference/android/view/WindowManager.LayoutParams.html)
 
Изначальная задача:

Полупрозрачный статус бар с заданой прозрачностью.

Решения:
1. Добавить флаг
```java
WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
```

Статус бар накладывается на содержимое окна. Но цвет задан системой.
fitsSystemWindows =  true, позволяет избежать перекрытия дочерних элементов статусбаром.

ActionBar статуБаром не перекрывается в любом случае.

2.
```java
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
```
- позволяет получить тот же эффект что и при translucent но с возможностью задавать цвет.
[Источник](https://developer.android.com/training/system-ui/immersive)

Для полностью прозрачного статусбара проще использовать:
```java
WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
```
Для добавления view на окно:

```java
View view = new View(this);
                view.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                WindowManager.LayoutParams params = new WindowManager.LayoutParams(LayoutParams.MATCH_PARENT,50,
                        WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                        PixelFormat.TRANSLUCENT);
                params.gravity = Gravity.RIGHT | Gravity.TOP;
                params.setTitle("Load Average");
                WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
                wm.addView(view, params);
```

Ссылки:
https://stackoverflow.com/questions/27856603/lollipop-draw-behind-statusbar-with-its-color-set-to-transparent
https://developer.android.com/reference/android/view/WindowManager.LayoutParams.html

[#Window insets](https://developer.android.com/reference/android/view/WindowInsets)

inset - дословно вставка, есть DrawableInset - позволяет сделать Drawable c отступами:

```xml
<inset
    android:drawable="@drawable/(Enter the file name under drawable)"
    android:insetBottom="4dp"
    android:insetTop="4dp"/>
```
то есть по сути inset это размеры отступов. То есть inset хранит, размеры statusBar и navigationBar???

Получить WindowInsets можно из любой приаттаченой вьюхи:

```java
view.getRootWindowInsets(); //C 23 api
```
Ссылки:
https://medium.com/@azizbekian/windowinsets-24e241d4afb9

Если для ConstraintLayout выставить background="@color/..." то этот цвет так же будет под статус баром, даже при наличии fitsSystemWindow = true.
При попытке выставить topMargin для ConstraintLayout получаеся пробел м.у статус баром и тулбаром (верхним элементом ConstraintLayout )

отказаться от fitsSystemWindow нельзя т.к разметка тянется при скрытии/появлении клавиатуры.




