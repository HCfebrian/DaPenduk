package com.example.dapenduk;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class Utils {

    public static void showSnackbar(View view, String sting) {
        final Snackbar snackbar = Snackbar.make(view, sting, Snackbar.LENGTH_LONG);
        snackbar.setAction("close", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        }).show();
    }
}
