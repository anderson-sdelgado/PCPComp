package br.com.usinasantafe.pcpcomp.external.sharedpreferences

import android.content.SharedPreferences
import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.utils.BASE_SHARE_PREFERENCES_TABLE_CONFIG
import com.google.gson.Gson

class ConfigSharedPreferencesDatasourceImpl(
    private val sharedPreferences: SharedPreferences
) : ConfigSharedPreferencesDatasource {

    fun teste(): String {
        return "teste"
    }

    override suspend fun hasConfig(): Boolean {
        val result = sharedPreferences.getString(BASE_SHARE_PREFERENCES_TABLE_CONFIG, null)
        return result != null
    }

    override suspend fun getConfig(): Config {
        val config = sharedPreferences.getString(BASE_SHARE_PREFERENCES_TABLE_CONFIG, null)
        if(config.isNullOrEmpty())
            return Config()
        return Gson().fromJson(config, Config::class.java)
    }

    override suspend fun saveConfig(config: Config) {
        val editor = sharedPreferences.edit()
        editor.putString(BASE_SHARE_PREFERENCES_TABLE_CONFIG, Gson().toJson(config))
        editor.commit()
    }
}