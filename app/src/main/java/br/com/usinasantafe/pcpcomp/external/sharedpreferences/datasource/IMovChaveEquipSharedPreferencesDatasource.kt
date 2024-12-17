package br.com.usinasantafe.pcpcomp.external.sharedpreferences.datasource

import android.content.SharedPreferences
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovChaveEquipSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.MovChaveEquipSharedPreferencesModel
import br.com.usinasantafe.pcpcomp.utils.BASE_SHARE_PREFERENCES_TABLE_MOV_CHAVE_EQUIP
import com.google.gson.Gson

class IMovChaveEquipSharedPreferencesDatasource(
    private val sharedPreferences: SharedPreferences
): MovChaveEquipSharedPreferencesDatasource {

    override suspend fun clear(): Result<Boolean> {
        try {
            val editor = sharedPreferences.edit()
            editor.putString(
                BASE_SHARE_PREFERENCES_TABLE_MOV_CHAVE_EQUIP,
                null
            )
            editor.apply()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "IMovChaveEquipSharedPreferencesDatasource.clear",
                    cause = e
                )
            )
        }
    }

    override suspend fun get(): Result<MovChaveEquipSharedPreferencesModel> {
        try {
            val movChaveEquip = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_MOV_CHAVE_EQUIP,
                null
            )!!
            return Result.success(
                Gson().fromJson(
                    movChaveEquip,
                    MovChaveEquipSharedPreferencesModel::class.java
                )
            )
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "IMovChaveEquipSharedPreferencesDatasource.get",
                    cause = e
                )
            )
        }
    }

    override suspend fun setIdEquip(idEquip: Int): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            val movChave = resultGet.getOrNull()!!
            movChave.idEquipMovChaveEquip = idEquip
            save(movChave)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "IMovChaveEquipSharedPreferencesDatasource.setIdEquip",
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
            movChave.matricColabMovChaveEquip = matricColab
            save(movChave)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "IMovChaveEquipSharedPreferencesDatasource.setMatricColab",
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
            movChave.observMovChaveEquip = observ
            save(movChave)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "IMovChaveEquipSharedPreferencesDatasource.setMatricColab",
                    cause = e
                )
            )
        }
    }

    override suspend fun start(movChaveSharedPreferencesModel: MovChaveEquipSharedPreferencesModel): Result<Boolean> {
        try {
            save(movChaveSharedPreferencesModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "IMovChaveEquipSharedPreferencesDatasource.start",
                    cause = e
                )
            )
        }
    }

    fun save(movChaveEquip: MovChaveEquipSharedPreferencesModel) {
        val editor = sharedPreferences.edit()
        editor.putString(
            BASE_SHARE_PREFERENCES_TABLE_MOV_CHAVE_EQUIP,
            Gson().toJson(movChaveEquip)
        )
        editor.apply()
    }

}