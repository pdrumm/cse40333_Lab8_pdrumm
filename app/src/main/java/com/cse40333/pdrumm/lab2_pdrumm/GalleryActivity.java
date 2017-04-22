package com.cse40333.pdrumm.lab2_pdrumm;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class GalleryActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1;
    int gameId;
    GridView gridview;
    String timeStamp;
    DBHelper dbHelper;
    private String AUTHORITY = "com.cse40333.pdrumm.fileprovider";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        dbHelper = new DBHelper(this);
        Intent i = getIntent();
        gameId = i.getExtras().getInt("gameId");

        gridview = (GridView) findViewById(R.id.grid_view);
        populateGridView();

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // Send intent to ImageActivity
                Intent i = new Intent(GalleryActivity.this, ImageActivity.class);
                TextView tv = (TextView) v.findViewById(R.id.col_id);
                i.putExtra("id", Integer.parseInt(tv.getText().toString()));
                startActivity(i);
            }
        });

        FloatingActionButton addPhotoBtn = (FloatingActionButton) findViewById(R.id.addGalleryPhoto);
        addPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraClick(v);
            }
        });
    }

    private void populateGridView() {
        String[] fields = new String[]{DBHelper.C_IMAGES_ID, DBHelper.C_IMAGES_IMAGE, DBHelper.C_IMAGES_DATE};
        String where = " " + DBHelper.C_IMAGES_GAME + " = ?";
        String[] args = new String[]{Integer.toString(gameId)};
        int[] items = new int[] {R.id.col_id, R.id.book_image, R.id.date_tv};

        Cursor cursor = dbHelper.getSelectEntries(DBHelper.TABLE_IMAGES, fields, where, args);
        SimpleCursorAdapter galleryCursorAdapter = new SimpleCursorAdapter (this, R.layout.image_layout, cursor, fields, items, 0);;

        galleryCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue (View view, Cursor cursor, int columnIndex){
                if (view.getId() == R.id.book_image) {
                    ImageView imageView=(ImageView) view;
                    byte[] imageArray = cursor.getBlob(1);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageArray,0,imageArray.length);
                    imageView.setImageBitmap(bitmap);
                    return true;
                }
                return false;
            }});

        gridview.setAdapter(galleryCursorAdapter);
    }

    public void cameraClick(View v) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        //REMOVE IF USING image RETURNED BY THE camera intent in onActivityResult method
        File storageDirectory = new File(getApplicationContext().getFilesDir(), "images");
        if(!storageDirectory.exists()) storageDirectory.mkdir();
        setTimeStamp();
        File imageFile = new File(storageDirectory, getPictureName());

        Uri pictureUri = FileProvider.getUriForFile(getApplicationContext(),
                AUTHORITY,
                imageFile);
        System.out.println(pictureUri);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
        cameraIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        //=====================================================

        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    private void setTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        timeStamp = sdf.format(new Date());
    }

    private String getPictureName() {
        String imageName = "BookImages" + timeStamp + ".jpg";
        return imageName;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("Hello 2222");
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {

                //USE if image RETURNED BY THE camera intent 'data'
/*                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), imageBitmap, getPictureName(), null);
                imageUri = Uri.parse(path);
*/

                //REMOVE IF USING image RETURNED BY THE camera intent 'data'
                File imagePath = new File(getApplicationContext().getFilesDir(), "images");
                File imageFile = new File( imagePath, getPictureName());
                Uri imageUri = FileProvider.getUriForFile(this, AUTHORITY, imageFile);
                byte[]  byteArray = getBytes(imageUri, 150);
                //==============================================================

                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                String iDate = sdf.format(new Date()).toString();
                ContentValues contentValues = new ContentValues();
                contentValues.put(DBHelper.C_IMAGES_GAME, gameId);
                contentValues.put(DBHelper.C_IMAGES_IMAGE, byteArray);
                contentValues.put(DBHelper.C_IMAGES_DATE, iDate);
                contentValues.put(DBHelper.C_IMAGES_URI, imageUri.toString());
                dbHelper.insertData(DBHelper.TABLE_IMAGES, contentValues);
                System.out.println("image saved");
                populateGridView();
            }
        }
    }

    private byte[] getBytes(Uri imageUri, int maxSize) {
        System.out.println("image saved" + imageUri);
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        Bitmap imageBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        return stream.toByteArray();
    }

}
