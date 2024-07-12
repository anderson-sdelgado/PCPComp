package br.com.usinasantafe.pcpcomp.external.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import br.com.usinasantafe.pcpcomp.utils.BASE_SHARE_PREFERENCES

fun SharedPreferences(appContext: Context): SharedPreferences {
    return appContext.getSharedPreferences(BASE_SHARE_PREFERENCES, Context.MODE_PRIVATE)
}