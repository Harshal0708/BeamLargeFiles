package com.example.android.beamlargefiles.singleton;

import com.example.android.beamlargefiles.models.Contact;

public class SingletonClass {

    public Contact listItem;
    private static SingletonClass mInstance;

    private SingletonClass() {
    }

    public static SingletonClass getInstance(){
        if(mInstance == null)
            mInstance = new SingletonClass();
        return mInstance;
    }

}