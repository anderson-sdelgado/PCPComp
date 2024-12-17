package br.com.usinasantafe.pcpcomp.domain.usecases.chave

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IGetObservMovChaveTest {

    private val movChaveRepository = mock<MovChaveRepository>()
    private val usecase = IGetObservMovChave(
        movChaveRepository
    )

    @Test
    fun `Check return failure if have error in MovChaveRepository getObserv`() =
        runTest {
            whenever(
                movChaveRepository.getObserv(1)
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "IGetObservMovChaveImpl",
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
                "Failure Usecase -> IGetObservMovChaveImpl"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            whenever(
                movChaveRepository.getObserv(1)
            ).thenReturn(
                Result.success("OBSERV")
            )
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "OBSERV"
            )
        }

}