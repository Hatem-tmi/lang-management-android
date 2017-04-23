package personal.langmanagement.util;

import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

import personal.langmanagement.LangManageApp;
import personal.langmanagement.R;

public class AppLanguage {
    private static final String TAG = AppLanguage.class.getSimpleName();

    public enum AppLanguageEnum {
        FRENCH_CODE {
            @Override
            public String getAcronyme() {
                return "fr";
            }
        }, ENGLISH_CODE {
            @Override
            public String getAcronyme() {
                return "en";
            }
        }, ARABIC_CODE {
            @Override
            public String getAcronyme() {
                return "ar";
            }
        };

        public abstract String getAcronyme();
    }

    private static AppLanguage instance = null;
    private Context context;

    public static AppLanguage getInstance(Context context) {
        if (instance == null) {
            instance = new AppLanguage(context);
        }
        return instance;
    }

    private AppLanguage(Context context) {
        this.context = context;
    }

    public AppLanguageEnum getAppLanguageEnum() {
        return LangManageApp.getInstance().getAppLanguage();
    }

    public String getIsoAppLanguageCode() {
        String[] isoLanguageList = context.getResources().getStringArray(R.array.iso_language_list);

        switch (LangManageApp.getInstance().getAppLanguage()) {
            case FRENCH_CODE:
                return isoLanguageList[0];
            case ENGLISH_CODE:
                return isoLanguageList[1];
            case ARABIC_CODE:
                return isoLanguageList[2];
            default:
                return null;
        }
    }

    public void updateAppLanguage() {
        updateLanguage(LangManageApp.getInstance().getAppLanguage().getAcronyme());
    }

    public void updateAppLanguage(AppLanguageEnum appLanguageEnum) {
        updateLanguage(appLanguageEnum.getAcronyme());
        LangManageApp.getInstance().setAppLanguage(appLanguageEnum);
    }

    private void updateLanguage(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getApplicationContext().getResources()
                .updateConfiguration(config, context.getResources().getDisplayMetrics());
    }
}
