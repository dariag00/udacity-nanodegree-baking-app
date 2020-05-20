package com.example.bakingapp.ui.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.bakingapp.R;
import com.example.bakingapp.data.http.recipes.Recipe;
import com.example.bakingapp.data.http.recipes.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailActivity extends AppCompatActivity {

    private List<Step> steps;
    private int currentStep;

    @BindView(R.id.button_next_step)
    Button nextStepButton;

    @BindView(R.id.button_previous_step)
    Button previousStepButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        ButterKnife.bind(this);

        currentStep = 0;

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            Recipe recipe = (Recipe) bundle.getSerializable("recipe");
            steps = recipe.getSteps();
            currentStep = intent.getIntExtra( "clickedStep",0);
        }

        if(steps != null){
            commitFragment();
            handleButtons();
        }

        nextStepButton.setOnClickListener(v -> {
            System.out.println("QUE PASA");
            currentStep++;
            handleButtons();
            commitFragment();
        });

        previousStepButton.setOnClickListener(v -> {
            System.out.println("QUE PASA");
            currentStep--;
            handleButtons();
            commitFragment();
        });

    }

    private void handleButtons(){
        if(currentStep == 0){
            previousStepButton.setVisibility(View.INVISIBLE);
        }else if(currentStep == steps.size() -1 ){
            nextStepButton.setVisibility(View.INVISIBLE);
        }else if(previousStepButton.getVisibility() == View.INVISIBLE || nextStepButton.getVisibility() == View.INVISIBLE){
            previousStepButton.setVisibility(View.VISIBLE);
            nextStepButton.setVisibility(View.VISIBLE);
        }
    }

    private void commitFragment(){
        StepDetailFragment stepFragment = new StepDetailFragment();
        stepFragment.setStep(steps.get(currentStep));
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.step_container, stepFragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
