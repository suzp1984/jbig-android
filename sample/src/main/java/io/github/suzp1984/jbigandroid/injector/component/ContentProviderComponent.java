package io.github.suzp1984.jbigandroid.injector.component;

import javax.inject.Singleton;

import dagger.Component;

import io.github.suzp1984.jbigandroid.injector.module.PersistenceModule;
import io.github.suzp1984.jbigandroid.injector.module.UtilsModule;
import io.github.suzp1984.jbigandroid.provider.JbigContentProvider;

/**
 * Created by jacobsu on 5/29/16.
 */
@Singleton
@Component(modules = {
        UtilsModule.class,
        PersistenceModule.class})
public interface ContentProviderComponent {
    void inject(JbigContentProvider provider);
}
