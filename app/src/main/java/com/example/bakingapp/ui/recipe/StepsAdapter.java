package com.example.bakingapp.ui.recipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;
import com.example.bakingapp.data.http.recipes.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepViewHolder> {

    private List<Step> stepList;

    private StepClickListener stepClickListener;

    public StepsAdapter(List<Step> stepList,  StepClickListener stepClickListener){
        this.stepList = stepList;
        this.stepClickListener = stepClickListener;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.step_list_item, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if(stepList != null){
            return stepList.size();
        }
        return 0;
    }


    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_step_number)
        TextView stepNumber;
        @BindView(R.id.tv_step_desc)
        TextView stepDescription;

        StepViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bind(int position){
            Step step = stepList.get(position);

            stepNumber.setText(String.valueOf(position + 1));
            stepDescription.setText(step.getShortDescription());
        }

        @Override
        public void onClick(View v) {
            stepClickListener.onStepClick(getAdapterPosition());
        }
    }

    public interface StepClickListener {
        void onStepClick(int clickedStep);
    }
}
