package com.bogoslovov.kaloqn.drawing;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static com.bogoslovov.kaloqn.drawing.MainActivity.drawingView;

/**
 * Created by kaloqn on 3/20/17.
 */

public class SaveImageActivity extends AppCompatActivity {
    private static String currentFormat = ".png";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_image);

      initSpinner();
    }

    private void initSpinner(){

        String[] fileTypes = getResources().getStringArray(R.array.file_types);
        final SpinnerAdapter fileTypeSpinnerAdapter = new SpinnerAdapter(this, R.layout.spinner_row, fileTypes);

        Spinner fileTypesSpinner = (Spinner) findViewById(R.id.save_image_spinner);
        fileTypesSpinner.setAdapter(fileTypeSpinnerAdapter);
        fileTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentFormat = fileTypeSpinnerAdapter.getItem(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void saveImage(View v){

        EditText fileName= (EditText) findViewById(R.id.save_image_edit_text);
        String imgSaved = MediaStore.Images.Media.insertImage(
                getContentResolver(), drawingView.getDrawingCache(),
                fileName.getText().toString()+currentFormat, "drawing");
        drawingView.destroyDrawingCache();

        if (imgSaved.equals(null)) {
            Toast.makeText(this, "image NOT SAVED",Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent (SaveImageActivity.this,MainActivity.class);
            startActivity(intent);
            if (imgSaved.equals(null)) Toast.makeText(this, "image SAVED",Toast.LENGTH_SHORT).show();
        }
    }
}
