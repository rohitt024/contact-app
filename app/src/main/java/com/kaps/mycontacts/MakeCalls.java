package com.kaps.mycontacts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class MakeCalls {
    MakeCalls(Context context,String phone,String name){

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+phone));
        context.startActivity(intent);
        Toast.makeText(context, "call to "+name, Toast.LENGTH_SHORT).show();
    }
}
