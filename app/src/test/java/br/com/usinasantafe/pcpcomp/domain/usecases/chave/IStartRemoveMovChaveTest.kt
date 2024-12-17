package br.com.usinasantafe.pcpcomp.domain.usecases.chave

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IStartRemoveMovChaveTest {

    private val movChaveRepository = mock<MovChaveRepository>()

    private val usecase = IStartRemoveMovChave(
        movChaveRepository = movChaveRepository
    )

    @Test
    fun `Check return failure if have error in MovChaveRepository start`() =
        runTest {
            whenever(
                movChaveRepository.start()
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovChaveRepository.start",
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
                "Failure Repository -> MovChaveRepository.start"
            )
        }

    @Test
    fun `Check return true if IStartRemoveChave execute successfully`() =
        runTest {
            whenever(
                movChaveRepository.start()
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