package personal.langmanagement.view.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.BuildConfig;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import personal.langmanagement.R;

/**
 * Created by hatemtoumi on 5/31/15.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();
    private static final String KILL_RECEIVER = BuildConfig.APPLICATION_ID + ".KILL_RECEIVER";

    private boolean isActivityVisible = false;
    private ProgressDialog progress;

    private BroadcastReceiver killReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == null)
                return;

            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         * Register killReceiver
         */
        IntentFilter intentFilter = new IntentFilter(BaseActivity.KILL_RECEIVER);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(killReceiver, intentFilter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.isActivityVisible = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.isActivityVisible = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        /*
         * unregister killReceiver
         */
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(killReceiver);
    }

    /**
     * Show ProgressDialog
     */
    public void showProgress(boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        if (!this.isActivityVisible)
            return;

        if (progress == null || !progress.isShowing())
            try {
                progress = ProgressDialog.show(this, "", "Please wait ...", true, cancelable,
                        cancelListener);
            } catch (Exception e) {
            }
    }

    /**
     * Hide ProgressDialog
     */
    public void hideProgress() {

        if (progress != null && progress.isShowing())
            try {
                progress.dismiss();
            } catch (Exception e) {
            }
    }

    /**
     * Recreate Activity
     */
    public void recreateActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    /**
     * Kill all Activities
     */
    public void broadcastKillIntent() {
        Intent intent = new Intent();
        intent.setAction(BaseActivity.KILL_RECEIVER);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    /**
     * Restart Application
     */
    public void restartApplication() {
        broadcastKillIntent();
        startActivity(new Intent(this, MainActivity.class));
    }

    protected boolean isActivityVisible() {
        return this.isActivityVisible;
    }
}
