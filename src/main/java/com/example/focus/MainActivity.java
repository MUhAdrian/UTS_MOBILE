package com.example.focus;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView timerText;
    private ProgressBar progressBar;
    private ImageButton playButton, pauseButton, stopButton, markButton;
    private CountDownTimer countDownTimer;
    private boolean isRunning = false;
    private long timeLeftInMillis = 300000; // 5 menit dalam milidetik (default)
    private long initialTimeInMillis = 300000;
    //private MediaPlayer mediaPlayer; // Hapus variable media player
    private boolean isChangingActivity = false; // Tambahkan variabel ini

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi komponen
        timerText = findViewById(R.id.timer_text);
        progressBar = findViewById(R.id.progress_bar);
        playButton = findViewById(R.id.play_button);
        pauseButton = findViewById(R.id.pause_button);
        stopButton = findViewById(R.id.stop_button);
        markButton = findViewById(R.id.mark_button);

        progressBar.setMax((int) initialTimeInMillis);
        updateTimerText();

        // Set OnClickListener for timerText to input time
        timerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeInputDialog();
            }
        });

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
                Toast.makeText(MainActivity.this, "fitur masih belum tersedia", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimerText();
                progressBar.setProgress((int) timeLeftInMillis); // Update ProgressBar
                Log.d(TAG, "Time left: " + millisUntilFinished + " ms");
            }

            @Override
            public void onFinish() {
                isRunning = false;
                progressBar.setProgress(0); // Set progress to 0% when timer finishes
                Log.d(TAG, "Timer finished!");
                // Ketika waktu habis
                changeToCongratulationsScreen();
            }
        }.start();

        isRunning = true;
    }

    private void pauseTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        isRunning = false;
    }

    private void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        timeLeftInMillis = initialTimeInMillis; // Reset to initial time
        updateTimerText();
        progressBar.setProgress((int) initialTimeInMillis); // Reset ProgressBar
        isRunning = false;
    }

    private void updateTimerText() {
        int hours = (int) (timeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((timeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timerText.setText(timeFormatted);
    }

    private void showTimeInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atur Waktu");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(64, 32, 64, 32);

        final EditText inputHours = new EditText(this);
        inputHours.setHint("Jam");
        inputHours.setInputType(InputType.TYPE_CLASS_NUMBER);
        layout.addView(inputHours);

        final EditText inputMinutes = new EditText(this);
        inputMinutes.setHint("Menit");
        inputMinutes.setInputType(InputType.TYPE_CLASS_NUMBER);
        layout.addView(inputMinutes);

        final EditText inputSeconds = new EditText(this);
        inputSeconds.setHint("Detik");
        inputSeconds.setInputType(InputType.TYPE_CLASS_NUMBER);
        layout.addView(inputSeconds);

        builder.setView(layout);

        builder.setPositiveButton("OK", (dialog, which) -> {
            int hours = 0;
            int minutes = 0;
            int seconds = 0;

            try {
                hours = Integer.parseInt(inputHours.getText().toString());
                minutes = Integer.parseInt(inputMinutes.getText().toString());
                seconds = Integer.parseInt(inputSeconds.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "Input tidak valid", Toast.LENGTH_SHORT).show();
                return;
            }

            if (hours < 0 || minutes < 0 || seconds < 0 || minutes >= 60 || seconds >= 60) {
                Toast.makeText(MainActivity.this, "Input tidak valid", Toast.LENGTH_SHORT).show();
                return;
            }

            long totalMillis = (hours * 3600 + minutes * 60 + seconds) * 1000L;
            if (totalMillis == 0) {
                Toast.makeText(MainActivity.this, "Input tidak valid", Toast.LENGTH_SHORT).show();
                return;
            }
            timeLeftInMillis = totalMillis;
            initialTimeInMillis = totalMillis;
            progressBar.setMax((int) initialTimeInMillis);
            updateTimerText();

        });

        builder.setNegativeButton("Batal", (dialog, which) -> dialog.cancel());
        builder.show();
    }
    private void changeToCongratulationsScreen() {
        Intent intent = new Intent(MainActivity.this, CongratulationsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}