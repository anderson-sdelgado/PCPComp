package br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveEquipRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IStartReceiptMovChaveEquipTest {

    private val movChaveEquipRepository = mock<MovChaveEquipRepository>()
    private val usecase = IStartReceiptMovChaveEquip(
        movChaveEquipRepository = movChaveEquipRepository
    )

    @Test
    fun `Check return failure if have error in MovChaveRepository start`() =
        runTest {
            whenever(
                movChaveEquipRepository.start()
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovChaveEquipRepository.start",
                        cause = Exception()
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovChaveEquipRepository.start"
            )
        }

    @Test
    fun `Check return true if IStartRemoveChave execute successfully`() =
        runTest {
            whenever(
                movChaveEquipRepository.start()
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

}