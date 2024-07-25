package br.com.usinasantafe.pcpcomp.domain.usecases.config

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException

interface SaveDataConfig {
    suspend operator fun invoke(
        number: String,
        password: String,
        version: String,
        idBD: Long,
    ): Result<Boolean>
}

class SaveDataConfigImpl(
    private val configRepository: ConfigRepository
): SaveDataConfig {

    override suspend fun invoke(
        number: String,
        password: String,
        version: String,
        idBD: Long
    ): Result<Boolean> {
        try {
            val config = Config(
                number = number.toLong(),
                password= password,
                version = version,
                idBD = idBD
            )
            return configRepository.save(config)
        } catch (ed: DatasourceException){
            throw ed
        } catch (eu: RepositoryException){
            throw eu
        } catch (e: Exception){
            throw UsecaseException(cause = e)
        }
    }

}