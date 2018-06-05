Кароч, написал демку по FragmentStatePagerAdapter. Выяснил для себя что в адаптере в один момент времени существует от двух до трех фрагментов в активном состоянии (Текущий + соседние). Вот для этих соседних как раз и не вызываются колбеки onPause, onStop, onDestroy. И вся херня в том что нормального варианта получить ссыль на реальный фрагмент, что бы у него грохнуть загрузку особо нет. 

[ пример адаптера с отслеживанием текущего фрагмента ](/AdapterState.java)

Получение фрагмента по позиции:
```java
public static Fragment getCurrentFragment(ViewPager pager, FragmentPagerAdapter adapter) {
    try {
        Method m = adapter.getClass().getSuperclass().getDeclaredMethod("makeFragmentName", int.class, long.class);
        Field f = adapter.getClass().getSuperclass().getDeclaredField("mFragmentManager");
        f.setAccessible(true);
        FragmentManager fm = (FragmentManager) f.get(adapter);
        m.setAccessible(true);
        String tag = null;
        tag = (String) m.invoke(null, pager.getId(), (long) pager.getCurrentItem());
        return fm.findFragmentByTag(tag);
    } catch (NoSuchMethodException e) {
        e.printStackTrace();
    } catch (IllegalArgumentException e) {
        e.printStackTrace();
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    } catch (InvocationTargetException e) {
        e.printStackTrace();
    } catch (NoSuchFieldException e) {
        e.printStackTrace();
    } 
    return null;
}
```