package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipVisitTercDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipVisitTercSharedPreferencesDatasource
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

class SetPlacaVisitTercImplTest : KoinTest {

    private val usecase: SetPlacaVisitTerc by inject()
    private val movEquipVisitTercSharedPreferencesDatasource:
            MovEquipVisitTercSharedPreferencesDatasource by inject()
    private val movEquipVisitTercDao: MovEquipVisitTercDao by inject()

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
                placa = "Teste Placa",
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
                placa = "Teste Placa",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercRoomDatasourceImpl.setPlaca"
            )
        }

    @Test
    fun check_return_true_if_set_placa_execute_success_and_flow_add() =
        runTest {
            movEquipVisitTercSharedPreferencesDatasource.start(
                MovEquipVisitTercSharedPreferencesModel(
                    placaMovEquipVisitTerc = "Teste",
                )
            )
            val resultGetBefore = movEquipVisitTercSharedPreferencesDatasource.get()
            assertTrue(resultGetBefore.isSuccess)
            val entityBefore = resultGetBefore.getOrNull()!!
            assertEquals(entityBefore.placaMovEquipVisitTerc, "Teste")
            val result = usecase(
                placa = "Teste Placa",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
            val resultGetAfter = movEquipVisitTercSharedPreferencesDatasource.get()
            assertTrue(resultGetAfter.isSuccess)
            val entityAfter = resultGetAfter.getOrNull()!!
            assertEquals(entityAfter.placaMovEquipVisitTerc, "Teste Placa")
        }

    @Test
    fun check_return_true_if_set_placa_execute_success_and_flow_change() =
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
                    statusSendMovEquipVisitTerc = StatusSend.SENT,
                    statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
                )
            )
            val entityBefore = movEquipVisitTercDao.get(1)
            assertEquals(entityBefore.placaMovEquipVisitTerc, "PLACA TESTE")
            val result = usecase(
                placa = "Teste Placa",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
            val entityAfter = movEquipVisitTercDao.get(1)
            assertEquals(entityAfter.placaMovEquipVisitTerc, "Teste Placa")
        }
}