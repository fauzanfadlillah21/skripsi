package com.skripsi.fauzanfadlillah.tiket_hellprint.cetakqrcode;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.skripsi.fauzanfadlillah.tiket_hellprint.R;


/**
 * Created by FauzanFadlillah on 24-Nov-17.
 */

public class Cetak_Qrcode extends AppCompatActivity implements View.OnClickListener{
    private String LOG_TAG = "GenerateQRCode";
    TextView enkrip;
    String qrInputText;
    Button share,cetak;
    ImageView myImage;
    String ImagePath;
    Bitmap bitmap;
    Uri URI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generateqrcode);

        enkrip = (TextView) findViewById(R.id.txttiket);
        share=(Button)findViewById(R.id.share);
        cetak=(Button)findViewById(R.id.cetak);

        share.setOnClickListener(this);
        cetak.setOnClickListener(this);


        Intent data = getIntent();
        final int update = data.getIntExtra("update", 0);
        String j = data.getStringExtra("enkripsi");


        if (update == 1) {

            enkrip.setText(j);
            qrInputText = enkrip.getText().toString();


            cetak();


        }


    }




    void cetak(){


        Log.v(LOG_TAG, qrInputText);

        //Find screen size
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 6/6;

        //Encode with a QR Code image
        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrInputText,
                null,
                Contents.Type.TEXT,
                BarcodeFormat.QR_CODE.toString(),
                smallerDimension);
        try {
             bitmap = qrCodeEncoder.encodeAsBitmap();
             myImage = (ImageView) findViewById(R.id.imageView1);
            myImage.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }





    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.share:
                Drawable mDrawable = myImage.getDrawable();
                Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();

                String path = MediaStore.Images.Media.insertImage(getContentResolver(), mBitmap, "Image Description", null);
                Uri uri = Uri.parse(path);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(intent, "Share Image"));
                break;

            case R.id.cetak:



                ImagePath = MediaStore.Images.Media.insertImage(
                        getContentResolver(),
                        bitmap,
                        "demo_image",
                        "demo_image"
                );

                URI = Uri.parse(ImagePath);

                Toast.makeText(Cetak_Qrcode.this, "Qr code berhasil disimpan", Toast.LENGTH_LONG).show();



                break;
        }
    }
}
