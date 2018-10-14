package com.gmail.arnasrad.recyclerviewdemo.data;

import android.arch.persistence.room.Database;

@Database(entities = {ListItem.class}, version = 1)
public abstract class ListItemDatabase {
    
}
