package com.example.to_do_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.to_do_list.Utlis.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    //Declaration
    private RecyclerView mRecyclerview;
    private FloatingActionButton fab;
    private DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Binding
        mRecyclerview=findViewById(R.id.RecyclerView);
        fab=findViewById(R.id.floatButton);
        mydb=new DatabaseHelper(MainActivity.this);

        //Float Button Click Listener
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}