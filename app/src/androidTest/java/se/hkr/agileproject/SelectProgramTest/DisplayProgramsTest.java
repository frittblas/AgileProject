package se.hkr.agileproject.SelectProgramTest;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import se.hkr.agileproject.HomeActivity;
import se.hkr.agileproject.R;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DisplayProgramsTest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void displayProgramsTest() {
        ViewInteraction linearLayout = onView(
                Matchers.allOf(ViewMatchers.withId(R.id.layoutStrength),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                0)));
        linearLayout.perform(scrollTo(), click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.activityName), withText("Programs"),
                        withParent(allOf(withId(R.id.layoutHeader),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        textView.check(matches(withText("Programs")));

        ViewInteraction checkedTextView = onView(
                allOf(withId(android.R.id.text1), withText("Beginner"),
                        withParent(allOf(withId(R.id.listview),
                                withParent(withId(R.id.rl)))),
                        isDisplayed()));
        checkedTextView.check(matches(isDisplayed()));

        ViewInteraction checkedTextView2 = onView(
                allOf(withId(android.R.id.text1), withText("Bodyweight"),
                        withParent(allOf(withId(R.id.listview),
                                withParent(withId(R.id.rl)))),
                        isDisplayed()));
        checkedTextView2.check(matches(isDisplayed()));

        ViewInteraction checkedTextView3 = onView(
                allOf(withId(android.R.id.text1), withText("Full-body"),
                        withParent(allOf(withId(R.id.listview),
                                withParent(withId(R.id.rl)))),
                        isDisplayed()));
        checkedTextView3.check(matches(isDisplayed()));

        ViewInteraction checkedTextView4 = onView(
                allOf(withId(android.R.id.text1), withText("Legs"),
                        withParent(allOf(withId(R.id.listview),
                                withParent(withId(R.id.rl)))),
                        isDisplayed()));
        checkedTextView4.check(matches(isDisplayed()));

        ViewInteraction checkedTextView5 = onView(
                allOf(withId(android.R.id.text1), withText("Lower-body"),
                        withParent(allOf(withId(R.id.listview),
                                withParent(withId(R.id.rl)))),
                        isDisplayed()));
        checkedTextView5.check(matches(isDisplayed()));

        ViewInteraction checkedTextView6 = onView(
                allOf(withId(android.R.id.text1), withText("Pull"),
                        withParent(allOf(withId(R.id.listview),
                                withParent(withId(R.id.rl)))),
                        isDisplayed()));
        checkedTextView6.check(matches(isDisplayed()));

        ViewInteraction checkedTextView7 = onView(
                allOf(withId(android.R.id.text1), withText("Push"),
                        withParent(allOf(withId(R.id.listview),
                                withParent(withId(R.id.rl)))),
                        isDisplayed()));
        checkedTextView7.check(matches(isDisplayed()));

        ViewInteraction checkedTextView8 = onView(
                allOf(withId(android.R.id.text1), withText("Upper-body"),
                        withParent(allOf(withId(R.id.listview),
                                withParent(withId(R.id.rl)))),
                        isDisplayed()));
        checkedTextView8.check(matches(isDisplayed()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
