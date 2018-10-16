package com.gmail.arnasrad.recyclerviewdemo.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.gmail.arnasrad.recyclerviewdemo.data.ListItem;
import com.gmail.arnasrad.recyclerviewdemo.data.ListItemRepository;

import java.util.List;

import javax.inject.Inject;

public class ListItemCollectionViewModel extends ViewModel {

    private ListItemRepository repository;

    @Inject
    public ListItemCollectionViewModel(ListItemRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<ListItem>> getListItems() {
        return repository.getListOfData();
    }

    public void deleteListItem(ListItem listItem) {
        DeleteItemTask task = new DeleteItemTask();
        task.execute(listItem);
    }

    private class DeleteItemTask extends AsyncTask<ListItem, Void, Void> {

        @Override
        protected Void doInBackground(ListItem... listItems) {
            repository.deleteListItem(listItems[0]);
            return null;
        }
    }
}
