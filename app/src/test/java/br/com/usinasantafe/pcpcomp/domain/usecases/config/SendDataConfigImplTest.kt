package br.com.usinasantafe.pcpcomp.domain.usecases.config

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class SendDataConfigImplTest {

    @Test
    fun `Check return failure Usecase if occur Exception`() = runTest {
        val configRepository = mock<ConfigRepository>()
        val usecase = ISendDataConfig(configRepository)
        val result = usecase(
                number = "1df52",
                password = "12345",
                version = "6.00"
            )
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Usecase -> SendDataConfig")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), NumberFormatException("For input string: \"1df52\"").toString())
    }

    @Test
    fun `Check return failure Repository if occur Exception in Repository`() = runTest {
        val config = Config(
            number = 16997417840,
            password = "12345",
            version = "6.00",
        )
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.send(config)).thenReturn(
            Result.failure(
                RepositoryException(
                    function = "ConfigRepository.send",
                    cause = Exception()
                )
            )
        )
        val usecase = ISendDataConfig(configRepository)
        val result = usecase(
            number = "16997417840",
            password = "12345",
            version = "6.00"
        )
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Repository -> ConfigRepository.send")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), Exception().toString())
    }

    @Test
    fun `Check return failure Datasource if occur Exception in Datasource`() = runTest {
        val config = Config(
            number = 16997417840,
            password = "12345",
            version = "6.00",
        )
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.send(config)).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "ConfigRepository.send",
                    cause = Exception()
                )
            )
        )
        val usecase = ISendDataConfig(configRepository)
        val result = usecase(
            number = "16997417840",
            password = "12345",
            version = "6.00"
        )
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ConfigRepository.send")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), Exception().toString())
    }

    @Test
    fun `Check return ID Usecase`() = runTest {
        val config = Config(
            number = 16997417840,
            password = "12345",
            version = "6.00",
        )
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.send(config)).thenReturn(Result.success(1))
        val usecase = ISendDataConfig(configRepository)
        val result = usecase(
            number = "16997417840",
            password = "12345",
            version = "6.00"
        )
        assertEquals(result.isSuccess, true)
        assertEquals(result, Result.success(1))
    }

}