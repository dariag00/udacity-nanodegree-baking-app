package com.example.bakingapp;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.example.bakingapp.ui.main.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTest {

    public static final String RECIPE_NAME = "Brownies";

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup() {
        activityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void clickRecyclerViewItem_OpensRecipeActivity(){
        onView(withId(R.id.master_list_fragment)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.rv_recipees)).perform(actionOnItemAtPosition(1,click()));
        onView(withId(R.id.tv_recipe_name)).check(matches(withText(RECIPE_NAME)));
    }

}
