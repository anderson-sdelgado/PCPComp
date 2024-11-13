package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable

import br.com.usinasantafe.pcpcomp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcpcomp.domain.entities.stable.RLocalFluxo
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.cleantable.CleanRLocalFluxo
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.getserver.GetAllRLocalFluxoServer
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savealltable.SaveAllRLocalFluxo
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.updatePercentage
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class UpdateRRLocalFluxoFluxoImplTest {

    private val cleanRLocalFluxo = mock<CleanRLocalFluxo>()
    private val getAllRLocalFluxoServer = mock<GetAllRLocalFluxoServer>()
    private val saveAllRLocalFluxo = mock<SaveAllRLocalFluxo>()
    private fun getUsecase() = UpdateRLocalFluxoImpl(
        cleanRLocalFluxo = cleanRLocalFluxo,
        getAllRLocalFluxoServer = getAllRLocalFluxoServer,
        saveAllRLocalFluxo = saveAllRLocalFluxo,
    )

    @Test
    fun `check return failure usecase if have error in usecase RecoverRLocalFluxoServer`() =
        runTest {
            var pos = 0f
            whenever(
                getAllRLocalFluxoServer()
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "GetAllRLocalFluxoServer",
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
                    msgProgress = "Recuperando dados da tabela tb_r_local_fluxo do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> GetAllRLocalFluxoServer -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> GetAllRLocalFluxoServer -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure usecase if have error in usecase CleanRLocalFluxo`() =
        runTest {
            var pos = 0f
            whenever(
                getAllRLocalFluxoServer()
            ).thenReturn(
                Result.success(
                    rLocalFluxoList
                )
            )
            whenever(
                cleanRLocalFluxo()
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "CleanRLocalFluxo",
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
                    msgProgress = "Recuperando dados da tabela tb_r_local_fluxo do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_r_local_fluxo",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> CleanRLocalFluxo -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanRLocalFluxo -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure usecase if have error in datasource SaveAllRLocalFluxo`() =
        runTest {
            var pos = 0f
            whenever(
                getAllRLocalFluxoServer()
            ).thenReturn(
                Result.success(
                    rLocalFluxoList
                )
            )
            whenever(
                cleanRLocalFluxo()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveAllRLocalFluxo(rLocalFluxoList)
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "SaveAllRLocalFluxo",
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
                    msgProgress = "Recuperando dados da tabela tb_r_local_fluxo do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_r_local_fluxo",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_r_local_fluxo",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[3],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> SaveAllRLocalFluxo -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> SaveAllRLocalFluxo -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return true if have UpdateRLocalFluxo execute successfully`() =
        runTest {
            var pos = 0f
            whenever(
                getAllRLocalFluxoServer()
            ).thenReturn(
                Result.success(
                    rLocalFluxoList
                )
            )
            whenever(
                cleanRLocalFluxo()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveAllRLocalFluxo(rLocalFluxoList)
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
                    msgProgress = "Recuperando dados da tabela tb_r_local_fluxo do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_r_local_fluxo",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_r_local_fluxo",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
        }

}

val rLocalFluxoList = listOf(
    RLocalFluxo(
        idRLocalFluxo = 1,
        idLocal = 1,
        idFluxo = 1
    )
)
