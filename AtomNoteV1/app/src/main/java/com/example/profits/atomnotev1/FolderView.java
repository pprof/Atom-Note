package com.example.profits.atomnotev1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;


public class FolderView extends AppCompatActivity {

    private Toolbar toolbar;
    String fTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_view);

        Bundle extras1 = getIntent().getExtras();
        fTitle = extras1.getString("folderitem");
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(fTitle);
      if(extras1==null){
          Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();

      }else{
          ListView listView2 = (ListView)findViewById(R.id.listView2);

          File folderRoot = Environment.getExternalStorageDirectory();
          File folderDir = new File(folderRoot.getAbsolutePath() + "/AtomFolders/");
          File file = new File(folderDir,fTitle);
          String[] foldernames = file.list();
          if(foldernames==null){
              Toast.makeText(getApplicationContext(),"Folder is empty",Toast.LENGTH_LONG).show();

          }else {


              ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                      R.layout.mylist,R.id.Itemname, foldernames);
              adapter2.notifyDataSetChanged();
              listView2.setAdapter(adapter2);

              listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                  @Override
                  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                      String subftitle;
                      Intent i = new Intent(getApplication(),SubReadNotes.class);

                      Bundle bundle1 = new Bundle();
                      String item = (String.valueOf(parent.getItemAtPosition(position)));
                      bundle1.putString("subnoteitem", item);


                      Bundle extras1 = getIntent().getExtras();
                      subftitle = extras1.getString("folderitem");
                      String mfoldertitle = subftitle;

                      bundle1.putString("subftitle",mfoldertitle);
                      i.putExtras(bundle1);
                      startActivity(i);
                  }
              });
          }

      }

    }


    public void folderAddNote(View view){
       // startActivity(new Intent(this, NewNote.class));
        String fTitle;
        Intent i2 = new Intent(this,FolderAddNotes.class);
        Bundle b2 = new Bundle();
        Bundle extras1 = getIntent().getExtras();
        fTitle = extras1.getString("folderitem");
        String mfoldertitle = fTitle;


        b2.putString("foldertitle",mfoldertitle);
        i2.putExtras(b2);
        startActivity(i2);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_folder_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id ==R.id.deletefol){

          alert();

        }


        return super.onOptionsItemSelected(item);
    }

    public void alert(){

        new AlertDialog.Builder(this)
                .setTitle("Delete " + fTitle )
                .setMessage("Are you sure you want to delete this folder and all of its files?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        File folderRoot = Environment.getExternalStorageDirectory();
                        File folderDir = new File(folderRoot.getAbsolutePath() + "/AtomFolders/");
                        File file = new File(folderDir,fTitle);
                        if (file.isDirectory())
                            for (File child : file.listFiles())
                                child.delete();

                        file.delete();




                        Toast.makeText(getApplicationContext(), fTitle + " Deleted", Toast.LENGTH_LONG).show();
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


