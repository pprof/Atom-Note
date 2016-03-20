package com.example.profits.atomnotev1;

import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;


public class add_folder_activity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_folder_activity);
        editText= (EditText) findViewById(R.id.folderName);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

    }

    public void newFolder(View view) {
        String state;
        String mFolderName = editText.getText().toString();
        state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File Root = Environment.getExternalStorageDirectory();
            File Dir = new File(Root.getAbsolutePath() + "/AtomFolders");

            if (!Dir.exists()) {
                Dir.mkdir();

            } File file = new File(Dir,mFolderName);
                file.mkdir();
                Toast.makeText(getApplicationContext(), "Folder created", Toast.LENGTH_LONG).show();
                finish();
        } else {
            Toast.makeText(getApplicationContext(), "Failed to create folder", Toast.LENGTH_LONG).show();
        }
    }
    public void cancelFolder(View view){

        finish();


    }




}
