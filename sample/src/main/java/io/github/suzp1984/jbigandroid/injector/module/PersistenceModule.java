package io.github.suzp1984.jbigandroid.injector.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.suzp1984.jbigandroid.db.DataBaseHelper;
import io.github.suzp1984.jbigandroid.db.RealmDbOpenHelper;

/**
 * Created by jacobsu on 4/24/16.
 */
@Module
public class PersistenceModule {
    @Provides
    @Singleton
    public DataBaseHelper providerDatabaseHelper(RealmDbOpenHelper dbOpenHelper) {
        return dbOpenHelper;
    }
}
