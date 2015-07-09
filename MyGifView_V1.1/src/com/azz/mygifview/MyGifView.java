/******************************************************************
 *
 *    Java Lib For Android, Powered By Shenzhen Jiuzhou.
 *
 *    Copyright (c) 2001-2014 Digital Telemedia Co.,Ltd
 *    http://www.d-telemedia.com/
 *
 *    Package:     com.azz.mygifview
 *
 *    Filename:    MyGifView.java
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
 *    Create at:   2015年7月8日 上午8:46:17
 *
 *    Revision:
 *
 *    2015年7月8日 上午8:46:17
 *        - first revision
 *
 *****************************************************************/
package com.azz.mygifview;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;

import com.ant.liao.GifView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;


/**
 * @ClassName MyGifView
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author sxszhangz
 * @Date 2015年7月8日 上午8:46:17
 * @version 1.0.0
 */
public class MyGifView extends GifView {
    /**
     * @Field @TAG : 打印表签
     */
    private static final String TAG = "MyGifView";
    /**
     * @Field @mMovie : 动图播放
     */
    private Movie mMovie;
    /**
     * @Field @mImageWidth : 图片宽
     */
    private int mImageWidth = 100;
    /**
     * @Field @mImageHeight : 图片高
     */
    private int mImageHeight = 100;
    /**
     * @Field @mMovieStart : 播放开始时间
     */
    private long mMovieStart = -1;
    /**
     * @Description 构造方法
     * @param context
     * @param attrs
     * @param defStyle
     */
    public MyGifView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Log.d(TAG, "构造方法（，，）");
        int count = attrs.getAttributeCount();
        for (int i = 0; i < count; i++) {
            Log.d(TAG, "attrs-name = " + attrs.getAttributeName(i));
            Log.d(TAG, "attrs-value = " + attrs.getAttributeValue(i));
        }
        init(context, attrs);
    }

    /**
     * @Description 构造方法
     * @param context
     * @param attrs
     */
    public MyGifView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        Log.d(TAG, "构造方法（，）");
    }

    /**
     * @Description 构造方法
     * @param context
     */
    public MyGifView(Context context) {
        super(context);
        Log.d(TAG, "构造方法（）");
    }

    /**
     * @Description 初始化
     */
    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GifView);
        //打印出属性值
        int count = typedArray.getIndexCount();
        Log.d(TAG, "typedArray.count = " + count);
        for (int i = 0; i <count; i++) {
            Log.d(TAG, "typedArray = " + typedArray.getText(i));
        }
        
        int resId = getResourceId(typedArray, context); //src属性对应值resId
        
//        int resId = typedArray.getResourceId(R.styleable.GifView_gif_src, 0); //gif_src属性对应值
        
        typedArray.recycle();
        
        if (resId != 0) {
            Log.e(TAG, "getResources()" + getResources());
            //转输入流
            InputStream iStream = getResources().openRawResource(resId);
            Movie movie = Movie.decodeStream(iStream);
            setMovie(movie, iStream);
        }
    }
    /* (非 Javadoc)
     * Description:
     * @see android.widget.ImageView#onDraw(android.graphics.Canvas)
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw");
        if (mMovie != null) {
             playMovie(canvas);
             invalidate();
        }
    }
    /* (非 Javadoc)
     * Description:
     * @see android.widget.ImageView#onMeasure(int, int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "noMeasure-width = " + widthMeasureSpec + " height = " + heightMeasureSpec);
        if (mMovie != null) {
            //如果是GIF图片，重新设定大小
            setMeasuredDimension(mImageWidth, mImageHeight);
        }
    }
    /* (非 Javadoc)
     * Description:
     * @see android.view.View#onLayout(boolean, int, int, int, int)
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout");
    }
    /**  
     * 开始播放GIF动画，播放完成返回true，未完成返回false。  
     *   
     * @param canvas  
     * @return 播放完成返回true，未完成返回false。  
     */ 
    private boolean playMovie(Canvas canvas) {
        if (mMovie == null) { //普通图片
            return false;
        }
        long now = SystemClock.uptimeMillis();  
        //第一次播放
        if (mMovieStart == 0) {  
            mMovieStart = now;  
        }  
        int duration = mMovie.duration();  
        Log.d(TAG, "movie duration is " + duration);
        if (duration == 0) {  
            duration = 1000;  
        }  
        int relTime = (int) ((now - mMovieStart) % duration);  
        mMovie.setTime(relTime); 
        mMovie.draw(canvas, 0, 0); 
//        if ((now - mMovieStart) >= duration) {  
//            mMovieStart = 0;  
//            return true;  
//        }  
        return false;  
    } 
    /**
     * 设置
     * @param movie
     * @param is 输入流
     */
    private void setMovie(final Movie movie, final InputStream is) {
        mMovie = movie;
        if (mMovie != null) { //不为空说明是GIF
            Bitmap bitmap = BitmapFactory.decodeStream(is);  
            mImageWidth = bitmap.getWidth();  
            mImageHeight = bitmap.getHeight();  
            Log.i(TAG, "setMovie---width = " + mImageWidth + ", height = " + mImageHeight);
            bitmap.recycle();  
        }
    }
    /**
     * 设置
     * @param is 输入流
     * @see {link #Movie.decodeStream(InputStream is)}
     */
    private void setMovie(final InputStream is) {
        mMovie = Movie.decodeStream(is);
        if (mMovie != null) { //不为空说明是GIF
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            if (bitmap == null) {
                Log.e(TAG, "setMovie --- decode fail ! bitmap is null");
                return;
            }
            mImageWidth = bitmap.getWidth();  
            mImageHeight = bitmap.getHeight();  
            Log.i(TAG, "setMovie---width = " + mImageWidth + ", height = " + mImageHeight);
            bitmap.recycle();  
        }
    }
    /**  
     * 通过Java反射，获取到src指定图片资源所对应的id。  
     *   
     * @param typedArray 属性组 
     * @param context  
     * @return 返回布局文件中指定图片资源所对应的id，没有指定任何图片资源就返回0。  
     */ 
    private int getResourceId(TypedArray typedArray, Context context) {  
        try {  
            Field field = TypedArray.class.getDeclaredField("mValue");  
            field.setAccessible(true);  
            TypedValue typedValueObject = (TypedValue) field.get(typedArray);  
            Log.i(TAG, "typedValueObject = " + typedValueObject.resourceId);
            return typedValueObject.resourceId;  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (typedArray != null) {  
                typedArray.recycle();  
            }  
        }  
        return 0;  
    } 
    
    /* (非 Javadoc)
     * Description:
     * @see android.widget.ImageView#setImageResource(int)
     */
    @Override
    public void setImageResource(int resId) {
        if (resId != 0) {
            InputStream iStream = getResources().openRawResource(resId);
            setMovie(iStream);
            if (mMovie == null) {
                super.setImageResource(resId);
            }
        } else {
            super.setImageResource(resId);
        }
        invalidate();
    }
    /* (非 Javadoc)
     * Description:
     * @see android.widget.ImageView#setImageDrawable(android.graphics.drawable.Drawable)
     */
//    public void setImageDrawable(Drawable drawable) {
//        if (drawable != null) {
//            mImageWidth = drawable.getIntrinsicWidth();
//            mImageHeight = drawable.getIntrinsicHeight();
//            Log.d(TAG, "setImageDrawable --- width = " + mImageWidth + " height = " + mImageHeight);
//            InputStream iStream = FomatUtils.Drawable2InputStream(drawable);
//            Log.d(TAG, "input sream = " + iStream);
//            setMovie(iStream);
//            if (mMovie == null) {
//                Log.e(TAG, "setImageDrawable---mMovie is null");
//                super.setImageDrawable(drawable);
//            }
//        } else {
//            super.setImageDrawable(drawable);
//        }
//        invalidate();
//    };
    
    /* (非 Javadoc)
     * Description:
     * @see android.widget.ImageView#setImageBitmap(android.graphics.Bitmap)
     */
//    @Override
//    public void setImageBitmap(Bitmap bm) {
//        Log.d(TAG, "setImageBitmap(Bitmap)");
//        this.setImageDrawable(new BitmapDrawable(getContext().getResources(), bm));
//    }
    
    /* (非 Javadoc)
     * Description:
     * @see android.widget.ImageView#setImageURI(android.net.Uri)
     */
//    @Override
//    public void setImageURI(Uri uri) {
//        try {
////            mMovie = Movie.decodeFile("/mnt/sda/sda1/智能UI测试数据/picture/static_gif.gif");
//            mMovie = Movie.decodeFile(uri.toString());
////            FileInputStream fileInputStream = new FileInputStream("/mnt/sda/sda1/智能UI测试数据/picture/static_gif.gif");
////            setMovie(fileInputStream);
//            if (mMovie == null) {
//                Log.e(TAG, "setImageURI---mMovie is null");
//                super.setImageURI(uri);
//            }
//        } catch (Exception e) {
//            super.setImageURI(uri);
//            e.printStackTrace();
//        }
//    }
    
    /* (非 Javadoc)
     * Description:
     * @see android.widget.ImageView#setImageURI(android.net.Uri)
     */
    @Override
    public void setImageURI(Uri uri) {
        String path = uri.toString();
        if (isGif(path)) {
            setGifImage(path);
        } else {
            this.pauseGifAnimation(); //暂停之前的动画
            super.setImageURI(uri);
        }
    }
    /**
     * @Description 判断是否是gif图片
     * @param path 文件路径
     * @return true 是gif， false 不是gif图片
     */
    private boolean isGif(final String path) {
        Log.d(TAG, "isGif---imageBean path = " + path);
        if ("gif".equals(getExtFromFilename(path))) { //判断后缀名是否满足gif
            return true;
        }
        return false;
    }
    /**
     * 获取文件的扩展名 .
     * @param filename .
     * @return String .
     */
    public static String getExtFromFilename(final String filename) {
        int dotPosition = filename.lastIndexOf('.');
        if (dotPosition != -1) {
            return filename.substring(dotPosition + 1, filename.length());
        }
        return "";
    }
}
