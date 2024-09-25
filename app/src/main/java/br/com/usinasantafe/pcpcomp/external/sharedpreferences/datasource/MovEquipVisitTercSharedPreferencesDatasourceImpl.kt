package br.com.usinasantafe.pcpcomp.external.sharedpreferences.datasource

import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipVisitTercSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.MovEquipVisitTercSharedPreferencesModel

class MovEquipVisitTercSharedPreferencesDatasourceImpl(
): MovEquipVisitTercSharedPreferencesDatasource {
    override suspend fun get(): Result<MovEquipVisitTercSharedPreferencesModel> {
        TODO("Not yet implemented")
    }
}