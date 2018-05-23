#Navigation

##Principles of navigation
https://developer.android.com/topic/libraries/architecture/navigation/navigation-principles
###The app should have a fixed starting destination
Должен быть фиксированный стартовый экран (starting destination). Экран логина или иной единовременной установки не является стартовым. 
###A stack is used to represent the "navigation state" of an app
Navigation Stack - LIFO. Стартовый экран в низу стека. Работаем только с вершиной стека, или добавляем новый экран или убираем верхний.
###The Up button never exits your app
Кнопка Up не показывается на стартовом экране. При старте из чужой таски по кнопке Up можно выбраться к стартовому экрану, но нельзя выйти из приложения.
