package br.com.usinasantafe.pcpcomp.external.sharedpreferences.datasource

import android.annotation.SuppressLint
import android.content.SharedPreferences
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipProprioRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.MovEquipProprioSharedPreferencesModel
import br.com.usinasantafe.pcpcomp.utils.BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import com.google.gson.Gson

class MovEquipProprioSharedPreferencesDatasourceImpl(
    private val sharedPreferences: SharedPreferences
): MovEquipProprioSharedPreferencesDatasource {

    override suspend fun get(): Result<MovEquipProprioSharedPreferencesModel> {
        try {
            val movEquipProprio = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO, null)!!
            return Result.success(Gson().fromJson(movEquipProprio, MovEquipProprioSharedPreferencesModel::class.java))
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioSharedPreferencesDatasourceImpl.get",
                    cause = e
                )
            )
        }
    }

    override suspend fun start(typeMov: TypeMov): Result<Boolean> {
        try{
            save(MovEquipProprioSharedPreferencesModel(tipoMovEquipProprio = typeMov))
            return Result.success(true)
        } catch(e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioSharedPreferencesDatasourceImpl.start",
                    cause = e
                )
            )
        }
    }

    fun save(movEquipProprio: MovEquipProprioSharedPreferencesModel) {
        val editor = sharedPreferences.edit()
        editor.putString(BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO, Gson().toJson(movEquipProprio))
        editor.apply()
    }

}