package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.external.room.dao.stable.TerceiroDao
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.VisitanteDao
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipVisitTercDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.TerceiroRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.VisitanteRoomModel
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

class IGetCpfVisitTercTest: KoinTest {

    private val usecase: GetCpfVisitTerc by inject()
    private val movEquipVisitTercDao: MovEquipVisitTercDao by inject()
    private val terceiroDao: TerceiroDao by inject()
    private val visitanteDao: VisitanteDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_failure_if_not_have_data() = runTest {
        val result = usecase(id = 1)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Usecase -> GetCpfVisitTercImpl"
        )
    }

    @Test
    fun check_return_failure_if_not_have_id_terc_in_table() = runTest {
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
        val result = usecase(id = 1)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> TerceiroRepositoryImpl.getCpf"
        )
    }

    @Test
    fun check_return_cpf_if_get_cpf_of_terc_execute_successfully() = runTest {
        val tercRoomModel = TerceiroRoomModel(
            idTerceiro = 1,
            idBDTerceiro = 1,
            cpfTerceiro = "326.949.728-88",
            nomeTerceiro = "TESTE",
            empresaTerceiro = "EMPRESA TESTE"
        )
        terceiroDao.insertAll(listOf(tercRoomModel))
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
        val result = usecase(id = 1)
        assertTrue(result.isSuccess)
        assertEquals(
            result.getOrNull()!!,
            "326.949.728-88"
        )
    }

    @Test
    fun check_return_failure_if_not_have_id_visit_in_table() = runTest {
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
        val result = usecase(id = 1)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> VisitanteRepositoryImpl.getCpf"
        )
    }

    @Test
    fun check_return_cpf_if_get_cpf_of_visit_execute_successfully() = runTest {
        val visitRoomModel = VisitanteRoomModel(
            idVisitante = 1,
            cpfVisitante = "123.456.789-00",
            nomeVisitante = "TESTE",
            empresaVisitante = "EMPRESA TESTE"
        )
        visitanteDao.insertAll(listOf(visitRoomModel))
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
        val result = usecase(id = 1)
        assertTrue(result.isSuccess)
        assertEquals(
            result.getOrNull()!!,
            "123.456.789-00"
        )
    }

}