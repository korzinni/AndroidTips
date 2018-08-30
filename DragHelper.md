Ползунок сильно напоминает SeekBar, первый вариант пили на нем, 
но SeekBar кликабельный и кнопку не обязательно тащить от начала до конца, а можно просто кликнуть в конец бара.

Тащим FAB, в зависимости от положения выставляем значение прогресбару. Прогресбар естественно:
```xml
style="@style/Base.Widget.AppCompat.ProgressBar.Horizontal"
```


```java
public class DragSwitcher extends FrameLayout {

    public static final int TIME_ANIMATION = 600;

    int offLevel = 2;
    int onLevel = 98;
    int triggerLevel = 97;

    DragSwitcherCoverFrame frame;
    boolean clickAnimationProgress = false;
    private ProgressBar progressBar;
    private ViewDragHelper dragHelper;
    private View root;
    private FloatingActionButton fab;

    private SwitchOnListenerImpl switchOnListener = new SwitchOnListenerImpl(null);

    public DragSwitcher(@NonNull Context context) {
        super(context);
        init(context);
    }

    public DragSwitcher(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DragSwitcher(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setSwitchOnListener(SwitchOnListener switchOnListener) {
        this.switchOnListener = new SwitchOnListenerImpl(switchOnListener);
    }

    private void init(Context context) {
        root = LayoutInflater.from(context).inflate(R.layout.drag_switcher, this);
        progressBar = root.findViewById(R.id.seekBar);
        fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(view -> click());
        dragHelper = ViewDragHelper.create(this, new Callback() {

            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                return child == fab;
            }

            @Override
            public int getViewHorizontalDragRange(@NonNull View child) {
                return progressBar.getWidth() - child.getWidth();
            }

            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
                if (!isEnabled()) {
                    return getOffEdge() - fab.getWidth() / 2;
                }

                if (left + child.getWidth() / 2 > getOnEdge()) {
                    return getOnEdge() - child.getWidth() / 2;
                } else if (left + child.getWidth() / 2 < getOffEdge()) {
                    return getOffEdge() - child.getWidth() / 2;
                } else {
                    return left;
                }

            }


            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                return child.getTop();
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, 0, 0);
                float percent = progressBar.getWidth() / 100f;
                float centerView = releasedChild.getX() + releasedChild.getWidth() / 2;
                if (centerView - progressBar.getX() < triggerLevel * percent) {
                    switchOnListener.switchFail();
                    if (dragHelper.smoothSlideViewTo(fab, getOffEdge() - releasedChild.getWidth() / 2, releasedChild.getTop())) {
                        ViewCompat.postInvalidateOnAnimation(DragSwitcher.this);
                    }
                } else {
                    switchOnListener.switchOn();
                    if (frame != null) {
                        frame.animate(getSliderRect(), new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                reset();
                            }

                            @Override
                            public void onAnimationStart(Animator animation) {
                                fab.setVisibility(INVISIBLE);
                            }
                        });
                    }
                    if (dragHelper.smoothSlideViewTo(fab, getOnEdge() - releasedChild.getWidth() / 2, releasedChild.getTop())) {
                        ViewCompat.postInvalidateOnAnimation(DragSwitcher.this);
                    }
                }
            }

            @Override
            public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
                float percent = progressBar.getWidth() / 100f;
                float centerView = changedView.getX() + changedView.getWidth() / 2;
                progressBar.setProgress((int) ((centerView - progressBar.getX()) / percent));
            }

            @Override
            public void onViewDragStateChanged(int state) {
                super.onViewDragStateChanged(state);
                if (ViewDragHelper.STATE_IDLE == state &&
                        fab.getX() == getOffEdge() - fab.getWidth() / 2 +
                                (int) DimensHelper.dpToPx(getContext(), 72)) {
                    reset();
                }
            }
        });


        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            fab.postDelayed(() -> {
                if (progressBar.getWidth() > 0 && fab.getWidth() > 0) {
                    float present = progressBar.getWidth() / 100f;
                    offLevel = (int) (fab.getWidth() / 2 / present);
                    onLevel = 100 - offLevel;
                    triggerLevel = onLevel - 1;
                    reset();
                }
            }, 100);
        } else {
            MarginLayoutParams mlp = (MarginLayoutParams) progressBar.getLayoutParams();
            mlp.leftMargin = (int) DimensHelper.dpToPx(getContext(), 32);
            mlp.rightMargin = (int) DimensHelper.dpToPx(getContext(), 32);
        }

        postDelayed(this::reset, 100);

    }


    private int getOffEdge() {
        return (int) (progressBar.getX() + progressBar.getWidth() * offLevel / 100f);
    }

    private int getOnEdge() {
        return (int) (progressBar.getX() + progressBar.getWidth() * onLevel / 100f);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN ||
                event.getActionMasked() == MotionEvent.ACTION_MOVE) {
            switchOnListener.onStartDrag();
        } else if (event.getAction() == MotionEvent.ACTION_UP ||
                event.getAction() == MotionEvent.ACTION_CANCEL) {
            switchOnListener.onFinishDrag();
        }


        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public Rect getSliderRect() {
        Rect rect = new Rect();
        fab.getGlobalVisibleRect(rect);
        return rect;
    }

    public interface SwitchOnListener {
        void switchOn();

        default void switchFail() {
        }

        default void onStartDrag() {
        }

        default void onFinishDrag() {
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        fab.setEnabled(enabled);
    }

    public void reset() {
        clickAnimationProgress = false;
        fab.setVisibility(VISIBLE);
        switchOnListener.switchFail();
        if (isEnabled()) {
            if (dragHelper.smoothSlideViewTo(fab, getOffEdge() - fab.getWidth() / 2, fab.getTop())) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {

        }

    }

    public void click() {
        if (dragHelper.smoothSlideViewTo(fab,
                getOffEdge() - fab.getWidth() / 2 + (int) DimensHelper.dpToPx(getContext(), 72),
                fab.getTop())) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        clickAnimationProgress = true;
    }

    private class SwitchOnListenerImpl implements SwitchOnListener {
        SwitchOnListener delegate;
        long lastCall;
        boolean isMoved;
        boolean switchOn;

        public SwitchOnListenerImpl(SwitchOnListener delegate) {
            this.delegate = delegate;
        }

        @Override
        public void switchOn() {
            long currentTime = Calendar.getInstance().getTimeInMillis();
            if (delegate != null && lastCall + 500 < currentTime && !switchOn) {
                delegate.switchOn();
                switchOn = true;
                lastCall = currentTime;
            }
        }

        @Override
        public void switchFail() {
            long currentTime = Calendar.getInstance().getTimeInMillis();
            if (delegate != null && lastCall + 500 < currentTime) {
                delegate.switchFail();
                switchOn = false;
                lastCall = currentTime;
            }
        }

        @Override
        public void onStartDrag() {
            if (delegate != null && !isMoved) {
                isMoved = true;
                delegate.onStartDrag();
            }
        }

        @Override
        public void onFinishDrag() {
            if (delegate != null) {
                delegate.onFinishDrag();
                isMoved = false;
            }
        }
    }

    public void setCoverFrame(DragSwitcherCoverFrame frame) {
        this.frame = frame;
    }

}

```