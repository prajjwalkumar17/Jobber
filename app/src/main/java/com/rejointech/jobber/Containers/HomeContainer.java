package com.rejointech.jobber.Containers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.rejointech.jobber.CommonInterfaces.botnavController;
import com.rejointech.jobber.Fragments.Home.homeFragment;
import com.rejointech.jobber.Fragments.bookmarksFragment;
import com.rejointech.jobber.R;
import com.rejointech.jobber.Startup.splash;
import com.rejointech.jobber.Utils.Constants;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeContainer extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        botnavController.botVisibilityController {
    NavigationView nav_view;
    DrawerLayout drawer;
    ImageView navBotimg;
    View tool;
    NavController navController;
    Animation rotate;
    BottomNavigationView botnav;
    BottomAppBar completebotnav;
    TextView nav_name, nav_email;
    CircleImageView nav_dp;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.TOKENPREF, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(Constants.TOKEN, null);
        muticals();
//        defaultfragmentonstartup(savedInstanceState);
        addfirstFragment4User();
    }
    private void addfirstFragment4User() {
        getSupportFragmentManager().beginTransaction().add(R.id.maincontainerview,new homeFragment()).commit();
    }
//    private void defaultfragmentonstartup(Bundle savedInstanceState) {
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.maincontainerview, new homeFragment()).commit();
//        }
//    }

    private void muticals() {
        nav_view = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer);
        navBotimg = findViewById(R.id.toolwithbackbotheadbot);
        tool = findViewById(R.id.tool);
        navController = Navigation.findNavController(this, R.id.maincontainerview);
        rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        botnav = findViewById(R.id.botnav);
        botnav.setBackground(null);
        botnav.getMenu().getItem(2).setEnabled(false);
        completebotnav = findViewById(R.id.completebotnav);


        View headerView = nav_view.getHeaderView(0);
        nav_name = headerView.findViewById(R.id.nav_name);
        nav_email = headerView.findViewById(R.id.nav_email);
        nav_dp = headerView.findViewById(R.id.nav_dp);
        //TODO TO change profile in header
        nav_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.maincontainerview, new AccountsFragment()).addToBackStack(null).commit();
                setDrawerLocked();

            }
        });
        NavigationUI.setupWithNavController(nav_view, navController);
        NavigationUI.setupWithNavController(botnav, navController);
        nav_view.setNavigationItemSelectedListener(this);
        manageBottomNavigation(botnav);
        botnav.getMenu().findItem(R.id.botnav_menu_home).setChecked(true);
    }


    private void manageBottomNavigation(BottomNavigationView botnav) {
        botnav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.botnav_menu_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainerview, new homeFragment()).addToBackStack(null).commit();
                        break;

                    case R.id.botnav_menu_Bookmarks:
                        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainerview, new bookmarksFragment()).addToBackStack(null).commit();
                        break;
                    case R.id.botnav_menu_Categories:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainerview, new LeaderBoardFragment()).addToBackStack(null).commit();
                        break;
                    case R.id.botnav_menu_Message:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainerview, new AccountsFragment()).addToBackStack(null).commit();
                        break;
                }
                return true;
            }
        });
    }

    public void clickMenu(View view) {
        openDrawer(drawer);
        navBotimg.setAnimation(rotate);
    }
    private void openDrawer(DrawerLayout drawer) {
        drawer.openDrawer(GravityCompat.START);
    }
    private void closeDrawer(DrawerLayout drawer) {
        drawer.closeDrawer(GravityCompat.START);
    }
    @Override
    public void setDrawerLocked() {
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

    }

    @Override
    public void setDrawerunLocked() {
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
    }

    @Override
    public void setToolbarInvisible() {
        tool.setVisibility(View.GONE);

    }

    @Override
    public void setToolbarVisible() {
        tool.setVisibility(View.VISIBLE);
    }


    @Override
    public void setbotInvisible() {
        botnav.setVisibility(View.INVISIBLE);
        completebotnav.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setbotVisible() {
        botnav.setVisibility(View.VISIBLE);
        completebotnav.setVisibility(View.VISIBLE);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.nav_menu_account:
//                getSupportFragmentManager().beginTransaction().replace(R.id.maincontainerview, new AccountsFragment()).addToBackStack(null).commit();
//                break;
//            case R.id.nav_menu_dashboard:
//                getSupportFragmentManager().beginTransaction().replace(R.id.maincontainerview, new DashboardFragment()).addToBackStack(null).commit();
//                break;
//            case R.id.nav_menu_notes:
//                getSupportFragmentManager().beginTransaction().replace(R.id.maincontainerview, new NotesFragment()).addToBackStack(null).commit();
//                break;
//            case R.id.nav_menu_history:
//                getSupportFragmentManager().beginTransaction().replace(R.id.maincontainerview, new HistoryFragment()).addToBackStack(null).commit();
//                break;
//            case R.id.nav_menu_quiz:
//                CommonMethods.DisplayLongTOAST(getApplicationContext(), "Making in Progress Will be updated soon !");
//                break;
//            case R.id.nav_menu_tutorials:
//                CommonMethods.DisplayLongTOAST(getApplicationContext(), "Making in Progress Will be updated soon !");
//                break;

        }
        closeDrawer(drawer);
        return true;
    }


}