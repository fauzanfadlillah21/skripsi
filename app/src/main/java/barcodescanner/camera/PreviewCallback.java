/*
 * Copyright (c)Fauzan Fadlillah 2017
 */

package barcodescanner.camera;

import barcodescanner.SourceData;

/**
 * Callback for camera previews.
 */
public interface PreviewCallback {
    abstract void onPreview(SourceData sourceData);
}
