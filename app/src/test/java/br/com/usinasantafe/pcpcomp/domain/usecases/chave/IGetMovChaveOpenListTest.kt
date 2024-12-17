package br.com.usinasantafe.pcpcomp.domain.usecases.chave

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovChave
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcpcomp.utils.TypeMovKey
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date
import kotlin.test.assertEquals

class IGetMovChaveOpenListTest {

    private val movChaveRepository = mock<MovChaveRepository>()
    private val colabRepository = mock<ColabRepository>()
    private val getDescrFullChave = mock<GetDescrFullChave>()
    private val usecase = IGetMovChaveOpenList(
        movChaveRepository = movChaveRepository,
        colabRepository = colabRepository,
        getDescrFullChave = getDescrFullChave
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
    fun `Check return failure if have error in ColabRepository GetNome`() =
        runTest {
            whenever(
                movChaveRepository.listOpen()
            ).thenReturn(
                Result.success(
                    listOf(
                        MovChave(
                            idMovChave = 1,
                            dthrMovChave = Date(),
                            tipoMovChave = TypeMovKey.REMOVE,
                            idChaveMovChave = 1,
                            idLocalMovChave = 1,
                            matricColabMovChave = 19759,
                            matricVigiaMovChave = 19035,
                            observMovChave = "teste",
                        )
                    )
                )
            )
            whenever(
                colabRepository.getNome(
                    matric = 19759
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "ColabRepository.getNome",
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
                "Failure Repository -> ColabRepository.getNome"
            )
        }

    @Test
    fun `Check return failure if have error in GetDescrFullChave`() =
        runTest {
            whenever(
                movChaveRepository.listOpen()
            ).thenReturn(
                Result.success(
                    listOf(
                        MovChave(
                            idMovChave = 1,
                            dthrMovChave = Date(),
                            tipoMovChave = TypeMovKey.REMOVE,
                            idChaveMovChave = 1,
                            idLocalMovChave = 1,
                            matricColabMovChave = 19759,
                            matricVigiaMovChave = 19035,
                            observMovChave = "teste",
                        )
                    )
                )
            )
            whenever(
                colabRepository.getNome(
                    matric = 19759
                )
            ).thenReturn(
                Result.success(
                    "ANDERSON DA SILVA DELGADO"
                )
            )
            whenever(
                getDescrFullChave(1)
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "GetDescrFullChave",
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
                "Failure Usecase -> GetDescrFullChave"
            )
        }

    @Test
    fun `Check return list if MovChaveRepository listRemove execute successfully`() =
        runTest {
            whenever(
                movChaveRepository.listOpen()
            ).thenReturn(
                Result.success(
                    listOf(
                        MovChave(
                            idMovChave = 1,
                            dthrMovChave = Date(),
                            tipoMovChave = TypeMovKey.REMOVE,
                            idChaveMovChave = 1,
                            idLocalMovChave = 1,
                            matricColabMovChave = 19759,
                            matricVigiaMovChave = 19035,
                            observMovChave = "teste",
                        )
                    )
                )
            )
            whenever(
                colabRepository.getNome(
                    matric = 19759
                )
            ).thenReturn(
                Result.success(
                    "ANDERSON DA SILVA DELGADO"
                )
            )
            whenever(
                getDescrFullChave(1)
            ).thenReturn(
                Result.success(
                    "01 - SALA TI - TI"
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
            )
            val entity = list[0]
            assertEquals(
                entity.id,
                1
            )
            assertEquals(
                entity.colab,
                "19759 - ANDERSON DA SILVA DELGADO"
            )
            assertEquals(
                entity.chave,
                "01 - SALA TI - TI"
            )
            assertEquals(
                entity.tipoMov,
                "RETIRADA"
            )
        }

}