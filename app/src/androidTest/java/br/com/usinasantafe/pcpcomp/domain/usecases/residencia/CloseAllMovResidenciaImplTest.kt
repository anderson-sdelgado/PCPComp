package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipResidenciaDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipResidenciaRoomModel
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class CloseAllMovResidenciaImplTest : KoinTest {

    private val usecase: CloseAllMovResidencia by inject()
    private val movEquipResidenciaDao: MovEquipResidenciaDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_true_if_not_have_mov_open() =
        runTest {
            val result = usecase()
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun check_return_true_and_close_mov_if_close_all_mov_execute_successfully() =
        runTest {
            val roomModel1 = MovEquipResidenciaRoomModel(
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
            val roomModel2 = MovEquipResidenciaRoomModel(
                nroMatricVigiaMovEquipResidencia = 19759,
                idLocalMovEquipResidencia = 1,
                tipoMovEquipResidencia = TypeMov.INPUT,
                dthrMovEquipResidencia = 1723213270250,
                motoristaMovEquipResidencia = "MOTORISTA TESTE 2",
                veiculoMovEquipResidencia = "VEICULO TESTE 2",
                placaMovEquipResidencia = "PLACA TESTE 2",
                observMovEquipResidencia = "OBSERV TESTE 2",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
            )
            movEquipResidenciaDao.insert(roomModel1)
            movEquipResidenciaDao.insert(roomModel2)
            val roomModelBefore1 =
                movEquipResidenciaDao.get(1)
            assertEquals(roomModelBefore1.statusMovEquipResidencia, StatusData.OPEN)
            val roomModelBefore2 =
                movEquipResidenciaDao.get(2)
            assertEquals(roomModelBefore2.statusMovEquipResidencia, StatusData.OPEN)
            val result = usecase()
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
            val roomModelAfter1 =
                movEquipResidenciaDao.get(1)
            assertEquals(roomModelAfter1.statusMovEquipResidencia, StatusData.CLOSE)
            val roomModelAfter2 =
                movEquipResidenciaDao.get(2)
            assertEquals(roomModelAfter2.statusMovEquipResidencia, StatusData.CLOSE)
        }

}