package com.roomdemo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 1)
public abstract class TaskDatabase extends RoomDatabase {
    public abstract TaskDao getTaskDao();
    private static TaskDatabase instance;
    public static TaskDatabase getInstance(Context context){
        if(instance==null) {
            instance =  Room.databaseBuilder(context.getApplicationContext(), TaskDatabase.class, "course_database")
                    //.allowMainThreadQueries()
                    .build();
            return instance;
        }else{
            return instance;
        }
    }
}
