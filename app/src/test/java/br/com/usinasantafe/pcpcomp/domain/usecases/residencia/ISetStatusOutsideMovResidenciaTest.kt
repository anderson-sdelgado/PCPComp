package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISetStatusOutsideMovResidenciaTest {

    @Test
    fun `Check return failure if have failure in MovEquipResidenciaRepository get`() =
        runTest {
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            whenever(
                movEquipResidenciaRepository.get(1)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipResidenciaRepository.get",
                        cause = Exception()
                    )
                )
            )
            val usecase = ISetStatusOutsideMovResidencia(
                movEquipResidenciaRepository,
            )
            val result = usecase(1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepository.get"
            )
        }

    @Test
    fun `Check return failure if have failure in MovEquipResidenciaRepository setOutside`() =
        runTest {
            val movEquipResidencia = MovEquipResidencia(
                idMovEquipResidencia = 1
            )
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            whenever(
                movEquipResidenciaRepository.get(1)
            ).thenReturn(
                Result.success(movEquipResidencia)
            )
            whenever(
                movEquipResidenciaRepository.setOutside(
                    movEquipResidencia
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipResidenciaRepository.setOutside",
                        cause = Exception()
                    )
                )
            )
            val usecase = ISetStatusOutsideMovResidencia(
                movEquipResidenciaRepository,
            )
            val result = usecase(1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepository.setOutside"
            )
        }

    @Test
    fun `Check return true if OutsideMovResidenciaImplTest execute successfully`() =
        runTest {
            val movEquipResidencia = MovEquipResidencia(
                idMovEquipResidencia = 1
            )
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            whenever(
                movEquipResidenciaRepository.get(1)
            ).thenReturn(
                Result.success(movEquipResidencia)
            )
            whenever(
                movEquipResidenciaRepository.setOutside(
                    movEquipResidencia
                )
            ).thenReturn(
                Result.success(true)
            )
            val usecase = ISetStatusOutsideMovResidencia(
                movEquipResidenciaRepository,
            )
            val result = usecase(1)
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }
}