/******************************************************************
 *
 *    Java Lib For Android, Powered By Shenzhen Jiuzhou.
 *
 *    Copyright (c) 2001-2014 Digital Telemedia Co.,Ltd
 *    http://www.d-telemedia.com/
 *
 *    Package:     com.azz.gifview
 *
 *    Filename:    Utils.java
 *
 *    Description: TODO(用一句话描述该文件做什么)
 *
 *    Copyright:   Copyright (c) 2001-2014
 *
 *    Company:     Digital Telemedia Co.,Ltd
 *
 *    @author:     sxszhangz
 *
 *    @version:    1.0.0
 *
 *    Create at:   2015年7月8日 下午8:43:38
 *
 *    Revision:
 *
 *    2015年7月8日 下午8:43:38
 *        - first revision
 *
 *****************************************************************/
package com.azz.mygifview;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import android.R.integer;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;


/**
 * @ClassName Utils
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author sxszhangz
 * @Date 2015年7月8日 下午8:43:38
 * @version 1.0.0
 */
public class FomatUtils {
    /**
     * @Description drawable - > bitmap
     * @param drawable
     * @return
     */
    public static Bitmap Drawable2Bitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("error argument: the argument drawable's width or height is 0.");
        }
        Bitmap bitmap = Bitmap.createBitmap(
                width, 
                height, 
                drawable.getOpacity() != PixelFormat.OPAQUE ?
                        Bitmap.Config.ARGB_8888 :
                            Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }
    /**
     * @Description drawable - > input stream
     * @param drawable
     * @return
     */
    public static InputStream Drawable2InputStream(final Drawable drawable) {
        Bitmap bitmap = Drawable2Bitmap(drawable);
        return Bitmap2InputStream(bitmap, 100);
    }
    /**
     * @Description bitmap - > drawable
     * @param bitmap
     * @return
     */
    public static Drawable Bitmap2Drawable(Bitmap bitmap) {
        BitmapDrawable bDrawable = new BitmapDrawable(bitmap);
        return bDrawable;
    }
    /**
     * @Description bitmap - > input stream
     * @param bitmap
     * @param quality
     * @return
     */
    public static InputStream Bitmap2InputStream(final Bitmap bitmap, final int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.WEBP, quality, baos); //将bitmap压缩为webp输出
        InputStream iStream = new ByteArrayInputStream(baos.toByteArray());
        return iStream;
    }
    /**
     * @Description input sream - > bitmap
     * @param iStream
     */
    public static Bitmap InputStream2Bitmap(final InputStream iStream){
        return BitmapFactory.decodeStream(iStream);
    }
    /**
     * @Description input stream - > drawable
     * @param is
     * @return
     */
    public static Drawable InputStream2Drawable(final InputStream is) {
        Bitmap bitmap = InputStream2Bitmap(is);
        return Bitmap2Drawable(bitmap);
    }
}
