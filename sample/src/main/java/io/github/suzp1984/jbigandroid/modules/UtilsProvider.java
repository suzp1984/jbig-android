package io.github.suzp1984.jbigandroid.modules;

import com.squareup.otto.Bus;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.suzp1984.jbigandroid.db.RealmDbOpenHelper;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by moses on 8/31/15.
 */

@Module(
        injects = {RealmDbOpenHelper.class},
        library = true
)
public class UtilsProvider {

    public UtilsProvider(Context context) {
        RealmConfiguration config = new RealmConfiguration.Builder(context).build();
        Realm.setDefaultConfiguration(config);
    }

    @Provides @Singleton
    public Bus provideEventBus() {
        return new Bus();
    }

    @Provides
    public Realm provideRealmDb() {
        return Realm.getDefaultInstance();
    }
}
