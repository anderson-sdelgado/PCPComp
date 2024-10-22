package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.external.room.dao.stable.TerceiroDao
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.VisitanteDao
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipVisitTercDao
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipVisitTercPassagDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipVisitTercPassagSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipVisitTercSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.TerceiroRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.VisitanteRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipVisitTercPassagRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipVisitTercRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.MovEquipVisitTercSharedPreferencesModel
import br.com.usinasantafe.pcpcomp.utils.FlowApp
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

class GetPassagVisitTercListImplTest : KoinTest {

    private val usecase: GetPassagVisitTercList by inject()
    private val movEquipVisitTercPassagSharedPreferencesDatasource:
            MovEquipVisitTercPassagSharedPreferencesDatasource by inject()
    private val movEquipVisitTercSharedPreferencesDatasource:
            MovEquipVisitTercSharedPreferencesDatasource by inject()
    private val visitanteDao: VisitanteDao by inject()
    private val terceiroDao: TerceiroDao by inject()
    private val movEquipVisitTercPassagDao:
            MovEquipVisitTercPassagDao by inject()
    private val movEquipVisitTercDao:
            MovEquipVisitTercDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_list_empty_if_not_have_data() =
        runTest {
            val result = usecase(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isSuccess)
            val list = result.getOrNull()!!
            assertEquals(list.size, 0)
        }

    @Test
    fun check_return_failure_if_have_passag_but_not_have_type_visit_terc_and_flow_add() =
        runTest {
            movEquipVisitTercPassagSharedPreferencesDatasource.add(10)
            movEquipVisitTercPassagSharedPreferencesDatasource.add(20)
            movEquipVisitTercPassagSharedPreferencesDatasource.add(30)
            val result = usecase(
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
    fun check_return_failure_if_have_passag_and_have_type_visit_but_not_have_visit_and_flow_add() =
        runTest {
            movEquipVisitTercPassagSharedPreferencesDatasource.add(10)
            movEquipVisitTercPassagSharedPreferencesDatasource.add(20)
            movEquipVisitTercPassagSharedPreferencesDatasource.add(30)
            movEquipVisitTercSharedPreferencesDatasource.start(
                MovEquipVisitTercSharedPreferencesModel(
                    tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.VISITANTE
                )
            )
            val result = usecase(
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> VisitanteRepositoryImpl.getCpf"
            )
        }

    @Test
    fun check_return_failure_if_have_passag_and_have_type_visit_terc_but_not_have_all_visit_and_flow_add() =
        runTest {
            movEquipVisitTercPassagSharedPreferencesDatasource.add(10)
            movEquipVisitTercPassagSharedPreferencesDatasource.add(20)
            movEquipVisitTercPassagSharedPreferencesDatasource.add(30)
            movEquipVisitTercSharedPreferencesDatasource.start(
                MovEquipVisitTercSharedPreferencesModel(
                    tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.VISITANTE
                )
            )
            visitanteDao.insertAll(
                listOf(
                    VisitanteRoomModel(
                        idVisitante = 10,
                        cpfVisitante = "123.456.789-00",
                        nomeVisitante = "Visitante 1",
                        empresaVisitante = "Empresa 1"
                    ),
                    VisitanteRoomModel(
                        idVisitante = 20,
                        cpfVisitante = "123.456.789-00",
                        nomeVisitante = "Visitante 2",
                        empresaVisitante = "Empresa 2"
                    )
                )
            )
            val result = usecase(
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> VisitanteRepositoryImpl.getCpf"
            )
        }

    @Test
    fun check_return_list_if_get_passag_execute_successfully_and_flow_add_and_visit() =
        runTest {
            movEquipVisitTercPassagSharedPreferencesDatasource.add(10)
            movEquipVisitTercPassagSharedPreferencesDatasource.add(20)
            movEquipVisitTercPassagSharedPreferencesDatasource.add(30)
            movEquipVisitTercSharedPreferencesDatasource.start(
                MovEquipVisitTercSharedPreferencesModel(
                    tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.VISITANTE
                )
            )
            visitanteDao.insertAll(
                listOf(
                    VisitanteRoomModel(
                        idVisitante = 10,
                        cpfVisitante = "123.456.789-01",
                        nomeVisitante = "Visitante 1",
                        empresaVisitante = "Empresa 1"
                    ),
                    VisitanteRoomModel(
                        idVisitante = 20,
                        cpfVisitante = "123.456.789-02",
                        nomeVisitante = "Visitante 2",
                        empresaVisitante = "Empresa 2"
                    ),
                    VisitanteRoomModel(
                        idVisitante = 30,
                        cpfVisitante = "123.456.789-03",
                        nomeVisitante = "Visitante 3",
                        empresaVisitante = "Empresa 3"
                    )
                )
            )
            val result = usecase(
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            val list = result.getOrNull()!!
            assertEquals(list.size, 3)
            assertEquals(list[0].id, 10)
            assertEquals(list[1].id, 20)
            assertEquals(list[0].nome, "Visitante 1")
            assertEquals(list[2].cpf, "123.456.789-03")
        }

    @Test
    fun check_return_failure_if_have_passag_and_have_type_terc_visit_but_not_have_terc_and_flow_add() =
        runTest {
            movEquipVisitTercPassagSharedPreferencesDatasource.add(10)
            movEquipVisitTercPassagSharedPreferencesDatasource.add(20)
            movEquipVisitTercPassagSharedPreferencesDatasource.add(30)
            movEquipVisitTercSharedPreferencesDatasource.start(
                MovEquipVisitTercSharedPreferencesModel(
                    tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO
                )
            )
            val result = usecase(
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> TerceiroRepositoryImpl.getCpf"
            )
        }

    @Test
    fun check_return_failure_if_have_passag_and_have_type_visit_terc_but_not_have_all_terc_and_flow_add() =
        runTest {
            movEquipVisitTercPassagSharedPreferencesDatasource.add(10)
            movEquipVisitTercPassagSharedPreferencesDatasource.add(20)
            movEquipVisitTercPassagSharedPreferencesDatasource.add(30)
            movEquipVisitTercSharedPreferencesDatasource.start(
                MovEquipVisitTercSharedPreferencesModel(
                    tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO
                )
            )
            terceiroDao.insertAll(
                listOf(
                    TerceiroRoomModel(
                        idTerceiro = 10,
                        idBDTerceiro = 10,
                        cpfTerceiro = "123.456.789-00",
                        nomeTerceiro = "Terceiro 1",
                        empresaTerceiro = "Empresa 1"
                    ),
                    TerceiroRoomModel(
                        idTerceiro = 20,
                        idBDTerceiro = 20,
                        cpfTerceiro = "123.456.789-00",
                        nomeTerceiro = "Terceiro 2",
                        empresaTerceiro = "Empresa 2"
                    )
                )
            )
            val result = usecase(
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> TerceiroRepositoryImpl.getCpf"
            )
        }

    @Test
    fun check_return_list_if_get_passag_execute_successfully_and_flow_add_and_terc() =
        runTest {
            movEquipVisitTercPassagSharedPreferencesDatasource.add(10)
            movEquipVisitTercPassagSharedPreferencesDatasource.add(20)
            movEquipVisitTercPassagSharedPreferencesDatasource.add(30)
            movEquipVisitTercSharedPreferencesDatasource.start(
                MovEquipVisitTercSharedPreferencesModel(
                    tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.VISITANTE
                )
            )
            visitanteDao.insertAll(
                listOf(
                    VisitanteRoomModel(
                        idVisitante = 10,
                        cpfVisitante = "123.456.789-01",
                        nomeVisitante = "Visitante 1",
                        empresaVisitante = "Empresa 1"
                    ),
                    VisitanteRoomModel(
                        idVisitante = 20,
                        cpfVisitante = "123.456.789-02",
                        nomeVisitante = "Visitante 2",
                        empresaVisitante = "Empresa 2"
                    ),
                    VisitanteRoomModel(
                        idVisitante = 30,
                        cpfVisitante = "123.456.789-03",
                        nomeVisitante = "Visitante 3",
                        empresaVisitante = "Empresa 3"
                    )
                )
            )
            val result = usecase(
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            val list = result.getOrNull()!!
            assertEquals(list.size, 3)
            assertEquals(list[0].id, 10)
            assertEquals(list[1].id, 20)
            assertEquals(list[0].nome, "Visitante 1")
            assertEquals(list[2].cpf, "123.456.789-03")
        }

    @Test
    fun check_return_failure_if_have_passag_but_not_have_type_visit_terc_and_flow_change() =
        runTest {
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTercPassag = 1,
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 10
                )
            )
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTercPassag = 2,
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 20
                )
            )
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTercPassag = 3,
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 30
                )
            )
            val result = usecase(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Usecase -> GetPassagVisitTercListImpl"
            )
        }

    @Test
    fun check_return_failure_if_have_passag_and_have_type_visit_but_not_have_visit_and_flow_change() =
        runTest {
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTercPassag = 1,
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 10
                )
            )
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTercPassag = 2,
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 20
                )
            )
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTercPassag = 3,
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 30
                )
            )
            movEquipVisitTercDao.insert(
                MovEquipVisitTercRoomModel(
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
            )
            val result = usecase(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> VisitanteRepositoryImpl.getCpf"
            )
        }

    @Test
    fun check_return_failure_if_have_passag_and_have_type_visit_terc_but_not_have_all_visit_and_flow_change() =
        runTest {
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTercPassag = 1,
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 10
                )
            )
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTercPassag = 2,
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 20
                )
            )
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTercPassag = 3,
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 30
                )
            )
            movEquipVisitTercDao.insert(
                MovEquipVisitTercRoomModel(
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
            )
            visitanteDao.insertAll(
                listOf(
                    VisitanteRoomModel(
                        idVisitante = 10,
                        cpfVisitante = "123.456.789-00",
                        nomeVisitante = "Visitante 1",
                        empresaVisitante = "Empresa 1"
                    ),
                    VisitanteRoomModel(
                        idVisitante = 20,
                        cpfVisitante = "123.456.789-00",
                        nomeVisitante = "Visitante 2",
                        empresaVisitante = "Empresa 2"
                    )
                )
            )
            val result = usecase(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> VisitanteRepositoryImpl.getCpf"
            )
        }

    @Test
    fun check_return_list_if_get_passag_execute_successfully_and_flow_change_and_visit() =
        runTest {
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTercPassag = 1,
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 10
                )
            )
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTercPassag = 2,
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 20
                )
            )
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTercPassag = 3,
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 30
                )
            )
            movEquipVisitTercDao.insert(
                MovEquipVisitTercRoomModel(
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
            )
            visitanteDao.insertAll(
                listOf(
                    VisitanteRoomModel(
                        idVisitante = 10,
                        cpfVisitante = "123.456.789-01",
                        nomeVisitante = "Visitante 1",
                        empresaVisitante = "Empresa 1"
                    ),
                    VisitanteRoomModel(
                        idVisitante = 20,
                        cpfVisitante = "123.456.789-02",
                        nomeVisitante = "Visitante 2",
                        empresaVisitante = "Empresa 2"
                    ),
                    VisitanteRoomModel(
                        idVisitante = 30,
                        cpfVisitante = "123.456.789-03",
                        nomeVisitante = "Visitante 3",
                        empresaVisitante = "Empresa 3"
                    )
                )
            )
            val result = usecase(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isSuccess)
            val list = result.getOrNull()!!
            assertEquals(list.size, 3)
            assertEquals(list[0].id, 10)
            assertEquals(list[1].id, 20)
            assertEquals(list[0].nome, "Visitante 1")
            assertEquals(list[2].cpf, "123.456.789-03")
        }

    @Test
    fun check_return_failure_if_have_passag_and_have_type_terc_visit_but_not_have_terc_and_flow_change() =
        runTest {
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTercPassag = 1,
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 10
                )
            )
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTercPassag = 2,
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 20
                )
            )
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTercPassag = 3,
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 30
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
                    statusMovEquipVisitTerc = StatusData.OPEN,
                    statusSendMovEquipVisitTerc = StatusSend.SEND,
                    statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
                )
            )
            val result = usecase(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> TerceiroRepositoryImpl.getCpf"
            )
        }

    @Test
    fun check_return_failure_if_have_passag_and_have_type_visit_terc_but_not_have_all_terc_and_flow_change() =
        runTest {
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTercPassag = 1,
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 10
                )
            )
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTercPassag = 2,
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 20
                )
            )
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTercPassag = 3,
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 30
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
                    statusMovEquipVisitTerc = StatusData.OPEN,
                    statusSendMovEquipVisitTerc = StatusSend.SEND,
                    statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
                )
            )
            terceiroDao.insertAll(
                listOf(
                    TerceiroRoomModel(
                        idTerceiro = 10,
                        idBDTerceiro = 10,
                        cpfTerceiro = "123.456.789-00",
                        nomeTerceiro = "Terceiro 1",
                        empresaTerceiro = "Empresa 1"
                    ),
                    TerceiroRoomModel(
                        idTerceiro = 20,
                        idBDTerceiro = 20,
                        cpfTerceiro = "123.456.789-00",
                        nomeTerceiro = "Terceiro 2",
                        empresaTerceiro = "Empresa 2"
                    )
                )
            )
            val result = usecase(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> TerceiroRepositoryImpl.getCpf"
            )
        }

    @Test
    fun check_return_list_if_get_passag_execute_successfully_and_flow_change_and_terc() =
        runTest {
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTercPassag = 1,
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 10
                )
            )
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTercPassag = 2,
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 20
                )
            )
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTercPassag = 3,
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 30
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
                    statusMovEquipVisitTerc = StatusData.OPEN,
                    statusSendMovEquipVisitTerc = StatusSend.SEND,
                    statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
                )
            )
            terceiroDao.insertAll(
                listOf(
                    TerceiroRoomModel(
                        idTerceiro = 10,
                        idBDTerceiro = 10,
                        cpfTerceiro = "123.456.789-01",
                        nomeTerceiro = "Terceiro 1",
                        empresaTerceiro = "Empresa 1"
                    ),
                    TerceiroRoomModel(
                        idTerceiro = 20,
                        idBDTerceiro = 20,
                        cpfTerceiro = "123.456.789-02",
                        nomeTerceiro = "Terceiro 2",
                        empresaTerceiro = "Empresa 2"
                    ),
                    TerceiroRoomModel(
                        idTerceiro = 30,
                        idBDTerceiro = 30,
                        cpfTerceiro = "123.456.789-03",
                        nomeTerceiro = "Terceiro 3",
                        empresaTerceiro = "Empresa 3"
                    )
                )
            )
            val result = usecase(
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isSuccess)
            val list = result.getOrNull()!!
            assertEquals(list.size, 3)
            assertEquals(list[0].id, 10)
            assertEquals(list[1].id, 20)
            assertEquals(list[0].nome, "Terceiro 1")
            assertEquals(list[2].cpf, "123.456.789-03")
        }

}