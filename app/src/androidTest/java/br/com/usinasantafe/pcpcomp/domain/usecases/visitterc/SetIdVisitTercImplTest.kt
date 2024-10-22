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
import br.com.usinasantafe.pcpcomp.utils.TypeOcupante
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class SetIdVisitTercImplTest : KoinTest {
    
    private val usecase : SetIdVisitTerc by inject()
    private val movEquipVisitTercSharedPreferencesDatasource:
            MovEquipVisitTercSharedPreferencesDatasource by inject()
    private val movEquipVisitTercPassagSharedPreferencesDatasource:
            MovEquipVisitTercPassagSharedPreferencesDatasource by inject()
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
    fun check_return_failure_if_not_have_data_and_flow_add_and_type_motorista() =
        runTest {
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.MOTORISTA,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercSharedPreferencesDatasourceImpl.get"
            )
        }

    @Test
    fun check_return_failure_if_have_mov_and_without_visit_and_flow_add_and_type_motorista() =
        runTest {
            movEquipVisitTercSharedPreferencesDatasource.start(
                MovEquipVisitTercSharedPreferencesModel(
                    tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.VISITANTE
                )
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.MOTORISTA,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> VisitanteRepositoryImpl.getId"
            )
        }

    @Test
    fun check_return_failure_if_have_mov_and_without_terc_and_flow_add_and_type_motorista() =
        runTest {
            movEquipVisitTercSharedPreferencesDatasource.start(
                MovEquipVisitTercSharedPreferencesModel(
                    tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO
                )
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.MOTORISTA,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> TerceiroRepositoryImpl.getId"
            )
        }

    @Test
    fun check_return_true_if_set_id_executes_correctly_and_type_visit_and_flow_add_and_type_motorista() =
        runTest {
            movEquipVisitTercSharedPreferencesDatasource.start(
                MovEquipVisitTercSharedPreferencesModel(
                    tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.VISITANTE,
                    idVisitTercMovEquipVisitTerc = 10
                )
            )
            visitanteDao.insertAll(
                listOf(
                    VisitanteRoomModel(
                        idVisitante = 20,
                        nomeVisitante = "Visitante Teste",
                        cpfVisitante = "123.456.789-00",
                        empresaVisitante = "Empresa Teste"
                    )
                )
            )
            val resultGetBefore = movEquipVisitTercSharedPreferencesDatasource.get()
            assertTrue(resultGetBefore.isSuccess)
            val entityBefore = resultGetBefore.getOrNull()!!
            assertEquals(entityBefore.idVisitTercMovEquipVisitTerc, 10)
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.MOTORISTA,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
            val resultGetAfter = movEquipVisitTercSharedPreferencesDatasource.get()
            assertTrue(resultGetAfter.isSuccess)
            val entityAfter = resultGetAfter.getOrNull()!!
            assertEquals(entityAfter.idVisitTercMovEquipVisitTerc, 20)
        }

    @Test
    fun check_return_true_if_set_id_executes_correctly_and_type_terc_and_flow_add_and_type_motorista() =
        runTest {
            movEquipVisitTercSharedPreferencesDatasource.start(
                MovEquipVisitTercSharedPreferencesModel(
                    tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                    idVisitTercMovEquipVisitTerc = 10
                )
            )
            terceiroDao.insertAll(
                listOf(
                    TerceiroRoomModel(
                        idTerceiro = 2,
                        idBDTerceiro = 20,
                        nomeTerceiro = "Terceiro Teste",
                        cpfTerceiro = "123.456.789-00",
                        empresaTerceiro = "Empresa Teste"
                    )
                )
            )
            val resultGetBefore = movEquipVisitTercSharedPreferencesDatasource.get()
            assertTrue(resultGetBefore.isSuccess)
            val entityBefore = resultGetBefore.getOrNull()!!
            assertEquals(entityBefore.idVisitTercMovEquipVisitTerc, 10)
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.MOTORISTA,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
            val resultGetAfter = movEquipVisitTercSharedPreferencesDatasource.get()
            assertTrue(resultGetAfter.isSuccess)
            val entityAfter = resultGetAfter.getOrNull()!!
            assertEquals(entityAfter.idVisitTercMovEquipVisitTerc, 20)
        }

    @Test
    fun check_return_failure_if_not_have_data_and_flow_add_and_type_passag() =
        runTest {
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.PASSAGEIRO,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercSharedPreferencesDatasourceImpl.get"
            )
        }

    @Test
    fun check_return_failure_if_have_mov_and_without_visit_and_flow_add_and_type_passag() =
        runTest {
            movEquipVisitTercSharedPreferencesDatasource.start(
                MovEquipVisitTercSharedPreferencesModel(
                    tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.VISITANTE
                )
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.PASSAGEIRO,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> VisitanteRepositoryImpl.getId"
            )
        }

    @Test
    fun check_return_failure_if_have_mov_and_without_terc_and_flow_add_and_type_passag() =
        runTest {
            movEquipVisitTercSharedPreferencesDatasource.start(
                MovEquipVisitTercSharedPreferencesModel(
                    tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO
                )
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.PASSAGEIRO,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> TerceiroRepositoryImpl.getId"
            )
        }

    @Test
    fun check_return_true_if_set_id_executes_correctly_and_type_visit_and_flow_add_and_type_passag() =
        runTest {
            movEquipVisitTercPassagSharedPreferencesDatasource.add(20)
            movEquipVisitTercSharedPreferencesDatasource.start(
                MovEquipVisitTercSharedPreferencesModel(
                    tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.VISITANTE,
                    idVisitTercMovEquipVisitTerc = 10
                )
            )
            visitanteDao.insertAll(
                listOf(
                    VisitanteRoomModel(
                        idVisitante = 30,
                        nomeVisitante = "Visitante Teste",
                        cpfVisitante = "123.456.789-00",
                        empresaVisitante = "Empresa Teste"
                    )
                )
            )
            val resultListBefore = movEquipVisitTercPassagSharedPreferencesDatasource.list()
            assertTrue(resultListBefore.isSuccess)
            val listBefore = resultListBefore.getOrNull()!!
            assertEquals(listBefore.size, 1)
            assertEquals(listBefore[0], 20)
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.PASSAGEIRO,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
            val resultListAfter = movEquipVisitTercPassagSharedPreferencesDatasource.list()
            assertTrue(resultListAfter.isSuccess)
            val listAfter = resultListAfter.getOrNull()!!
            assertEquals(listAfter.size, 2)
            assertEquals(listAfter[0], 20)
            assertEquals(listAfter[1], 30)
        }

    @Test
    fun check_return_true_if_set_id_executes_correctly_and_type_terc_and_flow_add_and_type_passag() =
        runTest {
            movEquipVisitTercPassagSharedPreferencesDatasource.add(20)
            movEquipVisitTercSharedPreferencesDatasource.start(
                MovEquipVisitTercSharedPreferencesModel(
                    tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                    idVisitTercMovEquipVisitTerc = 10
                )
            )
            terceiroDao.insertAll(
                listOf(
                    TerceiroRoomModel(
                        idTerceiro = 2,
                        idBDTerceiro = 30,
                        nomeTerceiro = "Terceiro Teste",
                        cpfTerceiro = "123.456.789-00",
                        empresaTerceiro = "Empresa Teste"
                    )
                )
            )
            val resultListBefore = movEquipVisitTercPassagSharedPreferencesDatasource.list()
            assertTrue(resultListBefore.isSuccess)
            val listBefore = resultListBefore.getOrNull()!!
            assertEquals(listBefore.size, 1)
            assertEquals(listBefore[0], 20)
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.PASSAGEIRO,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
            val resultListAfter = movEquipVisitTercPassagSharedPreferencesDatasource.list()
            assertTrue(resultListAfter.isSuccess)
            val listAfter = resultListAfter.getOrNull()!!
            assertEquals(listAfter.size, 2)
            assertEquals(listAfter[0], 20)
            assertEquals(listAfter[1], 30)
        }

    ///////////////////////////////////////////////

    @Test
    fun check_return_failure_if_not_have_data_and_flow_change_and_type_motorista() =
        runTest {
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.CHANGE,
                typeOcupante = TypeOcupante.MOTORISTA,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Usecase -> SetIdVisitTerc"
            )
        }

    @Test
    fun check_return_failure_if_have_mov_and_without_visit_and_flow_change_and_type_motorista() =
        runTest {
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
                cpf = "123.456.789-00",
                flowApp = FlowApp.CHANGE,
                typeOcupante = TypeOcupante.MOTORISTA,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> VisitanteRepositoryImpl.getId"
            )
        }

    @Test
    fun check_return_failure_if_have_mov_and_without_terc_and_flow_change_and_type_motorista() =
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
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.CHANGE,
                typeOcupante = TypeOcupante.MOTORISTA,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> TerceiroRepositoryImpl.getId"
            )
        }

    @Test
    fun check_return_true_if_set_id_executes_correctly_and_type_visit_and_flow_change_and_type_motorista() =
        runTest {
            movEquipVisitTercDao.insert(
                MovEquipVisitTercRoomModel(
                    idMovEquipVisitTerc = 1,
                    nroMatricVigiaMovEquipVisitTerc = 19759,
                    idLocalMovEquipVisitTerc = 1,
                    tipoMovEquipVisitTerc = TypeMov.INPUT,
                    idVisitTercMovEquipVisitTerc = 10,
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
                        idVisitante = 20,
                        nomeVisitante = "Visitante Teste",
                        cpfVisitante = "123.456.789-00",
                        empresaVisitante = "Empresa Teste"
                    )
                )
            )
            val entityBefore = movEquipVisitTercDao.get(1)
            assertEquals(entityBefore.idVisitTercMovEquipVisitTerc, 10)
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.CHANGE,
                typeOcupante = TypeOcupante.MOTORISTA,
                id = 1
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
            val entityAfter = movEquipVisitTercDao.get(1)
            assertEquals(entityAfter.idVisitTercMovEquipVisitTerc, 20)
        }

    @Test
    fun check_return_true_if_set_id_executes_correctly_and_type_terc_and_flow_change_and_type_motorista() =
        runTest {
            movEquipVisitTercDao.insert(
                MovEquipVisitTercRoomModel(
                    idMovEquipVisitTerc = 1,
                    nroMatricVigiaMovEquipVisitTerc = 19759,
                    idLocalMovEquipVisitTerc = 1,
                    tipoMovEquipVisitTerc = TypeMov.INPUT,
                    idVisitTercMovEquipVisitTerc = 10,
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
                        idTerceiro = 2,
                        idBDTerceiro = 20,
                        nomeTerceiro = "Terceiro Teste",
                        cpfTerceiro = "123.456.789-00",
                        empresaTerceiro = "Empresa Teste"
                    )
                )
            )
            val entityBefore = movEquipVisitTercDao.get(1)
            assertEquals(entityBefore.idVisitTercMovEquipVisitTerc, 10)
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.CHANGE,
                typeOcupante = TypeOcupante.MOTORISTA,
                id = 1
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
            val entityAfter = movEquipVisitTercDao.get(1)
            assertEquals(entityAfter.idVisitTercMovEquipVisitTerc, 20)
        }

    @Test
    fun check_return_failure_if_not_have_data_and_flow_change_and_type_passag() =
        runTest {
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.CHANGE,
                typeOcupante = TypeOcupante.PASSAGEIRO,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Usecase -> SetIdVisitTerc"
            )
        }

    @Test
    fun check_return_failure_if_have_mov_and_without_visit_and_flow_change_and_type_passag() =
        runTest {
            movEquipVisitTercDao.insert(
                MovEquipVisitTercRoomModel(
                    idMovEquipVisitTerc = 1,
                    nroMatricVigiaMovEquipVisitTerc = 19759,
                    idLocalMovEquipVisitTerc = 1,
                    tipoMovEquipVisitTerc = TypeMov.INPUT,
                    idVisitTercMovEquipVisitTerc = 10,
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
                typeOcupante = TypeOcupante.PASSAGEIRO,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> VisitanteRepositoryImpl.getId"
            )
        }

    @Test
    fun check_return_failure_if_have_mov_and_without_terc_and_flow_change_and_type_passag() =
        runTest {
            movEquipVisitTercDao.insert(
                MovEquipVisitTercRoomModel(
                    idMovEquipVisitTerc = 1,
                    nroMatricVigiaMovEquipVisitTerc = 19759,
                    idLocalMovEquipVisitTerc = 1,
                    tipoMovEquipVisitTerc = TypeMov.INPUT,
                    idVisitTercMovEquipVisitTerc = 10,
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
                typeOcupante = TypeOcupante.PASSAGEIRO,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> TerceiroRepositoryImpl.getId"
            )
        }

    @Test
    fun check_return_true_if_set_id_executes_correctly_and_type_visit_and_flow_change_and_type_passag() =
        runTest {
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTercPassag = 1,
                    idVisitTerc = 20,
                    idMovEquipVisitTerc = 1
                )
            )
            movEquipVisitTercDao.insert(
                MovEquipVisitTercRoomModel(
                    idMovEquipVisitTerc = 1,
                    nroMatricVigiaMovEquipVisitTerc = 19759,
                    idLocalMovEquipVisitTerc = 1,
                    tipoMovEquipVisitTerc = TypeMov.INPUT,
                    idVisitTercMovEquipVisitTerc = 10,
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
                        idVisitante = 30,
                        nomeVisitante = "Visitante Teste",
                        cpfVisitante = "123.456.789-00",
                        empresaVisitante = "Empresa Teste"
                    )
                )
            )
            val listBefore = movEquipVisitTercPassagDao.list(1)
            assertEquals(listBefore.size, 1)
            assertEquals(listBefore[0].idVisitTerc, 20)
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.CHANGE,
                typeOcupante = TypeOcupante.PASSAGEIRO,
                id = 1
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
            val listAfter = movEquipVisitTercPassagDao.list(1)
            assertEquals(listAfter.size, 2)
            assertEquals(listAfter[0].idVisitTerc, 20)
            assertEquals(listAfter[1].idVisitTerc, 30)
        }

    @Test
    fun check_return_true_if_set_id_executes_correctly_and_type_terc_and_flow_change_and_type_passag() =
        runTest {
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTercPassag = 1,
                    idVisitTerc = 20,
                    idMovEquipVisitTerc = 1
                )
            )
            movEquipVisitTercDao.insert(
                MovEquipVisitTercRoomModel(
                    idMovEquipVisitTerc = 1,
                    nroMatricVigiaMovEquipVisitTerc = 19759,
                    idLocalMovEquipVisitTerc = 1,
                    tipoMovEquipVisitTerc = TypeMov.INPUT,
                    idVisitTercMovEquipVisitTerc = 10,
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
                        idTerceiro = 2,
                        idBDTerceiro = 30,
                        nomeTerceiro = "Terceiro Teste",
                        cpfTerceiro = "123.456.789-00",
                        empresaTerceiro = "Empresa Teste"
                    )
                )
            )
            val listBefore = movEquipVisitTercPassagDao.list(1)
            assertEquals(listBefore.size, 1)
            assertEquals(listBefore[0].idVisitTerc, 20)
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.CHANGE,
                typeOcupante = TypeOcupante.PASSAGEIRO,
                id = 1
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
            val listAfter = movEquipVisitTercPassagDao.list(1)
            assertEquals(listAfter.size, 2)
            assertEquals(listAfter[0].idVisitTerc, 20)
            assertEquals(listAfter[1].idVisitTerc, 30)
        }

}