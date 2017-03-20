package com.bogoslovov.kaloqn.drawing;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity {
    private static final int COLOR_BLUE = Color.BLUE;
    private static final int COLOR_GREEN = Color.GREEN;
    private static final int COLOR_RED = Color.RED;

    private static final int LINE_THIN = 5;
    private static final int LINE_THICK = 15;

    private DrawingView drawingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDrawingView();
    }

    private void initDrawingView(){

        Paint paint = getPaint();
        drawingView = new DrawingView(this,paint);
        LinearLayout layout = (LinearLayout) findViewById(R.id.drawing);
        layout.addView(drawingView);
    }

    private Paint getPaint(){
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(COLOR_BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(LINE_THIN);
        return paint;
    }

    public void changeDrawingLineToThin(View v){
        drawingView.mPaint.setStrokeWidth(LINE_THIN);
    }

    public void changeDrawingLineToThick(View v){
        drawingView.mPaint.setStrokeWidth(LINE_THICK);
    }

    public void clearDrawing(View v){
        drawingView.clearDrawing();
    }

    public void changeDrawingColorToBlue(View v){
        drawingView.mPaint.setColor(COLOR_BLUE);

    }

    public void changeDrawingColorToGreen(View v){
        drawingView.mPaint.setColor(COLOR_GREEN);
    }

    public void changeDrawingColorToRed(View v){
        drawingView.mPaint.setColor(COLOR_RED);
    }
}
