# Principles of navigation
https://developer.android.com/topic/libraries/architecture/navigation/navigation-principles
## The app should have a fixed starting destination
Должен быть фиксированный стартовый экран (starting destination). Экран логина или иной единовременной установки не является стартовым. 
## A stack is used to represent the "navigation state" of an app
Navigation Stack - LIFO. Стартовый экран в низу стека. Работаем только с вершиной стека, или добавляем новый экран или убираем верхний.
## The Up button never exits your app
Кнопка Up не показывается на стартовом экране. При старте из чужой таски по кнопке Up можно выбраться к стартовому экрану, но нельзя выйти из приложения.
## Up and Back are equivalent within your app's task
Кнопка Up должна повторять поведениие системной кнопки Back если мы в своей таске и не на стартовом экране.

- _ Своя таска - для Activity своя таска та, которая имеет `affinity` такой же как и у Activity. 
`affinity` Activity = packageName, если не задан другой в манифесте. `affinity` таска = `affinity`
 Activity явл-ся корневой для таска _