package br.com.usinasantafe.pcpcomp.domain.usecases.initial

import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipProprioDao
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipProprioEquipSegDao
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipProprioPassagDao
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipResidenciaDao
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipVisitTercDao
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipVisitTercPassagDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioEquipSegRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioPassagRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipResidenciaRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipVisitTercPassagRoomModel
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

class DeleteMovSentImplTest: KoinTest {

    private val usecase: DeleteMovSent by inject()
    private val movEquipProprioDao: MovEquipProprioDao by inject()
    private val movEquipVisitTercDao: MovEquipVisitTercDao by inject()
    private val movEquipResidenciaDao: MovEquipResidenciaDao by inject()
    private val movEquipProprioPassagDao: MovEquipProprioPassagDao by inject()
    private val movEquipProprioEquipSegDao: MovEquipProprioEquipSegDao by inject()
    private val movEquipVisitTercPassagDao: MovEquipVisitTercPassagDao by inject()


    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_true_if_not_have_data() =
        runTest {
            val result = usecase()
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun check_return_true_and_data_returned() =
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
                    statusMovEquipProprio = StatusData.CLOSE,
                    statusSendMovEquipProprio = StatusSend.SENT
                )
            )
            movEquipProprioDao.insert(
                MovEquipProprioRoomModel(
                    idMovEquipProprio = 2,
                    matricVigiaMovEquipProprio = 19759,
                    idLocalMovEquipProprio = 1,
                    tipoMovEquipProprio = TypeMov.INPUT,
                    dthrMovEquipProprio = 1723213270250,
                    idEquipMovEquipProprio = 1,
                    matricColabMovEquipProprio = 19759,
                    destinoMovEquipProprio = "TESTE DESTINO",
                    notaFiscalMovEquipProprio = 123456789,
                    observMovEquipProprio = "TESTE OBSERV",
                    statusMovEquipProprio = StatusData.CLOSE,
                    statusSendMovEquipProprio = StatusSend.SEND
                )
            )
            movEquipProprioPassagDao.insert(
                MovEquipProprioPassagRoomModel(
                    idMovEquipProprioPassag = 1,
                    idMovEquipProprio = 1,
                    matricColab = 19035
                )
            )
            movEquipProprioEquipSegDao.insert(
                MovEquipProprioEquipSegRoomModel(
                    idMovEquipProprioEquipSeg = 1,
                    idMovEquipProprio = 1,
                    idEquip = 1
                )
            )
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
                    statusMovEquipVisitTerc = StatusData.CLOSE,
                    statusSendMovEquipVisitTerc = StatusSend.SENT,
                    statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
                )
            )
            movEquipVisitTercDao.insert(
                MovEquipVisitTercRoomModel(
                    idMovEquipVisitTerc = 2,
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
                    statusMovEquipVisitTerc = StatusData.CLOSE,
                    statusSendMovEquipVisitTerc = StatusSend.SEND,
                    statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
                )
            )
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTercPassag = 1,
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 1
                )
            )
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
                    statusMovEquipResidencia = StatusData.CLOSE,
                    statusSendMovEquipResidencia = StatusSend.SENT,
                    statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
                )
            )
            movEquipResidenciaDao.insert(
                MovEquipResidenciaRoomModel(
                    idMovEquipResidencia = 2,
                    nroMatricVigiaMovEquipResidencia = 19759,
                    idLocalMovEquipResidencia = 1,
                    tipoMovEquipResidencia = TypeMov.INPUT,
                    dthrMovEquipResidencia = 1723213270250,
                    motoristaMovEquipResidencia = "MOTORISTA TESTE",
                    veiculoMovEquipResidencia = "VEICULO TESTE",
                    placaMovEquipResidencia = "PLACA TESTE",
                    observMovEquipResidencia = "OBSERV TESTE",
                    statusMovEquipResidencia = StatusData.CLOSE,
                    statusSendMovEquipResidencia = StatusSend.SEND,
                    statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
                )
            )
            val movProprioListBefore = movEquipProprioDao.listStatusData(StatusData.CLOSE)
            assertEquals(movProprioListBefore.size, 2)
            val movVisitTercListBefore = movEquipVisitTercDao.listStatusData(StatusData.CLOSE)
            assertEquals(movVisitTercListBefore.size, 2)
            val movResidenciaListBefore = movEquipResidenciaDao.listStatusData(StatusData.CLOSE)
            assertEquals(movResidenciaListBefore.size, 2)

            val movProprioPassagListBefore = movEquipProprioPassagDao.list(1)
            assertEquals(movProprioPassagListBefore.size, 1)
            val movProprioEquipSegListBefore = movEquipProprioEquipSegDao.list(1)
            assertEquals(movProprioEquipSegListBefore.size, 1)
            val movVisitTercPassagListBefore = movEquipVisitTercPassagDao.list(1)
            assertEquals(movVisitTercPassagListBefore.size, 1)

            val result = usecase()
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)

            val movProprioListAfter = movEquipProprioDao.listStatusData(StatusData.CLOSE)
            assertEquals(movProprioListAfter.size, 1)
            val movVisitTercListAfter = movEquipVisitTercDao.listStatusData(StatusData.CLOSE)
            assertEquals(movVisitTercListAfter.size, 1)
            val movResidenciaListAfter = movEquipResidenciaDao.listStatusData(StatusData.CLOSE)
            assertEquals(movResidenciaListAfter.size, 1)

            val movProprioPassagListAfter = movEquipProprioPassagDao.list(1)
            assertEquals(movProprioPassagListAfter.size, 0)
            val movProprioEquipSegListAfter = movEquipProprioEquipSegDao.list(1)
            assertEquals(movProprioEquipSegListAfter.size, 0)
            val movVisitTercPassagListAfter = movEquipVisitTercPassagDao.list(1)
            assertEquals(movVisitTercPassagListAfter.size, 0)
        }

}