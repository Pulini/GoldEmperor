package com.goldemperor.Widget.banner.anim.unselect;

import android.view.View;


import com.goldemperor.Widget.banner.anim.BaseAnimator;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

public class NoAnimExist extends BaseAnimator {
    public NoAnimExist() {
        this.mDuration = 200;
    }

    public void setAnimation(View view) {
        this.mAnimatorSet.playTogether(new Animator[]{
                ObjectAnimator.ofFloat(view, "alpha", 1, 1)});
    }
}
