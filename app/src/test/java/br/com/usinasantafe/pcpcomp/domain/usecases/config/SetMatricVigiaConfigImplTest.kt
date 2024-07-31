package br.com.usinasantafe.pcpcomp.domain.usecases.config

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.utils.FlagUpdate
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class SetMatricVigiaConfigImplTest {

    @Test
    fun `Chech return failure Datasource if have error in getConfig`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.getConfig()).thenReturn(
            Result.failure(
                DatasourceException(cause = Exception())
            )
        )
        val usecase = SetMatricVigiaConfigImpl(configRepository)
        val result = usecase("19759")
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), Exception().toString())
    }

    @Test
    fun `Chech return failure Usecase if matric is numeric invalid`() = runTest {
        val config = Config(
            number = 16997417840,
            password = "12345",
            version = "6.00",
            idBD = 1
        )
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.getConfig()).thenReturn(Result.success(config))
        val usecase = SetMatricVigiaConfigImpl(configRepository)
        val result = usecase("19759a")
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Usecase")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "java.lang.NumberFormatException: For input string: \"19759a\"")
    }

    @Test
    fun `Chech return failure Datasource if have error in saveConfig`() = runTest {
        val config = Config(
            number = 16997417840,
            password = "12345",
            version = "6.00",
            idBD = 1
        )
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.getConfig()).thenReturn(Result.success(config))
        whenever(configRepository.save(config)).thenReturn(
            Result.failure(
                DatasourceException(cause = Exception())
            )
        )
        val usecase = SetMatricVigiaConfigImpl(configRepository)
        val result = usecase("19759")
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), Exception().toString())
    }

    @Test
    fun `Chech return true if usecase is success`() = runTest {
        val config = Config(
            number = 16997417840,
            password = "12345",
            version = "6.00",
            idBD = 1
        )
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.getConfig()).thenReturn(Result.success(config))
        whenever(configRepository.save(config)).thenReturn(Result.success(true))
        val usecase = SetMatricVigiaConfigImpl(configRepository)
        val result = usecase("19759")
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, true)
    }
}