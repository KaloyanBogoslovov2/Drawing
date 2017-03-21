package com.bogoslovov.kaloqn.drawing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.bogoslovov.kaloqn.drawing.MainActivity.drawingView;

/**
 * Created by kaloqn on 3/20/17.
 */

public class SaveImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_image);

    }


    public void saveImage(View v){

        EditText fileTitle= (EditText) findViewById(R.id.save_image_edit_text);
        Bitmap bitmap = drawingView.getDrawingCache();

        String imgSaved = MediaStore.Images.Media.insertImage(
                this.getContentResolver(),
                bitmap,
                fileTitle.getText().toString(),
                "drawing");

        drawingView.destroyDrawingCache();

        if (imgSaved.equals(null)) {
            Toast.makeText(this, "image NOT SAVED",Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent (SaveImageActivity.this,MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "image SAVED",Toast.LENGTH_SHORT).show();
        }
    }
}
