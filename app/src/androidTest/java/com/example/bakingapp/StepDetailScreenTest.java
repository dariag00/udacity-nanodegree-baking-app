package com.example.bakingapp;

import android.content.Intent;

import androidx.test.rule.ActivityTestRule;

import com.example.bakingapp.data.http.recipes.Recipe;
import com.example.bakingapp.data.http.recipes.Step;
import com.example.bakingapp.ui.detail.StepDetailActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNot.not;

public class StepDetailScreenTest {

    public static final String RECIPE_NAME = "Brownies";

    @Rule
    public ActivityTestRule<StepDetailActivity> activityTestRule = new ActivityTestRule<>(StepDetailActivity.class);

    @Before
    public void setup() {
        List<Step> steps = new ArrayList<>();

        Step step = new Step();
        step.setVideoURL("");
        steps.add(step);

        step = new Step();
        step.setVideoURL("");
        steps.add(step);

        Recipe recipe = new Recipe();
        recipe.setSteps(steps);

        Intent intent = new Intent();
        intent.putExtra("recipe",recipe);
        activityTestRule.launchActivity(intent);
    }

    @Test
    public void clickRecyclerViewItem_OpensRecipeActivity(){
        onView(withId(R.id.button_previous_step)).check(matches(not(isDisplayed())));
        onView(withId(R.id.button_next_step)).check(matches(isDisplayed()));
        onView(withId(R.id.button_next_step)).perform(click());
        onView(withId(R.id.button_previous_step)).check(matches(isDisplayed()));
        onView(withId(R.id.button_next_step)).check(matches(not(isDisplayed())));
    }

}
