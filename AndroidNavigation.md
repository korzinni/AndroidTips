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
 
 Что-то другое
 

 
 

