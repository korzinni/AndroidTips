# Navigation Architecture Component

The Navigation Architecture Component is implemented based on the [ Principles of navigation ](/PrinciplesOfNavigation.md)

Термины:

navigation graph - совокупность destination и action
destination - экран
action - соединение м/у экранами (действие перехода)

## [ Добавить компоненты в проект](https://developer.android.com/topic/libraries/architecture/adding-components)
в app/build.gradle


    dependencies {
        def nav_version = "1.0.0-alpha01"
        implementation "android.arch.navigation:navigation-fragment:$nav_version" // use -ktx for Kotlin
        implementation "android.arch.navigation:navigation-ui:$nav_version" // use -ktx for Kotlin
        // optional - Test helpers
        androidTestImplementation "android.arch.navigation:navigation-testing:$nav_version" // use -ktx for Kotlin
    }
 
Для передачи аргументов между экранами:

в build.gradle проекта добавить:

    buildscript {
        repositories {
            google()
        }
        dependencies {
            classpath "android.arch.navigation:navigation-safe-args-gradle-plugin:1.0.0-alpha01"
        }
    }
    
в build.gradle модуля:

    apply plugin: "androidx.navigation.safeargs"
 
## Создать `res/navigation/graph.xml`

В дальнейшем это можно сделать через правый клик на `res` и добавления Android resource file.
Текущая версия AndroidStudio (3.1.2) этого не поддерживает. Ставить alpha версию студии не стал.
Все манипуляции с редактором графа пропускаю, потому что пока не поддерживается.

#### Пример из двух фрагментов:


    <?xml version="1.0" encoding="utf-8"?>
    <navigation xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        xmlns:android="http://schemas.android.com/apk/res/android"
        app:startDestination="@id/blankFragment">
        
        <fragment
            android:id="@+id/blankFragment"
            android:name="com.example.cashdog.cashdog.BlankFragment"
            android:label="fragment_blank"
            tools:layout="@layout/fragment_blank" >
            
            <action
                android:id="@+id/action_blankFragment_to_blankFragment2"
                app:destination="@id/blankFragment2" />
                
        </fragment>
        
        <fragment
            android:id="@+id/blankFragment2"
            android:name="com.example.cashdog.cashdog.BlankFragment2"
            android:label="fragment_blank_fragment2"
            tools:layout="@layout/fragment_blank_fragment2" />
            
    </navigation>
    
 
 `<navigation>` - корневой тег
 
 `app:startDestination="@id/blankFragment"` - стартовый экран
 
 `name` - имя класса экрана
 
 `tools:layout` - для превью
 
 `android:label` - для превью

  `<action>` - тег внутри фрагмента, имеет атрибуты id и destination