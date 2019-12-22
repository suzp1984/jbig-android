package lib.jacob.org.jbigandroid;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.github.suzp1984.jbigandroid.MainActivity;
import io.github.suzp1984.jbigandroid.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

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
