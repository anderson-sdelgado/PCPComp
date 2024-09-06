package br.com.usinasantafe.pcpcomp.domain.usecases.initial

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.utils.FlagUpdate
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class CheckAccessMainImplTest {

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
        val usecase = CheckAccessMainImpl(configRepository)
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ConfigRepository.hasConfig")
    }

    @Test
    fun `Check not access if don't have db config save`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.hasConfig()).thenReturn(
            Result.success(false)
        )
        val usecase = CheckAccessMainImpl(configRepository)
        val result = usecase()
        assertTrue(result.isSuccess)
        assertFalse(result.getOrNull()!!)
    }

    @Test
    fun `Check return failure if have failure in getFlagUpdate`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.hasConfig()).thenReturn(
            Result.success(true)
        )
        whenever(configRepository.getFlagUpdate()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "ConfigRepository.getFlagUpdate",
                    cause = Exception()
                )
            )
        )
        val usecase = CheckAccessMainImpl(configRepository)
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ConfigRepository.getFlagUpdate")
    }

    @Test
    fun `Check return false if have flagUpdate is OUTDATED`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.hasConfig()).thenReturn(
            Result.success(true)
        )
        whenever(configRepository.getFlagUpdate()).thenReturn(
            Result.success(
                FlagUpdate.OUTDATED
            )
        )
        val usecase = CheckAccessMainImpl(configRepository)
        val result = usecase()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, false)
    }

    @Test
    fun `Check return true if have flagUpdate is UPDATE`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.hasConfig()).thenReturn(
            Result.success(true)
        )
        whenever(configRepository.getFlagUpdate()).thenReturn(
            Result.success(
                FlagUpdate.UPDATED
            )
        )
        val usecase = CheckAccessMainImpl(configRepository)
        val result = usecase()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, true)
    }
}