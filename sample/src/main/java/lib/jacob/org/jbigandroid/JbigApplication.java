package lib.jacob.org.jbigandroid;

import android.app.Application;
import android.content.Context;

import javax.inject.Inject;

import dagger.ObjectGraph;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import lib.jacob.org.jbigandroid.controller.MainController;
import lib.jacob.org.jbigandroid.modules.ContextProvider;
import lib.jacob.org.jbigandroid.modules.InjectorProvider;
import lib.jacob.org.jbigandroid.modules.PersistenceProvider;
import lib.jacob.org.jbigandroid.modules.StateProvider;
import lib.jacob.org.jbigandroid.modules.UtilsProvider;
import lib.jacob.org.jbigandroid.utils.Injector;

/**
 * Created by moses on 8/28/15.
 */
public class JbigApplication extends Application implements Injector {

    private ObjectGraph mObjectGraph;

    @Inject
    MainController mController;

    public static JbigApplication from(Context context) {
        return  (JbigApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mObjectGraph = ObjectGraph.create(new ContextProvider(this),
                new InjectorProvider(this),
                new UtilsProvider(this),
                new StateProvider(),
                new PersistenceProvider());

        mObjectGraph.inject(this);
    }

    @Override
    public void inject(Object object) {
        mObjectGraph.inject(object);
    }

    public MainController getMainController() {
        return mController;
    }
    
    public ObjectGraph getObjectGraph() {
        return mObjectGraph;
    }
}