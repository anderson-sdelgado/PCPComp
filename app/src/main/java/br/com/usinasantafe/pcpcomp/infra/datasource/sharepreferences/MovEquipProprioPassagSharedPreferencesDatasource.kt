package br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences

interface MovEquipProprioPassagSharedPreferencesDatasource {
    suspend fun add(matric: Long): Result<Boolean>
    suspend fun clear(): Result<Boolean>
    suspend fun list(): Result<List<Long>>
}