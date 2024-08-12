package br.com.usinasantafe.pcpcomp.external.sharedpreferences.datasource

import android.annotation.SuppressLint
import android.content.SharedPreferences
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioSegSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.utils.BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_SEG
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MovEquipProprioSegSharedPreferencesDatasourceImpl(
    private val sharedPreferences: SharedPreferences
): MovEquipProprioSegSharedPreferencesDatasource {

    val typeToken = object : TypeToken<List<Long>>() {}.type

    override suspend fun add(idEquip: Long): Result<Boolean> {
        try {
            val resultList = list()
            if(resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val list = resultList.getOrNull()!!
            var mutableList : MutableList<Long> = mutableListOf()
            if(list.isNotEmpty())
                mutableList = list.toMutableList()
            mutableList.add(idEquip)
            val editor = sharedPreferences.edit()
            editor.putString(BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_SEG, Gson().toJson(mutableList, typeToken))
            editor.apply()
            mutableList.clear()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioSegSharedPreferencesDatasource.add",
                    cause = e
                )
            )
        }
    }

    override suspend fun clear(): Result<Boolean> {
        try {
            val editor = sharedPreferences.edit()
            editor.putString(BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_SEG, null)
            editor.apply()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioSegSharedPreferencesDatasource.clear",
                    cause = e
                )
            )
        }
    }

    override suspend fun list(): Result<List<Long>> {
        try {
            val result = sharedPreferences.getString(BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_SEG, null)
            if(!result.isNullOrEmpty())
                return Result.success(Gson().fromJson(result, typeToken))
            return Result.success(emptyList())
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioSegSharedPreferencesDatasource.list",
                    cause = e
                )
            )
        }
    }

}