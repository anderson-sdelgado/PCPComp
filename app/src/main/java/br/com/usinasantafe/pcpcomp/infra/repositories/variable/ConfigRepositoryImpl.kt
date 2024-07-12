package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.ConfigWebServiceDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource

class ConfigRepositoryImpl(
    private val configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource,
    private val configWebServiceDatasource: ConfigWebServiceDatasource,
) : ConfigRepository {

    override suspend fun hasConfig(): Boolean {
        return configSharedPreferencesDatasource.hasConfig()
    }

    override suspend fun getPassword(): String {
        val config = configSharedPreferencesDatasource.getConfig()
        return config.passwordConfig!!
    }

    override suspend fun getConfig(): Config {
        return configSharedPreferencesDatasource.getConfig()
    }

}