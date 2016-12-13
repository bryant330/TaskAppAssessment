package com.example.hou.taskapp;

import java.io.Serializable;

/**
 * Created by Hou on 12/8/2016.
 */

public class TaskData implements Serializable {
    int _id;
    String name, description;

    public TaskData() {

    }

    public TaskData(int id, String name, String description) {
        this._id = id;
        this.name = name;
        this.description = description;
    }

    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }
}
