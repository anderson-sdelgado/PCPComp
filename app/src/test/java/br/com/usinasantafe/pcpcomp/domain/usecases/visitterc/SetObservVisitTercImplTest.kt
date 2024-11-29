package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcpcomp.utils.FlowApp
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
            val usecase = ISetObservVisitTerc(
                movEquipVisitTercRepository,
                startProcessSendData
            )
            val result = usecase(
                observ = "observ",
                flowApp = FlowApp.ADD,
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
            val usecase = ISetObservVisitTerc(
                movEquipVisitTercRepository,
                startProcessSendData
            )
            val result = usecase(
                observ = "observ",
                flowApp = FlowApp.ADD,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercPassagRepository.list"
            )
        }

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository setObserv`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
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
            val usecase = ISetObservVisitTerc(
                movEquipVisitTercRepository,
                startProcessSendData
            )
            val result = usecase(
                observ = "observ",
                flowApp = FlowApp.ADD,
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
            val usecase = ISetObservVisitTerc(
                movEquipVisitTercRepository,
                startProcessSendData
            )
            val result = usecase(
                observ = "observ",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }
}