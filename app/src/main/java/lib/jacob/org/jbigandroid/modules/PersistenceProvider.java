package lib.jacob.org.jbigandroid.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lib.jacob.org.jbigandroid.db.DatabaseHelper;
import lib.jacob.org.jbigandroid.db.RealmDbOpenHelper;

/**
 * Created by moses on 8/31/15.
 */
@Module(
        includes = {
                UtilsProvider.class
        },
        library = true
)
public class PersistenceProvider {
    @Provides @Singleton
    public DatabaseHelper providerDatabaseHelper(RealmDbOpenHelper dbOpenHelper) {
        return dbOpenHelper;
    }
}
