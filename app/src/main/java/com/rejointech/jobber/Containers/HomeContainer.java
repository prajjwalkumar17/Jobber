package com.rejointech.jobber.Containers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.rejointech.jobber.APICall.APICall;
import com.rejointech.jobber.CommonInterfaces.botnavController;
import com.rejointech.jobber.Fragments.Home.homeFragment;
import com.rejointech.jobber.Fragments.Profile.profileFragment;
import com.rejointech.jobber.Fragments.bookmarksFragment;
import com.rejointech.jobber.R;
import com.rejointech.jobber.Startup.StartupContainer;
import com.rejointech.jobber.Utils.CommonMethods;
import com.rejointech.jobber.Utils.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
    FloatingActionButton home_fab;
    BottomAppBar completebotnav;
    TextView nav_name, nav_view_profile,current_pos;
    ImageView nav_dp;
    String token,dp,url,name,Current_designation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.TOKENPREF, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(Constants.TOKEN, null);
        muticals();
        getme();
        addfirstFragment4User();
    }


    private void getme() {
        APICall.okhttpMaster().newCall(APICall.get4me(APICall.urlBuilder4http(Constants.getme),token)).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                CommonMethods.LOGthesite(Constants.LOG, e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String myResponse = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject myres = new JSONObject(myResponse);
                                JSONObject data=myres.optJSONObject("data");
                                JSONObject redata=data.optJSONObject("data");
                               name=redata.optString("Name");
                                String Email=redata.optString("Email");
                                Current_designation=redata.optString("Current_designation");
                                dp=redata.optString("Photo");
                                String Resume=redata.optString("Resume");
                                addDatatoPrefs(name,Email,Current_designation,dp,Resume);
                                url=Constants.dpurl+dp;
                                nav_name.setText(name);
                                current_pos.setText(Current_designation);
                                Picasso.get()
                                        .load(url)
                                        .error(R.drawable.defaultdp)
                                        .into(nav_dp);

                            } catch (Exception e) {
                                CommonMethods.LOGthesite(Constants.LOG, e.getMessage());

                            }
                        }
                    });

            }
        });
    }

    private void addDatatoPrefs(String name, String email, String current_designation, String dp, String resume) {
        SharedPreferences sharedPreferences =getSharedPreferences(Constants.PROFILEPREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.PROFILEname, name);
        editor.putString(Constants.PROFILEemail, email);
        editor.putString(Constants.PROFILEcurrent_designation, current_designation);
        CommonMethods.LOGthesite(Constants.LOG,dp);
        editor.putString(Constants.PROFILEdp, dp);
        editor.putString(Constants.PROFILEresume, resume);
        editor.apply();
    }

    private void addfirstFragment4User() {
        getSupportFragmentManager().beginTransaction().add(R.id.maincontainerview,new homeFragment()).commit();
    }


    private void muticals() {
        nav_view = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer);
        navBotimg = findViewById(R.id.toolwithbackbotheadbot);
        home_fab = findViewById(R.id.home_fab);
        tool = findViewById(R.id.tool);
        navController = Navigation.findNavController(this, R.id.maincontainerview);
        rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        botnav = findViewById(R.id.botnav);
        botnav.setBackground(null);
        botnav.getMenu().getItem(2).setEnabled(false);
        completebotnav = findViewById(R.id.completebotnav);


        View headerView = nav_view.getHeaderView(0);
        nav_name = headerView.findViewById(R.id.profilename);
        current_pos = headerView.findViewById(R.id.current_pos);
        nav_view_profile = headerView.findViewById(R.id.nav_email);
        ImageView backbot = headerView.findViewById(R.id.backbot);
        nav_dp = headerView.findViewById(R.id.profiledp);

        backbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDrawer(drawer);
            }
        });
        //TODO TO change profile in header
        nav_view_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.maincontainerview, new profileFragment()).addToBackStack(null).commit();
                setDrawerLocked();

            }
        });
        NavigationUI.setupWithNavController(nav_view, navController);
        NavigationUI.setupWithNavController(botnav, navController);
        nav_view.setNavigationItemSelectedListener(this);
        manageBottomNavigation(botnav);
        botnav.getMenu().findItem(R.id.botnav_menu_home).setChecked(true);
        home_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.maincontainerview, new profileFragment()).addToBackStack(null).commit();

            }
        });
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
        botnav.setVisibility(View.GONE);
        completebotnav.setVisibility(View.GONE);
    }

    @Override
    public void setbotVisible() {
        botnav.setVisibility(View.VISIBLE);
        completebotnav.setVisibility(View.VISIBLE);

    }

    @Override
    public void setfabinvisible() {
        home_fab.setVisibility(View.GONE);
    }

    @Override
    public void setfabvisible() {
        home_fab.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_menu_bookmarks:
                getSupportFragmentManager().beginTransaction().replace(R.id.maincontainerview, new bookmarksFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_menu_personalinfo:
                getSupportFragmentManager().beginTransaction().replace(R.id.maincontainerview, new profileFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_menu_Logout:
            {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Logout Confirmation");
                alertDialog.setMessage("Are you sure you want to Logout?");
                alertDialog.setIcon(R.drawable.ic_err);

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Logout", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        CommonMethods.DisplayShortTOAST (getApplicationContext(), "You are going to be Logged out");
                        logout();
                    }
                });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
                break;
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
    private void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.TOKENPREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.TOKEN, null);
        editor.apply();
        Intent intent = new Intent(this, StartupContainer.class);
        startActivity(intent);
    }

}