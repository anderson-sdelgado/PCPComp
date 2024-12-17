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

class IGetDetalheMovChaveTest {

    private val movChaveRepository = mock<MovChaveRepository>()
    private val colabRepository = mock<ColabRepository>()
    private val getDescrFullChave = mock<GetDescrFullChave>()
    private val usecase = IGetDetalheMovChave(
        movChaveRepository = movChaveRepository,
        colabRepository = colabRepository,
        getDescrFullChave = getDescrFullChave
    )

    @Test
    fun `Check return failure if have error in MovChaveRepository`() =
        runTest {
            whenever(
                movChaveRepository.get(1)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovChaveRepository.get",
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
                "Failure Repository -> MovChaveRepository.get"
            )
        }

    @Test
    fun `Check return failure if have error in ColabRepository getNome`() =
        runTest {
            val entity =
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
            whenever(
                movChaveRepository.get(1)
            ).thenReturn(
                Result.success(entity)
            )
            whenever(
                colabRepository.getNome(19759)
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "ColabRepository.getNome",
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
                "Failure Repository -> ColabRepository.getNome"
            )
        }

    @Test
    fun `Check return failure if have error in GetDescrFullChave`() =
        runTest {
            val entity =
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
            whenever(
                movChaveRepository.get(1)
            ).thenReturn(
                Result.success(entity)
            )
            whenever(
                colabRepository.getNome(19759)
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
            val result = usecase(1)
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
    fun `Check return correct if function execute successfully`() =
        runTest {
            val entity =
                MovChave(
                    idMovChave = 1,
                    dthrMovChave = Date(),
                    tipoMovChave = TypeMovKey.REMOVE,
                    idChaveMovChave = 1,
                    idLocalMovChave = 1,
                    matricColabMovChave = 19759,
                    matricVigiaMovChave = 19035,
                    observMovChave = "TESTE",
                )
            whenever(
                movChaveRepository.get(1)
            ).thenReturn(
                Result.success(entity)
            )
            whenever(
                colabRepository.getNome(19759)
            ).thenReturn(
                Result.success(
                    "ANDERSON DA SILVA DELGADO"
                )
            )
            whenever(
                getDescrFullChave(1)
            ).thenReturn(
                Result.success("01 - SALA TI - TI")
            )
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val entityResult = result.getOrNull()!!
            assertEquals(
                entityResult.dthr,
                entity.dthrMovChave.toString()
            )
            assertEquals(
                entityResult.tipoMov,
                "RETIRADA"
            )
            assertEquals(
                entityResult.chave,
                "01 - SALA TI - TI"
            )
            assertEquals(
                entityResult.colab,
                "19759 - ANDERSON DA SILVA DELGADO"
            )
            assertEquals(
                entityResult.observ,
                "TESTE"
            )
        }

}