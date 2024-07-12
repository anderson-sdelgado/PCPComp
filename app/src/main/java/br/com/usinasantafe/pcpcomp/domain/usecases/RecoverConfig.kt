package br.com.usinasantafe.pcpcomp.domain.usecases

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.presenter.config.ConfigModel
import br.com.usinasantafe.pcpcomp.presenter.config.toConfigModel

interface RecoverConfig {
    suspend operator fun invoke(): ConfigModel?
}

class RecoverConfigImpl(
    private val configRepository: ConfigRepository
): RecoverConfig {

    override suspend fun invoke(): ConfigModel? {
        if (!configRepository.hasConfig())
            return null
        return configRepository.getConfig().toConfigModel()
    }

}
