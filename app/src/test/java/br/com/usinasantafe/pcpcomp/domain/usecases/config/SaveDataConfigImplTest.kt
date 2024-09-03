package br.com.usinasantafe.pcpcomp.domain.usecases.config

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertFailsWith

class SaveDataConfigImplTest {

    @Test
    fun `Chech return true if save is correct`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(
            configRepository.saveInitial(
                number = 16997417840,
                password = "12345",
                version = "6.00",
                idBD = 1
            )
        ).thenReturn(Result.success(true))
        val usecase = SaveDataConfigImpl(configRepository)
        val result = usecase(
            number = "16997417840",
            password = "12345",
            version = "6.00",
            idBD = 1
        )
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull(), true)
    }

    @Test
    fun `Chech return Failure Usecase if have error in Usecase`() = runTest {
        val configRepository = mock<ConfigRepository>()
        val usecase = SaveDataConfigImpl(configRepository)
        val result = usecase(
            number = "16997417840A",
            password = "12345",
            version = "6.00",
            idBD = 1
        )
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Usecase -> SaveDataConfig")
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "java.lang.NumberFormatException: For input string: \"16997417840A\""
        )
    }

    @Test
    fun `Chech return Failure Datasource if have error in Datasource`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(
            configRepository.saveInitial(
                number = 16997417840,
                password = "12345",
                version = "6.00",
                idBD = 1
            )
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "ConfigRepository.save",
                    cause = Exception()
                )
            )
        )
        val usecase = SaveDataConfigImpl(configRepository)
        val result = usecase(
            number = "16997417840",
            password = "12345",
            version = "6.00",
            idBD = 1
        )
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> ConfigRepository.save"
        )
    }
}