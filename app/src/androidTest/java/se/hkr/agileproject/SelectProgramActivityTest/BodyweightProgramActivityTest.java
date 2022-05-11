package se.hkr.agileproject.SelectProgramActivityTest;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
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

import se.hkr.agileproject.R;
import se.hkr.agileproject.SelectProgramActivity;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class BodyweightProgramActivityTest {

    @Rule
    public ActivityTestRule<SelectProgramActivity> mActivityTestRule = new ActivityTestRule<>(SelectProgramActivity.class);

    @Test
    public void bodyweightProgramActivityTest() {
        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(Matchers.allOf(ViewMatchers.withId(R.id.listview),
                        childAtPosition(
                                withId(R.id.rl),
                                0)))
                .atPosition(1);
        appCompatCheckedTextView.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.activityName), withText("Bodyweight"),
                        withParent(allOf(withId(R.id.layoutHeader),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        textView.check(matches(withText("Bodyweight")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.exerciseNameShowProgram), withText("Pull-Up"),
                        withParent(withParent(withId(R.id.customListViewShowProgram))),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.exerciseNameShowProgram), withText("Jump Squat"),
                        withParent(withParent(withId(R.id.customListViewShowProgram))),
                        isDisplayed()));
        textView3.check(matches(isDisplayed()));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.exerciseNameShowProgram), withText("Push-Up"),
                        withParent(withParent(withId(R.id.customListViewShowProgram))),
                        isDisplayed()));
        textView4.check(matches(isDisplayed()));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.exerciseNameShowProgram), withText("Inverted Pull-Up"),
                        withParent(withParent(withId(R.id.customListViewShowProgram))),
                        isDisplayed()));
        textView5.check(matches(isDisplayed()));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.exerciseNameShowProgram), withText("Front Plank (from knees)"),
                        withParent(withParent(withId(R.id.customListViewShowProgram))),
                        isDisplayed()));
        textView6.check(matches(isDisplayed()));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.exerciseNameShowProgram), withText("Jump Lunge"),
                        withParent(withParent(withId(R.id.customListViewShowProgram))),
                        isDisplayed()));
        textView7.check(matches(isDisplayed()));

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.exerciseNameShowProgram), withText("Dips"),
                        withParent(withParent(withId(R.id.customListViewShowProgram))),
                        isDisplayed()));
        textView8.check(matches(isDisplayed()));

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.exerciseNameShowProgram), withText("Dips"),
                        withParent(withParent(withId(R.id.customListViewShowProgram))),
                        isDisplayed()));
        textView9.check(matches(isDisplayed()));
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
