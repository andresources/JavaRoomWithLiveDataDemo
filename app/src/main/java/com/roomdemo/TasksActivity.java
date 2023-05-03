package com.roomdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TasksActivity extends AppCompatActivity {
    EditText editTextTask,editTextDesc,editTextFinishBy;
    Button button_save,button_get;
    TextView tvNoTasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        editTextTask = findViewById(R.id.editTextTask);
        editTextDesc = findViewById(R.id.editTextDesc);
        editTextFinishBy = findViewById(R.id.editTextFinishBy);
        button_save = findViewById(R.id.button_save);
        button_get = findViewById(R.id.button_get);
        tvNoTasks  = findViewById(R.id.tvNoTasks);

        button_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetDataAsyn().execute();
            }
        });
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task =new Task(editTextTask.getText().toString(),editTextDesc.getText().toString(),editTextFinishBy.getText().toString(),false);
                new InsertDataAsyn(task).execute();
            }
        });
    }
    class GetDataAsyn extends AsyncTask<Void,Void,Void>{
        int task_count;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            task_count =0;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            TaskDatabase taskDatabase=TaskDatabase.getInstance(TasksActivity.this);
            TaskDao taskDao=taskDatabase.getTaskDao();
            List<Task> list=taskDao.getAll();
            task_count = list.size();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            tvNoTasks.setText("No of Tasks : "+task_count);
        }
    }

    class InsertDataAsyn extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
        }

        Task task;
        public InsertDataAsyn(Task task){
            this.task = task;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            TaskDatabase taskDatabase=TaskDatabase.getInstance(TasksActivity.this);
            TaskDao taskDao=taskDatabase.getTaskDao();
            taskDao.insert(task);
            return null;
        }
    }
}