# TEXT

##Неразрывные символы:

Пробел:
Название в Юникоде	No-Break Space
Номер в Юникоде	U+00A0
HTML-код	&#160;
Мнемоника	&nbsp;

Дефис:
Название в Юникоде	Non-Breaking Hyphen
Номер в Юникоде	U+2011
HTML-код	&#8209;

##Шрифты:

Начиная с 8 или при помощи саппорт либы:

создаем res\font\
туда складываем файлы шрифтов .ttf
и создаем там файл:

```xml
<?xml version="1.0" encoding="utf-8"?>
<font-family xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <!-- regular -->
    <font
        android:font="@font/roboto_medium"
        android:fontStyle="normal"
        android:fontWeight="400"

        app:font="@font/roboto_medium"
        app:fontStyle="normal"
        app:fontWeight="400" />

    <!-- italic -->
    <font
        android:font="@font/roboto_medium_italic"
        android:fontStyle="italic"
        android:fontWeight="400"

        app:font="@font/roboto_medium_italic"
        app:fontStyle="italic"
        app:fontWeight="400" />


</font-family>
```
где -  @font/roboto_medium_italic ссылки на фалы шрифта.

используется так:

```xml
app:fontFamily="@font/roboto_medium_family"
```

в четверке нет roboto-medium, и roboto-regular старый нет знаков валют. 

Много шрифтов можно скачать здесь:

https://github.com/google/fonts/tree/master/apache

 ##Автоскейл:
 
 ```xml
<android.support.v7.widget.AppCompatTextView
                    android:id="@+id/secondsLeft"
                    style="@style/Text.Medium"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:gravity="center_vertical|bottom"
                    android:text="@string/activity_enter_sms_counter"
                    android:textAlignment="center"
                    app:autoSizeMaxTextSize="14dp"
                    app:autoSizeMinTextSize="8dp"
                    app:autoSizeStepGranularity="1dp"
                    app:autoSizeTextType="uniform" />
```
