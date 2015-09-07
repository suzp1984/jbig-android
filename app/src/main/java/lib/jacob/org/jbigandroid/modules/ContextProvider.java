package lib.jacob.org.jbigandroid.modules;

import com.google.common.base.Preconditions;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import lib.jacob.org.jbigandroid.JbigApplication;

/**
 * Created by moses on 8/31/15.
 */

@Module(
        injects = JbigApplication.class,
        library = true,
        includes = {
                UtilsProvider.class,
                StateProvider.class
        }

)
public class ContextProvider {
    private final Context mApplicationContext;

    public ContextProvider(Context context) {
        mApplicationContext = Preconditions.checkNotNull(context, "Context cannot be null");
    }

    @Provides
    public Context provideApplicationContext() {
        return mApplicationContext;
    }
}
