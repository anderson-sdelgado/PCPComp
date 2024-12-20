package br.com.usinasantafe.pcpcomp.external.sharedpreferences.datasource

import android.content.SharedPreferences
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioPassagSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.utils.BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_PASSAG
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class IMovEquipProprioPassagSharedPreferencesDatasource(
    private val sharedPreferences: SharedPreferences
): MovEquipProprioPassagSharedPreferencesDatasource {

    val typeToken = object : TypeToken<List<Int>>() {}.type

    override suspend fun add(matricColab: Int): Result<Boolean> {
        try {
            val resultList = list()
            if(resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val list = resultList.getOrNull()!!
            var mutableList : MutableList<Int> = mutableListOf()
            if(list.isNotEmpty())
                mutableList = list.toMutableList()
            mutableList.add(matricColab)
            val editor = sharedPreferences.edit()
            editor.putString(
                BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_PASSAG,
                Gson().toJson(mutableList, typeToken)
            )
            editor.apply()
            mutableList.clear()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioPassagSharedPreferencesDatasource.add",
                    cause = e
                )
            )
        }
    }

    override suspend fun clear(): Result<Boolean> {
        try {
            val editor = sharedPreferences.edit()
            editor.putString(BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_PASSAG, null)
            editor.apply()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioPassagSharedPreferencesDatasource.clear",
                    cause = e
                )
            )
        }
    }

    override suspend fun delete(matricColab: Int): Result<Boolean> {
        try {
            val resultList = list()
            if(resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val list = resultList.getOrNull()!!
            val listDelete = list.filter { it != matricColab }
            val editor = sharedPreferences.edit()
            editor.putString(BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_PASSAG, Gson().toJson(listDelete, typeToken))
            editor.apply()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioPassagSharedPreferencesDatasource.delete",
                    cause = e
                )
            )
        }
    }
    
    override suspend fun list(): Result<List<Int>> {
        try {
            val result = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_PASSAG,
                null
            )
            if(!result.isNullOrEmpty())
                return Result.success(Gson().fromJson(result, typeToken))
            return Result.success(emptyList())
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioPassagSharedPreferencesDatasource.list",
                    cause = e
                )
            )
        }
    }


}