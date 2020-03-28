package unko.getfit;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

public class App_intro extends AppIntro {
    Context _ctx = this;

    @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            SliderPage sliderPage = new SliderPage();
            sliderPage.setTitle(getResources().getString(R.string.title_slide_1));
            sliderPage.setDescription(getResources().getString(R.string.content_slide_1));
            sliderPage.setImageDrawable(R.drawable.welcome);
            sliderPage.setBgColor(Color.parseColor("#e27e9e"));
            addSlide(AppIntroFragment.newInstance(sliderPage));

            SliderPage sliderPage2 = new SliderPage();
            sliderPage2.setTitle(getResources().getString(R.string.title_slide_2));
            sliderPage2.setDescription(getResources().getString(R.string.content_slide_2));
            sliderPage2.setImageDrawable(R.drawable.doubt);
            sliderPage2.setBgColor(Color.parseColor("#f5aa7e"));
            addSlide(AppIntroFragment.newInstance(sliderPage2));

            SliderPage sliderPage4 = new SliderPage();
            sliderPage4.setTitle(getResources().getString(R.string.title_slide_4));
            sliderPage4.setDescription(getResources().getString(R.string.content_slide_4));
            sliderPage4.setImageDrawable(R.drawable.doctor);
            sliderPage4.setBgColor(Color.parseColor("#e27e9e"));
            addSlide(AppIntroFragment.newInstance(sliderPage4));

            SliderPage sliderPage5 = new SliderPage();
            sliderPage5.setTitle(getResources().getString(R.string.title_slide_5));
            sliderPage5.setDescription(getResources().getString(R.string.content_slide_5));
            sliderPage5.setImageDrawable(R.drawable.star);
            sliderPage5.setBgColor(Color.parseColor("#f5aa7e"));
            addSlide(AppIntroFragment.newInstance(sliderPage5));

            SliderPage sliderPage6 = new SliderPage();
            sliderPage6.setTitle(getResources().getString(R.string.title_slide_6));
            sliderPage6.setDescription(getResources().getString(R.string.content_slide_6));
            sliderPage6.setImageDrawable(R.drawable.settings);
            sliderPage6.setBgColor(Color.parseColor("#e27e9e"));
            addSlide(AppIntroFragment.newInstance(sliderPage6));

            showSkipButton(false);
            setProgressButtonEnabled(true);
            setVibrate(true);
            setVibrateIntensity(5);
        }

        @Override
        public void onSkipPressed(Fragment currentFragment) {
            super.onSkipPressed(currentFragment);
        }

        @Override
        public void onDonePressed(Fragment currentFragment) {
            super.onDonePressed(currentFragment);
            // Do something when users tap on Done button.
            startActivities(new Intent[]{new Intent(this, configuracion.class)});
        }

        @Override
        public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
            final MediaPlayer mp = MediaPlayer.create(this,R.raw.slide_sound);
            super.onSlideChanged(oldFragment, newFragment);
            mp.start();
        }
    }