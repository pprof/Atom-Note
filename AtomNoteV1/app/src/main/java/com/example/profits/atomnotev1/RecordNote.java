package com.example.profits.atomnotev1;
import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;
import com.melnykov.fab.FloatingActionButton;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class RecordNote extends AppCompatActivity {
    String OldValue = "";
    private Toolbar toolbar;
    private EditText eTitle;
    private EditText eNote;
    private FloatingActionButton floatingActionButton;

    private final int REQ_CODE_SPEECH_INPUT = 10000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_note);
        eTitle = (EditText)findViewById(R.id.noteTitle);
        eNote = (EditText)findViewById(R.id.recwriteText);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.startrec);


        setSupportActionBar(toolbar);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeechInput();
            }
        });
    }


    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak Now");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),"Speech not Supported",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    if (resultCode == RESULT_OK && null != data) {
                        OldValue = eNote.getText().toString();
                        ArrayList<String> text = data
                                .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                        if (OldValue.matches("")) {

                            eNote.setText(text.get(0));
                            eNote.setSelection(eNote.getText().length());

                        }

                        else {

                            eNote.setText(OldValue + " " + text.get(0));
                            eNote.setSelection(eNote.getText().length());

                        }

                    }
                }
                break;
            }

        }
    }
    public void onSaveExternalrec() {
        String title = eTitle.getText().toString() + ".txt";
        String state;
        state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File Root = Environment.getExternalStorageDirectory();
            File Dir = new File(Root.getAbsolutePath() + "/AtomNotes");
            if (!Dir.exists()) {
                Dir.mkdir();

            }
            // later change how the text naming works
            File file = new File(Dir, title);
            String Message = eNote.getText().toString();
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(Message.getBytes());
                fileOutputStream.close();

                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {

            Toast.makeText(getApplicationContext(), "Failed to Save", Toast.LENGTH_LONG).show();


        }

    }


    public void cancelNote(){

        eNote.setText(" ");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_record_note, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_save) {
            onSaveExternalrec();
        }
        if(id == R.id.action_clear){
            cancelNote();

        }
        return super.onOptionsItemSelected(item);
    }
}
