/*
* Copyright (C) 2013 The OmniROM Project
* Copyright (C) 2020 The Android Ice Cold Project
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*
*/
package com.aicp.device;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

public class Startup extends BroadcastReceiver {

    private static void restore(String file, boolean enabled) {
        if (file == null) {
            return;
        }
        Utils.writeValueSimple(file, enabled ? "1" : "0");
    }

    @Override
    public void onReceive(final Context context, final Intent bootintent) {
        restoreAfterUserSwitch(context);
        Intent serviceIntent = new Intent(context, HtcGestureService.class);
        context.startService(serviceIntent);
    }

    public static void restoreAfterUserSwitch(Context context) {

        boolean enabled = Settings.System.getInt(context.getContentResolver(), FastChargeSwitch.SETTINGS_KEY, 0) != 0;
        restore(FastChargeSwitch.getFile(), enabled);

        VibratorStrengthPreference.restore(context);
        HeadphoneGainPreference.restore(context);
    }
}
