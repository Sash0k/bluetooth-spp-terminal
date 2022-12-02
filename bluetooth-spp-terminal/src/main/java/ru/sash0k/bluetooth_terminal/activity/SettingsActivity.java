package ru.sash0k.bluetooth_terminal.activity;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import ru.sash0k.bluetooth_terminal.R;
import ru.sash0k.bluetooth_terminal.Utils;

/**
 * Created by sash0k on 29.11.13.
 * Настройки приложения
 */
@SuppressWarnings("deprecation")
public final class SettingsActivity extends PreferenceActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_activity);

        final ActionBar bar = getActionBar();
        bar.setHomeButtonEnabled(true);
        bar.setDisplayHomeAsUpEnabled(true);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
        setPreferenceTitle(getString(R.string.pref_commands_mode));
        setPreferenceTitle(getString(R.string.pref_checksum_mode));
        setPreferenceTitle(getString(R.string.pref_commands_ending));
        setPreferenceTitle(getString(R.string.pref_log_limit));
        setPreferenceTitle(getString(R.string.pref_log_limit_size));
    }
    // ============================================================================


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    // ============================================================================


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String value) {
        setPreferenceTitle(value);
    }
    // ============================================================================


    /**
     * Установка заголовка списка
     */
    private void setPreferenceTitle(String TAG) {
        final Preference preference = findPreference(TAG);
        if (preference == null) return;

        if (getString(R.string.pref_log_limit).equals(preference.getKey())) {
            boolean isEnabled = ((CheckBoxPreference)preference).isChecked();
            Preference limitSize = findPreference(getString(R.string.pref_log_limit_size));
            if (limitSize != null) limitSize.setEnabled(isEnabled);
        }

        if (preference instanceof ListPreference) {
            if (((ListPreference) preference).getEntry() == null) return;
            final String title = ((ListPreference) preference).getEntry().toString();
            preference.setTitle(title);
        } else if (preference instanceof EditTextPreference) {
            final String text = ((EditTextPreference) preference).getText();
            preference.setSummary(Integer.toString(Utils.formatNumber(text)));
        }
    }
    // ============================================================================
}
