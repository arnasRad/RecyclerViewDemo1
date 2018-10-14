package com.gmail.arnasrad.recyclerviewdemo.view;

import android.view.View;

import com.gmail.arnasrad.recyclerviewdemo.data.ListItem;

import java.util.List;

public interface ViewInterface {
    void startDetailActivity(String dateAndTime, String message, int colorResource, View testViewRoot);

    void setUpAdapterAndView(List<ListItem> listOfDate);
}
