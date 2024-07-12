package br.com.usinasantafe.pcpcomp.domain.usecases

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
        whenever(configRepository.hasConfig()).thenReturn(false)
        val usecase = CheckPasswordConfigImpl(configRepository)
        val result = usecase("")
        assertTrue(result)
    }

    @Test
    fun `Check don't access if have db config save and password is null`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.hasConfig()).thenReturn(true)
        val usecase = CheckPasswordConfigImpl(configRepository)
        val result = usecase("")
        assertFalse(result)
    }

    @Test
    fun `Check access if have db config save and input password equal password db`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.hasConfig()).thenReturn(true)
        whenever(configRepository.getPassword()).thenReturn("12345")
        val usecase = CheckPasswordConfigImpl(configRepository)
        val result = usecase("12345")
        assertTrue(result)
    }

    @Test
    fun `Check don't access if have db config save and input password different password db`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.hasConfig()).thenReturn(true)
        whenever(configRepository.getPassword()).thenReturn("123456")
        val usecase = CheckPasswordConfigImpl(configRepository)
        val result = usecase("12345")
        assertFalse(result)
    }
}