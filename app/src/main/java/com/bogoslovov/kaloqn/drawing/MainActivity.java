package com.bogoslovov.kaloqn.drawing;

import static com.bogoslovov.kaloqn.drawing.R.id.drawing;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private static final int LINE_THIN = 5;
    private static final int LINE_THICK = 15;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE=0;

    private DrawingView drawingView;

    public static boolean run = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initDrawingView();
        if (!run) {
            getIntentData();
        }
        checkForPermissions();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();

        if(id==R.id.save_image){

            openSaveImageDialog();

            return true;
        }else if (id == R.id.open_image){

            Intent intent = new Intent(MainActivity.this,OpenImageActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getIntentData() {

        Intent intent = getIntent();
        String uri = intent.getStringExtra("uri");

        //make bitmap mutable
        Bitmap workingBitmap = Bitmap.createBitmap(getBitmap(uri));
        Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);

        Drawable d = new BitmapDrawable(getResources(), mutableBitmap);
        drawingView.setBackground(d);
        run =true;
    }

    private void initDrawingView(){

        Paint paint = getPaint();
        drawingView = new DrawingView(this,paint);
        drawingView.setBackgroundColor(Color.WHITE);
        drawingView.setDrawingCacheEnabled(true);
        LinearLayout layout = (LinearLayout) findViewById(drawing);
        layout.addView(drawingView);
    }


    private Bitmap getBitmap(String uri){
        Bitmap image=null;
        ParcelFileDescriptor parcelFileDescriptor = null;
        try {
            parcelFileDescriptor = getContentResolver().openFileDescriptor(Uri.parse(uri), "r");
            if (parcelFileDescriptor!=null) {
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(this ,"FileNotFoundException please try again",Toast.LENGTH_SHORT ).show();
            e.printStackTrace();
        }finally{
            try {
                if (parcelFileDescriptor!=null)
                    parcelFileDescriptor.close();
            } catch (IOException e) {
                Toast.makeText(this ,"IOException please try again",Toast.LENGTH_SHORT ).show();
                e.printStackTrace();
            }
        }


        return image;
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
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(LINE_THIN);
        return paint;
    }

    private void openSaveImageDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save Image");
        builder.setMessage("Are you sure that you want to save the image?");


        LinearLayout layout = new LinearLayout(this);
        //LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        layout.setPadding(16,0,16,0);
        //layout.setLayoutParams(params);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setSingleLine(false);
        input.setHint("title of image");
        //input.setWidth(layout.getWidth());
        layout.addView(input);
        builder.setView(layout);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveImage(input.getText().toString());
            }

        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "image wasn't saved", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void saveImage(String title){
        MediaStore.Images.Media.insertImage(
                getContentResolver(),
                drawingView.getDrawingCache(),
                title,
                "drawing");

        Toast.makeText(getApplicationContext(), "image SAVED", Toast.LENGTH_SHORT).show();
        drawingView.destroyDrawingCache();
    }

    public void changeDrawingLineToThin(View v){
        drawingView.mPaint.setStrokeWidth(LINE_THIN);
    }

    public void changeDrawingLineToThick(View v){
        drawingView.mPaint.setStrokeWidth(LINE_THICK);
    }

    public void clearDrawing(View v){
        drawingView.clearDrawing();
        drawingView.setBackgroundColor(Color.WHITE);
    }

    public void changeDrawingColorToBlue(View v){
        drawingView.mPaint.setColor(Color.BLUE);
    }

    public void changeDrawingColorToGreen(View v){
        drawingView.mPaint.setColor(Color.GREEN);
    }

    public void changeDrawingColorToRed(View v){
        drawingView.mPaint.setColor(Color.RED);
    }


}
