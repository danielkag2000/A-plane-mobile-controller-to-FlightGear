package biu.ex4.danevy.view.joystick;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import static android.graphics.Color.GRAY;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.WHITE;

public class Joystick extends View {

    private Paint circlePaint;
    private Paint bgPaint;
    private Paint borderPaint;

    public Joystick(Context context) {
        super(context);

        initPaints();
    }

    public Joystick(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initPaints();
    }

    private void initPaints() {
        /* initialize objects */
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint = new Paint();

        /* initialize each paint individually */
        circlePaint.setColor(GREEN);
        circlePaint.setStyle(Paint.Style.FILL);

        bgPaint.setColor(GRAY);
        bgPaint.setStyle(Paint.Style.FILL);

        borderPaint.setColor(Color.rgb(100, 100, 100));
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(10f);
    }

    private int height;
    private int width;

    private int circleRadius;
    private float cx, cy;

    private float borderWidth;
    private float halvedBorderWidth;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int xpad = getPaddingLeft() + getPaddingRight();
        int ypad = getPaddingTop() + getPaddingBottom();

        width = w - xpad;
        height = h - ypad;

        /*
         * make sure there is a space from where the inner circle is
         * to the borders
         */
        int minDim = Math.min(width, height);
        circleRadius = Math.min(120, minDim / 4);

        // position circle in middle
        cx = width / 2f;
        cy = height / 2f;

        borderWidth = Math.min(10f, minDim / 10f);
        borderPaint.setStrokeWidth(borderWidth);
        halvedBorderWidth = borderWidth / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw the background
        canvas.drawOval(0, 0, width, height, bgPaint);

        // draw the borders
        canvas.drawOval(halvedBorderWidth, halvedBorderWidth,
                width - halvedBorderWidth, height - halvedBorderWidth, borderPaint);

        // draw circle on top of all
        canvas.drawCircle(cx, cy, circleRadius, circlePaint);
    }
}
