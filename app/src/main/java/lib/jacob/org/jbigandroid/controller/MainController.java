package lib.jacob.org.jbigandroid.controller;

import com.google.common.base.Preconditions;

import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import lib.jacob.org.jbigandroid.display.IDisplay;
import lib.jacob.org.jbigandroid.states.ApplicationState;
import lib.jacob.org.jbigandroid.states.JbigDbState;

/**
 * Created by moses on 9/2/15.
 */

@Singleton
public class MainController extends BaseUiController<MainController.MainControllerUi,
        MainController.MainControllerUiCallback> {

    public enum TabItem {
        PAINT_TAB, DECODER_TAB;
    }

    private final ApplicationState mApplicationState;

    @Inject
    public MainController(ApplicationState state) {
        super();

        mApplicationState = Preconditions.checkNotNull(state, "Applicatin cannot be null.");
    }

    @Override
    MainControllerUiCallback createUiCallbacks(MainControllerUi ui) {
        return new MainControllerUiCallback() {
            @Override
            public void onTabItemSelected(TabItem item) {
                IDisplay display = getDisplay();

                if (display != null) {
                    showUiItem(display, item);
                    display.closeDrawerLayout();
                }
            }
        };
    }

    @Override
    void onUiAttached(MainControllerUi ui) {

    }

    @Override
    void onUiDetached(MainControllerUi ui) {

    }

    @Override
    void populateUi(MainControllerUi ui) {
        // do nothing?
        if (ui instanceof DecoderTabUi) {
            populateUi((DecoderTabUi) ui);
        }
    }

    @Override
    void onInited() {
        mApplicationState.registerForEvents(this);
    }

    @Override
    void onSuspended() {
        mApplicationState.unregisterForEvents(this);
    }

    @Subscribe
    public void onJbigDataAdd(JbigDbState.JbigDbAddEvent event) {
        // populate Decoder Tab
        populateUis();
    }

    private void populateUi(DecoderTabUi ui) {
        ui.showJbigs(mApplicationState.getJbigDbs());
    }

    private void showUiItem(IDisplay display, TabItem item) {
        Preconditions.checkNotNull(display, "IDisplay cannot be null");
        Preconditions.checkNotNull(item, "TabItem cannot be null.");

        switch (item) {
            case PAINT_TAB:
                display.showPaintUi();
                break;
            case DECODER_TAB:
                display.showDecoderUi();
                break;
            default:
                break;
        }

        // set selected TabItem
    }

    public interface MainControllerUi
            extends BaseUiController.Ui<MainControllerUiCallback> {

    }

    public interface DecoderTabUi extends MainControllerUi {
        void showJbigs(List<byte[]> jbigs);
    }

    public interface MainControllerUiCallback {
        void onTabItemSelected(TabItem item);
    }
}
