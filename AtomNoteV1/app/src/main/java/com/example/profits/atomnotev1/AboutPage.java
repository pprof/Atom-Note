package com.example.profits.atomnotev1;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class AboutPage extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        String info = " An Android Application Utilizing Speech Recognition " +
                "That Will Automatically Write Down Notes Through Hidden Markov Model.\n" +
                "\n" +

                "Description:\n" +
                "\n" +
                "\tThis App enables you to convert your spoken speech to text. " +
                "It is an offline app and can use anywhere, but to make all words " +
                "be accurate you're environment must be quite and only the speaker can hear by your mobile " +
                "device.\n" +
                "\n" +

                "Features:\n" +
                "\n" +
                "1.\tOffline Appication.\n" +
                "2.\tSearch the created Notes and Folders.\n" +
                "3.\tEditable Notes.\n" +
                "4.\tCan highlight to copy words and paste while reading.\n" +
                "5.\tFriendly Application.\n" +
                "\n" +

                "Supported Language: English\n" +
                "\n" +

                "Compatibility:\n" +
                "\n" +
                "1.\tRequires Android version 4.4 (Kitkat) or later.\n" +
                "2.\tCompatible in any android smart phone and android tablet with 4.4 version.\n" +
                "3.\tCompatible in any smart phone with 5 inches size or android tablet.\n" +
                "\n" +
                "\t";

        textView= (TextView)findViewById(R.id.textView1);

        textView.setText(info);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if(id==android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);

        }

        return super.onOptionsItemSelected(item);
    }
}
