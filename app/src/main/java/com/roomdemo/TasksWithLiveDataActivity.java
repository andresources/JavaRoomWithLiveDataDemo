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

import java.util.List;


public class TasksWithLiveDataActivity extends AppCompatActivity {
    EditText editTextTask,editTextDesc,editTextFinishBy;
    Button button_save;
    TextView tvNoTasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_with_live_data);
        editTextTask = findViewById(R.id.editTextTask);
        editTextDesc = findViewById(R.id.editTextDesc);
        editTextFinishBy = findViewById(R.id.editTextFinishBy);
        button_save = findViewById(R.id.button_save);

        tvNoTasks  = findViewById(R.id.tvNoTasks);

        TaskDatabase taskDatabase=TaskDatabase.getInstance(TasksWithLiveDataActivity.this);
        TaskDao taskDao=taskDatabase.getTaskDao();
        LiveData<List<Task>> list=taskDao.getAllTasksWithLiveData();
        list.observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                tvNoTasks.setText("No of Tasks Live Data: "+tasks.size()+"");
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
            TaskDatabase taskDatabase=TaskDatabase.getInstance(TasksWithLiveDataActivity.this);
            TaskDao taskDao=taskDatabase.getTaskDao();
            taskDao.insert(task);
            return null;
        }
    }
}