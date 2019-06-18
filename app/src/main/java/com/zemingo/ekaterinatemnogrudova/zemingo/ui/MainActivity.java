package com.zemingo.ekaterinatemnogrudova.zemingo.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zemingo.ekaterinatemnogrudova.zemingo.databinding.ActivityMainBinding;
import com.zemingo.ekaterinatemnogrudova.zemingo.R;
import com.zemingo.ekaterinatemnogrudova.zemingo.utils.RssParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.zemingo.ekaterinatemnogrudova.zemingo.utils.Constants.FRAGMENT_GLOBES;
import static com.zemingo.ekaterinatemnogrudova.zemingo.utils.DateUtil.getCurrentDateTime;

public class MainActivity extends AppCompatActivity {

    public ActivityMainBinding mBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinder.toolBar.setTitle(getString(R.string.app_name));
        mBinder.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobesFragment articleFragment = GlobesFragment.newInstance();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, articleFragment, FRAGMENT_GLOBES);
                fragmentTransaction.addToBackStack(FRAGMENT_GLOBES);
                fragmentTransaction.commitAllowingStateLoss();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        mBinder.tvDatetime.setText(getResources().getString(R.string.date_time) + " " + getCurrentDateTime());
    }

}
