package com.zhy.lxroundhead;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by lixian on 2016/5/13.
 */
public class LxDrawDemo extends ImageView {

    Context ct;

    public LxDrawDemo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ct = context;
    }

    public LxDrawDemo(Context context, AttributeSet attrs) {
        super(context, attrs);
        ct = context;
    }

    public LxDrawDemo(Context context) {
        super(context);
        ct = context;
    }

    Paint paint;
    Paint paint2;

    @Override
    protected void onDraw(Canvas canvas) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        if (getDrawable() == null) {
            super.onDraw(canvas);
        } else {
            int width = getWidth();
            int height = getHeight();
            //画拓元
            RectF rectF = new RectF();
            float min = Math.min(width, height);
            rectF.left = (width - min) / 2;
            rectF.top = (height - min) / 2;
            rectF.right = width - (width - min) / 2;
            rectF.bottom = height - (height - min) / 2;
            Path path = new Path();
            path.addRoundRect(rectF, 10, 10, Path.Direction.CCW);
//
//            float radii[] = {10, 15, 20, 25, 30, 35, 40, 45};
//            path.addRoundRect(rectF, radii, Path.Direction.CCW);

            canvas.drawPath(path, paint);
        }


    }


}
