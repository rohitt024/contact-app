package com.kaps.mycontacts;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.RejectedExecutionException;

public class SetupAdapter {

    ContactAdapter adapter;


    public SetupAdapter(Context context,ArrayList<ContactModel> arr,RecyclerView recyclerView) {

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new ContactAdapter(context,arr);
        recyclerView.setAdapter(adapter);


    }


}
