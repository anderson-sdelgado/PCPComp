package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable

import br.com.usinasantafe.pcpcomp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcpcomp.domain.entities.stable.LocalTrab
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.cleantable.CleanLocalTrab
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.getserver.GetServerLocalTrab
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable.SaveLocalTrab
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.updatePercentage
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IUpdateLocalTrabTest {

    private val cleanLocalTrab = mock<CleanLocalTrab>()
    private val getServerLocalTrab = mock<GetServerLocalTrab>()
    private val saveLocalTrab = mock<SaveLocalTrab>()
    private fun getUsecase() = IUpdateLocalTrab(
        cleanLocalTrab = cleanLocalTrab,
        getServerLocalTrab = getServerLocalTrab,
        saveLocalTrab = saveLocalTrab,
    )

    @Test
    fun `check return failure usecase if have error in usecase RecoverLocalTrabServer`() =
        runTest {
            var pos = 0f
            whenever(
                getServerLocalTrab()
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "GetAllLocalTrabServer",
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
                    msgProgress = "Recuperando dados da tabela tb_local_trab do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> GetAllLocalTrabServer -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> GetAllLocalTrabServer -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure usecase if have error in usecase CleanLocalTrab`() =
        runTest {
            var pos = 0f
            whenever(
                getServerLocalTrab()
            ).thenReturn(
                Result.success(
                    localTrabList
                )
            )
            whenever(
                cleanLocalTrab()
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "CleanLocalTrab",
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
                    msgProgress = "Recuperando dados da tabela tb_local_trab do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_local_trab",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> CleanLocalTrab -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanLocalTrab -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure usecase if have error in datasource SaveAllLocalTrab`() =
        runTest {
            var pos = 0f
            whenever(
                getServerLocalTrab()
            ).thenReturn(
                Result.success(
                    localTrabList
                )
            )
            whenever(
                cleanLocalTrab()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveLocalTrab(localTrabList)
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "SaveAllLocalTrab",
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
                    msgProgress = "Recuperando dados da tabela tb_local_trab do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_local_trab",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_local_trab",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[3],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> SaveAllLocalTrab -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> SaveAllLocalTrab -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return true if have UpdateLocalTrab execute successfully`() =
        runTest {
            var pos = 0f
            whenever(
                getServerLocalTrab()
            ).thenReturn(
                Result.success(
                    localTrabList
                )
            )
            whenever(
                cleanLocalTrab()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveLocalTrab(localTrabList)
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
                    msgProgress = "Recuperando dados da tabela tb_local_trab do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_local_trab",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_local_trab",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
        }

}

val localTrabList = listOf(
    LocalTrab(
        idLocalTrab = 1,
        descrLocalTrab = "TI"
    )
)
