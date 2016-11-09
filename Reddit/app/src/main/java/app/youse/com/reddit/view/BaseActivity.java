package app.youse.com.reddit.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import app.youse.com.reddit.R;
import app.youse.com.reddit.utils.ConnectionUtil;

/**
 * Created by heitornascimento on 11/2/16.
 */

public class BaseActivity extends AppCompatActivity {

    public String DEBUG = "reddit";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(DEBUG, "base activity on create");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(DEBUG, "base activity on resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(DEBUG, "base activity on pause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(DEBUG, "base activity on destroy");
    }

    protected boolean checkInternetConnection() {

        boolean result = ConnectionUtil.hasValidaConnection(this);

        if (!result) {
            Snackbar snackbar = Snackbar.
                    make(findViewById(R.id.cl),
                            getResources().getString(R.string.no_internet), Snackbar.LENGTH_SHORT)
                    .setAction("Settings", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                        }
                    });
            snackbar.show();
        }
        return result;
    }
}
