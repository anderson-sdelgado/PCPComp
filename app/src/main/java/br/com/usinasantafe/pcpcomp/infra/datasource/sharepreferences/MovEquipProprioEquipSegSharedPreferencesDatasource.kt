package br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences

interface MovEquipProprioEquipSegSharedPreferencesDatasource {
    suspend fun add(idEquip: Int): Result<Boolean>
    suspend fun clear(): Result<Boolean>
    suspend fun delete(idEquip: Int): Result<Boolean>
    suspend fun list(): Result<List<Int>>
}