package lib.jacob.org.jbigandroid.modules;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lib.jacob.org.jbigandroid.db.DataBaseHelper;
import lib.jacob.org.jbigandroid.states.ApplicationState;
import lib.jacob.org.jbigandroid.states.JbigDbState;

/**
 * Created by moses on 8/31/15.
 */

@Module(
        includes = {
                UtilsProvider.class,
                PersistenceProvider.class
        },
        library = true
)
public class StateProvider {

    @Provides @Singleton
    public ApplicationState provideApplicationState(Bus bus, DataBaseHelper helper) {
        return new ApplicationState(bus, helper);
    }

    @Provides @Singleton
    public JbigDbState provideJbigDbState(ApplicationState state) {
        return state;
    }
}
