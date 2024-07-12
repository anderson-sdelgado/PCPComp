package br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config

interface ConfigSharedPreferencesDatasource {
    suspend fun hasConfig(): Boolean
    suspend fun getConfig(): Config
    suspend fun saveConfig(config: Config)
}