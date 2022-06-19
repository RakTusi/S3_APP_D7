package com.example.s3_app_d7;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Currency;

public class MyProvider extends ContentProvider {

    private static final int TABLE1_DIR = 0;

    private static final int TABLE1_ITEM = 1;

    public static final String AUTHORITY = "com.example.s3_app_d7.provider";

    private static UriMatcher uriMatcher;

    private MyDataBaseHelper dbHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "address", TABLE1_DIR);
    }


    @Override
    public boolean onCreate() {
        dbHelper = new MyDataBaseHelper(getContext(), "test.db", null, 1);
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case TABLE1_DIR:
                cursor = db.query("address", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case TABLE1_ITEM:
                String name = uri.getPathSegments().get(1);
                cursor = db.query("address", projection, "name=?", new String[]{name}, null, null, sortOrder);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case TABLE1_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.s3_app_d7.provider.address";
            case TABLE1_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.s3_app_d7.provider.address";
            default:
                break;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)) {
            case TABLE1_DIR:
            case TABLE1_ITEM:
                long newID = db.insert("address", null, values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/address/" + newID);
                break;
        }
        return uriReturn;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
