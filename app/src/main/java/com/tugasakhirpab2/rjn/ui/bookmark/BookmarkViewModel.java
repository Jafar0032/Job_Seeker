package com.tugasakhirpab2.rjn.ui.bookmark;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BookmarkViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BookmarkViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Bookmark fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
