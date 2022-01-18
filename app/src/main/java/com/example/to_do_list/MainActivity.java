package com.example.to_do_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.to_do_list.Adaptor.ToDoAdaptor;
import com.example.to_do_list.Model.ToDoModel;
import com.example.to_do_list.Utlis.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListner {

    //Declaration
    private RecyclerView mRecyclerview;
    private FloatingActionButton fab;
    private DatabaseHelper mydb;

    private List<ToDoModel> mList;
    private ToDoAdaptor adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Binding
        mRecyclerview=findViewById(R.id.RecyclerView);
        fab=findViewById(R.id.floatButton);
        mydb=new DatabaseHelper(MainActivity.this);

        mList = new ArrayList<>();
        adaptor=new ToDoAdaptor(mydb,MainActivity.this);

        //Recycler
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setAdapter(adaptor);

        mList=mydb.getAllTask();
        Collections.reverse(mList);
        adaptor.setTasks(mList);

        //Float Button Click Listener
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
            }
        });
    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
            mList=mydb.getAllTask();
        Collections.reverse(mList);
        adaptor.setTasks(mList);
        adaptor.notifyDataSetChanged();
    }
}