package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetPlacaResidenciaImplTest {

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository GetPlaca`() =
        runTest {
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            whenever(
                movEquipResidenciaRepository.getPlaca(
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipResidenciaRepository.GetPlaca",
                        cause = Exception()
                    )
                )
            )
            val usecase = GetPlacaResidenciaImpl(
                movEquipResidenciaRepository
            )
            val result = usecase(id = 1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepository.GetPlaca"
            )
        }

    @Test
    fun `Check return placa if GetPlacaResidenciaImpl execute successfully`() =
        runTest {
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            whenever(
                movEquipResidenciaRepository.getPlaca(
                    id = 1
                )
            ).thenReturn(
                Result.success("Placa")
            )
            val usecase = GetPlacaResidenciaImpl(
                movEquipResidenciaRepository
            )
            val result = usecase(id = 1)
            assertTrue(result.isSuccess)
            assertEquals(result.getOrNull()!!, "Placa")
        }
}