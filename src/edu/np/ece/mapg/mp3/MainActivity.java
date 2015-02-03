package edu.np.ece.mapg.mp3;

import java.util.Locale;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends Activity {
DrawerLayout DrawerLayout;
ListView DrawerList;
private ActionBarDrawerToggle DrawerToggle;

CharSequence DrawerTitle;
CharSequence Title;
String[] PolyTitles;
TextView Poly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Title = DrawerTitle = getTitle();
        PolyTitles = getResources().getStringArray(R.array.polys_array);
        DrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        DrawerList = (ListView) findViewById(R.id.left_drawer);
        Poly = (TextView) findViewById(R.id.poly);
        
        DrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, PolyTitles));
        DrawerList.setOnItemClickListener(new DrawerItemClickListener());
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        DrawerToggle = new ActionBarDrawerToggle(
                this,
                DrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(Title);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(DrawerTitle);
                invalidateOptionsMenu();
            }
        };
        DrawerLayout.setDrawerListener(DrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (DrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = DrawerLayout.isDrawerOpen(DrawerList);
        return super.onPrepareOptionsMenu(menu);
    }
    
 
    
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
    
    private void selectItem(int position) {
        Fragment fragment = new PolyFragment();
        Bundle args = new Bundle();
        args.putInt(PolyFragment.ARG_POLY_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        DrawerList.setItemChecked(position, true);
        setTitle(PolyTitles[position]);
        DrawerLayout.closeDrawer(DrawerList);        	
    }
    

    
    @Override
    public void setTitle(CharSequence title) {
        Title = title;
        getActionBar().setTitle(Title);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        DrawerToggle.syncState();
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        DrawerToggle.onConfigurationChanged(newConfig);
    }
    
    public static class PolyFragment extends Fragment {
        public static final String ARG_POLY_NUMBER = "poly_number";

        public PolyFragment() {
            // Empty constructor required for fragment subclasses
        }
        
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_poly, container, false);
            int i = getArguments().getInt(ARG_POLY_NUMBER);
            String poly = getResources().getStringArray(R.array.polys_array)[i];
            String polys = getResources().getStringArray(R.array.poly_array)[i];
            int imageId = getResources().getIdentifier(poly.toLowerCase(Locale.getDefault()),
                            "drawable", getActivity().getPackageName());
            ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
            ((TextView) rootView.findViewById(R.id.poly)).setText(polys);
            getActivity().setTitle(poly);
            return rootView;
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
}
