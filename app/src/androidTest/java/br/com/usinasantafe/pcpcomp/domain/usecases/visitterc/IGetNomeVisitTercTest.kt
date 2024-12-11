package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.external.room.dao.stable.TerceiroDao
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.VisitanteDao
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipVisitTercDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipVisitTercSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.TerceiroRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.VisitanteRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipVisitTercRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.MovEquipVisitTercSharedPreferencesModel
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMovEquip
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class IGetNomeVisitTercTest : KoinTest {

    private val usecase: GetNomeVisitTerc by inject()
    private val movEquipVisitTercSharedPreferencesDatasource:
            MovEquipVisitTercSharedPreferencesDatasource by inject()
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
    fun check_return_failure_if_not_have_data_and_flow_add() =
        runTest {
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercSharedPreferencesDatasourceImpl.get"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_and_flow_change() =
        runTest {
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Usecase -> GetNomeVisitTercImpl"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_visitante_and_flow_add() =
        runTest {
            movEquipVisitTercSharedPreferencesDatasource.start(
                MovEquipVisitTercSharedPreferencesModel(
                    tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.VISITANTE
                )
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> VisitanteRepositoryImpl.getNome"
            )
        }

    @Test
    fun check_return_model_if_usecase_execute_successfully_and_flow_add_and_visitante() =
        runTest {
            movEquipVisitTercSharedPreferencesDatasource.start(
                MovEquipVisitTercSharedPreferencesModel(
                    tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.VISITANTE
                )
            )
            visitanteDao.insertAll(
                listOf(
                    VisitanteRoomModel(
                        idVisitante = 1,
                        cpfVisitante = "123.456.789-00",
                        nomeVisitante = "NOME TESTE",
                        empresaVisitante = "EMPRESA TESTE"
                    )
                )
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            val model = result.getOrNull()!!
            assertEquals(model.tipo, "VISITANTE")
            assertEquals(model.nome, "NOME TESTE")
            assertEquals(model.empresa, "EMPRESA TESTE")
        }

    @Test
    fun check_return_failure_if_not_have_data_in_terceiro_and_flow_add() =
        runTest {
            movEquipVisitTercSharedPreferencesDatasource.start(
                MovEquipVisitTercSharedPreferencesModel(
                    tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO
                )
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> TerceiroRepositoryImpl.getNome"
            )
        }

    @Test
    fun check_return_model_if_usecase_execute_successfully_and_flow_add_and_terceiro() =
        runTest {
            movEquipVisitTercSharedPreferencesDatasource.start(
                MovEquipVisitTercSharedPreferencesModel(
                    tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO
                )
            )
            terceiroDao.insertAll(
                listOf(
                    TerceiroRoomModel(
                        idTerceiro = 1,
                        idBDTerceiro = 1,
                        cpfTerceiro = "123.456.789-00",
                        nomeTerceiro = "NOME TESTE",
                        empresaTerceiro = "EMPRESA TESTE"
                    ),
                    TerceiroRoomModel(
                        idTerceiro = 2,
                        idBDTerceiro = 1,
                        cpfTerceiro = "123.456.789-00",
                        nomeTerceiro = "NOME TESTE",
                        empresaTerceiro = "EMPRESA TESTE 2"
                    )
                )
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            val model = result.getOrNull()!!
            assertEquals(model.tipo, "TERCEIRO")
            assertEquals(model.nome, "NOME TESTE")
            assertEquals(model.empresa, "EMPRESA TESTE\nEMPRESA TESTE 2")
        }

    @Test
    fun check_return_failure_if_not_have_data_in_visitante_and_flow_change() =
        runTest {
            movEquipVisitTercDao.insert(
                MovEquipVisitTercRoomModel(
                    idMovEquipVisitTerc = 1,
                    nroMatricVigiaMovEquipVisitTerc = 19759,
                    idLocalMovEquipVisitTerc = 1,
                    tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
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
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> VisitanteRepositoryImpl.getNome"
            )
        }

    @Test
    fun check_return_model_if_usecase_execute_successfully_and_flow_change_and_visitante() =
        runTest {
            movEquipVisitTercDao.insert(
                MovEquipVisitTercRoomModel(
                    idMovEquipVisitTerc = 1,
                    nroMatricVigiaMovEquipVisitTerc = 19759,
                    idLocalMovEquipVisitTerc = 1,
                    tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
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
            )
            visitanteDao.insertAll(
                listOf(
                    VisitanteRoomModel(
                        idVisitante = 1,
                        cpfVisitante = "123.456.789-00",
                        nomeVisitante = "NOME TESTE",
                        empresaVisitante = "EMPRESA TESTE"
                    )
                )
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isSuccess)
            val model = result.getOrNull()!!
            assertEquals(model.tipo, "VISITANTE")
            assertEquals(model.nome, "NOME TESTE")
            assertEquals(model.empresa, "EMPRESA TESTE")
        }

    @Test
    fun check_return_failure_if_not_have_data_in_terceiro_and_flow_change() =
        runTest {
            movEquipVisitTercDao.insert(
                MovEquipVisitTercRoomModel(
                    idMovEquipVisitTerc = 1,
                    nroMatricVigiaMovEquipVisitTerc = 19759,
                    idLocalMovEquipVisitTerc = 1,
                    tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
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
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> TerceiroRepositoryImpl.getNome"
            )
        }

    @Test
    fun check_return_model_if_usecase_execute_successfully_and_flow_change_and_terceiro() =
        runTest {
            movEquipVisitTercDao.insert(
                MovEquipVisitTercRoomModel(
                    idMovEquipVisitTerc = 1,
                    nroMatricVigiaMovEquipVisitTerc = 19759,
                    idLocalMovEquipVisitTerc = 1,
                    tipoMovEquipVisitTerc = TypeMovEquip.INPUT,
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
            )
            terceiroDao.insertAll(
                listOf(
                    TerceiroRoomModel(
                        idTerceiro = 1,
                        idBDTerceiro = 1,
                        cpfTerceiro = "123.456.789-00",
                        nomeTerceiro = "NOME TESTE",
                        empresaTerceiro = "EMPRESA TESTE"
                    ),
                    TerceiroRoomModel(
                        idTerceiro = 2,
                        idBDTerceiro = 1,
                        cpfTerceiro = "123.456.789-00",
                        nomeTerceiro = "NOME TESTE",
                        empresaTerceiro = "EMPRESA TESTE 2"
                    )
                )
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isSuccess)
            val model = result.getOrNull()!!
            assertEquals(model.tipo, "TERCEIRO")
            assertEquals(model.nome, "NOME TESTE")
            assertEquals(model.empresa, "EMPRESA TESTE\nEMPRESA TESTE 2")
        }
}