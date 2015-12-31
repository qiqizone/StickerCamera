package com.stickercamera.app.camera.util;



        import android.content.Context;
        import android.content.res.Resources;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.util.DisplayMetrics;
        import android.view.MotionEvent;
        import android.view.View;
        import android.view.WindowManager;

        import com.github.skykai.stickercamera.R;

public class GetTextImage extends View
{
    private float x = 20, y = 40;
    private static float windowWidth;
    private static float windowHeight;
    private static float left = 0;      //图片在屏幕中位置X坐标
    private static float top = 0;       //图片在屏幕中位置Y坐标
    private DisplayMetrics dm = new DisplayMetrics();  //用于获取屏幕的高度和宽度
    private WindowManager windowManager;
    private Bitmap newbitmap;

    public GetTextImage(Context context)
    {
        super(context);
        windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        //屏幕的宽度
        windowWidth = windowManager.getDefaultDisplay().getWidth();
        //屏幕的高度
        windowHeight = windowManager.getDefaultDisplay().getHeight();
    }

    public void onDraw(Canvas canvas,String str,Bitmap bmp)
    {
        newbitmap = getTextImage(bmp, str, x, y);
        canvas.drawBitmap(newbitmap, 0, 0, null);
    }

    /**
     * 返回值: Bitmap 参数：原图片,文字 功能: 根据给定的文字生成相应图片
     *
     * @param originalMap
     * @param text  文字
     * @param x  点击的X坐标
     * @param y  点击的Y坐标
     * @return
     */
    public  Bitmap getTextImage(Bitmap originalMap, String text, float x,
                                      float y)
    {
        float bitmapWidth = originalMap.getWidth();
        float bitmapHeight = originalMap.getHeight();
        // 定义画布
        Canvas canvas = new Canvas(originalMap);
        // 定义画笔
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setTextSize(dip2px(getContext(),25));
        //获得文本的长度（像素）
        float textWidth = paint.measureText(text);
        canvas.drawBitmap(originalMap, 0, 0, null);

        // 如果图片宽度小于屏幕宽度
        if (left + bitmapWidth < windowWidth)
        {
            // 右边界
            if (x >= left + bitmapWidth - textWidth)
            {
                x = left + bitmapWidth - textWidth;
            }
            // 左边界
            else if (x <= left)
            {
                x = left;
            }
        }
        else
        {
            // 右边界
            if (x >= windowWidth - textWidth)
            {
                x = windowWidth - textWidth;
            }
            // 左边界
            else if (x <= 0)
            {
                x = 0;
            }
        }
        // 如果图片高度小于屏幕高度
        if (top + bitmapHeight < windowHeight)
        {
            // 下
            if (y >= top + bitmapHeight)
            {
                y = top + bitmapHeight;
            }
            // 上
            else if (y <= top + 10)
            {
                y = top + 10;
            }
        }
        else
        {
            if (y >= windowHeight)
            {
                y = windowHeight;
            }
            else if (y <= 0)
            {
                y = 0;
            }
        }

        // 添加字
        canvas.drawText(text, x, y, paint);
        return originalMap;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            x = event.getX();
            y = event.getY();
            // 重绘
            invalidate();
        }
        return true;
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}