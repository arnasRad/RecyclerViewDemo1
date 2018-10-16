package com.gmail.arnasrad.recyclerviewdemo.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.gmail.arnasrad.recyclerviewdemo.data.ListItemRepository;

public class CustomViewModelFactory implements ViewModelProvider.Factory {
    private final ListItemRepository repository;

    public CustomViewModelFactory(ListItemRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ListItemCollectionViewModel.class)) {
            return (T) new ListItemCollectionViewModel(repository);
        }
        else if(modelClass.isAssignableFrom(ListItemViewModel.class)) {
            return (T) new ListItemViewModel(repository);
        }
        else if(modelClass.isAssignableFrom(NewListItemViewModel.class)) {
            return (T) new NewListItemViewModel(repository);
        }
        else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }
}
