package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable

import br.com.usinasantafe.pcpcomp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.cleantable.CleanVisitante
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.getserver.GetAllVisitanteServer
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savealltable.SaveAllVisitante
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.updatePercentage
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class UpdateVisitanteImplTest {

    private val cleanVisitante = mock<CleanVisitante>()
    private val getAllVisitanteServer = mock<GetAllVisitanteServer>()
    private val saveAllVisitante = mock<SaveAllVisitante>()
    private fun getUsecase() = UpdateVisitanteImpl(
        cleanVisitante = cleanVisitante,
        getAllVisitanteServer = getAllVisitanteServer,
        saveAllVisitante = saveAllVisitante,
    )

    @Test
    fun `check return failure usecase if have error in usecase RecoverVisitanteServer`() =
        runTest {
            var pos = 0f
            whenever(
                getAllVisitanteServer()
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "GetAllVisitanteServer",
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
                    msgProgress = "Recuperando dados da tabela tb_visitante do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> GetAllVisitanteServer -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> GetAllVisitanteServer -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure usecase if have error in usecase CleanVisitante`() =
        runTest {
            var pos = 0f
            whenever(
                getAllVisitanteServer()
            ).thenReturn(
                Result.success(
                    visitanteList
                )
            )
            whenever(
                cleanVisitante()
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "CleanVisitante",
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
                    msgProgress = "Recuperando dados da tabela tb_visitante do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_visitante",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> CleanVisitante -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanVisitante -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure usecase if have error in datasource SaveAllVisitante`() =
        runTest {
            var pos = 0f
            whenever(
                getAllVisitanteServer()
            ).thenReturn(
                Result.success(
                    visitanteList
                )
            )
            whenever(
                cleanVisitante()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveAllVisitante(visitanteList)
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "SaveAllVisitante",
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
                    msgProgress = "Recuperando dados da tabela tb_visitante do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_visitante",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_visitante",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[3],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> SaveAllVisitante -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> SaveAllVisitante -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return true if have UpdateVisitante execute successfully`() =
        runTest {
            var pos = 0f
            whenever(
                getAllVisitanteServer()
            ).thenReturn(
                Result.success(
                    visitanteList
                )
            )
            whenever(
                cleanVisitante()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveAllVisitante(visitanteList)
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
                    msgProgress = "Recuperando dados da tabela tb_visitante do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_visitante",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_visitante",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
        }

}

val visitanteList = listOf(
    Visitante(
        idVisitante = 1,
        cpfVisitante = "123.456.789-00",
        nomeVisitante = "Visitante",
        empresaVisitante = "Empresa Visitante"
    )
)
