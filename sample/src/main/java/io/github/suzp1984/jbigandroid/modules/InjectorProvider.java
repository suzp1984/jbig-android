package io.github.suzp1984.jbigandroid.modules;

import com.google.common.base.Preconditions;

import dagger.Module;
import dagger.Provides;
import io.github.suzp1984.jbigandroid.utils.Injector;

/**
 * Created by moses on 8/31/15.
 */

@Module(
        library = true
)
public class InjectorProvider {
    private final Injector mInjector;

    public InjectorProvider(Injector injector) {
        mInjector = Preconditions.checkNotNull(injector, "injector cannot be null");
    }

    @Provides
    public Injector providerMainInjector() {
        return mInjector;
    }
}
