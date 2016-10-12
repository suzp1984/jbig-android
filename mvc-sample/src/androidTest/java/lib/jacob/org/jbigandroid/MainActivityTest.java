package lib.jacob.org.jbigandroid;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.github.suzp1984.jbigandroid.MainActivity;
import io.github.suzp1984.jbigandroid.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

/**
 * Created by jacobsu on 10/12/16.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityResult =
            new ActivityTestRule<MainActivity>(MainActivity.class);


    @Test
    public void testViewsAppeare() {
        onView(withId(R.id.toolbar))
                .check(matches(isDisplayed()));

        onView(withId(R.id.viewpager))
                .check(matches(isDisplayed()));

        onView(withId(R.id.tabs))
                .check(matches(isDisplayed()));
    }
}
