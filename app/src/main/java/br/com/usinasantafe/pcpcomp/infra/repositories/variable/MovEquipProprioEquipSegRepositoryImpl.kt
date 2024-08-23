package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioEquipSegSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.utils.FlowApp

class MovEquipProprioEquipSegRepositoryImpl(
    private val movEquipProprioEquipSegSharedPreferencesDatasource: MovEquipProprioEquipSegSharedPreferencesDatasource
) : MovEquipProprioEquipSegRepository {

    override suspend fun add(
        idEquip: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return when(flowApp){
            FlowApp.ADD -> movEquipProprioEquipSegSharedPreferencesDatasource.add(idEquip)
            FlowApp.CHANGE -> TODO()
        }
    }

    override suspend fun clear(): Result<Boolean> {
        return movEquipProprioEquipSegSharedPreferencesDatasource.clear()
    }

    override suspend fun list(): Result<List<Int>> {
        return movEquipProprioEquipSegSharedPreferencesDatasource.list()
    }

    override suspend fun delete(
        idEquip: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return when(flowApp){
            FlowApp.ADD -> movEquipProprioEquipSegSharedPreferencesDatasource.delete(idEquip)
            FlowApp.CHANGE -> TODO()
        }
    }

}