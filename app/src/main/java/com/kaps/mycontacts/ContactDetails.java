package com.kaps.mycontacts;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ContactDetails extends AppCompatActivity {
    private  static  final  int REQ_CALL=1;
    private  static  final  int REQ_GALL=2;
    TextView detName,detPhone;
    ImageView detCall,detMsg,backBtn,detImg;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        getSupportActionBar().hide();



        //=======================id 's==========================================================
            detName = findViewById(R.id.detName);
            detPhone = findViewById(R.id.detPhone);
            detCall = findViewById(R.id.detCall);
            detMsg = findViewById(R.id.detMsg);
            backBtn = findViewById(R.id.backBtn);
            detImg = findViewById(R.id.detImg);

        //========================get data==========================================================
        Intent iDet =  getIntent();
        String name = iDet.getStringExtra("name");
        String phone = iDet.getStringExtra("phone");
        detName.setText(name);
        detPhone.setText(phone);
        //==================================================================================

        //make calls
        detCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = detPhone.getText().toString();
                String name = detName.getText().toString();
                new MakeCalls(ContactDetails.this,phone,name);
//                Toast.makeText(ContactDetails.this, "call to"+detName, Toast.LENGTH_SHORT).show();
            }
        });

        //make messages
        detMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imsg = new Intent(Intent.ACTION_SENDTO);
                imsg.setData(Uri.parse("smsto:"+ Uri.encode("+91"+detPhone.getText().toString())));
                imsg.putExtra("sms_body","hello");
                startActivity(imsg);

            }
        });

        //set image task
        detImg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent icon = new Intent(Intent.ACTION_PICK);
//                icon.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                icon.setType("/image/*");
                startActivityForResult(icon,REQ_GALL);
                Toast.makeText(ContactDetails.this, "clicked at img", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

       // back pressed
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (resultCode==REQ_GALL){
                detImg.setImageURI(data.getData());
            }
        }
    }
}