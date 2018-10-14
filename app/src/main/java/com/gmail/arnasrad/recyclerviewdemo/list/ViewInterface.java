package com.gmail.arnasrad.recyclerviewdemo.list;

import android.view.View;

import com.gmail.arnasrad.recyclerviewdemo.data.ListItem;

import java.util.List;

public interface ViewInterface {
    void startDetailActivity(String dateAndTime, String message, int colorResource, View viewRoot);

    void setUpAdapterAndView(List<ListItem> listOfDate);

    void addNewListItemToView(ListItem newItem);

    void deleteListItemAt(int position);

    void showUndoSnackbar();

    void InsertListItemAt(int temporaryListItemPosition, ListItem temporaryListItem);
}
