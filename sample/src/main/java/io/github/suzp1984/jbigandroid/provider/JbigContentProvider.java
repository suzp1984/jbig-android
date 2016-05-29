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
import io.github.suzp1984.jbigandroid.injector.component.ContentProviderComponent;
import io.github.suzp1984.jbigandroid.injector.component.DaggerContentProviderComponent;
import io.github.suzp1984.jbigandroid.injector.module.PersistenceModule;
import io.github.suzp1984.jbigandroid.injector.module.UtilsModule;

public class JbigContentProvider extends ContentProvider {

    @Inject
    DataBaseHelper mDbHelper;

    private ContentProviderComponent mContentProviderComponent;

    public JbigContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return Uri.EMPTY;
    }

    @Override
    public boolean onCreate() {

        getComponent().inject(this);

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        synchronized (this) {
            List<byte[]> jbigs = mDbHelper.getJbigs();

            MatrixCursor c = new MatrixCursor(new String[]{"id", "jbig"});
            c.addRow(jbigs);

            return c;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }

    private ContentProviderComponent getComponent() {
        if (mContentProviderComponent == null) {
            mContentProviderComponent = DaggerContentProviderComponent.builder()
                                        .persistenceModule(new PersistenceModule())
                                        .utilsModule(new UtilsModule(getContext()))
                                        .build();
        }

        return mContentProviderComponent;
    }
}
