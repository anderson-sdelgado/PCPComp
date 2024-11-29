package br.com.usinasantafe.pcpcomp.external.sharedpreferences.datasource

import android.content.SharedPreferences
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipVisitTercSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.MovEquipVisitTercSharedPreferencesModel
import br.com.usinasantafe.pcpcomp.utils.BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc
import com.google.gson.Gson

class IMovEquipVisitTercSharedPreferencesDatasource(
    private val sharedPreferences: SharedPreferences
) : MovEquipVisitTercSharedPreferencesDatasource {

    override suspend fun clear(): Result<Boolean> {
        try {
            val editor = sharedPreferences.edit()
            editor.putString(
                BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC,
                null
            )
            editor.apply()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercSharedPreferencesDatasourceImpl.clear",
                    cause = e
                )
            )
        }
    }

    override suspend fun get(): Result<MovEquipVisitTercSharedPreferencesModel> {
        try {
            val movEquipVisitTerc = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC,
                null
            )!!
            return Result.success(
                Gson().fromJson(
                    movEquipVisitTerc,
                    MovEquipVisitTercSharedPreferencesModel::class.java
                )
            )
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercSharedPreferencesDatasourceImpl.get",
                    cause = e
                )
            )
        }
    }

    override suspend fun setDestino(destino: String): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            val movEquipVisitTerc = resultGet.getOrNull()!!
            movEquipVisitTerc.destinoMovEquipVisitTerc = destino
            save(movEquipVisitTerc)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercSharedPreferencesDatasourceImpl.setDestino",
                    cause = e
                )
            )
        }
    }

    override suspend fun setIdVisitTerc(idVisitTerc: Int): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            val movEquipVisitTerc = resultGet.getOrNull()!!
            movEquipVisitTerc.idVisitTercMovEquipVisitTerc = idVisitTerc
            save(movEquipVisitTerc)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercSharedPreferencesDatasourceImpl.setIdVisitTerc",
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
            val movEquipVisitTerc = resultGet.getOrNull()!!
            movEquipVisitTerc.observMovEquipVisitTerc = observ
            save(movEquipVisitTerc)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercSharedPreferencesDatasourceImpl.setObserv",
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
            val movEquipVisitTerc = resultGet.getOrNull()!!
            movEquipVisitTerc.placaMovEquipVisitTerc = placa
            save(movEquipVisitTerc)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercSharedPreferencesDatasourceImpl.setPlaca",
                    cause = e
                )
            )
        }
    }

    override suspend fun setTipoVisitTerc(typeVisitTerc: TypeVisitTerc): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            val movEquipVisitTerc = resultGet.getOrNull()!!
            movEquipVisitTerc.tipoVisitTercMovEquipVisitTerc = typeVisitTerc
            save(movEquipVisitTerc)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercSharedPreferencesDatasourceImpl.setTipo",
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
            val movEquipVisitTerc = resultGet.getOrNull()!!
            movEquipVisitTerc.veiculoMovEquipVisitTerc = veiculo
            save(movEquipVisitTerc)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercSharedPreferencesDatasourceImpl.setVeiculo",
                    cause = e
                )
            )
        }
    }

    override suspend fun start(
        movEquipVisitTercSharedPreferencesModel: MovEquipVisitTercSharedPreferencesModel
    ): Result<Boolean> {
        try {
            save(movEquipVisitTercSharedPreferencesModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercSharedPreferencesDatasourceImpl.start",
                    cause = e
                )
            )
        }
    }

    fun save(movEquipVisitTerc: MovEquipVisitTercSharedPreferencesModel) {
        val editor = sharedPreferences.edit()
        editor.putString(
            BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC,
            Gson().toJson(movEquipVisitTerc)
        )
        editor.apply()
    }

}