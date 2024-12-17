package br.com.usinasantafe.pcpcomp.external.sharedpreferences.datasource

import android.content.SharedPreferences
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovChaveSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.MovChaveSharedPreferencesModel
import br.com.usinasantafe.pcpcomp.utils.BASE_SHARE_PREFERENCES_TABLE_MOV_CHAVE
import com.google.gson.Gson

class IMovChaveSharedPreferencesDatasource(
    private val sharedPreferences: SharedPreferences
): MovChaveSharedPreferencesDatasource {

    override suspend fun clear(): Result<Boolean> {
        try {
            val editor = sharedPreferences.edit()
            editor.putString(
                BASE_SHARE_PREFERENCES_TABLE_MOV_CHAVE,
                null
            )
            editor.apply()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipResidenciaSharedPreferencesDatasourceImpl.clear",
                    cause = e
                )
            )
        }
    }

    override suspend fun get(): Result<MovChaveSharedPreferencesModel> {
        try{
            val movChave = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_MOV_CHAVE,
                null
            )!!
            return Result.success(
                Gson().fromJson(
                    movChave,
                    MovChaveSharedPreferencesModel::class.java
                )
            )
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "IMovChaveSharedPreferencesDatasource.get",
                    cause = e
                )
            )
        }

    }

    override suspend fun setIdChave(idChave: Int): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            val movChave = resultGet.getOrNull()!!
            movChave.idChaveMovChave = idChave
            save(movChave)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "IMovChaveSharedPreferencesDatasource.setIdChave",
                    cause = e
                )
            )
        }
    }

    override suspend fun setMatricColab(matricColab: Int): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            val movChave = resultGet.getOrNull()!!
            movChave.matricColabMovChave = matricColab
            save(movChave)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "IMovChaveSharedPreferencesDatasource.setMatricColab",
                    cause = e
                )
            )
        }
    }

    override suspend fun setObserv(observ: String?): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            val movChave = resultGet.getOrNull()!!
            movChave.observMovChave = observ
            save(movChave)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "IMovChaveSharedPreferencesDatasource.setMatricColab",
                    cause = e
                )
            )
        }
    }

    override suspend fun start(movChaveSharedPreferencesModel: MovChaveSharedPreferencesModel): Result<Boolean> {
        try {
            save(movChaveSharedPreferencesModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "IMovChaveSharedPreferencesDatasource.start",
                    cause = e
                )
            )
        }
    }

    fun save(movChave: MovChaveSharedPreferencesModel) {
        val editor = sharedPreferences.edit()
        editor.putString(
            BASE_SHARE_PREFERENCES_TABLE_MOV_CHAVE,
            Gson().toJson(movChave)
        )
        editor.apply()
    }

}