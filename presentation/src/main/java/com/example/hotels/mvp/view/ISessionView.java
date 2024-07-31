package com.example.hotels.mvp.view;

import com.example.domain.model.User;

public interface ISessionView extends IDataView {
    void updateUI(User user);
    void onSignedIn();
    void onSignedOut();
}
