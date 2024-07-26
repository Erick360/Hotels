package com.example.hotels.mvp.view;

import com.example.domain.model.User;

public interface ILoginView extends ISessionView{ }
public interface IsessionView extends IDataView {
    void update updateUI(User user);
    void onSignedIn();
    void onSignedOut();
}
