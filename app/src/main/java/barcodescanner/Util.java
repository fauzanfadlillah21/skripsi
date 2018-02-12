/*
 * Copyright (c)Fauzan Fadlillah 2017
 */

package barcodescanner;

import android.os.Looper;

/**
 *
 */
public class Util {
    public static void validateMainThread() {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new IllegalStateException("Must be called from the main thread.");
        }
    }
}
