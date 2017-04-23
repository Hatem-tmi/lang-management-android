package personal.langmanagement.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.preferencefragment.PreferenceFragment;

import personal.langmanagement.LangManageApp;
import personal.langmanagement.R;
import personal.langmanagement.util.AppLanguage;
import personal.langmanagement.view.activity.BaseActivity;

public class MyPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final int PREFS_FILE = R.xml.preferences_settings;
    private BaseActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (BaseActivity) context;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(PREFS_FILE);

        PreferenceManager.getDefaultSharedPreferences(this.activity).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (isAdded()) {
            if (key.equals(getResources().getString(R.string.pref_key_app_language))) {
                String value = sharedPreferences.getString(key, AppLanguage.AppLanguageEnum.ENGLISH_CODE.toString());
                LangManageApp.getInstance().setAppLanguage(AppLanguage.AppLanguageEnum.valueOf(value));

                // Update Application language
                AppLanguage.getInstance(activity).updateAppLanguage();

                // Restart application
                activity.restartApplication();
            }
        }
    }
}

