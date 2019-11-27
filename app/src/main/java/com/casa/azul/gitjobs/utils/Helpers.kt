package com.casa.azul.gitjobs.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

fun saveTimeStampPrefs(time: Long, context: Context){
    val TIME_STAMP = "time_stamp"
    val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    prefs.edit(commit = true){
        putLong(TIME_STAMP, time)
    }
}

