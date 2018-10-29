package com.jackoyee.geopics;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;

import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.provider.Settings.System.DATE_FORMAT;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final String TAG = "PhotoScreen";
    private ImageView imageView;

    private Button btn;
    private TextView longit,lat, imageName, mynotes;
    private String imageLocation="";
    final int Permission_All = 1;
    ProgressDialog mprogress;


    Uri photoUri;
    double latitude;
    double longitude;
    String timeStamp;
    StorageReference storageReference=null;
    DatabaseReference mRef1;


    private static final int DEFAULT_MIN_WIDTH_QUALITY = 400;        // min pixels
    public static int minWidthQuality = DEFAULT_MIN_WIDTH_QUALITY;
    public static final String DATE_FORMAT = "yyyyMMdd_HHmmss";
    public static final String IMAGE_DIRECTORY = "ImageScalling";

    GPSTracker gps;

    String imageFilePath;
    String imageFileName;
//    private File file;
//    private File sourceFile;
//    private File destFile;
//    private Uri imageCaptureUri;

    private SimpleDateFormat dateFormatter;

    private DrawerLayout drawer;
    private  ActionBarDrawerToggle toggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        StrictMode.VmPolicy.Builder newbuilder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(newbuilder.build());


        lat = (TextView) findViewById(R.id.lati);
        longit = (TextView) findViewById(R.id.longit);
        imageView = (ImageView) findViewById(R.id.capturedImage);
        imageName=(EditText)findViewById(R.id.image_name);
        mynotes=(EditText)findViewById(R.id.notes);


//        dateFormatter = new SimpleDateFormat(
//        DATE_FORMAT, Locale.US);
//        initView();

        mprogress=new ProgressDialog(this);

        //Storage initialized
        storageReference = FirebaseStorage.getInstance().getReference();
        mRef1 = FirebaseDatabase.getInstance().getReference("Homes");


        //pictureSaveFolderPath = getExternalCacheDir();

        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE,
         Manifest.permission.READ_EXTERNAL_STORAGE};

        if (!hasPermissions(this, permissions)) {

            ActivityCompat.requestPermissions(this, permissions, Permission_All);

          }

        btn = (Button) findViewById(R.id.sendBtn);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent camIntent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //startActivityForResult(camIntent,CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

//               File picDirectory=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//               String picName=getPictureName();
//               File imageFile=new File(picDirectory,picName);
////             picUri=Uri.fromFile(imageFile);.....causes UriException in sdk=>24
//
//                picUri=FileProvider.getUriForFile(MainActivity.this,BuildConfig.APPLICATION_ID+ ".provider",
//                        imageFile);

//                Uri photoURI = FileProvider.getUriForFile(MainActivity.this,
//                        BuildConfig.APPLICATION_ID + ".provider",
//                        createImageFile());

//BELLOW IS THE INITIAL WORKING CODE (Nougat too)
                File photoFile=null;

                try {
                   photoFile=createImage();

                   } catch (IOException e) {
                    e.printStackTrace();
                }
                if (photoFile!=null){
                    photoUri=Uri.fromFile(photoFile);
                    //FileProvider.getUriForFile(MainActivity.this,BuildConfig.APPLICATION_ID,photoFile);
                    camIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                    startActivityForResult(camIntent,CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                  }

            }
        });
    }

    public static boolean hasPermissions(Context context, String... permissions) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {

                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }

            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
                imageView.setImageURI(null);
                imageView.setImageURI(photoUri);
                Log.i(TAG,"image uri url"+photoUri);
            }

         }

        gps = new GPSTracker(MainActivity.this);
        if(gps.canGetLocation())
        {
            latitude = (float) gps.getLatitude();
            longitude= (float) gps.getLongitude();

            String  finalLatitude=Double.toString(latitude);
            String finalLongitude=Double.toString(longitude);

            longit.setText(finalLongitude);
            lat.setText(finalLatitude);
            // Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }


        }

    public void onClickAddUploadData(View view) {

        final String imageName_Str=imageName.getText().toString().trim();
        final String mynotes_Str=mynotes.getText().toString().trim();

        if (!TextUtils.isEmpty(imageName_Str) &&!TextUtils.isEmpty(mynotes_Str) ){
            mprogress.setMessage("Uploading...");
            mprogress.show();


            final StorageReference filepath = storageReference.child("images").child(photoUri.getLastPathSegment());

            filepath.putFile(photoUri)
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                    double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
//
//                    MyprogressBar.setProgress((int)progress);
//                }
//            })
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final Uri  downloadUri =uri;
//                            final String downloadUri=uri.toString();


                    Log.i(TAG,"image uri url"+downloadUri);
                   final  DatabaseReference newPost=mRef1.push();
                    newPost.child("Image_Name").setValue(imageName_Str);
                    newPost.child("Latitude").setValue(latitude);
                    newPost.child("Longitude").setValue(longitude);
                    newPost.child("Short_Notes").setValue(mynotes_Str);
                    newPost.child("Apartment_Image").setValue(downloadUri.toString());
                  // newPost.child("Apartment_Image") .setValue(downloadUri);


                            mprogress.dismiss();
                            Toast.makeText(MainActivity.this, "Upload Successful", Toast.LENGTH_LONG).show();

                        }
                    });
                }
            });


          }
        else {
            Toast.makeText(MainActivity.this, "You need to include some description", Toast.LENGTH_SHORT).show();
        }


    }


    @SuppressLint("SimpleDateFormat")
    private String getPictureName() {

        timeStamp= new SimpleDateFormat("yyyyMMdd_hhmmss").format(new Date());

        return "connected_home"+timeStamp+".jpg";

        }
       private File createImage() throws IOException{

            String timeStamp=
                    new SimpleDateFormat("yyyyMMdd_hhmmss", Locale.getDefault()).format(new Date());

             imageFileName= "Connected" +timeStamp+"_";

             File storageDir=getExternalFilesDir(Environment.DIRECTORY_PICTURES);

             File image=File.createTempFile(imageFileName,
                    ".jpg",storageDir);
            image.mkdir();
            imageFilePath=image.getAbsolutePath();

            return image;

         }

//         public void reduceImage(){
////             imageFilePath;
//             int targetW=imageView.getWidth();
//             int targetH=imageView.getHeight();
//
//             BitmapFactory.Options bmOption=new BitmapFactory.Options();
//             bmOption.inJustDecodeBounds=true;
//
//             BitmapFactory.decodeFile(imageFilePath, bmOption);
//             int photoW=bmOption.outWidth;
//             int photoH=bmOption.outHeight;
//
//             int scaleFactor =Math.min(photoW/targetW,photoH/targetH);
//
//             bmOption.inJustDecodeBounds=false;
//             bmOption.inSampleSize=scaleFactor;
//             bmOption.inPurgeable=true;
//
//            Bitmap bitmap= BitmapFactory.decodeFile(imageFilePath,bmOption);
//            imageView.setImageBitmap(bitmap);
//
//         }

        public void resizedImage(){

        Uri newUri=photoUri;

//        Bitmap rawTakenImage=BitmapFactory.decodeFile(newUri.getPath());
//      Bitmap resizedBitmap = BitmapScaler.scaleToFitWidth(rawTakenImage, SOME_WIDTH);
//
//            // Configure byte output stream
//            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//// Compress the image further
//            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
//// Create a new file for the resized bitmap (`getPhotoFileUri` defined above)
//         //   Uri resizedUri = createImage(imageFileName + "_resized");
//            Uri resizedUri = createImage()
//            File resizedFile = new File(resizedUri.getPath());
//            resizedFile.createNewFile();
//            FileOutputStream fos = new FileOutputStream(resizedFile);
//// Write the bytes of the bitmap to file
//            fos.write(bytes.toByteArray());
//            fos.close();


        }

  }





