package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable

import br.com.usinasantafe.pcpcomp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Local
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.cleantable.CleanLocal
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.getserver.GetServerLocal
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable.SaveLocal
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.updatePercentage
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IUpdateLocalTest {

    private val cleanLocal = mock<CleanLocal>()
    private val getServerLocal = mock<GetServerLocal>()
    private val saveLocal = mock<SaveLocal>()
    private fun getUsecase() = IUpdateLocal(
        cleanLocal = cleanLocal,
        getServerLocal = getServerLocal,
        saveLocal = saveLocal,
    )

    @Test
    fun `check return failure usecase if have error in usecase RecoverLocalServer`() =
        runTest {
            var pos = 0f
            whenever(
                getServerLocal()
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "GetAllLocalServer",
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
                    msgProgress = "Recuperando dados da tabela tb_local do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> GetAllLocalServer -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> GetAllLocalServer -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure usecase if have error in usecase CleanLocal`() =
        runTest {
            var pos = 0f
            whenever(
                getServerLocal()
            ).thenReturn(
                Result.success(
                    localList
                )
            )
            whenever(
                cleanLocal()
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "CleanLocal",
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
                    msgProgress = "Recuperando dados da tabela tb_local do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_local",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> CleanLocal -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanLocal -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure usecase if have error in datasource SaveAllLocal`() =
        runTest {
            var pos = 0f
            whenever(
                getServerLocal()
            ).thenReturn(
                Result.success(
                    localList
                )
            )
            whenever(
                cleanLocal()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveLocal(localList)
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "SaveAllLocal",
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
                    msgProgress = "Recuperando dados da tabela tb_local do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_local",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_local",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[3],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> SaveAllLocal -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> SaveAllLocal -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return true if have UpdateLocal execute successfully`() =
        runTest {
            var pos = 0f
            whenever(
                getServerLocal()
            ).thenReturn(
                Result.success(
                    localList
                )
            )
            whenever(
                cleanLocal()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveLocal(localList)
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
                    msgProgress = "Recuperando dados da tabela tb_local do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_local",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_local",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
        }

}

val localList = listOf(
    Local(
        idLocal = 1,
        descrLocal = "USINA"
    )
)
