package com.example.to_do_list;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.to_do_list.Model.ToDoModel;
import com.example.to_do_list.Utlis.DatabaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTask extends BottomSheetDialogFragment {

    static final String TAG="AddNewTask";

    //Widgets
    private EditText mEditText;
    private Button mSaveButton;

    //Database Helper
    private DatabaseHelper myDb;

    public static AddNewTask newInstance(){
        return new AddNewTask();
    }

    //To Inflate The Layout
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.add_new_task_layout,container,false);
        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEditText=view.findViewById(R.id.EnterTask);
        mSaveButton= view.findViewById(R.id.ButtonSave);

        myDb= new DatabaseHelper(getActivity());

        //To Check User wanted to update the data or add new data
        boolean isUpdate=false;


        //Fetching the Bundle From TodoAdaptor
        Bundle bundle=getArguments();

        //Check That data is there or not
        //If Not Empty means User Wanted To Update THe Data
        if(bundle != null){
            isUpdate=true;

            //Retrieving THe Data
            //Getting The Key Value From Bundle Into The Edit task in AddNEwTask
            String task =bundle.getString("task");
            mEditText.setText(task);

            //Checking text length is greater than 0 than and than only save button will enables else disabled
            if(task.length()>0){
                mSaveButton.setEnabled(false);
            }

        }

        //It Is Applied When text is changed
        //Three Methods are ther e But we have to apply onTextChanged
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(charSequence.toString().equals("")){
                        mSaveButton.setEnabled(false);
                        mSaveButton.setBackgroundColor(Color.GRAY);
                    }else{
                        mSaveButton.setEnabled(true);
                        mSaveButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //Update
        //It Will Set If Value is updated
        //BUndle having the id of the current task
        boolean finalIsUpdate = isUpdate;
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = mEditText.getText().toString();
                if(finalIsUpdate){
                    myDb.updateTask(bundle.getInt("id"),text);
                }else{
                    ToDoModel item = new ToDoModel();
                    item.setTask(text);
                    item.setStatus(0);
                    myDb.insertTask(item);
                }
                dismiss();
            }
        });

    }

    //It is to communicate between activity and sub activity
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        Activity activity=getActivity();
        if(activity instanceof OnDialogCloseListner){
            ((OnDialogCloseListner)activity).onDialogClose((dialog));
        }
    }
}
