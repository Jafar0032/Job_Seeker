package com.tugasakhirpab2.rjn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.appintro.AppIntroCustomLayoutFragment;
import com.github.appintro.AppIntroPageTransformerType;
import com.tugasakhirpab2.rjn.R;

public class AppIntro extends com.github.appintro.AppIntro {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.appintro1));
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.appintro2));
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.appintro3));

        showStatusBar(false);

        setIndicatorColor(
                getResources().getColor(R.color.primary),
                getResources().getColor(R.color.grey_lighter)
        );

        setNextArrowColor(getResources().getColor(R.color.primary));
        setColorDoneText(getResources().getColor(R.color.primary));
        setColorSkipButton(getResources().getColor(R.color.grey));

        //Show/hide skip button
        setSkipButtonEnabled(true);

        //Enable immersive mode (no status and nav bar)
        setImmersiveMode();

        //Enable/disable page indicators
        setIndicatorEnabled(true);

        //Dhow/hide ALL buttons
        setButtonsEnabled(true);
    }

    @Override
    protected void onSkipPressed(@Nullable Fragment currentFragment) {
        super.onSkipPressed(currentFragment);

        startActivity(new Intent(getApplicationContext(), SignInActivity.class));
        finish();
    }

    @Override
    protected void onDonePressed(@Nullable Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        startActivity(new Intent(getApplicationContext(), SignInActivity.class));
        finish();
    }
}
