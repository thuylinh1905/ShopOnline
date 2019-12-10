package com.example.shoponline.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.shoponline.GiohangFragment;
import com.example.shoponline.HoadonChitiet;
import com.example.shoponline.R;
import com.example.shoponline.Thongtin;
import com.example.shoponline.fragment.TrangChinh;
import com.example.shoponline.fragment.categoryFragment;
import com.example.shoponline.model.GiohangMD;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    int id1 = 0;
    String username = "";
    String phone = "";
    String email = "";
    String location = "";
    String success = "";
    private long backPressTime;
    public static ArrayList<GiohangMD> giohangMDS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context mContext = getApplicationContext();
        setContentView(R.layout.activity_main);
        if (giohangMDS != null) {

        } else {
            giohangMDS = new ArrayList<>();
        }
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            id1 = bundle.getInt("ID");
            username = bundle.getString("Username");
            phone = bundle.getString("Phone");
            email = bundle.getString("Email");
            location = bundle.getString("Location");
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            TrangChinh trangChinh = new TrangChinh();
            Bundle bundles = new Bundle();
            bundles.putInt("idss", id1);
            bundles.putString("namess", username);
            bundles.putString("phoness", phone);
            bundles.putString("emailss", email);
            bundles.putString("loationss", location);
            trangChinh.setArguments(bundles);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, trangChinh).commit();
            setTitle("Trang Chính");
            navigationView.setCheckedItem(R.id.home_nav);
        }
        navigationView.setNavigationItemSelectedListener(this);

//

    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (backPressTime + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
                return;
            } else {
                Toast.makeText(this, "Click Lần nữa để thoát ", Toast.LENGTH_SHORT).show();
            }
            backPressTime = System.currentTimeMillis();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home_nav) {
            TrangChinh trangChinh = new TrangChinh();
            Bundle bundles = new Bundle();
            bundles.putInt("idss", id1);
            bundles.putString("namess", username);
            bundles.putString("phoness", phone);
            bundles.putString("emailss", email);
            bundles.putString("loationss", location);
            trangChinh.setArguments(bundles);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    trangChinh).commit();
            setTitle("Trang Chính");
            CheckInternet();
        } else if (id == R.id.category_nav) {
            categoryFragment categoryFragments = new categoryFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id", id1);
            bundle.putString("name", username);
            bundle.putString("phone", phone);
            bundle.putString("email", email);
            bundle.putString("loation", location);
            categoryFragments.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, categoryFragments).commit();
            setTitle("Danh Mục");
            CheckInternet();
        } else if (id == R.id.cart_nav) {
            GiohangFragment trangChinh = new GiohangFragment();
            Bundle bundles = new Bundle();
            bundles.putInt("idss", id1);
            bundles.putString("namess", username);
            bundles.putString("phoness", phone);
            bundles.putString("emailss", email);
            bundles.putString("loationss", location);
            trangChinh.setArguments(bundles);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    trangChinh).commit();
            setTitle("Giỏ Hàng");
            CheckInternet();

        }else if (id==R.id.don_nav) {
            HoadonChitiet chitiet = new HoadonChitiet();
            Bundle bundles = new Bundle();
            bundles.putInt("idss", id1);

            chitiet.setArguments(bundles);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    chitiet).commit();
            setTitle("Hóa đơn của tôi");
            CheckInternet();

        }else if (id == R.id.contact_nav) {



        } else if (id == R.id.intro_nav) {
Intent intent=new Intent(MainActivity.this, Thongtin.class);
startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public final boolean CheckInternet() {
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            DialogMessegerInternet();
            return false;
        }
        return false;
    }

    public void DialogMessegerInternet() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Thông Báo");
        builder.setMessage("Vui Lòng Kiểm Tra Kết Nối Internet!");
        builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }
}
