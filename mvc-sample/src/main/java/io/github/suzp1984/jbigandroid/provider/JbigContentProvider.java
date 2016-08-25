package io.github.suzp1984.jbigandroid.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import java.util.List;

import javax.inject.Inject;

import io.github.suzp1984.jbigandroid.JbigApplication;
import io.github.suzp1984.jbigandroid.db.DataBaseHelper;
import io.github.suzp1984.jbigandroid.utils.ByteUtils;

public class JbigContentProvider extends ContentProvider {

    @Inject
    DataBaseHelper mDbHelper;

    public JbigContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not support external Delete");
    }

    @Override
    public String getType(Uri uri) {
        return "text/jbig";
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return Uri.EMPTY;
    }

    @Override
    public boolean onCreate() {

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        if (mDbHelper == null) {
            deferInit();
        }

        List<byte[]> jbigs = mDbHelper.getJbigs();

        MatrixCursor c = new MatrixCursor(new String[]{"id", "jbig"}, jbigs.size());
        int i = 0;
        for (byte[] d : jbigs) {
            c.addRow(new String[] {String.valueOf(i), ByteUtils.byteArray2HexString(d)});
        }

        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }

    private void deferInit() {
        JbigApplication.from(getContext()).getApplicationComponent().inject(this);
    }

}
