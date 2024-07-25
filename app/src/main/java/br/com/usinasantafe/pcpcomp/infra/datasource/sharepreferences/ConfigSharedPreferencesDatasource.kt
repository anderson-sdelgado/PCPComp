package br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config

interface ConfigSharedPreferencesDatasource {
    suspend fun hasConfig(): Result<Boolean>
    suspend fun getConfig(): Result<Config>
    suspend fun saveConfig(config: Config): Result<Boolean>
}