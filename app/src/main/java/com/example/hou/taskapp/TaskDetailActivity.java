package com.example.hou.taskapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TaskDetailActivity extends AppCompatActivity {
    EditText nameField;
    EditText descriptionField;
    Button editButton;
    DbHelper dbHelper;
    int id;
    String name, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        nameField = (EditText) findViewById(R.id.nameField);
        descriptionField = (EditText) findViewById(R.id.descriptionField);
        editButton = (Button) findViewById(R.id.buttonEdit);
        dbHelper = DbHelper.getInstance(getApplicationContext());

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            id = extras.getInt("id");
            name = extras.getString("name");
            description = extras.getString("description");
            nameField.setText(name);
            descriptionField.setText(description);
        }

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskData taskData = new TaskData(id, name, description);
                taskData.name = nameField.getText().toString();
                taskData.description = descriptionField.getText().toString();
                dbHelper.updateTask(taskData);
                Toast.makeText(getApplicationContext(), "TASK UPDATED SUCCESSFULLY", Toast.LENGTH_LONG).show();

            }
        });
    }
}
