package com.example.android.xyztouristattractions;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.android.xyztouristattractions.ui.AttractionListActivity;
import com.github.paolorotolo.appintro.AppIntro2;

public class SecondLayoutIntro extends AppIntro2 {
    @Override
    public void init(Bundle savedInstanceState) {
        addSlide(SampleSlide.newInstance(R.layout.intro_2));
        addSlide(SampleSlide.newInstance(R.layout.intro2_2));
        addSlide(SampleSlide.newInstance(R.layout.intro3_2));
    }

    private void loadMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDonePressed() {
        loadMainActivity();
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onSlideChanged() {

    }

    public void getStarted(View v){
        loadMainActivity();
    }
}
