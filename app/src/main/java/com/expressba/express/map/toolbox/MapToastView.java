package com.expressba.express.map.toolbox;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by songchao on 16/5/2.
 */
public class MapToastView {

    public static Bitmap getViewBitmap(View content){
        content.setDrawingCacheEnabled(true);
        content.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        );
        content.layout(0,0,content.getMeasuredWidth(),content.getMeasuredHeight());
        content.buildDrawingCache();
        Bitmap cacheBitmap = content.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        return bitmap;
    }
}
