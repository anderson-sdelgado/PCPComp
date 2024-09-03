package br.com.usinasantafe.pcpcomp.domain.usecases.config

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.utils.StatusSend

interface SetStatusSendConfig {
    suspend operator fun invoke(statusSend: StatusSend): Result<Boolean>
}

class SetStatusSendConfigImpl(
    private val configRepository: ConfigRepository
) : SetStatusSendConfig {

    override suspend fun invoke(statusSend: StatusSend): Result<Boolean> {
        return configRepository.setStatusSend(StatusSend.SENDING)
    }

}