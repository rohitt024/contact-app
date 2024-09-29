package com.kaps.mycontacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ArrayList<ContactModel> arr= new ArrayList<>();
SearchView searchView;
   RecyclerView recyclerView;
   LinearLayout layout;
    ContactAdapter adapter;
    MyDbHelper myDbHelper;
    ImageView call;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        myDbHelper = new MyDbHelper(this);
        arr = myDbHelper.fetchContact();
        recyclerView=findViewById(R.id.contList);
        Log.d("recylcer id 2",String.valueOf(recyclerView));
        adapter = new ContactAdapter(this,arr);
        SetupAdapter setupAdapter = new SetupAdapter(this,arr,recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
//        setAd(arr);

//----------------------------------------search view works-----------------------------------------------------------

//  View view = LayoutInflater.from(this).inflate(R.layout.contact_layout,null);
//  ConstraintLayout uiContain = view.findViewById(R.id.uiContain);
//  uiContain.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//          Toast.makeText(MainActivity.this, "click on ui conttion", Toast.LENGTH_SHORT).show();
//      }
//  });


//---------------------------------------detail data------------------------------------------------------------


//---------------------------------------------------------------------------------------------------

    }
    public void newContact(View view){
        EditText userName ,userPhone;
        Button addBtn;

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_layout);
        userName = dialog.findViewById(R.id.userName);
        userPhone = dialog.findViewById(R.id.userPhone);
        addBtn = dialog.findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name=""; String phone="";
                if (!userName.getText().toString().equals("")){
                    name=userName.getText().toString();
                }
                else {
                    Toast.makeText(MainActivity.this, "please enter name", Toast.LENGTH_SHORT).show();
                }
                if (!userPhone.getText().toString().equals("")){
                    phone = userPhone.getText().toString();
                }
                else {
                    Toast.makeText(MainActivity.this, "please enter number", Toast.LENGTH_SHORT).show();
                }

               if (!name.equals("") && !phone.equals("")){
                   myDbHelper.insertContact(name,phone);
                   Toast.makeText(MainActivity.this, "new contact added", Toast.LENGTH_SHORT).show();
               }
                arr = myDbHelper.fetchContact();
                SetupAdapter setupAdapter = new SetupAdapter(MainActivity.this,arr,recyclerView);
                adapter.notifyItemInserted(arr.size());
                recyclerView.scrollToPosition(arr.size());
                dialog.dismiss();


            }
        });

        dialog.show();


    }

    public void  setAd(ArrayList<ContactModel> arr){
        recyclerView=findViewById(R.id.contList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContactAdapter(this,arr);
        recyclerView.setAdapter(adapter);
    }

    public  Context cont(){
        return  MainActivity.this;
    }

}