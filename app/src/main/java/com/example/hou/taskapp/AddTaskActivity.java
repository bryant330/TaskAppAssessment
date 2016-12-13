package com.example.hou.taskapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTaskActivity extends AppCompatActivity {

    EditText name, description;
    Button btnAdd;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        dbHelper = DbHelper.getInstance(getApplicationContext());

        name = (EditText) findViewById(R.id.nameField);
        description = (EditText) findViewById(R.id.descriptionField);
        btnAdd = (Button) findViewById(R.id.buttonAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskData taskData = new TaskData();

                if(!name.getText().toString().isEmpty()) {
                    taskData.name = name.getText().toString();
                } else {
                    taskData.name = "";
                }

                if(!description.getText().toString().isEmpty()) {
                    taskData.description = description.getText().toString();
                } else {
                    taskData.description = "";
                }

                dbHelper.insertTaskDetail(taskData);
                Toast.makeText(getApplicationContext(), "TASK CREATED SUCCESSFULLY ", Toast.LENGTH_LONG).show();
                // go back to previous activity
                AddTaskActivity.super.finish();
            }
        });

    }
}
