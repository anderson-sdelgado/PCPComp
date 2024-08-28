package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class SetDestinoProprioImplTest {

    @Test
    fun `Chech return failure Datasource if have error in MovEquipProprioRepository setDestino`() =
        runTest {
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            whenever(
                movEquipProprioRepository.setDestino(
                    destino = "Teste",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipProprioRepository.setDestino",
                        cause = Exception()
                    )
                )
            )
            val usecase = SetDestinoProprioImpl(movEquipProprioRepository)
            val result = usecase(
                destino = "Teste",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(result.isFailure, true)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipProprioRepository.setDestino"
            )
        }

    @Test
    fun `Chech return true if MovEquipProprioRepository setDestino execute success`() =
        runTest {
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            whenever(
                movEquipProprioRepository.setDestino(
                    destino = "Teste",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val usecase = SetDestinoProprioImpl(movEquipProprioRepository)
            val result = usecase(
                destino = "Teste",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(
                result.getOrNull()!!
            )
        }
}