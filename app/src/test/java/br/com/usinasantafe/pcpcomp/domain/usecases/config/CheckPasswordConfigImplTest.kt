package br.com.usinasantafe.pcpcomp.domain.usecases.config

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class CheckPasswordConfigImplTest {

    @Test
    fun `Check access if don't have db config save and password is null`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.hasConfig()).thenReturn(Result.success(false))
        val usecase = CheckPasswordConfigImpl(configRepository)
        val result = usecase("")
        assertTrue(result.getOrNull()!!)
    }

    @Test
    fun `Check don't access if have db config save and password is null`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.hasConfig()).thenReturn(Result.success(true))
        val usecase = CheckPasswordConfigImpl(configRepository)
        val result = usecase("")
        assertFalse(result.getOrNull()!!)
    }

    @Test
    fun `Check access if have db config save and input password equal password db`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.hasConfig()).thenReturn(Result.success(true))
        whenever(configRepository.getPassword()).thenReturn(Result.success("12345"))
        val usecase = CheckPasswordConfigImpl(configRepository)
        val result = usecase("12345")
        assertTrue(result.getOrNull()!!)
    }

    @Test
    fun `Check don't access if have db config save and input password different password db`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.hasConfig()).thenReturn(Result.success(true))
        whenever(configRepository.getPassword()).thenReturn(Result.success("123456"))
        val usecase = CheckPasswordConfigImpl(configRepository)
        val result = usecase("12345")
        assertFalse(result.getOrNull()!!)
    }
}