package com.example.to_do_list.Model;

public class ToDoModel {

    //For Task
    //For Id And To Check The Status Checked Or Unchecked
    private String Task;
    private int id,status;

    //Getter And Setter
    //Getters and setters are used to protect your data
    //Getter method returns its value while a setter method sets or updates its value.
    public String getTask() {
        return Task;
    }

    public void setTask(String task) {
        Task = task;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
