package br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences

import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.MovChaveSharedPreferencesModel

interface MovChaveSharedPreferencesDatasource {
    suspend fun clear(): Result<Boolean>
    suspend fun get(): Result<MovChaveSharedPreferencesModel>
    suspend fun setIdChave(idChave: Int): Result<Boolean>
    suspend fun setMatricColab(matricColab: Int): Result<Boolean>
    suspend fun setObserv(observ: String?): Result<Boolean>
    suspend fun start(
        movChaveSharedPreferencesModel: MovChaveSharedPreferencesModel =
            MovChaveSharedPreferencesModel()
    ): Result<Boolean>
}