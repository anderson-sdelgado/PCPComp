package br.com.usinasantafe.pcpcomp.domain.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.utils.FlagUpdate
import br.com.usinasantafe.pcpcomp.utils.StatusSend

interface ConfigRepository {
    suspend fun getConfig(): Result<Config>
    suspend fun getFlagUpdate(): Result<FlagUpdate>
    suspend fun getPassword(): Result<String>
    suspend fun getMatricVigia(): Result<Int>
    suspend fun hasConfig(): Result<Boolean>
    suspend fun save(config: Config): Result<Boolean>
    suspend fun send(config: Config): Result<Int>
    suspend fun setStatusSend(statusSend: StatusSend): Result<Boolean>
}