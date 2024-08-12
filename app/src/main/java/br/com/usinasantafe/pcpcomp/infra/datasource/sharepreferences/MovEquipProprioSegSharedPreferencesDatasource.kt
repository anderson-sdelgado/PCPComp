package br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences

interface MovEquipProprioSegSharedPreferencesDatasource {
    suspend fun add(idEquip: Long): Result<Boolean>
    suspend fun clear(): Result<Boolean>
    suspend fun list(): Result<List<Long>>
}