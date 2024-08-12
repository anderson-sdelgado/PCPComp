package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioSegRepository
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioSegSharedPreferencesDatasource

class MovEquipProprioSegRepositoryImpl(
    private val movEquipProprioSegSharedPreferencesDatasource: MovEquipProprioSegSharedPreferencesDatasource
): MovEquipProprioSegRepository {

    override suspend fun clear(): Result<Boolean> {
        return movEquipProprioSegSharedPreferencesDatasource.clear()
    }

}