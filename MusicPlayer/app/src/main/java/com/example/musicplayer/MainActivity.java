package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.UiModeManager;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    ImageView playPauseIcon;
    SeekBar seekBar;
    boolean playing;
    SeekBar volumeSeekBar;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);



        playPauseIcon = findViewById(R.id.playImageView);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music);

        seekBar = findViewById(R.id.SeekBar);
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    playPauseIcon.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                    playing = true;
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (playing) {
                    mediaPlayer.start();
                    playPauseIcon.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
                    playing = false;
                }
            }
        });

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        volumeSeekBar = findViewById(R.id.VolumeSeekBar);
        volumeSeekBar.setMax(maxVolume);
        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int volumeProgress, boolean volumeFromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volumeProgress, 50);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
            }
        }, 0, 1000);
    }

    public void previous(View view) {
        mediaPlayer.pause();
        seekBar.setProgress(0);
        mediaPlayer.seekTo(0);
        playPauseIcon.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
    }

    public void next(View view) {
        mediaPlayer.pause();
        seekBar.setProgress(mediaPlayer.getDuration());
        mediaPlayer.seekTo(mediaPlayer.getDuration());
        playPauseIcon.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
    }

    public void play(View view){
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            playPauseIcon.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
        } else {
            mediaPlayer.start();
            playPauseIcon.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
        }
    }
}