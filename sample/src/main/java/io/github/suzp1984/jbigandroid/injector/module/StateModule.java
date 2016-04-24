package io.github.suzp1984.jbigandroid.injector.module;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.suzp1984.jbigandroid.db.DataBaseHelper;
import io.github.suzp1984.jbigandroid.states.ApplicationState;
import io.github.suzp1984.jbigandroid.states.JbigDbState;

/**
 * Created by jacobsu on 4/24/16.
 */
@Module
public class StateModule {
    @Provides
    @Singleton
    public ApplicationState provideApplicationState(Bus bus, DataBaseHelper helper) {
        return new ApplicationState(bus, helper);
    }

    @Provides @Singleton
    public JbigDbState provideJbigDbState(ApplicationState state) {
        return state;
    }
}
