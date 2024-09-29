package com.kaps.mycontacts;

import static androidx.core.content.ContextCompat.startActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    Context context;
    private  static  final  int REQ_CALL=1;
    ArrayList<ContactModel> arrContact;
    MyDbHelper myDbHelper;
    RecyclerView recyclerView;
    static boolean refresh = false;

    public ContactAdapter(Context context,ArrayList<ContactModel> arrContact) {
        this.context=context;
        this.arrContact = arrContact;
        myDbHelper = new MyDbHelper(context);


    }

//
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.contact_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.userImg.setImageResource(arrContact.get(position).userImg);
        String data = String.valueOf(arrContact.get(position).id);
        holder.idt.setText(data);
        holder.namet.setText(arrContact.get(position).name);
        holder.phonet.setText(arrContact.get(position).phone);


//call btn make calls--------------------------------------------------------------------------------------------------------------
        holder.callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE )== PackageManager.PERMISSION_GRANTED){
                   String no =holder.phonet.getText().toString();
                   String name = holder.namet.getText().toString();
                    new MakeCalls(context,no,name);
//                    Intent intent = new Intent(Intent.ACTION_CALL);
//                    intent.setData(Uri.parse("tel:"+no));
//                    context.startActivity(intent);
//                    Toast.makeText(context, "call to "+name, Toast.LENGTH_SHORT).show();

                }
                else{
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE},REQ_CALL);
                }

            }
        });

//update code on the clickuiContainer
        holder.userImg.setOnClickListener(new View.OnClickListener() {
            String id =  holder.idt.getText().toString();
            TextView heading;
            Button updateBtn;
            TextView userName,userPhone;

            @Override
            public void onClick(View view) {

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.add_layout);
                heading = dialog.findViewById(R.id.textView2);
                updateBtn = dialog.findViewById(R.id.addBtn);
                userName = dialog.findViewById(R.id.userName);
                userPhone = dialog.findViewById(R.id.userPhone);
                heading.setText(" Update ");
                updateBtn.setText("save");

                userName.setText(holder.namet.getText().toString());
                userPhone.setText(holder.phonet.getText().toString());
                updateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view){

                        Log.d("heeloo inside id",id);
                        String name="";
                        String phone="";

                        if (!userName.getText().toString().equals("")){
                            name=userName.getText().toString();
                        }
                        else {
                            Toast.makeText(context, "please enter name", Toast.LENGTH_SHORT).show();
                        }
                        if (!userPhone.getText().toString().equals("")){
                            phone = userPhone.getText().toString();
                        }
                        else {
                            Toast.makeText(context, "please enter number", Toast.LENGTH_SHORT).show();
                        }
                        if (!id.equals("")&& !name.equals("") && !phone.equals("")){

                      try{
                          Log.d("data",id+name+phone);
                         myDbHelper.updateContact(new ContactModel(R.drawable.user,Integer.parseInt(id),name,phone));
                         arrContact =myDbHelper.fetchContact();
                         notifyItemChanged(holder.getAdapterPosition());

                      }
                      catch (Exception e){
                          e.printStackTrace();
                      }


                        }dialog.dismiss();
//                        Log.d("id postion",String.valueOf(position);
                    }
                });
                dialog.show();

            }
        });

///delete contact
        holder.uiContainer.setOnLongClickListener(new View.OnLongClickListener() {
            String id =  holder.idt.getText().toString();
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete contact");
                builder.setMessage("Are you sure want delete contact");
                builder.setIcon(R.drawable.baseline_delete_24);
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                      try{
                          myDbHelper.delContact(Integer.parseInt(id));
                          arrContact = myDbHelper.fetchContact();
                          notifyItemRemoved(holder.getAdapterPosition());

                      }
                      catch (NullPointerException e){
                          e.printStackTrace();
                      }

                    }
                });
                builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
                return true;
            }
        });

//detail intent data
        holder.uiContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent iDetails = new Intent(context,ContactDetails.class);
               iDetails.putExtra("name",holder.namet.getText().toString());
               iDetails.putExtra("phone",holder.phonet.getText().toString());
               context.startActivity(iDetails);
            }
        });
//================================================
    }

    @Override
    public int getItemCount() {
        return arrContact.size();
    }

//nested class
    public class  ViewHolder extends RecyclerView.ViewHolder{
        TextView namet,phonet,idt;
        ImageView userImg,callBtn;
        ConstraintLayout uiContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namet = itemView.findViewById(R.id.name);
            phonet = itemView.findViewById(R.id.phone);
            userImg = itemView.findViewById(R.id.imageView);
            callBtn = itemView.findViewById(R.id.callBtn);
            idt = itemView.findViewById(R.id.getID);
            uiContainer = itemView.findViewById(R.id.uiContain);





        }
    }





}
