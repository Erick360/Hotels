package com.example.hotels.mvp.view;

import com.example.domain.model.User;

public class ISessionView  implements IDataView{
    void updateUI(User user);
    void onSignedIn();
    void onSignedOut();

}
