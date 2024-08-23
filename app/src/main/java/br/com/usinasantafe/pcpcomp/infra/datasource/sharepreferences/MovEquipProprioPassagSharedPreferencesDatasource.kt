package br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences

interface MovEquipProprioPassagSharedPreferencesDatasource {
    suspend fun add(matricColab: Int): Result<Boolean>
    suspend fun clear(): Result<Boolean>
    suspend fun delete(matricColab: Int): Result<Boolean>
    suspend fun list(): Result<List<Int>>
}