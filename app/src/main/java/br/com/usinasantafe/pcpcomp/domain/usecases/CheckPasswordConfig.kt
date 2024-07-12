package br.com.usinasantafe.pcpcomp.domain.usecases

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository

interface CheckPasswordConfig {
    suspend operator fun invoke(password: String): Boolean
}

class CheckPasswordConfigImpl(
    private val configRepository: ConfigRepository
): CheckPasswordConfig {

    override suspend fun invoke(password: String): Boolean {
        if (!configRepository.hasConfig())
            return true
        if (configRepository.getPassword() == password)
            return true
        return false
    }

}