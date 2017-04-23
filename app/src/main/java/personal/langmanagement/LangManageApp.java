package personal.langmanagement;

import android.app.Application;
import android.content.SharedPreferences;

import java.util.Locale;

import personal.langmanagement.util.AppLanguage;

/**
 * Created by Toumi on 23/04/2017.
 */

public class LangManageApp extends Application {
    private static final String SHAREDPREFS_FILENAME = "langmanage_prefs";
    private static LangManageApp instance;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        this.prefs = getSharedPreferences(SHAREDPREFS_FILENAME, MODE_PRIVATE);

        if (isFirstRun()) {
            setFirstRunFlag(false);
            InitializeLanguage();
        } else
            AppLanguage.getInstance(getApplicationContext()).updateAppLanguage(getAppLanguage());
    }

    public static LangManageApp getInstance() {
        return instance;
    }

    public void setFirstRunFlag(boolean isFirstRun) {
        editor = prefs.edit();
        editor.putBoolean("firstRun", isFirstRun);
        editor.commit();
    }

    public boolean isFirstRun() {
        return prefs.getBoolean("firstRun", true);
    }

    public void setAppLanguage(AppLanguage.AppLanguageEnum appLanguage) {
        editor = prefs.edit();
        editor.putString("language", appLanguage.toString());
        editor.commit();
    }

    public AppLanguage.AppLanguageEnum getAppLanguage() {
        String language = prefs.getString("language", AppLanguage.AppLanguageEnum.ENGLISH_CODE.toString());

        return AppLanguage.AppLanguageEnum.valueOf(language);
    }

    /**
     * Initialize App language
     */
    private void InitializeLanguage() {
        String language = Locale.getDefault().getDisplayLanguage();

        if (language != null && language.length() > 0) {
            if (language.equals(getResources().getString(R.string.LG_ENGLISH))) {
                this.setAppLanguage(AppLanguage.AppLanguageEnum.ENGLISH_CODE);
            } else if (language.equals(getResources().getString(R.string.LG_FRENCH))) {
                this.setAppLanguage(AppLanguage.AppLanguageEnum.FRENCH_CODE);
            } else if (language.equals(getResources().getString(R.string.LG_ARABIC))) {
                this.setAppLanguage(AppLanguage.AppLanguageEnum.ARABIC_CODE);
            }
        } else {
            this.setAppLanguage(AppLanguage.AppLanguageEnum.ENGLISH_CODE);
        }
    }
}
