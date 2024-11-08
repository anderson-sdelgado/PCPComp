package br.com.usinasantafe.pcpcomp.domain.usecases.initial

import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipProprioDao
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipResidenciaDao
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipVisitTercDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipResidenciaRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipVisitTercRoomModel
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class CheckMovOpenImplTest: KoinTest {

    private val usecase: CheckMovOpen by inject()
    private val movEquipProprioDao: MovEquipProprioDao by inject()
    private val movEquipVisitTercDao: MovEquipVisitTercDao by inject()
    private val movEquipResidenciaDao: MovEquipResidenciaDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_false_if_not_have_data() =
        runTest {
            val result = usecase()
            assertTrue(result.isSuccess)
            assertFalse(result.getOrNull()!!)
        }

    @Test
    fun check_return_true_if_have_data_in_mov_proprio() =
        runTest {
            movEquipProprioDao.insert(
                MovEquipProprioRoomModel(
                    idMovEquipProprio = 1,
                    matricVigiaMovEquipProprio = 19759,
                    idLocalMovEquipProprio = 1,
                    tipoMovEquipProprio = TypeMov.INPUT,
                    dthrMovEquipProprio = 1723213270250,
                    idEquipMovEquipProprio = 1,
                    matricColabMovEquipProprio = 19759,
                    destinoMovEquipProprio = "TESTE DESTINO",
                    notaFiscalMovEquipProprio = 123456789,
                    observMovEquipProprio = "TESTE OBSERV",
                    statusMovEquipProprio = StatusData.OPEN,
                    statusSendMovEquipProprio = StatusSend.SEND
                )
            )
            val result = usecase()
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
            MovEquipVisitTercRoomModel(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMov.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = 1723213270250,
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
            )
        }

    @Test
    fun check_return_true_if_have_data_in_mov_visit_terc() =
        runTest {
            movEquipVisitTercDao.insert(
                MovEquipVisitTercRoomModel(
                    idMovEquipVisitTerc = 1,
                    nroMatricVigiaMovEquipVisitTerc = 19759,
                    idLocalMovEquipVisitTerc = 1,
                    tipoMovEquipVisitTerc = TypeMov.INPUT,
                    idVisitTercMovEquipVisitTerc = 1000,
                    tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                    dthrMovEquipVisitTerc = 1723213270250,
                    veiculoMovEquipVisitTerc = "VEICULO TESTE",
                    placaMovEquipVisitTerc = "PLACA TESTE",
                    destinoMovEquipVisitTerc = "DESTINO TESTE",
                    observMovEquipVisitTerc = "OBSERV TESTE",
                    statusMovEquipVisitTerc = StatusData.OPEN,
                    statusSendMovEquipVisitTerc = StatusSend.SEND,
                    statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
                )
            )
            val result = usecase()
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun check_return_true_if_have_data_in_mov_residencia() =
        runTest {
            movEquipResidenciaDao.insert(
                MovEquipResidenciaRoomModel(
                    idMovEquipResidencia = 1,
                    nroMatricVigiaMovEquipResidencia = 19759,
                    idLocalMovEquipResidencia = 1,
                    tipoMovEquipResidencia = TypeMov.INPUT,
                    dthrMovEquipResidencia = 1723213270250,
                    motoristaMovEquipResidencia = "MOTORISTA TESTE",
                    veiculoMovEquipResidencia = "VEICULO TESTE",
                    placaMovEquipResidencia = "PLACA TESTE",
                    observMovEquipResidencia = "OBSERV TESTE",
                    statusMovEquipResidencia = StatusData.OPEN,
                    statusSendMovEquipResidencia = StatusSend.SEND,
                    statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
                )
            )
            val result = usecase()
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }
}