package suzp1984.github.io.agera_sample;

import android.support.design.widget.NavigationView;
import androidx.core.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jacobsu on 9/4/16.
 */
public abstract class BaseDrawerActivity extends AppCompatActivity {
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @Override
    public void setContentView(int layoutId) {
        super.setContentView(R.layout.activity_base_drawer);
        FrameLayout parent = (FrameLayout) findViewById(R.id.content);
        LayoutInflater.from(this).inflate(layoutId, parent, true);

        injectView();
    }

    private void injectView() {
        ButterKnife.bind(this);
    }

}
