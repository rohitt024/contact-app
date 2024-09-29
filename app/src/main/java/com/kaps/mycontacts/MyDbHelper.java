package com.kaps.mycontacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MyDbHelper extends SQLiteOpenHelper {
    private static final  String DB_NAME = "contact";//database name
    private  static  final int VERSION = 1;
    private  static  final String  TABLE_CONTACT="contacts"; //tables name inside the databases
    private  static  final  String   ID="id"; // col 1 of table contact
    private  static  final  String   NAME="name";// col 2
    private  static  final  String   NUMBER="number";//col 3
    public MyDbHelper(@Nullable Context context) {
        super(context,DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_CONTACT+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+NAME+" TEXT,"+NUMBER+" TEXT"+")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
         sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_CONTACT);
         onCreate(sqLiteDatabase);
    }

    public void  insertContact(String name , String phone){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME,name);
        values.put(NUMBER,phone);
        sqLiteDatabase.insert(TABLE_CONTACT,null,values);

    }
    public ArrayList<ContactModel> fetchContact(){
        ArrayList<ContactModel> contactData = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
       Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_CONTACT ,null);
       while (cursor.moveToNext()){
           contactData.add(new ContactModel(R.drawable.user,cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
//           Log.d("idddddddddddddddddddddddd",String.valueOf(cursor.getInt(0)));
       }
//       sqLiteDatabase.close()

        return  contactData;
    }
    //udpate data
    public  void updateContact(ContactModel contactModel){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME,contactModel.name);
        values.put(NUMBER,contactModel.phone);
        sqLiteDatabase.update(TABLE_CONTACT,values,ID+" = "+ contactModel.id,null);

    }
///delete code or method for deletion of the contact
    public void delContact(int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_CONTACT,ID+"= ? ",new String[]{String.valueOf(id)});
    }
}
