package personal.langmanagement.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import personal.langmanagement.R;
import personal.langmanagement.view.fragment.MyPreferenceFragment;

public class PreferencesActivity extends BaseActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        initActionBar();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentsContainer, new MyPreferenceFragment()).commit();
    }

    /**
     * Initialize ActionBar
     */
    private void initActionBar() {
        if (toolbar == null)
            return;
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
