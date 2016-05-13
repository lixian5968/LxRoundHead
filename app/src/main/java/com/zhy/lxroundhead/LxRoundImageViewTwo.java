package com.zhy.lxroundhead;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
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
public class LxRoundImageViewTwo extends ImageView {

    Context ct;

    public LxRoundImageViewTwo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ct = context;
    }


    float mDimens;
    int mColor;
    int ShapeType;
    float roundRadius;
    float LeftTopRadius;
    float RightTopRadius;
    float LeftButtomRadius;
    float RightButtomRadius;

    public LxRoundImageViewTwo(Context context, AttributeSet attrs) {
        super(context, attrs);
        ct = context;

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.LxImage);
        mColor = mTypedArray.getColor(R.styleable.LxImage_borderColor, ct.getResources().getColor(R.color.white));
        mDimens = mTypedArray.getDimension(R.styleable.LxImage_borderWidth, 0);
        ShapeType = mTypedArray.getInt(R.styleable.LxImage_shape, Shape.CIRCLE);
        roundRadius = mTypedArray.getDimension(R.styleable.LxImage_roundRadius, 0);
        LeftTopRadius = mTypedArray.getDimension(R.styleable.LxImage_LeftTopRadius, 0);
        RightTopRadius = mTypedArray.getDimension(R.styleable.LxImage_RightTopRadius, 0);
        LeftButtomRadius = mTypedArray.getDimension(R.styleable.LxImage_LeftButtomRadius, 0);
        RightButtomRadius = mTypedArray.getDimension(R.styleable.LxImage_RightButtomRadius, 0);
        mTypedArray.recycle();


    }

    public LxRoundImageViewTwo(Context context) {
        super(context);
        ct = context;
    }

    Paint paint;
    Paint paint2;

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
            float min = Math.min(width, height);
            BitmapShader mShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            paint.setShader(mShader);
            RectF rectF = new RectF();
            rectF.left = (width - min) / 2 + mDimens / 2;
            rectF.top = (height - min) / 2 + mDimens / 2;
            rectF.right = width - (width - min) / 2 - mDimens / 2;
            rectF.bottom = height - (height - min) / 2 - mDimens / 2;


            if (ShapeType == Shape.CIRCLE) {
                //椭圆形
                canvas.drawRoundRect(rectF, min / 2, min / 2, paint);

            } else if (ShapeType == Shape.RECTANGLE) {
                Path path = new Path();
                if (roundRadius > 0) {
                    path.addRoundRect(rectF, roundRadius, roundRadius, Path.Direction.CCW);
                } else {
                    float radii[] = {LeftTopRadius, LeftTopRadius, RightTopRadius, RightTopRadius, RightButtomRadius, RightButtomRadius, LeftButtomRadius, LeftButtomRadius};
                    path.addRoundRect(rectF, radii, Path.Direction.CCW);
                }
                canvas.drawPath(path, paint);
            }


            if (mDimens > 0) {
                paint2.setAntiAlias(true);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(mDimens);
                paint2.setColor(mColor);
                if (ShapeType == Shape.CIRCLE) {
                    float radius = min / 2;
                    float cx = getWidth() / 2f;
                    float cy = getHeight() / 2f;
                    canvas.drawCircle(cx, cy, radius - mDimens, paint2);
                } else if (ShapeType == Shape.RECTANGLE) {
                    Path path = new Path();
                    if (roundRadius > 0) {
                        path.addRoundRect(rectF, roundRadius, roundRadius, Path.Direction.CCW);
                    } else {
                        float radii[] = {LeftTopRadius, LeftTopRadius, RightTopRadius, RightTopRadius, RightButtomRadius, RightButtomRadius, LeftButtomRadius, LeftButtomRadius};
                        path.addRoundRect(rectF, radii, Path.Direction.CCW);
                    }
                    canvas.drawPath(path, paint2);
                }
            }
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


    public static class Shape {
        public static final int CIRCLE = 1;
        public static final int RECTANGLE = 2;
    }


}
