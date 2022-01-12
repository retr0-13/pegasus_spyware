package android.support.v4.content;

import android.content.Context;
import android.content.Intent;
/* loaded from: classes.dex */
class ContextCompatHoneycomb {
    ContextCompatHoneycomb() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void startActivities(Context context, Intent[] intents) {
        context.startActivities(intents);
    }
}
