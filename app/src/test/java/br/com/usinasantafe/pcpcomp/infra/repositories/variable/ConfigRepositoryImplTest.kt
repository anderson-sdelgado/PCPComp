package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.webservice.variable.ConfigRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.webservice.ConfigWebServiceModelInput
import br.com.usinasantafe.pcpcomp.infra.models.webservice.toConfigWebServiceModel
import br.com.usinasantafe.pcpcomp.utils.FlagUpdate
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ConfigRepositoryImplTest {

    private lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource
    private lateinit var configRetrofitDatasource: ConfigRetrofitDatasource

    @Before
    fun init() {
        configSharedPreferencesDatasource = mock()
        configRetrofitDatasource = mock()
    }

    @Test
    fun `Check return true if have data in table Config internal`() = runTest {
        whenever(configSharedPreferencesDatasource.hasConfig()).thenReturn(Result.success(true))
        val repository = ConfigRepositoryImpl(configSharedPreferencesDatasource, configRetrofitDatasource)
        val result = repository.hasConfig()
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }

    @Test
    fun `Check return false if don't have data in table Config internal`() = runTest {
        whenever(configSharedPreferencesDatasource.hasConfig()).thenReturn(Result.success(false))
        val repository = ConfigRepositoryImpl(configSharedPreferencesDatasource, configRetrofitDatasource)
        val result = repository.hasConfig()
        assertTrue(result.isSuccess)
        assertFalse(result.getOrNull()!!)
    }

    @Test
    fun `Check return failure Datasource if have failure in hasConfig`() = runTest {
        whenever(configSharedPreferencesDatasource.hasConfig()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "ConfigSharedPreferencesDatasource.hasConfig",
                    cause = Exception()
                )
            )
        )
        val repository = ConfigRepositoryImpl(configSharedPreferencesDatasource, configRetrofitDatasource)
        val result = repository.hasConfig()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ConfigSharedPreferencesDatasource.hasConfig")
    }

    @Test
    fun `Check return password input table Config internal`() = runTest {
        whenever(configSharedPreferencesDatasource.getConfig()).thenReturn(
            Result.success(
                Config(password = "12345")
            )
        )
        val repository = ConfigRepositoryImpl(configSharedPreferencesDatasource, configRetrofitDatasource)
        val result = repository.getPassword()
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, "12345")
    }

    @Test
    fun `Check return failure if have failure in execution getConfig getPassword`() = runTest {
        whenever(configSharedPreferencesDatasource.getConfig()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "ConfigSharedPreferencesDatasource.getConfig",
                    cause = Exception()
                )
            )
        )
        val repository = ConfigRepositoryImpl(configSharedPreferencesDatasource, configRetrofitDatasource)
        val result = repository.getPassword()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ConfigSharedPreferencesDatasource.getConfig")
    }

    @Test
    fun `Check return flagUpdate input table Config internal`() = runTest {
        whenever(configSharedPreferencesDatasource.getConfig()).thenReturn(
            Result.success(
                Config(flagUpdate = FlagUpdate.UPDATED)
            )
        )
        val repository = ConfigRepositoryImpl(configSharedPreferencesDatasource, configRetrofitDatasource)
        val result = repository.getFlagUpdate()
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, FlagUpdate.UPDATED)
    }

    @Test
    fun `Check return failure if have failure in execution getConfig getFlagUpdate`() = runTest {
        whenever(configSharedPreferencesDatasource.getConfig()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "ConfigSharedPreferencesDatasource.getConfig",
                    cause = Exception()
                )
            )
        )
        val repository = ConfigRepositoryImpl(configSharedPreferencesDatasource, configRetrofitDatasource)
        val result = repository.getFlagUpdate()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ConfigSharedPreferencesDatasource.getConfig")
    }

    @Test
    fun `Check return object Config if have data in table Config internal`() = runTest {
        val config = Config(
            password = "12345",
            number = 16997417840
        )
        whenever(configSharedPreferencesDatasource.getConfig()).thenReturn(Result.success(config))
        val repository = ConfigRepositoryImpl(configSharedPreferencesDatasource, configRetrofitDatasource)
        val result = repository.getConfig()
        assertEquals(result.getOrNull()!!.number, 16997417840)
        assertEquals(result.getOrNull()!!.password, "12345")
    }

    @Test
    fun `Check return failure de Repository if Config is null`() = runTest {
        val repository = ConfigRepositoryImpl(configSharedPreferencesDatasource, configRetrofitDatasource)
        val result = repository.send((Config()))
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Repository -> ConfigRepositoryImpl.send")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), NullPointerException().toString())
    }

    @Test
    fun `Check return failure de Datasource if have error in Config Datasource`() = runTest {
        val config = Config(
            version = "6.00",
            number = 16997417840,
        )
        whenever(configRetrofitDatasource.recoverToken(config.toConfigWebServiceModel())).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "ConfigRetrofitDatasource.recoverToken",
                    cause = NullPointerException()
                )
            )
        )
        val repository = ConfigRepositoryImpl(configSharedPreferencesDatasource, configRetrofitDatasource)
        val result = repository.send(config)
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message,"Failure Datasource -> ConfigRetrofitDatasource.recoverToken")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), NullPointerException().toString())
    }

    @Test
    fun `Check return ID if send is correct`() = runTest {
        val config = Config(
            version = "6.00",
            number = 16997417840,
        )
        val configWebServiceModelInput = ConfigWebServiceModelInput(
            idBD = 1
        )
        whenever(configRetrofitDatasource.recoverToken(config.toConfigWebServiceModel())).thenReturn(
            Result.success(configWebServiceModelInput)
        )
        val repository = ConfigRepositoryImpl(configSharedPreferencesDatasource, configRetrofitDatasource)
        val result = repository.send(config)
        assertEquals(result.isSuccess, true)
        assertEquals(result, Result.success(1L))
    }

    @Test
    fun `Check return success save Config`() = runTest {
        val config = Config(
            version = "6.00",
            number = 16997417840,
            password = "12345",
            idBD = 1
        )
        val repository = ConfigRepositoryImpl(configSharedPreferencesDatasource, configRetrofitDatasource)
        val result = repository.save(config)
        assertEquals(result.isSuccess, true)
    }
}