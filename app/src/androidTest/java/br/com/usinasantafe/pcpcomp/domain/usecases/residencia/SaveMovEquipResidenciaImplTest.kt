package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipResidenciaDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipResidenciaSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipResidenciaRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.MovEquipResidenciaSharedPreferencesModel
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
import java.util.Date

class SaveMovEquipResidenciaImplTest : KoinTest {

    private val usecase: SaveMovEquipResidencia by inject()
    private val movEquipResidenciaDao: MovEquipResidenciaDao by inject()
    private val configSharedPreferencesDatasource:
            ConfigSharedPreferencesDatasource by inject()
    private val movEquipResidenciaSharedPreferencesDatasource:
            MovEquipResidenciaSharedPreferencesDatasource by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_failure_if_not_have_data_output() =
        runTest {
            val result = usecase(
                typeMov = TypeMov.OUTPUT,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipResidenciaRepositoryImpl.get"
            )
        }

    @Test
    fun check_return_failure_if_have_data_mov_and_not_have_config_and_output() =
        runTest {
            movEquipResidenciaDao.insert(
                MovEquipResidenciaRoomModel(
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
            val result = usecase(
                typeMov = TypeMov.OUTPUT,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Usecase -> SaveMovEquipResidenciaImpl"
            )
        }

    @Test
    fun check_return_failure_if_have_data_mov_config_and_without_mov_shared_preferences_and_output() =
        runTest {
            movEquipResidenciaDao.insert(
                MovEquipResidenciaRoomModel(
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
            configSharedPreferencesDatasource.save(
                Config(
                    matricVigia = 19759,
                    idLocal = 1,
                )
            )
            val result = usecase(
                typeMov = TypeMov.OUTPUT,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipResidenciaSharedPreferencesDatasourceImpl.get"
            )
        }

    @Test
    fun check_return_true_if_save_execute_successfully_and_output() =
        runTest {
            movEquipResidenciaDao.insert(
                MovEquipResidenciaRoomModel(
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
            configSharedPreferencesDatasource.save(
                Config(
                    matricVigia = 19759,
                    idLocal = 1,
                )
            )
            movEquipResidenciaSharedPreferencesDatasource.start(
                MovEquipResidenciaSharedPreferencesModel(
                    tipoMovEquipResidencia = TypeMov.OUTPUT,
                    dthrMovEquipResidencia = Date(1723213270250),
                    motoristaMovEquipResidencia = "MOTORISTA TESTE",
                    veiculoMovEquipResidencia = "VEICULO TESTE",
                    placaMovEquipResidencia = "PLACA TESTE",
                    observMovEquipResidencia = null,
                )
            )
            val result = usecase(
                typeMov = TypeMov.OUTPUT,
                id = 1
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
            val roomModel1 = movEquipResidenciaDao.get(1)
            assertEquals(roomModel1.idMovEquipResidencia, 1)
            assertEquals(roomModel1.tipoMovEquipResidencia, TypeMov.INPUT)
            assertEquals(roomModel1.observMovEquipResidencia, "OBSERV TESTE")
            assertEquals(roomModel1.statusMovEquipForeigResidencia, StatusForeigner.OUTSIDE)
            val roomModel2 = movEquipResidenciaDao.get(2)
            assertEquals(roomModel2.idMovEquipResidencia, 2)
            assertEquals(roomModel2.tipoMovEquipResidencia, TypeMov.OUTPUT)
            assertEquals(roomModel2.observMovEquipResidencia, null)
            assertEquals(roomModel2.statusMovEquipForeigResidencia, StatusForeigner.OUTSIDE)
        }

    @Test
    fun check_return_failure_if_not_have_data_input() =
        runTest {
            val result = usecase(
                typeMov = TypeMov.INPUT,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Usecase -> SaveMovEquipResidenciaImpl"
            )
        }

    @Test
    fun check_return_failure_if_have_data_config_and_without_mov_shared_preferences_and_input() =
        runTest {
            configSharedPreferencesDatasource.save(
                Config(
                    matricVigia = 19759,
                    idLocal = 1,
                )
            )
            val result = usecase(
                typeMov = TypeMov.INPUT,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipResidenciaSharedPreferencesDatasourceImpl.get"
            )
        }

    @Test
    fun check_return_true_if_save_execute_successfully_and_input() =
        runTest {
            configSharedPreferencesDatasource.save(
                Config(
                    matricVigia = 19759,
                    idLocal = 1,
                )
            )
            movEquipResidenciaSharedPreferencesDatasource.start(
                MovEquipResidenciaSharedPreferencesModel(
                    tipoMovEquipResidencia = TypeMov.INPUT,
                    dthrMovEquipResidencia = Date(1723213270250),
                    motoristaMovEquipResidencia = "MOTORISTA TESTE",
                    veiculoMovEquipResidencia = "VEICULO TESTE",
                    placaMovEquipResidencia = "PLACA TESTE",
                    observMovEquipResidencia = "OBSERV TESTE",
                )
            )
            val result = usecase(
                typeMov = TypeMov.INPUT,
                id = 1
            )
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
            val roomModel1 = movEquipResidenciaDao.get(1)
            assertEquals(roomModel1.idMovEquipResidencia, 1)
            assertEquals(roomModel1.tipoMovEquipResidencia, TypeMov.INPUT)
            assertEquals(roomModel1.observMovEquipResidencia, "OBSERV TESTE")
            assertEquals(roomModel1.statusMovEquipForeigResidencia, StatusForeigner.INSIDE)
        }
}