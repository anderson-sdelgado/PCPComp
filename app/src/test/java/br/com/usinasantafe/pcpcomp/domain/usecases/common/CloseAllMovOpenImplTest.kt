package br.com.usinasantafe.pcpcomp.domain.usecases.common

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class CloseAllMovOpenImplTest {

    @Test
    fun `Check return failure if have failure in MovEquipProprioRepository listOpen`() = runTest {
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
        val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
        whenever(movEquipProprioRepository.listOpen()).thenReturn(
            Result.failure(
                RepositoryException(
                    function = "MovEquipProprioRepository.listOpen",
                    cause = Exception()
                )
            )
        )
        val usecase = CloseAllMovOpenImpl(
            movEquipProprioRepository,
            movEquipVisitTercRepository,
            movEquipResidenciaRepository
        )
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> MovEquipProprioRepository.listOpen"
        )
    }

    @Test
    fun `Check return failure if have failure in MovEquipProprioRepository setClose`() = runTest {
        val movEquipProprio = MovEquipProprio(
            idMovEquipProprio = 1
        )
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
        val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
        whenever(movEquipProprioRepository.listOpen()).thenReturn(
            Result.success(
                listOf(
                    movEquipProprio
                )
            )
        )
        whenever(
            movEquipProprioRepository.setClose(
                movEquipProprio
            )
        ).thenReturn(
            Result.failure(
                RepositoryException(
                    function = "MovEquipProprioRepository.setClose",
                    cause = Exception()
                )
            )
        )
        val usecase = CloseAllMovOpenImpl(
            movEquipProprioRepository,
            movEquipVisitTercRepository,
            movEquipResidenciaRepository
        )
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> MovEquipProprioRepository.setClose"
        )
    }

    @Test
    fun `Check return failure if have failure in MovEquipVisitTercRepository listOpen`() = runTest {
        val movEquipProprio = MovEquipProprio(
            idMovEquipProprio = 1
        )
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
        val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
        whenever(movEquipProprioRepository.listOpen()).thenReturn(
            Result.success(
                listOf(
                    movEquipProprio
                )
            )
        )
        whenever(
            movEquipProprioRepository.setClose(
                movEquipProprio
            )
        ).thenReturn(
            Result.success(true)
        )
        whenever(movEquipVisitTercRepository.listOpen()).thenReturn(
            Result.failure(
                RepositoryException(
                    function = "MovEquipProprioVisitTerc.listOpen",
                    cause = Exception()
                )
            )
        )
        val usecase = CloseAllMovOpenImpl(
            movEquipProprioRepository,
            movEquipVisitTercRepository,
            movEquipResidenciaRepository
        )
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> MovEquipProprioVisitTerc.listOpen"
        )
    }

    @Test
    fun `Check return failure if have failure in MovEquipVisitTercRepository setClose`() = runTest {
        val movEquipProprio = MovEquipProprio(
            idMovEquipProprio = 1
        )
        val movEquipVisitTerc = MovEquipVisitTerc(
            idMovEquipVisitTerc = 1
        )
        val movEquipProprioRepository = mock<MovEquipProprioRepository>()
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
        val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
        whenever(movEquipProprioRepository.listOpen()).thenReturn(
            Result.success(
                listOf(
                    movEquipProprio
                )
            )
        )
        whenever(
            movEquipProprioRepository.setClose(
                movEquipProprio
            )
        ).thenReturn(
            Result.success(true)
        )
        whenever(movEquipVisitTercRepository.listOpen()).thenReturn(
            Result.success(
                listOf(
                    movEquipVisitTerc
                )
            )
        )
        whenever(movEquipVisitTercRepository.setClose(movEquipVisitTerc)).thenReturn(
            Result.failure(
                RepositoryException(
                    function = "MovEquipProprioVisitTerc.setClose",
                    cause = Exception()
                )
            )
        )
        val usecase = CloseAllMovOpenImpl(
            movEquipProprioRepository,
            movEquipVisitTercRepository,
            movEquipResidenciaRepository
        )
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> MovEquipProprioVisitTerc.setClose"
        )
    }

    @Test
    fun `Check return failure if have failure in MovEquipResidenciaRepository listOpen`() =
        runTest {
            val movEquipProprio = MovEquipProprio(
                idMovEquipProprio = 1
            )
            val movEquipVisitTerc = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1
            )
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            whenever(movEquipProprioRepository.listOpen()).thenReturn(
                Result.success(
                    listOf(
                        movEquipProprio
                    )
                )
            )
            whenever(
                movEquipProprioRepository.setClose(
                    movEquipProprio
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(movEquipVisitTercRepository.listOpen()).thenReturn(
                Result.success(
                    listOf(
                        movEquipVisitTerc
                    )
                )
            )
            whenever(movEquipVisitTercRepository.setClose(movEquipVisitTerc)).thenReturn(
                Result.success(true)
            )
            whenever(movEquipResidenciaRepository.listOpen()).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipResidenciaRepository.listOpen",
                        cause = Exception()
                    )
                )
            )
            val usecase = CloseAllMovOpenImpl(
                movEquipProprioRepository,
                movEquipVisitTercRepository,
                movEquipResidenciaRepository
            )
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepository.listOpen"
            )
        }

    @Test
    fun `Check return failure if have failure in MovEquipResidenciaRepository setClose`() =
        runTest {
            val movEquipProprio = MovEquipProprio(
                idMovEquipProprio = 1
            )
            val movEquipVisitTerc = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1
            )
            val movEquipResidencia = MovEquipResidencia(
                idMovEquipResidencia = 1
            )
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            whenever(movEquipProprioRepository.listOpen()).thenReturn(
                Result.success(
                    listOf(
                        movEquipProprio
                    )
                )
            )
            whenever(
                movEquipProprioRepository.setClose(
                    movEquipProprio
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(movEquipVisitTercRepository.listOpen()).thenReturn(
                Result.success(
                    listOf(
                        movEquipVisitTerc
                    )
                )
            )
            whenever(movEquipVisitTercRepository.setClose(movEquipVisitTerc)).thenReturn(
                Result.success(true)
            )
            whenever(movEquipResidenciaRepository.listOpen()).thenReturn(
                Result.success(
                    listOf(
                        movEquipResidencia
                    )
                )
            )
            whenever(movEquipResidenciaRepository.setClose(movEquipResidencia)).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipResidenciaRepository.setClose",
                        cause = Exception()
                    )
                )
            )
            val usecase = CloseAllMovOpenImpl(
                movEquipProprioRepository,
                movEquipVisitTercRepository,
                movEquipResidenciaRepository
            )
            val result = usecase()
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepository.setClose"
            )
        }


    @Test
    fun `Check return success if have executed correctly`() =
        runTest {
            val movEquipProprio = MovEquipProprio(
                idMovEquipProprio = 1
            )
            val movEquipVisitTerc = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1
            )
            val movEquipResidencia = MovEquipResidencia(
                idMovEquipResidencia = 1
            )
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            whenever(movEquipProprioRepository.listOpen()).thenReturn(
                Result.success(
                    listOf(
                        movEquipProprio
                    )
                )
            )
            whenever(
                movEquipProprioRepository.setClose(
                    movEquipProprio
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(movEquipVisitTercRepository.listOpen()).thenReturn(
                Result.success(
                    listOf(
                        movEquipVisitTerc
                    )
                )
            )
            whenever(movEquipVisitTercRepository.setClose(movEquipVisitTerc)).thenReturn(
                Result.success(true)
            )
            whenever(movEquipResidenciaRepository.listOpen()).thenReturn(
                Result.success(
                    listOf(
                        movEquipResidencia
                    )
                )
            )
            whenever(movEquipResidenciaRepository.setClose(movEquipResidencia)).thenReturn(
                Result.success(true)
            )
            val usecase = CloseAllMovOpenImpl(
                movEquipProprioRepository,
                movEquipVisitTercRepository,
                movEquipResidenciaRepository
            )
            val result = usecase()
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }
}