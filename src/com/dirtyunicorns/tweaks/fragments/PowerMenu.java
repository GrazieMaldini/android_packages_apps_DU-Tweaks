/*
 * Copyright (C) 2017-2018 The Dirty Unicorns Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dirtyunicorns.tweaks.fragments;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;
import androidx.preference.PreferenceScreen;
import androidx.preference.Preference.OnPreferenceChangeListener;

import com.android.internal.logging.nano.MetricsProto;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

import com.android.internal.util.du.Utils;

import java.util.ArrayList;
import java.util.List;

public class PowerMenu extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener {

    private static final String KEY_POWERMENU_TORCH = "powermenu_torch";

    private SwitchPreference mPowermenuTorch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.powermenu);
        final ContentResolver resolver = getActivity().getContentResolver();
        final PreferenceScreen prefScreen = getPreferenceScreen();

        mPowermenuTorch = (SwitchPreference) findPreference(KEY_POWERMENU_TORCH);
        mPowermenuTorch.setOnPreferenceChangeListener(this);
        if (!Utils.deviceHasFlashlight(getActivity())) {
            prefScreen.removePreference(mPowermenuTorch);
        } else {
        mPowermenuTorch.setChecked((Settings.System.getInt(resolver,
                Settings.System.POWERMENU_TORCH, 0) == 1));
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mPowermenuTorch) {
            boolean value = (Boolean) newValue;
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.POWERMENU_TORCH, value ? 1 : 0);
            return true;
        }
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.DIRTYTWEAKS;
    }
}
