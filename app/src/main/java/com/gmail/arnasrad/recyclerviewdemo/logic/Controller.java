package com.gmail.arnasrad.recyclerviewdemo.logic;

import android.view.View;

import com.gmail.arnasrad.recyclerviewdemo.data.DataSourceInterface;
import com.gmail.arnasrad.recyclerviewdemo.data.ListItem;
import com.gmail.arnasrad.recyclerviewdemo.view.ViewInterface;

public class Controller {
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
}
