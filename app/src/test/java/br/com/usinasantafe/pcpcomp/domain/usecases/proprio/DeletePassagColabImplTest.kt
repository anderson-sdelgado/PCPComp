package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class DeletePassagColabImplTest {

    @Test
    fun `Chech return failure Usecase if have error in MovEquipProprioPassagRepository`() =
        runTest {
            val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
            whenever(
                movEquipProprioPassagRepository.delete(
                    matricColab = 19759,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipProprioPassagRepository.delete",
                        cause = Exception()
                    )
                )
            )
            val usecase = DeletePassagColabImpl(
                movEquipProprioPassagRepository
            )
            val result = usecase(
                matricColab = 19759,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipProprioPassagRepository.delete"
            )
        }

    @Test
    fun `Chech return true if MovEquipProprioPassagRepository delete execute success`() = runTest {
        val movEquipProprioPassagRepository = mock<MovEquipProprioPassagRepository>()
        whenever(
            movEquipProprioPassagRepository.delete(
                matricColab = 19759,
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        val usecase = DeletePassagColabImpl(
            movEquipProprioPassagRepository
        )
        val result = usecase(
            matricColab = 19759,
            flowApp = FlowApp.ADD,
            id = 0
        )
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }

}