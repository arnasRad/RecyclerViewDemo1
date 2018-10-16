package com.gmail.arnasrad.recyclerviewdemo.data;



import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;
/**
 * This is a Contract between Classes that dictate how they can
 * talk to each other without giving implementation Details
 */

@Dao
public interface ListItemDao {

    @Query("SELECT * FROM ListItem")
    LiveData<List<ListItem>> getListItems();


    @Query("SELECT * FROM ListItem WHERE itemId = :itemId")
    LiveData<ListItem> getListItemById(String itemId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertListItem(ListItem listItem);

    @Delete
    void deleteListItem(ListItem listItem);
}
