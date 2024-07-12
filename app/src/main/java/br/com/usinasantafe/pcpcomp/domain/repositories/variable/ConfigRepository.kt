package br.com.usinasantafe.pcpcomp.domain.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config

interface ConfigRepository {
    suspend fun hasConfig(): Boolean
    suspend fun getPassword(): String
    suspend fun getConfig(): Config
}