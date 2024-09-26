package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date

class SetObservResidenciaImplTest {

    @Test
    fun `Check return failure if have error in MovEquipResidenciaRepository Get`() =
        runTest {
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            val startMovEquipResidencia = mock<StartMovEquipResidencia>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipResidenciaRepository.get(
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipResidenciaRepository.get",
                        cause = Exception()
                    )
                )
            )
            val usecase = SetObservResidenciaImpl(
                movEquipResidenciaRepository,
                startMovEquipResidencia,
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
                "Failure Repository -> MovEquipResidenciaRepository.get"
            )
        }

    @Test
    fun `Check return failure if have error in StartMovEquipResidencia`() =
        runTest {
            val model = MovEquipResidencia(
                idMovEquipResidencia = 1,
                nroMatricVigiaMovEquipResidencia = 1000,
                idLocalMovEquipResidencia = 1000,
                tipoMovEquipResidencia = TypeMov.INPUT,
                dthrMovEquipResidencia = Date(1723213270250),
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERVACAO TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
            )
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            val startMovEquipResidencia = mock<StartMovEquipResidencia>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipResidenciaRepository.get(
                    id = 1
                )
            ).thenReturn(
                Result.success(model)
            )
            model.observMovEquipResidencia = "observ"
            model.tipoMovEquipResidencia = TypeMov.OUTPUT
            model.dthrMovEquipResidencia = Date()
            model.statusMovEquipForeigResidencia = StatusForeigner.OUTSIDE
            whenever(
                startMovEquipResidencia(model)
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "StartMovEquipResidencia",
                        cause = Exception()
                    )
                )
            )
            val usecase = SetObservResidenciaImpl(
                movEquipResidenciaRepository,
                startMovEquipResidencia,
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
                "Failure Usecase -> StartMovEquipResidencia"
            )
        }

    @Test
    fun `Check return true if SetObservResidenciaImpl - TypeMov OUTPUT and FlowApp ADD - execute successfully`() =
        runTest {
            val model = MovEquipResidencia(
                idMovEquipResidencia = 1,
                nroMatricVigiaMovEquipResidencia = 1000,
                idLocalMovEquipResidencia = 1000,
                tipoMovEquipResidencia = TypeMov.INPUT,
                dthrMovEquipResidencia = Date(1723213270250),
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERVACAO TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
            )
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            val startMovEquipResidencia = mock<StartMovEquipResidencia>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipResidenciaRepository.get(
                    id = 1
                )
            ).thenReturn(
                Result.success(model)
            )
            model.observMovEquipResidencia = "observ"
            model.tipoMovEquipResidencia = TypeMov.OUTPUT
            model.dthrMovEquipResidencia = Date()
            model.statusMovEquipForeigResidencia = StatusForeigner.OUTSIDE
            whenever(
                startMovEquipResidencia(model)
            ).thenReturn(
                Result.success(true)
            )
            val usecase = SetObservResidenciaImpl(
                movEquipResidenciaRepository,
                startMovEquipResidencia,
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
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            val startMovEquipResidencia = mock<StartMovEquipResidencia>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipResidenciaRepository.setObserv(
                    observ = "observ",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipResidenciaRepository.setObserv",
                        cause = Exception()
                    )
                )
            )
            val usecase = SetObservResidenciaImpl(
                movEquipResidenciaRepository,
                startMovEquipResidencia,
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
                "Failure Repository -> MovEquipResidenciaRepository.setObserv"
            )
        }

    @Test
    fun `Check return true if SetObservVisitTercImpl - NOT TypeMov OUTPUT and FlowApp ADD - execute successfully`() =
        runTest {
            val movEquipResidenciaRepository = mock<MovEquipResidenciaRepository>()
            val startMovEquipResidencia = mock<StartMovEquipResidencia>()
            val startProcessSendData = mock<StartProcessSendData>()
            whenever(
                movEquipResidenciaRepository.setObserv(
                    observ = "observ",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val usecase = SetObservResidenciaImpl(
                movEquipResidenciaRepository,
                startMovEquipResidencia,
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