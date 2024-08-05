package br.com.usinasantafe.pcpcomp.domain.usecases.config

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.presenter.config.ConfigModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class RecoverConfigInternalImplTest {

    @Test
    fun `Check return failure if have failure in hasConfig`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.hasConfig()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "ConfigRepository.hasConfig",
                    cause = Exception()
                )
            )
        )
        val usecase = RecoverConfigInternalImpl(configRepository)
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ConfigRepository.hasConfig")
    }

    @Test
    fun `Check null if haven't db config save`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.hasConfig()).thenReturn(Result.success(false))
        val usecase = RecoverConfigInternalImpl(configRepository)
        val result = usecase()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull(), null)
    }

    @Test
    fun `Check return failure if have failure in getConfig`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.hasConfig()).thenReturn(
            Result.success(true)
        )
        whenever(configRepository.getConfig()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "ConfigRepository.getConfig",
                    cause = Exception()
                )
            )
        )
        val usecase = RecoverConfigInternalImpl(configRepository)
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ConfigRepository.getConfig")
    }

    @Test
    fun `Check return data if have db config save`() = runTest {
        val config = Config(
            number = 16997417840,
            password = "12345"
        )
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.hasConfig()).thenReturn(
            Result.success(true)
        )
        whenever(configRepository.getConfig()).thenReturn(
            Result.success(config)
        )
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