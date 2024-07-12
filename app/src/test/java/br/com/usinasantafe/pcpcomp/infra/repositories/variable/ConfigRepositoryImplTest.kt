package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.ConfigWebServiceDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ConfigRepositoryImplTest {

    private lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource
    private lateinit var configWebServiceDatasource: ConfigWebServiceDatasource

    @Before
    fun init() {
        configSharedPreferencesDatasource = mock()
        configWebServiceDatasource = mock()
    }

    @Test
    fun `Check return true if have data in table Config internal`() = runTest {
        whenever(configSharedPreferencesDatasource.hasConfig()).thenReturn(true)
        val repository = ConfigRepositoryImpl(configSharedPreferencesDatasource, configWebServiceDatasource);
        val result = repository.hasConfig()
        assertTrue(result)
    }

    @Test
    fun `Check return false if don't have data in table Config internal`() = runTest {
        whenever(configSharedPreferencesDatasource.hasConfig()).thenReturn(false)
        val repository = ConfigRepositoryImpl(configSharedPreferencesDatasource, configWebServiceDatasource);
        val result = repository.hasConfig()
        assertFalse(result)
    }

    @Test
    fun `Check return password input table Config internal`() = runTest {
        whenever(configSharedPreferencesDatasource.getConfig()).thenReturn(Config(passwordConfig = "12345"))
        val repository = ConfigRepositoryImpl(configSharedPreferencesDatasource, configWebServiceDatasource);
        val result = repository.getPassword()
        assertEquals(result, "12345")
    }


    @Test
    fun `Check return object Config if have data in table Config internal`() = runTest {
        val config = Config(
            passwordConfig = "12345",
            numberConfig = 16997417840
        )
        whenever(configSharedPreferencesDatasource.getConfig()).thenReturn(config)
        val repository = ConfigRepositoryImpl(configSharedPreferencesDatasource, configWebServiceDatasource);
        val result = repository.getConfig()
        assertEquals(result.numberConfig, 16997417840)
        assertEquals(result.passwordConfig, "12345")
    }
}