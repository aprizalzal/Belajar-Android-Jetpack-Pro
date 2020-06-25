package com.application.academy.ui.home;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;

import com.application.academy.R;
import com.application.academy.data.CourseEntity;
import com.application.academy.utils.DataDummy;
import com.application.academy.utils.EspressoIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class HomeActivityTest {
    private List<CourseEntity> dummyCourse = DataDummy.generateDummyCourses();

    @Rule
    public ActivityTestRule<HomeActivity> activityRule = new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource());
    }
    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource());
    }
    
    @Test
    public void loadCourse() {
        ////delay2seconds();
        onView(withId(R.id.rv_academy)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_academy)).perform(RecyclerViewActions.scrollToPosition(dummyCourse.size()));
    }

    @Test
    public void loadDetailCourse() {
        ////delay2seconds();
        onView(withId(R.id.rv_academy)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        ////delay2seconds();
        onView(withId(R.id.text_title)).check(matches(isDisplayed()));
        onView(withId(R.id.text_title)).check(matches(withText(dummyCourse.get(0).getTitle())));
        onView(withId(R.id.text_date)).check(matches(isDisplayed()));
        onView(withId(R.id.text_date)).check(matches(withText(String.format("Deadline %s", dummyCourse.get(0).getDeadline()))));
    }

    @Test
    public void loadModule() {
        ////delay2seconds();
        onView(withId(R.id.rv_academy)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        ////delay2seconds();
        onView(withId(R.id.btn_start)).perform(click());
        ////delay2seconds();
        onView(withId(R.id.rv_module)).check(matches(isDisplayed()));
    }

    @Test
    public void loadDetailModule() {
        //delay2seconds();
        onView(withId(R.id.rv_academy)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        //delay2seconds();
        onView(withId(R.id.btn_start)).perform(click());
        //delay2seconds();
        onView(withId(R.id.rv_module)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        //delay2seconds();
        onView(withId(R.id.web_view)).check(matches(isDisplayed()));
    }

    @Test
    public void loadBookmark() {
        onView(withText("BOOKMARK")).perform(click());
        //delay2seconds();
        onView(withId(R.id.rv_bookmark)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_bookmark)).perform(RecyclerViewActions.scrollToPosition(dummyCourse.size()));
    }

    /*
    private void delay2seconds() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    */
}