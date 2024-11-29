package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class DeleteEquipSegImplTest {

    @Test
    fun `Chech return failure Usecase if have error in MovEquipProprioEquipSegRepository`() =
        runTest {
            val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
            whenever(
                movEquipProprioEquipSegRepository.delete(
                    idEquip = 10,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipProprioEquipSegRepository.delete",
                        cause = Exception()
                    )
                )
            )
            val usecase = IDeleteEquipSeg(
                movEquipProprioEquipSegRepository
            )
            val result = usecase(
                idEquip = 10,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipProprioEquipSegRepository.delete"
            )
        }

    @Test
    fun `Chech return true if MovEquipProprioEquipSegRepository delete execute success`() =
        runTest {
            val movEquipProprioEquipSegRepository = mock<MovEquipProprioEquipSegRepository>()
            whenever(
                movEquipProprioEquipSegRepository.delete(
                    idEquip = 10,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val usecase = IDeleteEquipSeg(
                movEquipProprioEquipSegRepository
            )
            val result = usecase(
                idEquip = 10,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

}