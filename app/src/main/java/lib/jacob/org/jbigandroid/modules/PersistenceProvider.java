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
        injects = RealmDbOpenHelper.class,
        includes = {
                UtilsProvider.class
        }
)
public class PersistenceProvider {
    @Provides @Singleton
    public DatabaseHelper getDatabaseHelper() {
        return new RealmDbOpenHelper();
    }
}
