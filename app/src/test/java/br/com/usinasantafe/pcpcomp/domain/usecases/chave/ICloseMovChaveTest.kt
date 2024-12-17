package br.com.usinasantafe.pcpcomp.domain.usecases.chave

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class ICloseMovChaveTest {

    private val movChaveRepository = mock<MovChaveRepository>()
    private val usecase = ICloseMovChave(
        movChaveRepository
    )

    @Test
    fun `Check return failure if have error in MovChaveRepository get`() =
        runTest {
            whenever(
                movChaveRepository.setClose(1)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovChaveRepository.setClose",
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
                "Failure Repository -> MovChaveRepository.setClose"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            whenever(
                movChaveRepository.setClose(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(1)
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