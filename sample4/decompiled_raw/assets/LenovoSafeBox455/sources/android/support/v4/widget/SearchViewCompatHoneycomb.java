package android.support.v4.widget;

import android.content.Context;
import android.view.View;
import android.widget.SearchView;
/* loaded from: classes.dex */
class SearchViewCompatHoneycomb {

    /* loaded from: classes.dex */
    interface OnQueryTextListenerCompatBridge {
        boolean onQueryTextChange(String str);

        boolean onQueryTextSubmit(String str);
    }

    SearchViewCompatHoneycomb() {
    }

    public static View newSearchView(Context context) {
        return new SearchView(context);
    }

    public static Object newOnQueryTextListener(final OnQueryTextListenerCompatBridge listener) {
        return new SearchView.OnQueryTextListener() { // from class: android.support.v4.widget.SearchViewCompatHoneycomb.1
            @Override // android.widget.SearchView.OnQueryTextListener
            public boolean onQueryTextSubmit(String query) {
                return OnQueryTextListenerCompatBridge.this.onQueryTextSubmit(query);
            }

            @Override // android.widget.SearchView.OnQueryTextListener
            public boolean onQueryTextChange(String newText) {
                return OnQueryTextListenerCompatBridge.this.onQueryTextChange(newText);
            }
        };
    }

    public static void setOnQueryTextListener(Object searchView, Object listener) {
        ((SearchView) searchView).setOnQueryTextListener((SearchView.OnQueryTextListener) listener);
    }
}
