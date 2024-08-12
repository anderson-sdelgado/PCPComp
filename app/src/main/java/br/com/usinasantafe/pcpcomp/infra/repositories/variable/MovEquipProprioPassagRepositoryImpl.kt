package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioPassagSharedPreferencesDatasource

class MovEquipProprioPassagRepositoryImpl(
    private val movEquipProprioPassagSharedPreferencesDatasource: MovEquipProprioPassagSharedPreferencesDatasource
): MovEquipProprioPassagRepository {

    override suspend fun clear(): Result<Boolean> {
        return movEquipProprioPassagSharedPreferencesDatasource.clear()
    }

}