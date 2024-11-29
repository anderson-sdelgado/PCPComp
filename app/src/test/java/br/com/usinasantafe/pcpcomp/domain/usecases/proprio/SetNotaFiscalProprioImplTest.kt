package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class SetNotaFiscalProprioImplTest {

    @Test
    fun `Chech return failure if have error in MovEquipProprioRepository setNotaFiscal`() =
        runTest {
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipProprioRepository.setNotaFiscal(
                    notaFiscal = 123456,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipProprioRepository.setNotaFiscal",
                        cause = Exception()
                    )
                )
            )
            val usecase = ISetNotaFiscalProprio(
                movEquipProprioRepository,
                startProcessSendData
            )
            val result = usecase(
                notaFiscal = "123456",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipProprioRepository.setNotaFiscal"
            )
        }

    @Test
    fun `Chech return true if MovEquipProprioRepository setNotaFiscal execute success`() =
        runTest {
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipProprioRepository.setNotaFiscal(
                    notaFiscal = 123456,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val usecase = ISetNotaFiscalProprio(
                movEquipProprioRepository,
                startProcessSendData
            )
            val result = usecase(
                notaFiscal = "123456",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(
                result.getOrNull()!!
            )
        }


    @Test
    fun `Chech return true if MovEquipProprioRepository setNotaFiscal execute success and value is null`() =
        runTest {
            val movEquipProprioRepository = mock<MovEquipProprioRepository>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipProprioRepository.setNotaFiscal(
                    notaFiscal = null,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val usecase = ISetNotaFiscalProprio(
                movEquipProprioRepository,
                startProcessSendData
            )
            val result = usecase(
                notaFiscal = null,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(
                result.getOrNull()!!
            )
        }
}