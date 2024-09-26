package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipVisitTercPassag
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date

class SetObservVisitTercImplTest {

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository Get`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val startMovEquipVisitTerc = mock<StartMovEquipVisitTerc>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipVisitTercRepository.get(
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercRepository.get",
                        cause = Exception()
                    )
                )
            )
            val usecase = SetObservVisitTercImpl(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                startMovEquipVisitTerc,
                startProcessSendData
            )
            val result = usecase(
                observ = "observ",
                flowApp = FlowApp.ADD,
                typeMov = TypeMov.OUTPUT,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercRepository.get"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercPassagRepository List`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val startMovEquipVisitTerc = mock<StartMovEquipVisitTerc>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipVisitTercRepository.get(
                    id = 1
                )
            ).thenReturn(
                Result.success(
                    MovEquipVisitTerc(
                        idMovEquipVisitTerc = 1,
                        dthrMovEquipVisitTerc = Date(1723213270250),
                        tipoMovEquipVisitTerc = TypeMov.INPUT,
                        veiculoMovEquipVisitTerc = "GOL",
                        placaMovEquipVisitTerc = "AAA-0000",
                        idVisitTercMovEquipVisitTerc = 1
                    )
                )
            )
            whenever(
                movEquipVisitTercPassagRepository.list(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercPassagRepository.list",
                        cause = Exception()
                    )
                )
            )
            val usecase = SetObservVisitTercImpl(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                startMovEquipVisitTerc,
                startProcessSendData
            )
            val result = usecase(
                observ = "observ",
                flowApp = FlowApp.ADD,
                typeMov = TypeMov.OUTPUT,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercPassagRepository.list"
            )
        }

    @Test
    fun `Check return failure if have error in StartMovEquipVisitTerc`() =
        runTest {
            val passagList = listOf(
                MovEquipVisitTercPassag(
                    idMovEquipVisitTercPassag = 1,
                    idVisitTercMovEquipVisitTercPassag = 10,
                    idMovEquipVisitTerc = 1,
                )
            )
            val mov = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1,
                dthrMovEquipVisitTerc = Date(1723213270250),
                tipoMovEquipVisitTerc = TypeMov.INPUT,
                veiculoMovEquipVisitTerc = "GOL",
                placaMovEquipVisitTerc = "AAA-0000",
                idVisitTercMovEquipVisitTerc = 1
            )
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val startMovEquipVisitTerc = mock<StartMovEquipVisitTerc>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipVisitTercRepository.get(
                    id = 1
                )
            ).thenReturn(
                Result.success(mov)
            )
            whenever(
                movEquipVisitTercPassagRepository.list(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(passagList)
            )
            mov.observMovEquipVisitTerc = "observ"
            mov.tipoMovEquipVisitTerc = TypeMov.OUTPUT
            mov.dthrMovEquipVisitTerc = Date()
            mov.destinoMovEquipVisitTerc = null
            mov.statusMovEquipForeigVisitTerc = StatusForeigner.OUTSIDE
            mov.movEquipVisitTercPassagList = passagList
            whenever(
                startMovEquipVisitTerc(mov)
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "StartMovEquipVisitTerc",
                        cause = Exception()
                    )
                )
            )
            val usecase = SetObservVisitTercImpl(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                startMovEquipVisitTerc,
                startProcessSendData
            )
            val result = usecase(
                observ = "observ",
                flowApp = FlowApp.ADD,
                typeMov = TypeMov.OUTPUT,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Usecase -> StartMovEquipVisitTerc"
            )
        }

    @Test
    fun `Check return true if SetObservVisitTercImpl - TypeMov OUTPUT and FlowApp ADD - execute successfully`() =
        runTest {
            val passagList = listOf(
                MovEquipVisitTercPassag(
                    idMovEquipVisitTercPassag = 1,
                    idVisitTercMovEquipVisitTercPassag = 10,
                    idMovEquipVisitTerc = 1,
                )
            )
            val mov = MovEquipVisitTerc(
                idMovEquipVisitTerc = 1,
                dthrMovEquipVisitTerc = Date(1723213270250),
                tipoMovEquipVisitTerc = TypeMov.INPUT,
                veiculoMovEquipVisitTerc = "GOL",
                placaMovEquipVisitTerc = "AAA-0000",
                idVisitTercMovEquipVisitTerc = 1
            )
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val startMovEquipVisitTerc = mock<StartMovEquipVisitTerc>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipVisitTercRepository.get(
                    id = 1
                )
            ).thenReturn(
                Result.success(mov)
            )
            whenever(
                movEquipVisitTercPassagRepository.list(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(passagList)
            )
            mov.observMovEquipVisitTerc = "observ"
            mov.tipoMovEquipVisitTerc = TypeMov.OUTPUT
            mov.dthrMovEquipVisitTerc = Date()
            mov.destinoMovEquipVisitTerc = null
            mov.statusMovEquipForeigVisitTerc = StatusForeigner.OUTSIDE
            mov.movEquipVisitTercPassagList = passagList
            whenever(
                startMovEquipVisitTerc(mov)
            ).thenReturn(
                Result.success(true)
            )
            val usecase = SetObservVisitTercImpl(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                startMovEquipVisitTerc,
                startProcessSendData
            )
            val result = usecase(
                observ = "observ",
                flowApp = FlowApp.ADD,
                typeMov = TypeMov.OUTPUT,
                id = 1
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository setObserv`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val startMovEquipVisitTerc = mock<StartMovEquipVisitTerc>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipVisitTercRepository.setObserv(
                    observ = "observ",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercRepository.setObservVisitTerc",
                        cause = Exception()
                    )
                )
            )
            val usecase = SetObservVisitTercImpl(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                startMovEquipVisitTerc,
                startProcessSendData
            )
            val result = usecase(
                observ = "observ",
                flowApp = FlowApp.ADD,
                typeMov = TypeMov.INPUT,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercRepository.setObservVisitTerc"
            )
        }

    @Test
    fun `Check return true if SetObservVisitTercImpl - NOT TypeMov OUTPUT and FlowApp ADD - execute successfully`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val startMovEquipVisitTerc = mock<StartMovEquipVisitTerc>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipVisitTercRepository.setObserv(
                    observ = "observ",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val usecase = SetObservVisitTercImpl(
                movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository,
                startMovEquipVisitTerc,
                startProcessSendData
            )
            val result = usecase(
                observ = "observ",
                flowApp = FlowApp.ADD,
                typeMov = TypeMov.INPUT,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }
}