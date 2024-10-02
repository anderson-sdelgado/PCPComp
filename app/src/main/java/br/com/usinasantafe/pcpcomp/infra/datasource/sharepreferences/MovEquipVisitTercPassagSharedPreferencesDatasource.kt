package br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences

interface MovEquipVisitTercPassagSharedPreferencesDatasource {
    suspend fun add(idVisitTerc: Int): Result<Boolean>
    suspend fun clear(): Result<Boolean>
    suspend fun delete(idVisitTerc: Int): Result<Boolean>
    suspend fun list(): Result<List<Int>>
}