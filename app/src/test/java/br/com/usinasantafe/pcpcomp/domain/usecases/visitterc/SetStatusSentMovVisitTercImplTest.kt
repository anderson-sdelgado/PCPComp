package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class SetStatusSentMovVisitTercImplTest {

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository setStatusSent`() =
        runTest {
            val list = listOf(
                MovEquipVisitTerc(
                    idMovEquipVisitTerc = 1
                )
            )
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            whenever(
                movEquipVisitTercRepository.setSent(list)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "SetStatusSentMovVisitTercImpl",
                        cause = Exception()
                    )
                )
            )
            val usecase = SetStatusSentMovVisitTercImpl(movEquipVisitTercRepository)
            val result = usecase(list)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> SetStatusSentMovVisitTercImpl"
            )
        }

    @Test
    fun `Check return true if MovEquipVisitTercRepository setStatusSent execute successfully`() =
        runTest {
            val list = listOf(
                MovEquipVisitTerc(
                    idMovEquipVisitTerc = 1
                )
            )
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            whenever(
                movEquipVisitTercRepository.setSent(list)
            ).thenReturn(
                Result.success(true)
            )
            val usecase = SetStatusSentMovVisitTercImpl(movEquipVisitTercRepository)
            val result = usecase(list)
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }
}