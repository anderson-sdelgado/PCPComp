package br.com.usinasantafe.pcpcomp.domain.usecases.config

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.presenter.config.ConfigModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class RecoverConfigInternalImplTest {

    @Test
    fun `Check null if haven't db config save`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.hasConfig()).thenReturn(Result.success(false))
        val usecase = RecoverConfigInternalImpl(configRepository)
        val result = usecase()
        assertEquals(result.getOrNull(), null)
    }

    @Test
    fun `Check return data if have db config save`() = runTest {
        val config = Config(
            number = 16997417840,
            password = "12345"
        )
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.hasConfig()).thenReturn(Result.success(true))
        whenever(configRepository.getConfig()).thenReturn(Result.success(config))
        val usecase = RecoverConfigInternalImpl(configRepository)
        val result = usecase()
        assertEquals(
            result.getOrNull()!!,
            ConfigModel(
                number = "16997417840",
                password = "12345"
            )
        )
    }

}