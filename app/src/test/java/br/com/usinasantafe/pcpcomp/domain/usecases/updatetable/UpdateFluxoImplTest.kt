package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable

import br.com.usinasantafe.pcpcomp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Fluxo
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.cleantable.CleanFluxo
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.getserver.GetAllFluxoServer
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savealltable.SaveAllFluxo
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.updatePercentage
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class UpdateFluxoImplTest {

    private val cleanFluxo = mock<CleanFluxo>()
    private val getAllFluxoServer = mock<GetAllFluxoServer>()
    private val saveAllFluxo = mock<SaveAllFluxo>()
    private fun getUsecase() = UpdateFluxoImpl(
        cleanFluxo = cleanFluxo,
        getAllFluxoServer = getAllFluxoServer,
        saveAllFluxo = saveAllFluxo,
    )

    @Test
    fun `check return failure usecase if have error in usecase RecoverFluxoServer`() =
        runTest {
            var pos = 0f
            whenever(
                getAllFluxoServer()
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "GetAllFluxoServer",
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
                    msgProgress = "Recuperando dados da tabela tb_fluxo do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> GetAllFluxoServer -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> GetAllFluxoServer -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure usecase if have error in usecase CleanFluxo`() =
        runTest {
            var pos = 0f
            whenever(
                getAllFluxoServer()
            ).thenReturn(
                Result.success(
                    fluxoList
                )
            )
            whenever(
                cleanFluxo()
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "CleanFluxo",
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
                    msgProgress = "Recuperando dados da tabela tb_fluxo do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_fluxo",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> CleanFluxo -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanFluxo -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure usecase if have error in datasource SaveAllFluxo`() =
        runTest {
            var pos = 0f
            whenever(
                getAllFluxoServer()
            ).thenReturn(
                Result.success(
                    fluxoList
                )
            )
            whenever(
                cleanFluxo()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveAllFluxo(fluxoList)
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "SaveAllFluxo",
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
                    msgProgress = "Recuperando dados da tabela tb_fluxo do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_fluxo",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_fluxo",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[3],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> SaveAllFluxo -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> SaveAllFluxo -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return true if have UpdateFluxo execute successfully`() =
        runTest {
            var pos = 0f
            whenever(
                getAllFluxoServer()
            ).thenReturn(
                Result.success(
                    fluxoList
                )
            )
            whenever(
                cleanFluxo()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveAllFluxo(fluxoList)
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
                    msgProgress = "Recuperando dados da tabela tb_fluxo do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_fluxo",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_fluxo",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
        }

}


val fluxoList = listOf(
    Fluxo(
        idFluxo = 1,
        descrFluxo = "MOV. EQUIP. PRÓPRIO"
    )
)
