package com.covain.projects.moneycounter.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.covain.projects.moneycounter.R;
import com.covain.projects.moneycounter.enums.EditMode;
import com.covain.projects.moneycounter.fragment.ArchiveFragment;
import com.covain.projects.moneycounter.fragment.PaymentDialogFragment;
import com.covain.projects.moneycounter.fragment.TodaysFragment;
import com.covain.projects.moneycounter.model.Payment;
import com.covain.projects.moneycounter.presenter.IPaymentsPresenter;
import com.covain.projects.moneycounter.repository.PaymentRepository;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Covain on 6/11/2017.
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IPaymentsPresenter {

    @BindView(R.id.fragments_container)
    FrameLayout mFragmentsContainer;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private final PaymentRepository paymentRepository = new PaymentRepository();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
        if (savedInstanceState == null) {
            switchFragment(new TodaysFragment());
        }
    }

    protected void init() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (id == R.id.nav_today) {
            switchFragment(new TodaysFragment());
        } else if (id == R.id.nav_history_text) {
            switchFragment(new ArchiveFragment());
        } else if (id == R.id.nav_history_graphical) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void switchFragment(Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragments_container, fragment)
                .commit();
    }

    public void showPaymentDialog(EditMode editMode, Payment payment) {
        PaymentDialogFragment.builder()
                .setMode(editMode)
                .setPayment(payment)
                .create()
                .show(getFragmentManager(), "paymentDialog");
    }

    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    @Override
    public void addPayment(Payment payment) {
        paymentRepository.addPayment(payment);
    }

    @Override
    public void editPayment(Payment payment) {
        showPaymentDialog(EditMode.EDIT, payment);
    }

    @Override
    public void updatePayment(Payment payment) {
        paymentRepository.editPayment(payment);
    }

    @Override
    public void deletePayment(long paymentId) {
        paymentRepository.deletePaymentById(paymentId);
    }

}
