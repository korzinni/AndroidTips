## Есть view c  [кастомным атрибутом](/ViewCustomAttribute.md). Как установить значение атрибута внутри XML.
**Попытка:**
```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>

        <variable
            name="allowedChars"
            type="String" />

    </data>

    <android.support.constraint.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <br.com.sapereaude.maskedEditText.MaskedEditText
            android:id="@+id/editText"
            style="@style/EditText"
            android:layout_width="0dp"
            android:layout_height="50dp"
            app:allowed_chars="@{allowedChars}"/>


    </android.support.constraint.ConstraintLayout>
</layout>
```

**Результат:**
 ****/ data binding error ****msg:Cannot find the setter for attribute 'app:allowed_chars' with parameter type java.lang.String on br.com.sapereaude.maskedEditText.MaskedEditText. file:/home/korz/AndroidStudioProjects/open/openbroker_android/app/src/main/res/layouts/layout/view_masked_input_form.xml loc:84:33 - 84:44 ****\ data binding error ****

**Если view наша, то туда нужно просто добавить сеттер. А если нет, то адекватного решения нет. Неадекватное - копипаст всего класса**

#### Решение:
Аттрибут не выставляется, в отсутсвии сеттера можно восрпользоваться рефлексией:

```java
    @BindingAdapter("allowed_chars")
    public static void setAllowedChars(MaskedEditText v, String allowedChars) {
        try {
            Field field =  v.getClass().getDeclaredField("allowedChars");
            field.setAccessible(true);
            field.set(v,allowedChars);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
```
