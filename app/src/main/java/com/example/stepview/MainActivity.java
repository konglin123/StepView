package com.example.stepview;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;

public class MainActivity extends AppCompatActivity {

    private StepView stepView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stepView=findViewById(R.id.stepview);

        stepView.setMaxStep(10000);
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 8000);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float curStep= (float) animation.getAnimatedValue();
                stepView.setCurStep((int)curStep);
            }
        });
        valueAnimator.start();
    }
}
