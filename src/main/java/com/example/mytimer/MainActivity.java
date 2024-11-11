package com.example.mytimer;

import android.util.Log;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView timerText;
    private ProgressBar progressBar;
    private ImageButton playButton, pauseButton, stopButton, markButton;
    private CountDownTimer countDownTimer;
    private boolean isRunning = false;
    private long timeLeftInMillis = 300000; // 5 menit dalam milidetik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerText = findViewById(R.id.timer_text);
        progressBar = findViewById(R.id.progress_bar);
        playButton = findViewById(R.id.play_button);
        pauseButton = findViewById(R.id.pause_button);
        stopButton = findViewById(R.id.stop_button);
        markButton = findViewById(R.id.mark_button);

        progressBar.setMax((int) timeLeftInMillis);
        updateTimerText();

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    startTimer();
                }
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    pauseTimer();
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
            }
        });

        markButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle mark button click
            }
        });
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimerText();

                // Menghitung progress (0-100)
                int progress = (int) ((300000 - timeLeftInMillis) * 100 / 300000);
                progressBar.setProgress((int) timeLeftInMillis); // Update ProgressBar sesuai waktu yang tersisa

                Log.d("Timer", "Time left: " + millisUntilFinished + " ms, Progress: " + progress + "%");
            }

            @Override
            public void onFinish() {
                isRunning = false;
                progressBar.setProgress(100); // Set progress ke 100% saat timer selesai
                Log.d("Timer", "Timer finished!");
            }
        }.start();

        isRunning = true;
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        isRunning = false;
    }

    private void stopTimer() {
        countDownTimer.cancel();
        timeLeftInMillis = 300000; // Reset to 5 menit
        updateTimerText();
        progressBar.setProgress(0); // Reset ProgressBar ke 0%
        isRunning = false;
    }

    private void updateTimerText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format("%02d:%02d", minutes, seconds);
        timerText.setText(timeFormatted);
    }
}