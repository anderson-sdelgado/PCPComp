package br.com.usinasantafe.pcpcomp.domain.usecases.chave

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IGetMatricColabMovChaveTest {

    private val movChaveRepository = mock<MovChaveRepository>()
    private val usecase = IGetMatricColabMovChave(
        movChaveRepository
    )

    @Test
    fun `Check return failure if have error in MovChaveRepository getMatricColab`() =
        runTest {
            whenever(
                movChaveRepository.getMatricColab(1)
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "IGetMatricColabMovChaveImpl",
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
                "Failure Usecase -> IGetMatricColabMovChave"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            whenever(
                movChaveRepository.getMatricColab(1)
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