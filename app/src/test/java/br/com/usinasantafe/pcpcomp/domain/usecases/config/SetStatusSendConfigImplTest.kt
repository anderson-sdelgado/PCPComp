package br.com.usinasantafe.pcpcomp.domain.usecases.config

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class SetStatusSendConfigImplTest {

    @Test
    fun `Chech return failure Datasource if have error in ConfigRepository SetStatusSend`() =
        runTest {
            val configRepository = mock<ConfigRepository>()
            whenever(configRepository.setStatusSend(StatusSend.SENDING)).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "ConfigRepository.setStatusSend",
                        cause = Exception()
                    )
                )
            )
            val usecase = SetStatusSendConfigImpl(configRepository)
            val result = usecase(StatusSend.SENDING)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> ConfigRepository.setStatusSend"
            )
            assertEquals(result.exceptionOrNull()!!.cause.toString(), Exception().toString())
        }

    @Test
    fun `Chech return true if usecase is success`() = runTest {
            val configRepository = mock<ConfigRepository>()
            whenever(configRepository.setStatusSend(StatusSend.SENDING)).thenReturn(
                Result.success(true)
            )
            val usecase = SetStatusSendConfigImpl(configRepository)
            val result = usecase(StatusSend.SENDING)
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }
}