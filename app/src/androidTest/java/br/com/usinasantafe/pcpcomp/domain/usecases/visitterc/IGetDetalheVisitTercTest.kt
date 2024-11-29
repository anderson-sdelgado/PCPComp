package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.external.room.dao.stable.TerceiroDao
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.VisitanteDao
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipVisitTercDao
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipVisitTercPassagDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.TerceiroRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.VisitanteRoomModel
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

class IGetDetalheVisitTercTest : KoinTest {

    private val usecase: GetDetalheVisitTerc by inject()
    private val movEquipVisitTercDao: MovEquipVisitTercDao by inject()
    private val movEquipVisitTercPassagDao: MovEquipVisitTercPassagDao by inject()
    private val visitanteDao: VisitanteDao by inject()
    private val terceiroDao: TerceiroDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_failure_if_not_have_data() = runTest {
        val result = usecase(
            id = 1
        )
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> MovEquipVisitTercRepositoryImpl.get"
        )
    }

    @Test
    fun check_return_failure_if_not_have_data_terc() = runTest {
        val roomModel = MovEquipVisitTercRoomModel(
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
        movEquipVisitTercDao.insert(roomModel)
        val result = usecase(
            id = 1
        )
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> TerceiroRepositoryImpl.get"
        )
    }

    @Test
    fun check_return_detalhe_without_passag_if_get_detalhe_execute_success_without_passag_and_terc() =
        runTest {
            val roomModel = MovEquipVisitTercRoomModel(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMov.INPUT,
                idVisitTercMovEquipVisitTerc = 1,
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
            movEquipVisitTercDao.insert(roomModel)
            val tercRoomModel = TerceiroRoomModel(
                idTerceiro = 1,
                idBDTerceiro = 1,
                cpfTerceiro = "326.949.728-88",
                nomeTerceiro = "TESTE",
                empresaTerceiro = "EMPRESA TESTE"
            )
            terceiroDao.insertAll(listOf(tercRoomModel))
            val result = usecase(
                id = 1
            )
            assertTrue(result.isSuccess)
            val entity = result.getOrNull()!!
            assertEquals(
                entity.placa,
                "PLACA TESTE"
            )
            assertEquals(
                entity.passageiro,
                ""
            )
            assertEquals(
                entity.motorista,
                "326.949.728-88 - TESTE"
            )
        }

    @Test
    fun check_return_detalhe_with_passag_if_get_detalhe_execute_success_with_passag_and_terc() =
        runTest {
            val roomModel = MovEquipVisitTercRoomModel(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMov.INPUT,
                idVisitTercMovEquipVisitTerc = 1,
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
            movEquipVisitTercDao.insert(roomModel)
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTercPassag = 1,
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 2
                )
            )
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTercPassag = 2,
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 3
                )
            )
            val tercRoomModel1 = TerceiroRoomModel(
                idTerceiro = 1,
                idBDTerceiro = 1,
                cpfTerceiro = "326.949.728-88",
                nomeTerceiro = "TESTE",
                empresaTerceiro = "EMPRESA TESTE"
            )
            val tercRoomModel2 = TerceiroRoomModel(
                idTerceiro = 2,
                idBDTerceiro = 1,
                cpfTerceiro = "326.949.728-88",
                nomeTerceiro = "TESTE",
                empresaTerceiro = "EMPRESA TESTE 2"
            )
            val tercRoomModel3 = TerceiroRoomModel(
                idTerceiro = 3,
                idBDTerceiro = 2,
                cpfTerceiro = "123.456.789-00",
                nomeTerceiro = "TESTE 2",
                empresaTerceiro = "EMPRESA TESTE 3"
            )
            val tercRoomModel4 = TerceiroRoomModel(
                idTerceiro = 4,
                idBDTerceiro = 3,
                cpfTerceiro = "123.456.789-99",
                nomeTerceiro = "TESTE 3",
                empresaTerceiro = "EMPRESA TESTE 3"
            )
            terceiroDao.insertAll(
                listOf(
                    tercRoomModel1,
                    tercRoomModel2,
                    tercRoomModel3,
                    tercRoomModel4
                )
            )
            val result = usecase(
                id = 1
            )
            assertTrue(result.isSuccess)
            val entity = result.getOrNull()!!
            assertEquals(
                entity.placa,
                "PLACA TESTE"
            )
            assertEquals(
                entity.passageiro,
                "123.456.789-00 - TESTE 2; 123.456.789-99 - TESTE 3;"
            )
            assertEquals(
                entity.motorista,
                "326.949.728-88 - TESTE"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_visit() = runTest {
        val roomModel = MovEquipVisitTercRoomModel(
            idMovEquipVisitTerc = 1,
            nroMatricVigiaMovEquipVisitTerc = 19759,
            idLocalMovEquipVisitTerc = 1,
            tipoMovEquipVisitTerc = TypeMov.INPUT,
            idVisitTercMovEquipVisitTerc = 1000,
            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.VISITANTE,
            dthrMovEquipVisitTerc = 1723213270250,
            veiculoMovEquipVisitTerc = "VEICULO TESTE",
            placaMovEquipVisitTerc = "PLACA TESTE",
            destinoMovEquipVisitTerc = "DESTINO TESTE",
            observMovEquipVisitTerc = "OBSERV TESTE",
            statusMovEquipVisitTerc = StatusData.OPEN,
            statusSendMovEquipVisitTerc = StatusSend.SEND,
            statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
        )
        movEquipVisitTercDao.insert(roomModel)
        val result = usecase(
            id = 1
        )
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> VisitanteRepositoryImpl.get"
        )
    }

    @Test
    fun check_return_detalhe_without_passag_if_get_detalhe_execute_success_without_passag_and_visitante() =
        runTest {
            val roomModel = MovEquipVisitTercRoomModel(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMov.INPUT,
                idVisitTercMovEquipVisitTerc = 1,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.VISITANTE,
                dthrMovEquipVisitTerc = 1723213270250,
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
            )
            movEquipVisitTercDao.insert(roomModel)
            val visitRoomModel = VisitanteRoomModel(
                idVisitante = 1,
                cpfVisitante = "326.949.728-88",
                nomeVisitante = "TESTE",
                empresaVisitante = "EMPRESA TESTE"
            )
            visitanteDao.insertAll(listOf(visitRoomModel))
            val result = usecase(
                id = 1
            )
            assertTrue(result.isSuccess)
            val entity = result.getOrNull()!!
            assertEquals(
                entity.placa,
                "PLACA TESTE"
            )
            assertEquals(
                entity.passageiro,
                ""
            )
            assertEquals(
                entity.motorista,
                "326.949.728-88 - TESTE"
            )
        }

    @Test
    fun check_return_detalhe_with_passag_if_get_detalhe_execute_success_with_passag_and_visit() =
        runTest {
            val roomModel = MovEquipVisitTercRoomModel(
                idMovEquipVisitTerc = 1,
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMov.INPUT,
                idVisitTercMovEquipVisitTerc = 1,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.VISITANTE,
                dthrMovEquipVisitTerc = 1723213270250,
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
            )
            movEquipVisitTercDao.insert(roomModel)
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTercPassag = 1,
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 2
                )
            )
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTercPassag = 2,
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 3
                )
            )
            val visitRoomModel1 = VisitanteRoomModel(
                idVisitante = 1,
                cpfVisitante = "326.949.728-88",
                nomeVisitante = "TESTE",
                empresaVisitante = "EMPRESA TESTE"
            )
            val visitRoomModel2 = VisitanteRoomModel(
                idVisitante = 2,
                cpfVisitante = "123.456.789-99",
                nomeVisitante = "TESTE 2",
                empresaVisitante = "EMPRESA TESTE 2"
            )
            val visitRoomModel3 = VisitanteRoomModel(
                idVisitante = 3,
                cpfVisitante = "123.456.789-00",
                nomeVisitante = "TESTE 3",
                empresaVisitante = "EMPRESA TESTE 3"
            )
            visitanteDao.insertAll(
                listOf(
                    visitRoomModel1,
                    visitRoomModel2,
                    visitRoomModel3
                )
            )
            val result = usecase(
                id = 1
            )
            assertTrue(result.isSuccess)
            val entity = result.getOrNull()!!
            assertEquals(
                entity.placa,
                "PLACA TESTE"
            )
            assertEquals(
                entity.passageiro,
                "123.456.789-99 - TESTE 2; 123.456.789-00 - TESTE 3;"
            )
            assertEquals(
                entity.motorista,
                "326.949.728-88 - TESTE"
            )
        }
}