package br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveEquipRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IGetMatricColabMovChaveEquipTest {

    private val movChaveEquipRepository = mock<MovChaveEquipRepository>()
    private val usecase = IGetMatricColabMovChaveEquip(
        movChaveEquipRepository
    )

    @Test
    fun `Check return failure if have error in MovChaveRepository getMatricColab`() =
        runTest {
            whenever(
                movChaveEquipRepository.getMatricColab(1)
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "IGetMatricColabMovChaveEquip",
                        cause = Exception()
                    )
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Usecase -> IGetMatricColabMovChaveEquip"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            whenever(
                movChaveEquipRepository.getMatricColab(1)
            ).thenReturn(
                Result.success(19759)
            )
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "19759"
            )
        }

}