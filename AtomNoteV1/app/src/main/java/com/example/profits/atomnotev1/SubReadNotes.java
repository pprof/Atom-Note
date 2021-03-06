package com.example.profits.atomnotev1;

import android.content.DialogInterface;
import android.os.Environment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class SubReadNotes extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView textView;
    private EditText editText;
    private Button mSavebut;
    private Button mCancelbut;
    String subnTitle;
    String subfolder;

    TextView scaleGesture;
    ScaleGestureDetector scaleGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_read_notes);

        Bundle extras2 = getIntent().getExtras();
        subnTitle= extras2.getString("subnoteitem");
        subfolder = extras2.getString("subftitle");
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(subnTitle);
        textView = (TextView)findViewById(R.id.subreadNotes);
        editText = (EditText)findViewById(R.id.edttextreal);
        mSavebut = (Button)findViewById(R.id.savebyt);
        mCancelbut = (Button)findViewById(R.id.canbyt);

        textView.setMovementMethod(new ScrollingMovementMethod());
        editText.setVisibility(View.GONE);
        mSavebut.setVisibility(View.GONE);
        mCancelbut.setVisibility(View.GONE);

        scaleGesture = (TextView) findViewById(R.id.subreadNotes);
        scaleGestureDetector = new ScaleGestureDetector(this, new simpleOnScaleGestureListener());

        File folderRoot = Environment.getExternalStorageDirectory();
        File folderDir = new File(folderRoot.getAbsolutePath() + "/AtomFolders/");
        File subDir = new File(folderDir,subfolder);
        File file = new File(subDir,subnTitle);
        String Notes;

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();

            while((Notes=bufferedReader.readLine())!=null){

                stringBuffer.append(Notes + "\n");


            }
            textView.setText(stringBuffer.toString());
            editText.setText(stringBuffer.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
    public void cancelbyt(View view){
        textView.setVisibility(View.VISIBLE);
        editText.setVisibility(View.GONE);
        mSavebut.setVisibility(View.GONE);
        mCancelbut.setVisibility(View.GONE);

    }
    public void saveBytt(View view) {

        String state;
        state = Environment.getExternalStorageState();
        String ffTitle;
        Bundle extras2 = getIntent().getExtras();
        if (Environment.MEDIA_MOUNTED.equals(state)) {

            File Root = Environment.getExternalStorageDirectory();
            File Dir = new File(Root.getAbsolutePath() + "/AtomFolders/");
            File subFol = new File(Dir,subfolder);
            if (!Dir.exists()) {
                Dir.mkdir();

            }
            // later change how the text naming works
            File file = new File(subFol, subnTitle);
            String Message = editText.getText().toString();
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(Message.getBytes());
                fileOutputStream.close();
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
                finish();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {

            Toast.makeText(getApplicationContext(), "Failed to Save", Toast.LENGTH_LONG).show();


        }

        textView.setVisibility(View.VISIBLE);
        editText.setVisibility(View.GONE);
        mSavebut.setVisibility(View.GONE);
        mCancelbut.setVisibility(View.GONE);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sub_read_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.editaction) {
           if(editText.isShown()){
               textView.setVisibility(View.VISIBLE);
               editText.setVisibility(View.GONE);
               mSavebut.setVisibility(View.GONE);
               mCancelbut.setVisibility(View.GONE);
           }else {

               textView.setVisibility(View.GONE);
               editText.setVisibility(View.VISIBLE);
               mSavebut.setVisibility(View.VISIBLE);
               mCancelbut.setVisibility(View.VISIBLE);
           }

        }
        if (id ==R.id.deleteaction){

           alert();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }
    public class simpleOnScaleGestureListener extends
            ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            // TODO Auto-generated method stub
            float size = scaleGesture.getTextSize();
            Log.d("TextSizeStart", String.valueOf(size));

            float factor = detector.getScaleFactor();
            Log.d("Factor", String.valueOf(factor));

            float product = size * factor;
            Log.d("TextSize", String.valueOf(product));
            scaleGesture.setTextSize(TypedValue.COMPLEX_UNIT_PX, product);

            size = scaleGesture.getTextSize();
            Log.d("TextSizeEnd", String.valueOf(size));
            return true;
        }
    }
    public void alert(){

        new AlertDialog.Builder(this)
                .setTitle("Delete " + subnTitle )
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        File folderRoot = Environment.getExternalStorageDirectory();
                        File folderDir = new File(folderRoot.getAbsolutePath() + "/AtomFolders/");
                        File subDir = new File(folderDir,subfolder);
                        File file = new File(subDir,subnTitle);



                        file.delete();
                        Toast.makeText(getApplicationContext(), subnTitle + " Deleted", Toast.LENGTH_LONG).show();
                        finish();

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
