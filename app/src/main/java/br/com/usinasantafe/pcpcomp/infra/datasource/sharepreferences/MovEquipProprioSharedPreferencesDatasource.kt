package br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences

import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.MovEquipProprioSharedPreferencesModel
import br.com.usinasantafe.pcpcomp.utils.TypeMov

interface MovEquipProprioSharedPreferencesDatasource {
    suspend fun get(): Result<MovEquipProprioSharedPreferencesModel>
    suspend fun start(typeMov: TypeMov): Result<Boolean>
}