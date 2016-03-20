package com.example.profits.atomnotev1;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.profits.atomnotev1.tabs.SlidingTabLayout;
import com.melnykov.fab.FloatingActionButton;

import java.io.File;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager mPager;
    private SlidingTabLayout mTabs;
    private FloatingActionButton floatingActionButton;
    private EditText searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_appbar);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.navrecord);
        toolbar = (Toolbar) findViewById(R.id.app_bar);



        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        /** Drawer **/

        DrawerFragment drawerFragment = (DrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);
        drawerFragment.setUp(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);


        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setDistributeEvenly(true);
        mTabs.setTabsBackgroundColor(getResources().getColor(R.color.primaryColor));
        mTabs.setCustomTabView(R.layout.custom_tab_view, R.id.tabText);
        mTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.slidingCol);
            }
        });

        mTabs.setViewPager(mPager);


    }

    public void onClick(View view) {

        startActivity(new Intent(this, RecordNote.class));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();


        if (id == R.id.addfolder) {
            startActivity((new Intent(this, add_folder_activity.class)));
        }


        return super.onOptionsItemSelected(item);


    }


    public static class MyFragment extends Fragment {


        public static MyFragment getInstance(int position) {
            MyFragment myFragment = new MyFragment();
            Bundle args = new Bundle();
            args.putInt("position", position);
            myFragment.setArguments(args);
            return myFragment;


        }


        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


            //Root for notes only
            File noteRoot = Environment.getExternalStorageDirectory();
            final File noteDir = new File(noteRoot.getAbsolutePath() + "/AtomNotes");
            final String[] filenames = noteDir.list();
            //Button search
            View layout = inflater.inflate(R.layout.list_notes, container, false);
            EditText searchword = (EditText) layout.findViewById(R.id.search_notes);



            //Root for folders only
            File folderRoot = Environment.getExternalStorageDirectory();
            File folderDir = new File(folderRoot.getAbsolutePath() + "/AtomFolders/");
            String[] foldernames = folderDir.list();
            View layout2 = inflater.inflate(R.layout.list_folders, container, false);
            EditText searchfolder = (EditText) layout2.findViewById(R.id.search_folders);

            View display = null;

            //start of tabs
            Bundle bundle = getArguments();
            if (bundle.getInt("position") == 0) {
                if (filenames == null) {
                    Toast.makeText(getActivity(), "No Notes Created", Toast.LENGTH_LONG).show();
                } else {

                    ListView listView1 = (ListView) layout.findViewById(R.id.listView);


                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.mylist, R.id.Itemname, filenames);
                    adapter.notifyDataSetChanged();

                    listView1.setAdapter(adapter);



                    //search function
                    searchword.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                            adapter.getFilter().filter(s.toString());

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                    //clicks
                    //reading note
                    listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent i = new Intent(getActivity(), ReadNotes.class);
                            Bundle bundle1 = new Bundle();
                            String item = (String.valueOf(parent.getItemAtPosition(position)));
                            bundle1.putString("noteitem", item);
                            i.putExtras(bundle1);
                            startActivity(i);
                        }
                    });
                    //menu
                    adapter.notifyDataSetChanged();

                    display = layout;


                }
            } else if (bundle.getInt("position") == 1) {
                if (foldernames == null) {
                    Toast.makeText(getActivity(), "No Folders Created", Toast.LENGTH_LONG).show();
                } else {
                    ListView listView2 = (ListView) layout2.findViewById(R.id.listFolder);
                    final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),
                            R.layout.mylist2, R.id.Folname, foldernames);
                    adapter2.notifyDataSetChanged();

                    listView2.setAdapter(adapter2);

                    searchfolder.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                            adapter2.getFilter().filter(s.toString());

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Intent i2 = new Intent(getActivity(), FolderView.class);
                            Bundle b2 = new Bundle();
                            String folderitem = (String.valueOf(parent.getItemAtPosition(position)));


                            b2.putString("folderitem", folderitem);
                            i2.putExtras(b2);
                            startActivity(i2);

                        }
                    });

                    adapter2.notifyDataSetChanged();
                    display = layout2;


                }

            }


            return display;


        }


    }


    class MyPagerAdapter extends FragmentPagerAdapter {

        int icons[] = {R.drawable.note_pad, R.drawable.folder};
        String[] tabText = getResources().getStringArray(R.array.tabs);

        public MyPagerAdapter(FragmentManager fm) {

            super(fm);
            tabText = getResources().getStringArray(R.array.tabs);

        }

        public Fragment getItem(int position) {

            MyFragment myFragment = MyFragment.getInstance(position);
            return myFragment;

        }

        public CharSequence getPageTitle(int position) {

            Drawable drawable = getResources().getDrawable(icons[position]);
            drawable.setBounds(0, 0, 40, 40);

            ImageSpan imageSpan = new ImageSpan(drawable);

            SpannableString spannableString = new SpannableString(" ");
            spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            return spannableString;


        }

        public int getCount() {
            return 2;
        }


    }


}
