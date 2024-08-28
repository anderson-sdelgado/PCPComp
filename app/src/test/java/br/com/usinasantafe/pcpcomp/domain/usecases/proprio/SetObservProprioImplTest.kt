package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class SetObservProprioImplTest {

    @Test
    fun `Chech return failure if have error in MovEquipProprioRepository setObserv`() =
        runTest {
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            whenever(
                movEquipProprioRepository.setObserv(
                    observ = "Teste",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipProprioRepository.setObserv",
                        cause = Exception()
                    )
                )
            )
            val usecase = SetObservProprioImpl(movEquipProprioRepository)
            val result = usecase(
                observ = "Teste",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(result.isFailure, true)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipProprioRepository.setObserv"
            )
        }

    @Test
    fun `Chech return true if MovEquipProprioRepository setObserv execute success`() =
        runTest {
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            whenever(
                movEquipProprioRepository.setObserv(
                    observ = "Teste",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val usecase = SetObservProprioImpl(movEquipProprioRepository)
            val result = usecase(
                observ = "Teste",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }
}