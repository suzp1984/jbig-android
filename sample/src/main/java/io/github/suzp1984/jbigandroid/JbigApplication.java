package io.github.suzp1984.jbigandroid;

import android.app.Application;
import android.content.Context;

import javax.inject.Inject;

import dagger.ObjectGraph;
import io.github.suzp1984.jbigandroid.controller.MainController;
import io.github.suzp1984.jbigandroid.modules.ContextProvider;
import io.github.suzp1984.jbigandroid.modules.InjectorProvider;
import io.github.suzp1984.jbigandroid.modules.PersistenceProvider;
import io.github.suzp1984.jbigandroid.modules.StateProvider;
import io.github.suzp1984.jbigandroid.modules.UtilsProvider;
import io.github.suzp1984.jbigandroid.utils.Injector;


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
