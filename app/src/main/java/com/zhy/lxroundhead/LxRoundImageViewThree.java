package com.zhy.lxroundhead;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by lixian on 2016/5/13.
 */
public class LxRoundImageViewThree extends ImageView {

    Context ct;

    public LxRoundImageViewThree(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ct = context;
    }

    public LxRoundImageViewThree(Context context, AttributeSet attrs) {
        super(context, attrs);
        ct = context;
    }

    public LxRoundImageViewThree(Context context) {
        super(context);
        ct = context;
    }

    Paint paint;
    Paint  paint2;

    @Override
    protected void onDraw(Canvas canvas) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        if (getDrawable() == null) {
            super.onDraw(canvas);
        } else {
            int width = getWidth();
            int height = getHeight();
            Bitmap mBitmap = resizeImage(getBitmap(getDrawable()), width, height);
            int BitmapWidth = mBitmap.getWidth();
            int BitmapHeight = mBitmap.getHeight();
            Matrix matrix = new Matrix();

            //实现实际大小图片存储
//            canvas.drawBitmap(mBitmap, matrix, paint);


            //画拓元
            RectF rectF = new RectF();
            float min = Math.min(width, height);
            rectF.left = (width - min) / 2;
            rectF.top = (height - min) / 2;
            rectF.right = width - (width - min) / 2;
            rectF.bottom = height - (height - min) / 2;
            BitmapShader mShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
            paint.setShader(mShader);
            //椭圆形
            canvas.drawRoundRect(rectF, min / 2, min / 2, paint);


            float radius = min / 2;
            float cx = getWidth() / 2f;
            float cy = getHeight() / 2f;
            paint2.setAntiAlias(true);
            paint2.setStyle(Paint.Style.STROKE);
            paint2.setStrokeWidth(5);
            paint2.setColor(ct.getResources().getColor(R.color.colorPrimary));
            canvas.drawCircle(cx, cy, radius-5, paint2);

        }


    }


    private Bitmap getBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            Bitmap.Config config = drawable.getOpacity() == PixelFormat.OPAQUE ? Bitmap.Config.RGB_565 : Bitmap.Config.ARGB_8888;
            Bitmap mBitmap = Bitmap.createBitmap(width, height, config);
            Canvas canvas = new Canvas(mBitmap);
            drawable.setBounds(0, 0, width, height);
            drawable.draw(canvas);
            return mBitmap;
        }
    }

    //缩放图片实现显示
    public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {
        Bitmap BitmapOrg = bitmap;
        try {
            int width = BitmapOrg.getWidth();
            int height = BitmapOrg.getHeight();
            int newWidth = w;
            int newHeight = h;

            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;

            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            // if you want to rotate the Bitmap
            // matrix.postRotate(45);
            Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                    height, matrix, true);
            return resizedBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return bitmap;
        }
    }


}
