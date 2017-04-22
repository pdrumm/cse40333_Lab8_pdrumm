package com.cse40333.pdrumm.lab2_pdrumm;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;


public class ImageActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        // Get intent data
        Intent i = getIntent();

        // Selected image id
        int imageId = i.getExtras().getInt("id");
//        ImageAdapter imageAdapter = new ImageAdapter(this);

        ImageView imageView = (ImageView) findViewById(R.id.image_view);
//        imageView.setImageResource(imageAdapter.mThumbIds[position]);

        DBHelper dbHelper = new DBHelper(this);

        String[] fields = new String[]{DBHelper.C_IMAGES_ID, DBHelper.C_IMAGES_IMAGE};
        String where = " " + DBHelper.C_IMAGES_ID + " = ?";
        String[] args = new String[]{Integer.toString(imageId)};

        Cursor cursor = dbHelper.getSelectEntries(DBHelper.TABLE_IMAGES, fields, where, args);

        if (cursor.moveToFirst()) {
            byte[] imageArray = cursor.getBlob(1);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageArray,0,imageArray.length);
            imageView.setImageBitmap(bitmap);
        }

    }
}
