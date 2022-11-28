package com.example.ttsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private TextView mTV1;
    private Button mBtnTts;
    private EditText mETxt1;
    private static TextToSpeech mTts = null;
    private final int MY_DATA_CHECK_CODE = 2323;
    private void checkTTS() {
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                mTts = new TextToSpeech(this, this);
            } else {
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
                startActivity(installIntent);
            }
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            mTts.setLanguage(Locale.US);
            String myText1 = getResources().getString(R.string.hello_android);
            String myText2 = "Welcome to the IK10965 Android course. See you in Learn!";
            mTts.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);
            mTts.speak(myText2, TextToSpeech.QUEUE_ADD, null);
        }
        else
            Toast.makeText(this, "An error occurred with TTS init!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        if (mTts != null)
            mTts.shutdown();
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (mTts == null)
            checkTTS();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnTts = (Button) findViewById(R.id.button1);
        mETxt1 = (EditText) findViewById(R.id.editText1);
        mBtnTts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mETxt1.getText().toString().length() > 0 && mTts != null)
                    mTts.speak(mETxt1.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    // lifecycle methods, use @Override for all
    @Override
    public void onStart() { super.onStart(); }
    public void onRestart() { super.onRestart(); }
    public void onResume() { super.onResume(); }
    public void onPause() { super.onPause(); }
    public void onStop() { super.onStop(); }
}