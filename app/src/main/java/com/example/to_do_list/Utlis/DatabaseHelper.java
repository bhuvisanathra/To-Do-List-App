package com.example.to_do_list.Utlis;

//Related To Database
//Database Functions
//how to display the database in list

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.to_do_list.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

//Implements Methods Of SqLite
//Implement Constructor Of SqLite
public class DatabaseHelper extends SQLiteOpenHelper {

    //Database Object
    private SQLiteDatabase db;

    //Database Name
    //Table Name
    private static final String DATABASE_NAME="TODO_DATABASE";
    private static final String TABLE_NAME="TODO_TABLE";

    //Column Names
    private static final String COLUMN_1="ID";
    private static final String COLUMN_2="TASK";
    private static final String COLUMN_3="STATUS";


    //A helper class to manage database creation and version management
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creation Of Table
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,TASK TEXT,STATUS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //It Will Upgrade the table and send it to On Create
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    //To Insert Task
    public void insertTask(ToDoModel model){

        //To Get The Permission to write the database
        db=this.getWritableDatabase();

        //It Will Set The Value
        ContentValues values =new ContentValues();

        //Get the value from model gettask
        values.put(COLUMN_2,model.getTask());

        //Status
        values.put(COLUMN_3,0);

        //It Will insert the values to the database
        db.insert(TABLE_NAME,null,values);

    }

    //To Update Task
    public void updateTask(int id,String task){
        //To Get The Permission to write the database
        db=this.getWritableDatabase();

        //It Will Set The Value
        ContentValues values =new ContentValues();

        ///Passing The task
        values.put(COLUMN_2,task);

        //Update
        //update(String table, ContentValues values, String whereClause, String[] whereArgs)
        db.update(TABLE_NAME,values,"ID=?",new  String[]{String.valueOf(id)});
    }

    //To Update The Status
    public void updateStatus(int id, int Status){

        //To Get The Permission to write the database
        db=this.getWritableDatabase();

        //It Will Set The Value
        ContentValues values =new ContentValues();

        //Passing The Status
        values.put(COLUMN_3,Status);

        //Update
        //update(String table, ContentValues values, String whereClause, String[] whereArgs)
        db.update(TABLE_NAME,values,"ID=?",new String[]{String.valueOf(id)});
    }

    //To Delete The Task
    public void deleteTask(int id){

        //To Get The Permission to write the database
        db=this.getWritableDatabase();

        //Delete
        db.delete(TABLE_NAME,"ID=?",new String[]{String.valueOf(id)});
    }

    //Getting All Task
    //Displaying The Data As Recycler View

    public List<ToDoModel> getAllTask(){

        //To Get The Permission to write the database
        db=this.getWritableDatabase();

        //Cursor
        Cursor cursor=null;
        List<ToDoModel> modelList=new ArrayList<>();

        ///Transaction Begins
        db.beginTransaction();
        //Try Catch Block
        try{
            //Provinding Table Name 7 Setting All to NULL
            cursor=db.query(Boolean.parseBoolean(TABLE_NAME),null,null,null,null,null,null,null,null,null);

            //If Cursor is equal to NULL
            if(cursor!=null){
                //Moving Cursor To First Untli Next
                if(cursor.moveToFirst()){
                    do{
                        ToDoModel task= new ToDoModel();
                        task.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_1)));
                        task.setTask(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_2)));
                        task.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_3)));
                        modelList.add(task);
                    }while (cursor.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            cursor.close();
       }
        return modelList;

    }
}
