package bogoslovov.kaloyan.drawing;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kaloqn on 3/20/17.
 */

public class OpenImageActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final int MEDIA_LOADER = 1;

    private static final String[] PROJECTION = {
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME
    };
    private static final int COL_ID = 0;
    private static final int COL_DISPLAY_NAME = 1;

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
                new String[]{}
        );
        listView = (ListView) findViewById(R.id.images_list);
        listView.setAdapter(adapter);
        TextView emptyList = (TextView) findViewById(R.id.empty);
        listView.setEmptyView(emptyList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String imageName =(String) parent.getItemAtPosition(position);
                    if (imageName.equals(""))return;
                    final Uri mediaUri = imagesList.get(imageName);
                    MainActivity.run = false;

                    Intent intent  = new Intent(OpenImageActivity.this,MainActivity.class);
                    intent.putExtra("uri",mediaUri.toString());
                    startActivity(intent);
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
                long imageId = cursor.getLong(COL_ID);
                Uri mediaUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageId);

                imagesList.put(imageName, mediaUri);
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