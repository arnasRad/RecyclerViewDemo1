package com.gmail.arnasrad.recyclerviewdemo.data;



import java.util.List;
/**
 * This is a Contract between Classes that dictate how they can
 * talk to each other without giving implementation Details
 */
public interface DataSourceInterface {
    List<ListItem> getListOFData();

    ListItem createNewListItem();

    void deleteListItem(ListItem listItem);

    void insertListItem(ListItem temporaryListItem);
}
