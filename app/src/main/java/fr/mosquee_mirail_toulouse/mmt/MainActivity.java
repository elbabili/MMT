package fr.mosquee_mirail_toulouse.mmt;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import fr.mosquee_mirail_toulouse.mmt.DataBase.MainDataBase;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ImageButton btnOpenNewsActivity;
    private ImageButton btnOpenDjanazaActivity;
    private ImageButton btnOpenSalatActivity;
    private ImageButton btnOpenDonActivity;
    private ImageButton btnOpenWebSite;

    private DrawerLayout drawerLayout;
    //private Toolbar toolbar;
    private NavigationView navViewDrawer;
    private ActionBarDrawerToggle drawerToggle;

    public static MainDataBase mainDataBase = null;
    /*public static MainDataBase getMainDataBase() {
        return mainDataBase;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreate de MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainDataBase = new MainDataBase(this);      // création et ouverture base de donnée !

        /*-----------------Gestion du menu-----------*/
        // Set a Toolbar to replace the ActionBar.
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        // Find our drawer view
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navViewDrawer = (NavigationView) findViewById(R.id.navView);
        // Setup drawer view
        navViewDrawer.setNavigationItemSelectedListener(this);

        /*---------------Gestion des actions sur les boutons---------*/
        btnOpenNewsActivity = (ImageButton) findViewById(R.id.btnActu);
        btnOpenNewsActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,NewsActivity.class);
                //intent.putExtra(...);
                startActivity(intent);
            }
        });

        btnOpenDonActivity = (ImageButton) findViewById(R.id.btnDon);
        btnOpenDonActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,DonActivity.class);
                //intent.putExtra(...);
                startActivity(intent);
            }
        });

        btnOpenWebSite = (ImageButton) findViewById(R.id.btnSite);
        btnOpenWebSite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String url = "http://www.mosquee-mirail-toulouse.fr";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        btnOpenSalatActivity = (ImageButton) findViewById(R.id.btnSalat);
        btnOpenSalatActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,SalatActivity.class);
                //intent.putExtra("message", );
                startActivity(intent);
            }
        });

        btnOpenDjanazaActivity = (ImageButton) findViewById(R.id.btnDjanaza);
        btnOpenDjanazaActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,DjanazaActivity.class);
                //intent.putExtra(...);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item))    return true;
        /*switch(item.getItemId()){
            case R.id.nav_home:
                closeContextMenu();
                break;
            case R.id.nav_mmt:

                break;
            case R.id.nav_contact:

                break;
            case R.id.nav_facebook:
                String url = "https://fr-fr.facebook.com/MosqueeMirailToulouse/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                break;
            default:
        }

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Snackbar.make(drawerLayout, item.getTitle(), Snackbar.LENGTH_SHORT).show();
        drawerLayout.closeDrawers();
        return true;
    }

}

/*  @Override
    public void onStart() {
        System.out.println("onStart de MainActivity");
        super.onStart();
    }
    @Override
    public void onResume() {
        System.out.println("onResume de MainActivity");
        super.onResume();
    }
    @Override
    protected void onPause() {
        System.out.println("onPause de MainActivity");
        super.onPause();
    }
    @Override
    protected void onStop() {
        System.out.println("onStop de MainActivity");
        super.onStop();
    }
    @Override
    protected void onRestart() {
        System.out.println("onRestart de MainActivity");
        super.onRestart();
    }
    @Override
    protected void onDestroy() {
        System.out.println("onDestroy de MainActivity");
        super.onDestroy();
    }*/


    /*
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }*/

    /*public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                //fragmentClass = FirstFragment.class;
                break;
            case R.id.nav_second_fragment:
                //fragmentClass = SecondFragment.class;
                break;
            case R.id.nav_third_fragment:
                //fragmentClass = ThirdFragment.class;
                break;
            default:
                //fragmentClass = FirstFragment.class;
        }

        try {
            //fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        //fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        drawerLayout.closeDrawers();
    }*/

/*   @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.hamburgerMenu :
                drawerLayout.openDrawer(Gravity.LEFT);
                return true;

            case R.id.txtViewFirst :
                Toast.makeText(this, "Aymene rhasso la3ssa", Toast.LENGTH_LONG).show();
                return true;

            case R.id.txtViewSecond :
                Toast.makeText(this, "Maryam rhassa lhrawa", Toast.LENGTH_LONG).show();
                return true;

            case R.id.txtViewThird :
                Toast.makeText(this, "Saadia rhassa la3ssa o lhrawa", Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/