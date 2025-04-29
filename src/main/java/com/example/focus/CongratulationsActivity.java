package com.example.focus;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CongratulationsActivity extends AppCompatActivity {
    private static final String TAG = "CongratulationsActivity";
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.congratulations_layout);

        // Inisialisasi MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.congratulations_song);
        mediaPlayer.setOnErrorListener((mp, what, extra) -> {
            Log.e(TAG, "MediaPlayer Error: what=" + what + ", extra=" + extra);
            return true;
        });
        mediaPlayer.setOnCompletionListener(mp -> {
            Log.d(TAG, "lagu sudah selesai di putar");
        });

        // Memulai pemutaran
        mediaPlayer.start();

        // Menyiapkan TextView
        TextView textCongratulations = findViewById(R.id.congratulations_text);
        textCongratulations.setOnClickListener(v -> goBackToMainActivity());
    }

    @Override
    public void onBackPressed() {
        goBackToMainActivity();
        super.onBackPressed();
    }














    private void goBackToMainActivity() {
        stopPlaying();
        Intent intent = new Intent(CongratulationsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void stopPlaying() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopPlaying();
    }
}