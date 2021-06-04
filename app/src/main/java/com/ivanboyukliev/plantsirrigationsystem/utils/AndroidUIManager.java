package com.ivanboyukliev.plantsirrigationsystem.utils;

import android.os.Build;
import android.view.View;
import android.view.Window;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.NAV_BAR_INVISIBLE;

public final class AndroidUIManager {

    private Window currentWindow;
    private int currentApiVersion;

    public AndroidUIManager(Window currentWindow) {
        this.currentWindow = currentWindow;
    }

    public void disableNavigationBar() {
        currentApiVersion = Build.VERSION.SDK_INT;
        // This work only for android 4.4+
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {

            currentWindow.getDecorView().setSystemUiVisibility(NAV_BAR_INVISIBLE);

            // Code below is to handle presses of Volume up or Volume down.
            // Without this, after pressing volume buttons, the navigation bar will
            // show up and won't hide
            final View decorView = currentWindow.getDecorView();
            decorView
                    .setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {

                        @Override
                        public void onSystemUiVisibilityChange(int visibility) {
                            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                                decorView.setSystemUiVisibility(NAV_BAR_INVISIBLE);
                            }
                        }
                    });
        }
    }

}
