package io.github.suzp1984.jbigandroid.injector.module;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import dagger.internal.Preconditions;
import io.github.suzp1984.jbigandroid.injector.ApplicationContext;

/**
 * Created by jacobsu on 4/24/16.
 */
@Module
public class ApplicationModule {
    private Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = Preconditions.checkNotNull(application, "application cannot be null");
    }

    @Provides
    public Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    public Context provideContext() {
        return mApplication;
    }
}
