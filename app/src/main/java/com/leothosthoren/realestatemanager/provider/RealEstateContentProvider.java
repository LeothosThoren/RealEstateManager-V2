package com.leothosthoren.realestatemanager.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.leothosthoren.realestatemanager.database.RealEstateDatabase;
import com.leothosthoren.realestatemanager.entities.RealEstate;

public class RealEstateContentProvider extends ContentProvider {

    // FOR DATA
    public static final String AUTHORITY = "com.leothosthoren.realestatemanager.provider";
    public static final String TABLE_NAME = RealEstate.class.getSimpleName();
    public static final Uri URI_REAL_ESTATE = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if (getContext() != null) {
            long userId = ContentUris.parseId(uri);
            final Cursor cursor = RealEstateDatabase.getInstance(getContext()).realEstateDao().getRealEstateWithCursor(userId);
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
        }

        throw new IllegalArgumentException("Failed to query row for uri " + uri);
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "vnd.android.cursor.realEstate/" + AUTHORITY + "." + TABLE_NAME;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (getContext() != null) {
            final long id;
            if (values != null) {
                id = RealEstateDatabase.getInstance(getContext()).realEstateDao().insertRealEstate(RealEstate.fromContentValues(values));
                if (id != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    return ContentUris.withAppendedId(uri, id);
                }
            }
        }

        throw new IllegalArgumentException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        if (getContext() != null) {
            final int count = RealEstateDatabase.getInstance(getContext()).realEstateDao().deleteRealEstate(ContentUris.parseId(uri));
            getContext().getContentResolver().notifyChange(uri, null);
            return count;
        }
        throw new IllegalArgumentException("Failed to delete row into " + uri);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        if (getContext() != null) {
            final int count = RealEstateDatabase.getInstance(getContext()).realEstateDao().updateRealEstate(RealEstate.fromContentValues(values));
            getContext().getContentResolver().notifyChange(uri, null);
            return count;
        }
        throw new IllegalArgumentException("Failed to update row into " + uri);
    }
}
