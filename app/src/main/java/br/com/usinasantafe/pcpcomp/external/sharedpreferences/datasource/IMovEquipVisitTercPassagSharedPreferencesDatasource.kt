package br.com.usinasantafe.pcpcomp.external.sharedpreferences.datasource

import android.content.SharedPreferences
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipVisitTercPassagSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.utils.BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC_PASSAG
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class IMovEquipVisitTercPassagSharedPreferencesDatasource(
    private val sharedPreferences: SharedPreferences
): MovEquipVisitTercPassagSharedPreferencesDatasource {

    val typeToken = object : TypeToken<List<Int>>() {}.type

    override suspend fun add(idVisitTerc: Int): Result<Boolean> {
        try {
            val resultList = list()
            if(resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val list = resultList.getOrNull()!!
            var mutableList : MutableList<Int> = mutableListOf()
            if(list.isNotEmpty())
                mutableList = list.toMutableList()
            mutableList.add(idVisitTerc)
            val editor = sharedPreferences.edit()
            editor.putString(
                BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC_PASSAG,
                Gson().toJson(mutableList, typeToken)
            )
            editor.apply()
            mutableList.clear()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercPassagSharedPreferencesDatasource.add",
                    cause = e
                )
            )
        }
    }

    override suspend fun clear(): Result<Boolean> {
        try {
            val editor = sharedPreferences.edit()
            editor.putString(BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC_PASSAG, null)
            editor.apply()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercPassagSharedPreferencesDatasource.clear",
                    cause = e
                )
            )
        }
    }

    override suspend fun delete(idVisitTerc: Int): Result<Boolean> {
        try {
            val resultList = list()
            if(resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val list = resultList.getOrNull()!!
            val listDelete = list.filter { it != idVisitTerc }
            val editor = sharedPreferences.edit()
            editor.putString(
                BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC_PASSAG,
                Gson().toJson(listDelete, typeToken)
            )
            editor.apply()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercPassagSharedPreferencesDatasource.delete",
                    cause = e
                )
            )
        }
    }

    override suspend fun list(): Result<List<Int>> {
        try {
            val result = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC_PASSAG,
                null
            )
            if(!result.isNullOrEmpty())
                return Result.success(Gson().fromJson(result, typeToken))
            return Result.success(emptyList())
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercPassagSharedPreferencesDatasource.list",
                    cause = e
                )
            )
        }
    }
}