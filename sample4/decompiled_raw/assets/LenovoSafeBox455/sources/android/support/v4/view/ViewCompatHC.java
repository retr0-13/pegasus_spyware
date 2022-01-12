package android.support.v4.view;

import android.animation.ValueAnimator;
/* loaded from: classes.dex */
class ViewCompatHC {
    ViewCompatHC() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static long getFrameTime() {
        return ValueAnimator.getFrameDelay();
    }
}
