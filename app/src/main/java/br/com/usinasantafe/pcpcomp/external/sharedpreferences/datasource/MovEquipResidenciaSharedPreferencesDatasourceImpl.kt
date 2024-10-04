package br.com.usinasantafe.pcpcomp.external.sharedpreferences.datasource

import android.content.SharedPreferences
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipResidenciaSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.MovEquipResidenciaSharedPreferencesModel
import br.com.usinasantafe.pcpcomp.utils.BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_RESIDENCIA
import com.google.gson.Gson

class MovEquipResidenciaSharedPreferencesDatasourceImpl(
    private val sharedPreferences: SharedPreferences
) : MovEquipResidenciaSharedPreferencesDatasource {

    override suspend fun clear(): Result<Boolean> {
        try {
            val editor = sharedPreferences.edit()
            editor.putString(
                BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_RESIDENCIA,
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

    override suspend fun get(): Result<MovEquipResidenciaSharedPreferencesModel> {
        try {
            val movEquipResidencia = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_RESIDENCIA,
                null
            )!!
            return Result.success(
                Gson().fromJson(
                    movEquipResidencia,
                    MovEquipResidenciaSharedPreferencesModel::class.java
                )
            )
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipResidenciaSharedPreferencesDatasourceImpl.get",
                    cause = e
                )
            )
        }
    }

    override suspend fun setMotorista(motorista: String): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            val movEquipResidencia = resultGet.getOrNull()!!
            movEquipResidencia.motoristaMovEquipResidencia = motorista
            save(movEquipResidencia)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipResidenciaSharedPreferencesDatasourceImpl.setDestino",
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
            val movEquipResidencia = resultGet.getOrNull()!!
            movEquipResidencia.observMovEquipResidencia = observ
            save(movEquipResidencia)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipResidenciaSharedPreferencesDatasourceImpl.setObserv",
                    cause = e
                )
            )
        }
    }

    override suspend fun setPlaca(placa: String): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            val movEquipResidencia = resultGet.getOrNull()!!
            movEquipResidencia.placaMovEquipResidencia = placa
            save(movEquipResidencia)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipResidenciaSharedPreferencesDatasourceImpl.setObserv",
                    cause = e
                )
            )
        }
    }

    override suspend fun setVeiculo(veiculo: String): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            val movEquipResidencia = resultGet.getOrNull()!!
            movEquipResidencia.veiculoMovEquipResidencia = veiculo
            save(movEquipResidencia)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipResidenciaSharedPreferencesDatasourceImpl.setObserv",
                    cause = e
                )
            )
        }
    }

    override suspend fun start(
        movEquipResidenciaSharedPreferencesModel: MovEquipResidenciaSharedPreferencesModel
    ): Result<Boolean> {
        try {
            save(movEquipResidenciaSharedPreferencesModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipResidenciaSharedPreferencesDatasourceImpl.start",
                    cause = e
                )
            )
        }
    }

    fun save(movEquipResidencia: MovEquipResidenciaSharedPreferencesModel) {
        val editor = sharedPreferences.edit()
        editor.putString(
            BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_RESIDENCIA,
            Gson().toJson(movEquipResidencia)
        )
        editor.apply()
    }

}