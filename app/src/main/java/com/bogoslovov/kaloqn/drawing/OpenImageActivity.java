package com.bogoslovov.kaloqn.drawing;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kaloqn on 3/20/17.
 */

public class OpenImageActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final int MEDIA_LOADER = 1;

    public static final String[] PROJECTION = {
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME
    };
    public static final int COL_ID = 0;
    public static final int COL_DISPLAY_NAME = 1;

    private  HashMap<String, Uri> imagesList = new HashMap<String, Uri>();
    private ArrayList<String> allImagesNames= null;
    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_image);
        getSupportLoaderManager().initLoader(MEDIA_LOADER, null, this);
        initListView();
    }

    private void initListView(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.image_name,
                R.id.image_name_text_view,
                new String[]{" "}
        );
        listView = (ListView) findViewById(R.id.images_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String imageName =(String) parent.getItemAtPosition(position);
                   // if (imageName.equals(""))return;
                    final Uri mediaUri = imagesList.get(imageName);
                    Intent intent  = new Intent(OpenImageActivity.this,MainActivity.class);
                    intent.putExtra("uri",mediaUri.toString());
            }
        });

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                PROJECTION,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToFirst()) {
            allImagesNames = new ArrayList<>();
            do {
                String imageName = cursor.getString(COL_DISPLAY_NAME);
                long songId = cursor.getLong(COL_ID);
                Uri mediaUri = ContentUris.withAppendedId(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, songId);
                imagesList.put(imageName, mediaUri);
                System.out.println(imageName+"  "+ mediaUri);
                allImagesNames.add(imageName);
            } while (cursor.moveToNext());
            updateAdapter();
        }
    }

    private void updateAdapter(){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.image_name,
                R.id.image_name_text_view,
                allImagesNames
        );
        listView.setAdapter(adapter);

    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}