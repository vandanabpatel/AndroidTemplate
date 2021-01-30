package com.android.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.android.ui.App;

public class DisplayUtils {
    private static String TAG = "DisplayUtils";
    private static DisplayUtils instance;

    public static int screenWidth;
    public static int screenHeight;

    public DisplayUtils() {
        DisplayMetrics displayMetrics = App.getAppContext().getResources().getDisplayMetrics();

        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
    }

    public static DisplayUtils getInstance() {
        if (instance == null) {
            instance = new DisplayUtils();
        }
        return instance;
    }

    public int getHeight() {
        return screenHeight;
    }

    public int getWidth() {
        return screenWidth;
    }

    /**
     * get screen width
     *
     * @return
     */
    public int getDialogWidth() {
        return (int) (screenWidth * 0.8f);
    }

    /**
     * get screen width
     *
     * @return
     */
    public int getDialogHeight() {
        return (int) (screenWidth * 0.8f);
    }

    /**
     * convert dip to pixel
     *
     * @param mContext
     * @param dp
     * @return
     */
    public int convertDpToPixels(Context mContext, float dp) {
        Resources r = mContext.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    /**
     * convert dp to pixel
     *
     * @param mContext
     * @param dp
     * @return
     */
    public static float convertDpToPixel(Context mContext, float dp) {
        Resources resources = mContext.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * metrics.density;
        return px;
    }

    /**
     * this method converts device specific pixels to density independent
     * pixels.
     *
     * @param mContext
     * @param px       - a value in px (pixels) unit. Which we need to convert into db
     * @return - float value to represent dp equivalent to px value
     */
    public int convertPixelsToDp(Context mContext, float px) {
        Resources resources = mContext.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / metrics.density;
        return (int) dp;
    }

    /**
     * manage paint property
     *
     * @param color
     * @return
     */
    public Paint getPaint(int color) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setDither(true);
        paint.setColor(color);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);
        return paint;
    }

    public Paint getWhitePaint() {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);

        return paint;
    }

    public Paint getBlackPaint() {
        Paint paint = new Paint();
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);

        return paint;
    }

    /**
     * calculate size
     *
     * @param options
     * @return
     */
    public int calculateInSampleSize(BitmapFactory.Options options) {
        // raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > screenHeight || width > screenWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > screenHeight
                    && (halfWidth / inSampleSize) > screenWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
