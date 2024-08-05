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

class SetCheckUpdateAllTableImplTest {

    @Test
    fun `Chech return failure Datasource if have error in getConfig`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.getConfig()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "ConfigRepository.getConfig",
                    cause = Exception()
                )
            )
        )
        val usecase = SetCheckUpdateAllTableImpl(configRepository)
        val result = usecase(FlagUpdate.UPDATED)
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ConfigRepository.getConfig")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), Exception().toString())
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
                DatasourceException(
                    function = "ConfigRepository.save",
                    cause = Exception()
                )
            )
        )
        val usecase = SetCheckUpdateAllTableImpl(configRepository)
        val result = usecase(FlagUpdate.UPDATED)
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ConfigRepository.save")
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
        val usecase = SetCheckUpdateAllTableImpl(configRepository)
        val result = usecase(FlagUpdate.UPDATED)
        assertEquals(result.isSuccess, true)
    }
}