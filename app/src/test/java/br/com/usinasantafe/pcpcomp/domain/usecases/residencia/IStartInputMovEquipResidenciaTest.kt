package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IStartInputMovEquipResidenciaTest {

    private val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()

    private fun getUsecase() = IStartInputMovEquipResidencia(
        movEquipResidenciaRepository
    )

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository start`() =
        runTest {
            whenever(
                movEquipResidenciaRepository.start()
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipResidenciaRepository.start",
                        cause = Exception()
                    )
                )
            )
            val usecase = getUsecase()
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepository.start"
            )
        }

    @Test
    fun `Check return true if StartMovEquipResidenciaImplTest execute successfully`() =
        runTest {
            whenever(
                movEquipResidenciaRepository.start()
            ).thenReturn(
                Result.success(true)
            )
            val usecase = getUsecase()
            val result = usecase()
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

}