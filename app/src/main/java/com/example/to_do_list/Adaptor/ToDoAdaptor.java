package com.example.to_do_list.Adaptor;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_do_list.MainActivity;
import com.example.to_do_list.Model.ToDoModel;
import com.example.to_do_list.R;
import com.example.to_do_list.Utlis.DatabaseHelper;

import java.util.List;

public class ToDoAdaptor extends RecyclerView.Adapter<ToDoAdaptor.MyViewHolder> {

    private List<ToDoModel> mList;
    private final MainActivity ACTIVITY;
    private final DatabaseHelper MYDB;

    public ToDoAdaptor(DatabaseHelper myDB,MainActivity ACTIVITY){
        this.ACTIVITY = ACTIVITY;
        this.MYDB =myDB;
    }


    //Methods Of MyViewHolder
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Layout
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ToDoModel item=mList.get(position);
        holder.mCheckBox.setText(item.getTask());
        holder.mCheckBox.setChecked(toBoolean(item.getStatus()));
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    MYDB.updateStatus(item.getId(),1);
                }else{
                    MYDB.updateStatus(item.getId(),0);
                }
            }
        });
    }

    //To Check True Of False
    public boolean toBoolean(int num){
        return num!=0;
    }

    public Context getContext(){
        return ACTIVITY;
    }

    //To Set Task
    public void setTasks(List<ToDoModel> mList){
        //Set The Task
        this.mList=mList;
        //Notified That Data Is Changed
        notifyDataSetChanged();
    }

    //To Delete Task
    public void deleteTask(int position){

        //Created Instance Of TodoModel
        ToDoModel item=mList.get(position);
        //Called Deletetask From ToDoModel
        MYDB.deleteTask(item.getId());
        //Removed
        mList.remove(position);
        //Notified That Data Is Changed
        notifyItemChanged(position);
    }

    //To Edit Item
    public void editItem(int position){

        ToDoModel item=mList.get(position);
        //Bundle Is Used to pass the data between Activity
        Bundle bundle=new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("task", item.getTask());
    }


    @Override
    public int getItemCount() {
      return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        CheckBox mCheckBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mCheckBox=itemView.findViewById(R.id.checkbox);
        }
    }

}
