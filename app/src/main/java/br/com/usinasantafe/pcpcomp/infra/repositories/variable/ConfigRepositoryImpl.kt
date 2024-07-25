package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.infra.datasource.webservice.variable.ConfigRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.webservice.toConfigWebServiceModel

class ConfigRepositoryImpl(
    private val configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource,
    private val configRetrofitDatasource: ConfigRetrofitDatasource,
) : ConfigRepository {

    override suspend fun hasConfig(): Result<Boolean> {
        return configSharedPreferencesDatasource.hasConfig()
    }

    override suspend fun getPassword(): Result<String> {
        try {
            val config = configSharedPreferencesDatasource.getConfig()
            if(config.isFailure)
                return Result.failure(config.exceptionOrNull()!!)
            return Result.success(config.getOrNull()!!.password!!)
        } catch (e: Exception) {
            return Result.failure(RepositoryException(cause = e))
        }
    }

    override suspend fun getConfig(): Result<Config> {
        return configSharedPreferencesDatasource.getConfig()
    }

    override suspend fun send(config: Config): Result<Long> {
        try {
            val result = configRetrofitDatasource.recoverToken(config.toConfigWebServiceModel())
            result.fold(
                onSuccess = {
                    return Result.success(it.idBD)
                },
                onFailure = { exception ->
                    return Result.failure(exception)
                }
            )
        } catch (e: Exception) {
            return Result.failure(RepositoryException(cause = e))
        }
    }

    override suspend fun save(config: Config): Result<Boolean> {
        return configSharedPreferencesDatasource.saveConfig(config)
    }

}