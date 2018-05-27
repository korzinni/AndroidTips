# Action Bar Tips

Support action bar кастомная вьюха не во весь размер. 

Решение:

```java
        ActionBar bar = activity.getSupportActionBar();
        bar.setDisplayShowHomeEnabled(false);
        bar.setDisplayShowTitleEnabled(false);
        bar.setDisplayShowCustomEnabled(true);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        bar.setCustomView(mView, params);
        mView.post(new Runnable() {
            @Override
            public void run() {
                Toolbar parent = (Toolbar) mView.getParent();
                parent.setContentInsetsAbsolute(0, 0);
            }
        });
```