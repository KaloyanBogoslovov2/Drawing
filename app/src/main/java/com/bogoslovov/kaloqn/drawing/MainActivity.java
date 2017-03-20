package com.bogoslovov.kaloqn.drawing;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.UUID;

import static com.bogoslovov.kaloqn.drawing.R.id.drawing;


public class MainActivity extends AppCompatActivity {
    private static final int COLOR_BLUE = Color.BLUE;
    private static final int COLOR_GREEN = Color.GREEN;
    private static final int COLOR_RED = Color.RED;

    private static final int LINE_THIN = 5;
    private static final int LINE_THICK = 15;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE=0;

    public static DrawingView drawingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDrawingView();
        checkForPermissions();
    }

    private void initDrawingView(){

        Paint paint = getPaint();
        drawingView = new DrawingView(this,paint);
        drawingView.setDrawingCacheEnabled(true);
        LinearLayout layout = (LinearLayout) findViewById(drawing);
        layout.addView(drawingView);
    }

    private void checkForPermissions(){

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }
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

    public void saveImage(View v){

        Intent intent = new Intent(MainActivity.this,SaveImageActivity.class);
        startActivity(intent);


    }

    public void openImage(View v){

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
