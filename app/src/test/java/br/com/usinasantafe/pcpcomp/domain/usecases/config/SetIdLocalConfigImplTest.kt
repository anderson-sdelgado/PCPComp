package br.com.usinasantafe.pcpcomp.domain.usecases.config

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.utils.FlagUpdate
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class SetIdLocalConfigImplTest {

    @Test
    fun `Chech return failure Datasource if have error in ConfigRepository SetIdLocal`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.setIdLocal(1)).thenReturn(
            Result.failure(
                RepositoryException(
                    function = "ConfigRepository.setIdLocal",
                    cause = Exception()
                )
            )
        )
        val usecase = SetIdLocalConfigImpl(configRepository)
        val result = usecase(1)
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Repository -> ConfigRepository.setIdLocal")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), Exception().toString())
    }

    @Test
    fun `Chech return true if usecase is success`() = runTest {
        val configRepository = mock<ConfigRepository>()
        whenever(configRepository.setIdLocal(1)).thenReturn(
            Result.success(true)
        )
        val usecase = SetIdLocalConfigImpl(configRepository)
        val result = usecase(1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }
}