package br.com.usinasantafe.pcpcomp.external.sharedpreferences.datasource

import android.content.SharedPreferences
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioEquipSegSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.utils.BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_EQUIP_SEG
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class IMovEquipProprioEquipSegSharedPreferencesDatasource(
    private val sharedPreferences: SharedPreferences
): MovEquipProprioEquipSegSharedPreferencesDatasource {

    val typeToken = object : TypeToken<List<Int>>() {}.type

    override suspend fun add(idEquip: Int): Result<Boolean> {
        try {
            val resultList = list()
            if(resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val list = resultList.getOrNull()!!
            var mutableList : MutableList<Int> = mutableListOf()
            if(list.isNotEmpty())
                mutableList = list.toMutableList()
            mutableList.add(idEquip)
            val editor = sharedPreferences.edit()
            editor.putString(BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_EQUIP_SEG, Gson().toJson(mutableList, typeToken))
            editor.apply()
            mutableList.clear()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioEquipSegSharedPreferencesDatasource.add",
                    cause = e
                )
            )
        }
    }

    override suspend fun clear(): Result<Boolean> {
        try {
            val editor = sharedPreferences.edit()
            editor.putString(BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_EQUIP_SEG, null)
            editor.apply()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioEquipSegSharedPreferencesDatasource.clear",
                    cause = e
                )
            )
        }
    }

    override suspend fun delete(idEquip: Int): Result<Boolean> {
        try {
            val resultList = list()
            if(resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val list = resultList.getOrNull()!!
            val listDelete = list.filter { it != idEquip }
            val editor = sharedPreferences.edit()
            editor.putString(BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_EQUIP_SEG, Gson().toJson(listDelete, typeToken))
            editor.apply()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioEquipSegSharedPreferencesDatasource.delete",
                    cause = e
                )
            )
        }
    }

    override suspend fun list(): Result<List<Int>> {
        try {
            val result = sharedPreferences.getString(BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_EQUIP_SEG, null)
            if(!result.isNullOrEmpty())
                return Result.success(Gson().fromJson(result, typeToken))
            return Result.success(emptyList())
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioEquipSegSharedPreferencesDatasource.list",
                    cause = e
                )
            )
        }
    }

}