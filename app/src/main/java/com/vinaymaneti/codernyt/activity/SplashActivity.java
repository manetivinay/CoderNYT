package com.vinaymaneti.codernyt.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.vinaymaneti.codernyt.R;
import com.vinaymaneti.codernyt.util.NetworkUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.activity_splash_activity)
    FrameLayout mActivity_splash_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_actiivty);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (NetworkUtils.isNetworkAvailable(SplashActivity.this))
                    startActivity(new Intent(SplashActivity.this, SearchActivity.class));
                else
                    networkAlertDialog();
            }
        }, 1000);
    }

    public void networkAlertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You are offline please check your internet connection");
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(dialogIntent);
            }
        });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
