package io.github.suzp1984.jbigandroid.injector.module;

import android.content.Context;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.internal.Preconditions;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by jacobsu on 4/24/16.
 */
@Module
public class UtilsModule {
    public UtilsModule(Context context) {
        Preconditions.checkNotNull(context, "context cannot be null");
        Realm.init(context);
//        RealmConfiguration config = new RealmConfiguration.Builder().build();
//        Realm.setDefaultConfiguration(config);
    }

    @Provides
    @Singleton
    public Bus provideEventBus() {
        return new Bus();
    }

    @Provides
    public Realm provideRealmDb() {
        return Realm.getDefaultInstance();
    }
}
