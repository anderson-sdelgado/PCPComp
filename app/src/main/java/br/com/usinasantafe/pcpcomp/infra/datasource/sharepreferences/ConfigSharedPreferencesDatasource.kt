package br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config

interface ConfigSharedPreferencesDatasource {
    suspend fun clear(): Result<Boolean>
    suspend fun has(): Result<Boolean>
    suspend fun get(): Result<Config>
    suspend fun save(config: Config): Result<Boolean>
}