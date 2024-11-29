package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable

import br.com.usinasantafe.pcpcomp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.cleantable.CleanColab
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.getserver.GetServerColab
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable.SaveColab
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.updatePercentage
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IUpdateColabTest {

    private val cleanColab = mock<CleanColab>()
    private val getServerColab = mock<GetServerColab>()
    private val saveColab = mock<SaveColab>()
    private fun getUsecase() = IUpdateColab(
        cleanColab = cleanColab,
        getServerColab = getServerColab,
        saveColab = saveColab,
    )

    @Test
    fun `Check return failure usecase if have error in usecase RecoverColabServer`() =
        runTest {
            var pos = 0f
            whenever(
                getServerColab()
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "GetAllColabServer",
                        cause = NullPointerException()
                    )
                )
            )
            val usecase = getUsecase()
            val result = usecase(
                sizeAll = 16f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(result.count(), 2)
            assertEquals(
                list[0],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> GetAllColabServer -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> GetAllColabServer -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure usecase if have error in usecase CleanColab`() =
        runTest {
            var pos = 0f
            whenever(
                getServerColab()
            ).thenReturn(
                Result.success(
                    colabList
                )
            )
            whenever(
                cleanColab()
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "CleanColab",
                        cause = NullPointerException()
                    )
                )
            )
            val usecase = getUsecase()
            val result = usecase(
                sizeAll = 16f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(result.count(), 3)
            assertEquals(
                list[0],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_colab",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> CleanColab -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanColab -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure usecase if have error in datasource SaveAllColab`() =
        runTest {
            var pos = 0f
            whenever(
                getServerColab()
            ).thenReturn(
                Result.success(
                    colabList
                )
            )
            whenever(
                cleanColab()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveColab(colabList)
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "SaveAllColab",
                        cause = NullPointerException()
                    )
                )
            )
            val usecase = getUsecase()
            val result = usecase(
                sizeAll = 16f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(result.count(), 4)
            assertEquals(
                list[0],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_colab",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_colab",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[3],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> SaveAllColab -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> SaveAllColab -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return true if have UpdateColab execute successfully`() =
        runTest {
            var pos = 0f
            whenever(
                getServerColab()
            ).thenReturn(
                Result.success(
                    colabList
                )
            )
            whenever(
                cleanColab()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveColab(colabList)
            ).thenReturn(
                Result.success(true)
            )
            val usecase = getUsecase()
            val result = usecase(
                sizeAll = 16f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(result.count(), 3)
            assertEquals(
                list[0],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_colab",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_colab",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
        }

}

val colabList = listOf(
    Colab(
        matricColab = 19759,
        nomeColab = "ANDERSON DA SILVA DELGADO"
    )
)
