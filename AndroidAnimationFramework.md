## Animation Framework
- [ Drawable Animation ](https://developer.android.com/guide/topics/graphics/drawable-animation#AnimationDrawable) - покадровая анимация, позволяет создавать сложные анимации, но очень геморный
- View Animation - legacy

  Позволяет: alpha, translate, scale, rotate для конкретной view.
  после перетаскивания view, место остается кликабельным.
  переходы м/у активити реализовано на них
  
- [ Property Animation ](https://developer.android.com/guide/topics/graphics/prop-animation) - основной интсрумент
    `ValueAnimator`, `ObjectAnimator`, `ValuePropertyAnimator`, `AnimatorSet`
    
    ValueAnimator - многословен, но наиболее гибкий позволяет практически все:

```java
	final ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
	animator.setDuration(Constants.ANIM_DURATION);
	animator.setInterpolator(new AccelerateInterpolator());
	animator.setStartDelay(delay);

	animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

		int angle = 50 + (int) (Math.random() * 101);
		int movex = new Random().nextInt(mDisplaySize.right);

		@Override
		public void onAnimationUpdate(ValueAnimator animation) {
			float value = ((Float) (animation.getAnimatedValue()))
					.floatValue();

			aniView.setRotation(angle * value);
			aniView.setTranslationX((movex - 40) * value);
			aniView.setTranslationY((mDisplaySize.bottom + (150 * mScale))
					* value);
		}
	});

	animator.start();
```
`ObjectAnimator` заточен на изменение поля объекта, требует публичный сеттер поля у объекта
```java
ObjectAnimator animation = ObjectAnimator.ofFloat(textView, "translationX", 100f);
animation.setDuration(1000);
animation.start();
```
## Layout Transition
- Layout Animations
- Transitions Framework
## Window Transition
- Custom Animations
- Shared Objects
## Moar

## Tips:

