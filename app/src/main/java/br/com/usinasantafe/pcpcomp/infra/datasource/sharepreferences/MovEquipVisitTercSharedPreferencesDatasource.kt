package br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences

import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.MovEquipVisitTercSharedPreferencesModel

interface MovEquipVisitTercSharedPreferencesDatasource {
    suspend fun get(): Result<MovEquipVisitTercSharedPreferencesModel>
}