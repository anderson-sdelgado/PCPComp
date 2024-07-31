package br.com.usinasantafe.pcpcomp.domain.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.utils.FlagUpdate

interface ConfigRepository {
    suspend fun hasConfig(): Result<Boolean>
    suspend fun getPassword(): Result<String>
    suspend fun getFlagUpdate(): Result<FlagUpdate>
    suspend fun getMatricVigia(): Result<Long>
    suspend fun getConfig(): Result<Config>
    suspend fun send(config: Config): Result<Long>
    suspend fun save(config: Config): Result<Boolean>
}