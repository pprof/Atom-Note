package com.example.profits.atomnotev1;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.profits.atomnotev1.tabs.Help;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrawerFragment extends Fragment  {
    private RecyclerView recyclerView;
    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER="user_learned_drawer";
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private AtomAdapter adapter;
    private Context context;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private View containerView;
    public DrawerFragment() {
        // Required empty public constructor
    }

    public void  onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        mUserLearnedDrawer=Boolean.valueOf(readFromPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,"false"));
        if(savedInstanceState!=null){
            mFromSavedInstanceState=true;


        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View layout=inflater.inflate(R.layout.fragment_drawer, container, false);
        recyclerView= (RecyclerView) layout.findViewById(R.id.drawerList);
        adapter = new AtomAdapter(getActivity(),getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
            /* add action here */


                Intent intent;
                switch (position) {

                    case 0:
                        intent = new Intent(getActivity(), AboutPage.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        getActivity().startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(getActivity(), Help.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        getActivity().startActivity(intent);
                        break;

                }





            }

            @Override
            public void onLongClick(View view, int position) {





            }
        }));
        return layout;
    }
    public static List<Information> getData(){

        List<Information> data=new ArrayList<>();
        int[] icon ={R.drawable.download2,R.drawable.download3};
        String[] titles = {"About","Help"};
        for(int i=0; i<titles.length && i<icon.length; i++ ){

            Information current =new Information();
            current.iconId=icon[i];
            current.title=titles[i];
            data.add(current);

        }
        return data;

    }

    public void setUp(int fragmentId,DrawerLayout drawerLayout,final Toolbar toolbar) {
        containerView=getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer){
                    mUserLearnedDrawer=true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer+"");


                }
                getActivity().invalidateOptionsMenu();
            }

            public void onDrawerClosed(View drawerView) {

               super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();


            }

            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (slideOffset < 0.6) {
                    toolbar.setAlpha(1- slideOffset);


                }
            }

        };
        if(!mUserLearnedDrawer && !mFromSavedInstanceState){
            mDrawerLayout.openDrawer(containerView);

        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();


    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
      return sharedPreferences.getString(preferenceName,defaultValue);

    }


    static class RecyclerTouchListener implements  RecyclerView.OnItemTouchListener{


        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener){
        this.clickListener=clickListener;
        gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){

            public boolean onSingleTapUp(MotionEvent e){

                return  true;

            }

            public void onLongPress(MotionEvent e){
             View child = recyclerView.findChildViewUnder(e.getX(),e.getY());
             if(child!= null && clickListener!=null){
                 clickListener.onLongClick(child, recyclerView.getChildPosition(child));

             }
            }


        });


        }


       @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

           View child = rv.findChildViewUnder(e.getX(),e.getY());
           if(child!=null && clickListener!=null && gestureDetector.onTouchEvent(e)){

            clickListener.onClick(child, rv.getChildPosition(child));

           }

           return false;


       }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

    }

    public static interface ClickListener
    {
    public void onClick(View view, int position);
        public void onLongClick(View view, int position);



    }
}