package br.com.usinasantafe.pcpcomp.domain.usecases.chave

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovChave
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcpcomp.utils.TypeMovKey
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date
import kotlin.test.assertEquals

class ICloseAllMovChaveTest {

    private val movChaveRepository = mock<MovChaveRepository>()
    private val usecase = ICloseAllMovChave(
        movChaveRepository
    )

    @Test
    fun `Check return failure if have error in MovChaveRepository listOpen`() =
        runTest {
            whenever(
                movChaveRepository.listOpen()
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovChaveRepository.listOpen",
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
                "Failure Repository -> MovChaveRepository.listOpen"
            )
        }

    @Test
    fun `Check return failure if have error in MovChaveRepository setClose`() =
        runTest {
            val entity = MovChave(
                idMovChave = 1,
                dthrMovChave = Date(),
                tipoMovChave = TypeMovKey.REMOVE,
                idChaveMovChave = 1,
                idLocalMovChave = 1,
                matricColabMovChave = 19759,
                matricVigiaMovChave = 19035,
                observMovChave = "teste",
            )
            whenever(
                movChaveRepository.listOpen()
            ).thenReturn(
                Result.success(
                    listOf(
                        entity
                    )
                )
            )
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
            val result = usecase()
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
            val entity = MovChave(
                idMovChave = 1,
                dthrMovChave = Date(),
                tipoMovChave = TypeMovKey.REMOVE,
                idChaveMovChave = 1,
                idLocalMovChave = 1,
                matricColabMovChave = 19759,
                matricVigiaMovChave = 19035,
                observMovChave = "teste",
            )
            whenever(
                movChaveRepository.listOpen()
            ).thenReturn(
                Result.success(
                    listOf(
                        entity
                    )
                )
            )
            whenever(
                movChaveRepository.setClose(1)
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