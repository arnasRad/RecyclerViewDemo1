package com.gmail.arnasrad.recyclerviewdemo.logic;

import android.view.View;

import com.gmail.arnasrad.recyclerviewdemo.data.DataSourceInterface;
import com.gmail.arnasrad.recyclerviewdemo.data.ListItem;
import com.gmail.arnasrad.recyclerviewdemo.view.ViewInterface;

public class Controller {
    private ListItem temporaryListItem;
    private int temporaryListItemPosition;

    private ViewInterface view;
    private DataSourceInterface dataSource;

    public Controller(ViewInterface view, DataSourceInterface dataSource) {
        this.view = view;
        this.dataSource = dataSource;

        getListFromDataSource();
    }

    public void getListFromDataSource() {
        view.setUpAdapterAndView(
                dataSource.getListOFData()
        );
    }

    public void onListItemClick(ListItem testItem, View viewRoot) {
        view.startDetailActivity(
                testItem.getDateAndTime(),
                testItem.getMessage(),
                testItem.getColorResource(),
                viewRoot
        );
    }

    public void createNewListItem() {
        /*
        To simulate telling the DataSource to create a new record and waiting for it's response,
        we'll simply have it return a new ListItem.

        In a real App, I'd use RxJava 2 (or some other
        API/Framework for Asynchronous Communication) to have the Datasource do this on the
         IO thread, and respond via an Asynchronous callback to the Main thread.
         */

        ListItem newItem = dataSource.createNewListItem();

        view.addNewListItemToView(newItem);
    }

    public void onListItemSwiped(int position, ListItem listItem) {
        // ensure that the view and data layers have consistent state
        dataSource.deleteListItem(listItem);
        view.deleteListItemAt(position);

        temporaryListItemPosition = position;
        temporaryListItem = listItem;

        view.showUndoSnackbar();
    }

    public void onUndoConfirmed() {
        if (temporaryListItem != null) {
            // to ensure View/Data consistency
            dataSource.insertListItem(temporaryListItem);
            view.InsertListItemAt(temporaryListItemPosition, temporaryListItem);

            temporaryListItem = null;
            temporaryListItemPosition = 0;
        }
    }

    public void onSnackBarTimeout() {
        temporaryListItem = null;
        temporaryListItemPosition = 0;
    }
}
