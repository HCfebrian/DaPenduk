package com.example.dapenduk.data.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.dapenduk.data.model.AdminDao;
import com.example.dapenduk.data.model.DaoMaster;
import com.example.dapenduk.data.model.PendudukDao;

import org.greenrobot.greendao.database.StandardDatabase;

public class DbOpenHelper extends DaoMaster.OpenHelper {
    private static final String TAG = "DEBUG";
    public DbOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        Log.d(TAG, "DB_OLD_Version : " + oldVersion +" DB_NEW_VERSION : "+newVersion);

        MigrationHelper.migrate(new StandardDatabase(db),PendudukDao.class, AdminDao.class);
    }

}
