package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioPassagSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.utils.FlowApp

class MovEquipProprioPassagRepositoryImpl(
    private val movEquipProprioPassagSharedPreferencesDatasource: MovEquipProprioPassagSharedPreferencesDatasource
): MovEquipProprioPassagRepository {

    override suspend fun add(
        matricColab: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return when(flowApp){
            FlowApp.ADD -> movEquipProprioPassagSharedPreferencesDatasource.add(matricColab)
            FlowApp.CHANGE -> TODO()
        }
    }

    override suspend fun clear(): Result<Boolean> {
        return movEquipProprioPassagSharedPreferencesDatasource.clear()
    }

    override suspend fun list(): Result<List<Int>> {
        return movEquipProprioPassagSharedPreferencesDatasource.list()
    }

    override suspend fun delete(
        matricColab: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return when(flowApp){
            FlowApp.ADD -> movEquipProprioPassagSharedPreferencesDatasource.delete(matricColab)
            FlowApp.CHANGE -> TODO()
        }
    }

}