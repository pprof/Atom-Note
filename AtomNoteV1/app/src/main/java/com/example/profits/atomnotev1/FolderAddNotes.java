package com.example.profits.atomnotev1;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Environment;

import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;


public class FolderAddNotes extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText fWriteText;
    private EditText fTitleText;
    String OldValue = "";
    private FloatingActionButton floatingActionButton;
    private final int REQ_CODE_SPEECH_INPUT = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_add_notes);
        fWriteText = (EditText) findViewById(R.id.folderwriteText);
        fTitleText = (EditText) findViewById(R.id.foldernoteTitle);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.startrec);


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
                        OldValue = fWriteText.getText().toString();
                        ArrayList<String> text = data
                                .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                        if (OldValue.matches("")) {

                            fWriteText.setText(text.get(0));
                            fWriteText.setSelection(fWriteText.getText().length());

                        }

                        else {

                            fWriteText.setText(OldValue + " " + text.get(0));
                            fWriteText.setSelection(fWriteText.getText().length());

                        }

                    }
                }
                break;
            }

        }
    }

    public void onfolderSaveExternal() {
        String title = fTitleText.getText().toString() + ".txt";
        String state;
        state = Environment.getExternalStorageState();
        String ffTitle;
        Bundle extras2 = getIntent().getExtras();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            ffTitle = extras2.getString("foldertitle");
            File Root = Environment.getExternalStorageDirectory();
            File Dir = new File(Root.getAbsolutePath() + "/AtomFolders/");
            File subFol = new File(Dir, ffTitle);
            if (!Dir.exists()) {
                Dir.mkdir();

            }
            // later change how the text naming works
            File file = new File(subFol, title);
            String Message = fWriteText.getText().toString();
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

    public void fCancel() {
        fWriteText.setText(" ");
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
            onfolderSaveExternal();
        }
        if (id == R.id.action_clear) {
            fCancel();

        }
        return super.onOptionsItemSelected(item);
    }


}
