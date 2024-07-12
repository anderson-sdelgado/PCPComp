package br.com.usinasantafe.pcpcomp.domain.usecases

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.presenter.config.ConfigModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class RecoverConfigImplTest {

    @Test
    fun `Check null if haven't db config save`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.hasConfig()).thenReturn(false)
        val usecase = RecoverConfigImpl(configRepository)
        val result = usecase()
        assertEquals(result, null)
    }

    @Test
    fun `Check return data if have db config save`() = runTest {
        val config = Config(
            numberConfig = 16997417840,
            passwordConfig = "12345"
        )
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.hasConfig()).thenReturn(true)
        whenever(configRepository.getConfig()).thenReturn(config)
        val usecase = RecoverConfigImpl(configRepository)
        val result = usecase()
        assertEquals(
            result,
            ConfigModel(
                number = "16997417840",
                password = "12345"
            )
        )
    }

}