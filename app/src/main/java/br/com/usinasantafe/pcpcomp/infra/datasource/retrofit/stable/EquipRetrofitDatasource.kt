package br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Equip

interface EquipRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<Equip>>
}