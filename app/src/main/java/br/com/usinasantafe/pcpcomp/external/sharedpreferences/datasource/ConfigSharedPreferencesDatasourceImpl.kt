package br.com.usinasantafe.pcpcomp.external.sharedpreferences.datasource

import android.content.SharedPreferences
import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.utils.BASE_SHARE_PREFERENCES_TABLE_CONFIG
import com.google.gson.Gson

class ConfigSharedPreferencesDatasourceImpl(
    private val sharedPreferences: SharedPreferences
) : ConfigSharedPreferencesDatasource {

    override suspend fun clear(): Result<Boolean> {
        try {
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            return Result.success(true)
        } catch (e: Exception){
            return Result.failure(
                DatasourceException(
                    function = "ConfigSharedPreferencesDatasourceImpl.saveConfig",
                    cause = e
                )
            )
        }
    }

    override suspend fun has(): Result<Boolean> {
        try {
            val result = sharedPreferences.getString(BASE_SHARE_PREFERENCES_TABLE_CONFIG, null)
            return Result.success(result != null)
        } catch (e: Exception){
            return Result.failure(
                DatasourceException(
                    function = "ConfigSharedPreferencesDatasourceImpl.hasConfig",
                    cause = e
                )
            )
        }
    }

    override suspend fun get(): Result<Config> {
        try {
            val config = sharedPreferences.getString(BASE_SHARE_PREFERENCES_TABLE_CONFIG, null)
            if(config.isNullOrEmpty())
                return Result.success(Config())
            return Result.success(Gson().fromJson(config, Config::class.java))
        } catch (e: Exception){
            return Result.failure(
                DatasourceException(
                    function = "ConfigSharedPreferencesDatasourceImpl.getConfig",
                    cause = e
                )
            )
        }
    }

    override suspend fun save(config: Config): Result<Boolean> {
        try {
            val editor = sharedPreferences.edit()
            editor.putString(BASE_SHARE_PREFERENCES_TABLE_CONFIG, Gson().toJson(config))
            editor.apply()
            return Result.success(true)
        } catch (e: Exception){
            return Result.failure(
                DatasourceException(
                    function = "ConfigSharedPreferencesDatasourceImpl.saveConfig",
                    cause = e
                )
            )
        }
    }
}