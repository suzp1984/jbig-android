package io.github.suzp1984.jbigandroid.injector.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Provides;
import io.github.suzp1984.jbigandroid.BaseDrawerActivity;
import io.github.suzp1984.jbigandroid.controller.MainController;
import io.github.suzp1984.jbigandroid.fragment.DecoderFragment;
import io.github.suzp1984.jbigandroid.fragment.PaintViewFragment;
import io.github.suzp1984.jbigandroid.injector.module.ApplicationModule;
import io.github.suzp1984.jbigandroid.injector.module.PersistenceModule;
import io.github.suzp1984.jbigandroid.injector.module.StateModule;
import io.github.suzp1984.jbigandroid.injector.module.UtilsModule;
import io.github.suzp1984.jbigandroid.provider.JbigContentProvider;

/**
 * Created by jacobsu on 4/24/16.
 */
@Singleton
@Component(modules = {ApplicationModule.class,
                        UtilsModule.class,
                        StateModule.class,
                        PersistenceModule.class})
public interface ApplicationComponent {
    void inject(BaseDrawerActivity activity);
    void inject(DecoderFragment fragment);
    void inject(PaintViewFragment fragment);
    void inject(JbigContentProvider provider);

    MainController getMainController();
}
