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

class SetMatricVigiaConfigImplTest {

    @Test
    fun `Chech return failure Datasource if have error in ConfigRepository SetMatricVigia`() =
        runTest {
            val configRepository = mock<ConfigRepository>()
            whenever(configRepository.setMatricVigia(19759)).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "ConfigRepository.setMatricVigia",
                        cause = Exception()
                    )
                )
            )
            val usecase = SetMatricVigiaConfigImpl(configRepository)
            val result = usecase("19759")
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> ConfigRepository.setMatricVigia"
            )
            assertEquals(result.exceptionOrNull()!!.cause.toString(), Exception().toString())
        }

    @Test
    fun `Chech return true if usecase is success`() = runTest {
            val configRepository = mock<ConfigRepository>()
            whenever(configRepository.setMatricVigia(19759)).thenReturn(
                Result.success(true)
            )
            val usecase = SetMatricVigiaConfigImpl(configRepository)
            val result = usecase("19759")
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
    }
}