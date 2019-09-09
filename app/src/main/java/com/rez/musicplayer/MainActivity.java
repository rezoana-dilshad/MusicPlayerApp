package com.rez.musicplayer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    //for volume services, use AudioManages
    AudioManager audioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set up the media player
        mediaPlayer = MediaPlayer.create(this, R.raw.testaudio);

        //make use of the seekbars
        //get context from Audio Manager
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int myMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int myCurrentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        //Set context to seekbar - volumeRocker
        final SeekBar volumeRocker = findViewById(R.id.seekBar);
        volumeRocker.setMax(myMaxVolume);
        volumeRocker.setProgress(myCurrentVolume);

        //Set a listener on VolumeRocker
        volumeRocker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Timeline part of the app
        final SeekBar timeLine = findViewById(R.id.timeline);
        timeLine.setMax(mediaPlayer.getDuration());

        //Set OnchangeListener on timeline
        timeLine.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //Customise timeline Seekbar
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeLine.setProgress(mediaPlayer.getCurrentPosition());
            }
        }, 0, 1000);

    }

    public void playMe(View view){
        mediaPlayer.start();
    }

    public void pauseMe(View view){
        mediaPlayer.pause();
    }
}
