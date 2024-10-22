package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date

class StartInputMovEquipResidenciaImplTest {

    private val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()

    private fun getUsecase() = StartInputMovEquipResidenciaImpl(
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