package com.example.grant.gmo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.media.MediaPlayer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
        private long timer = 30000;
        private long timeleft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner spinner = findViewById(R.id.spinner);
        String[] items = new String[]{"15 Seconds", "30 Seconds", "1 Minute", "2 Minutes", "5 Minutes"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
        ProgressBar bar = findViewById(R.id.progressBar);
        bar.setMax(100);
        bar.setProgress(100);
        final Switch vibrateswitch = (Switch) findViewById(R.id.vibrationSwitch);
        final Switch volumeswitch = (Switch) findViewById(R.id.VolumeSwitch);
        Button back = findViewById(R.id.backButton);
    }
    public void ButtonOnClick(View v) {
        startTimer();
    }
    public void startTimer() {
        if (timeleft != 0) {
            return;
        }
        final Button cancel = findViewById(R.id.button2);
        final Button callbutton = findViewById(R.id.button);
        final ProgressBar bar = findViewById(R.id.progressBar);
        Spinner spinner = findViewById(R.id.spinner);
        String text = spinner.getSelectedItem().toString();
        if (text.indexOf("Second") != -1) {
            timer = 1000 * Long.parseLong(text.split(" ")[0]);
        } else {
            timer = 1000 * 60 * Long.parseLong(text.split(" ")[0]);
        }
        bar.setMax((int)timer);
        timeleft = new Long(timer);
        final CountDownTimer countDownTimer = new CountDownTimer(timer, 1000) {
            @Override
            public void onTick(long l) {
                timeleft-=1000;
                bar.setProgress((int)timeleft);
            }
            @Override
            public void onFinish() {
                //fakecall();
                vibrate();
                soundPlayer();
                fakeCallScreen();
                cancel.setVisibility(View.INVISIBLE);
                callbutton.setVisibility(View.INVISIBLE);
                backButton();
            }
        }.start();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setProgress((int)timer);
                countDownTimer.cancel();
                startTimer();
            }
        });
    }
    public void fakecall() {
        startActivity(new Intent(getApplicationContext(), call2.class));
        return;
    }
    public void vibrate() {
        final Switch vibrateswitch = (Switch) findViewById(R.id.vibrationSwitch);
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrateswitch.isChecked()) {
            vibrator.vibrate(2000);
        }
        return;
    }
    public void soundPlayer() {
        final MediaPlayer ringtone = MediaPlayer.create(this, R.raw.windchimer);
        final Switch volumeswitch = findViewById(R.id.VolumeSwitch);
        if (volumeswitch.isChecked()) {
            ringtone.start();
        }
        return;
    }
    public void fakeCallScreen() {
        ImageView fakescreen = (ImageView) findViewById(R.id.imageView3);
        fakescreen.setVisibility(View.VISIBLE);
    }
    public void backButton() {
        final Button cancel = findViewById(R.id.button2);
        final Button callbutton = findViewById(R.id.button);
        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressBar bar = findViewById(R.id.progressBar);
                ImageView fakescreen = (ImageView) findViewById(R.id.imageView3);
                fakescreen.setVisibility(View.INVISIBLE);
                cancel.setVisibility(View.VISIBLE);
                callbutton.setVisibility(View.VISIBLE);
                bar.setProgress((int)timer);
            }
        });
    }

//    public void cancelButton() {
//        Button cancel = findViewById(R.id.button2);
//        final CountDownTimer countDownTimer = new CountDownTimer(timer, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                timeleft-=1000;
//            }
//
//            @Override
//            public void onFinish() {
//                return;
//            }
//        };
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                countDownTimer.cancel();
//            }
//        });
 //   }
//    public void switches() {
//        final Switch vibrateswitch = (Switch) findViewById(R.id.vibrationSwitch);
//        final Switch volumeswitch = (Switch) findViewById(R.id.VolumeSwitch);
//
//        final AudioManager audioManager = (AudioManager) getSystemService(getApplicationContext().AUDIO_SERVICE);
//
//        vibrateswitch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (vibrateswitch.isChecked()){
//                    audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
//                }
//            }
//        });
//
//        volumeswitch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(volumeswitch.isChecked()) {
//                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
//                }
//            }
//        });
//        return;
//    }

}
