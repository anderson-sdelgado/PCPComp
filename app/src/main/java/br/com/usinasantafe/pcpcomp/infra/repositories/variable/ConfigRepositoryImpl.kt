package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.variable.ConfigRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.variable.toConfigWebServiceModel
import br.com.usinasantafe.pcpcomp.utils.FlagUpdate

class ConfigRepositoryImpl(
    private val configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource,
    private val configRetrofitDatasource: ConfigRetrofitDatasource,
) : ConfigRepository {

    override suspend fun hasConfig(): Result<Boolean> {
        return configSharedPreferencesDatasource.has()
    }

    override suspend fun saveInitial(
        number: Long,
        password: String,
        version: String,
        idBD: Int
    ): Result<Boolean> {
        val config = Config(
            number = number,
            password = password,
            version = version,
            idBD = idBD
        )
        return configSharedPreferencesDatasource.save(config)
    }

    override suspend fun getPassword(): Result<String> {
        try {
            val config = configSharedPreferencesDatasource.get()
            if (config.isFailure)
                return Result.failure(config.exceptionOrNull()!!)
            return Result.success(config.getOrNull()!!.password!!)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "ConfigRepositoryImpl.getPassword",
                    cause = e
                )
            )
        }
    }

    override suspend fun getFlagUpdate(): Result<FlagUpdate> {
        try {
            val config = configSharedPreferencesDatasource.get()
            if (config.isFailure)
                return Result.failure(config.exceptionOrNull()!!)
            return Result.success(config.getOrNull()!!.flagUpdate)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "ConfigRepositoryImpl.getFlagUpdate",
                    cause = e
                )
            )
        }
    }

    override suspend fun getMatricVigia(): Result<Int> {
        try {
            val config = configSharedPreferencesDatasource.get()
            if (config.isFailure)
                return Result.failure(config.exceptionOrNull()!!)
            return Result.success(config.getOrNull()!!.matricVigia!!)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "ConfigRepositoryImpl.getMatricVigia",
                    cause = e
                )
            )
        }
    }

    override suspend fun cleanConfig(): Result<Boolean> {
        return configSharedPreferencesDatasource.clear()
    }

    override suspend fun getConfig(): Result<Config> {
        return configSharedPreferencesDatasource.get()
    }

    override suspend fun send(config: Config): Result<Int> {
        try {
            val result = configRetrofitDatasource.recoverToken(config.toConfigWebServiceModel())
            if (result.isFailure)
                return Result.failure(result.exceptionOrNull()!!)
            return Result.success(result.getOrNull()!!.idBD)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "ConfigRepositoryImpl.send",
                    cause = e
                )
            )
        }
    }

    override suspend fun setFlagUpdate(flagUpdate: FlagUpdate): Result<Boolean> {
        try {
            val resultConfig = configSharedPreferencesDatasource.get()
            if (resultConfig.isFailure)
                return Result.failure(resultConfig.exceptionOrNull()!!)
            val config = resultConfig.getOrNull()!!
            config.flagUpdate = flagUpdate
            val resultSave = configSharedPreferencesDatasource.save(config)
            if (resultSave.isFailure)
                return Result.failure(resultSave.exceptionOrNull()!!)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "ConfigRepositoryImpl.setFlagUpdate",
                    cause = e
                )
            )
        }
    }

    override suspend fun setIdLocal(idLocal: Int): Result<Boolean> {
        try {
            val resultConfig = configSharedPreferencesDatasource.get()
            if (resultConfig.isFailure)
                return Result.failure(resultConfig.exceptionOrNull()!!)
            val config = resultConfig.getOrNull()!!
            config.idLocal = idLocal
            val resultSave = configSharedPreferencesDatasource.save(config)
            if (resultSave.isFailure)
                return Result.failure(resultSave.exceptionOrNull()!!)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "ConfigRepositoryImpl.setIdLocal",
                    cause = e
                )
            )
        }
    }

    override suspend fun setMatricVigia(matric: Int): Result<Boolean> {
        try {
            val resultConfig = configSharedPreferencesDatasource.get()
            if (resultConfig.isFailure)
                return Result.failure(resultConfig.exceptionOrNull()!!)
            val config = resultConfig.getOrNull()!!
            config.matricVigia = matric
            val resultSave = configSharedPreferencesDatasource.save(config)
            if (resultSave.isFailure)
                return Result.failure(resultSave.exceptionOrNull()!!)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "ConfigRepositoryImpl.setMatricVigia",
                    cause = e
                )
            )
        }
    }

}