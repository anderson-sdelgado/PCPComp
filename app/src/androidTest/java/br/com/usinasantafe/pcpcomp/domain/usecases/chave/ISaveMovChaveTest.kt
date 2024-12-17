package br.com.usinasantafe.pcpcomp.domain.usecases.chave

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovChaveDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovChaveSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovChaveRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.MovChaveSharedPreferencesModel
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMovKey
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import java.util.Date
import kotlin.test.assertEquals

class ISaveMovChaveTest : KoinTest {

    private val usecase: SaveMovChave by inject()
    private val configSharedPreferencesDatasource:
            ConfigSharedPreferencesDatasource by inject()
    private val movChaveSharedPreferencesDatasource:
            MovChaveSharedPreferencesDatasource by inject()
    private val movChaveDao: MovChaveDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(
            generateTestAppComponent(
                server.url("").toString()
            )
        )
    }

    @Test
    fun check_return_failure_if_not_have_data_in_config_remove() =
        runTest {
            val result = usecase(
                typeMov = TypeMovKey.REMOVE,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Usecase -> SaveMovChaveImpl"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause!!.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_mov_chave_shared_preferences_remove() =
        runTest {
            configSharedPreferencesDatasource.save(
                Config(
                    matricVigia = 19035,
                    idLocal = 1
                )
            )
            val result = usecase(
                typeMov = TypeMovKey.REMOVE,
                id = 0
            )

            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> IMovChaveSharedPreferencesDatasource.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause!!.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_true_and_data_returned_remove() =
        runTest {
            configSharedPreferencesDatasource.save(
                Config(
                    matricVigia = 19035,
                    idLocal = 1
                )
            )
            movChaveSharedPreferencesDatasource.start(
                MovChaveSharedPreferencesModel(
                    dthrMovChave = Date(),
                    tipoMovChave = TypeMovKey.REMOVE,
                    idChaveMovChave = 1,
                    matricColabMovChave = 19759,
                    observMovChave = "TESTE"
                )
            )
            val listBefore = movChaveDao.listStatusForeigner(StatusForeigner.INSIDE)
            assertEquals(
                listBefore.size,
                0
            )
            val result = usecase(
                typeMov = TypeMovKey.REMOVE,
                id = 0
            )
            assertEquals(
                result.isSuccess,
                true
            )
            val listAfter = movChaveDao.listStatusForeigner(StatusForeigner.INSIDE)
            assertEquals(
                listAfter.size,
                1
            )
            val movChave = listAfter[0]
            assertEquals(
                movChave.idChaveMovChave,
                1
            )
            assertEquals(
                movChave.matricColabMovChave,
                19759
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_mov_room() =
        runTest {
            val result = usecase(
                typeMov = TypeMovKey.RECEIPT,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovChaveRoomDatasourceImpl.setStatusOutside"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause!!.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'void br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovChaveRoomModel.setStatusForeignerMovChave(br.com.usinasantafe.pcpcomp.utils.StatusForeigner)' on a null object reference"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_config_receipt() =
        runTest {
            movChaveDao.insert(
                MovChaveRoomModel(
                    idChaveMovChave = 1,
                    matricVigiaMovChave = 19035,
                    dthrMovChave = Date().time,
                    tipoMovChave = TypeMovKey.REMOVE,
                    idLocalMovChave = 1,
                    matricColabMovChave = 19759,
                    observMovChave = "TESTE",
                    statusForeignerMovChave = StatusForeigner.INSIDE,
                    statusSendMovChave = StatusSend.SEND,
                    statusMovChave = StatusData.OPEN,
                )
            )
            val result = usecase(
                typeMov = TypeMovKey.RECEIPT,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Usecase -> SaveMovChaveImpl"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause!!.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_mov_chave_shared_preferences_receipt() =
        runTest {
            movChaveDao.insert(
                MovChaveRoomModel(
                    idChaveMovChave = 1,
                    matricVigiaMovChave = 19035,
                    dthrMovChave = Date().time,
                    tipoMovChave = TypeMovKey.REMOVE,
                    idLocalMovChave = 1,
                    matricColabMovChave = 19759,
                    observMovChave = "TESTE",
                    statusForeignerMovChave = StatusForeigner.INSIDE,
                    statusSendMovChave = StatusSend.SEND,
                    statusMovChave = StatusData.OPEN,
                )
            )
            configSharedPreferencesDatasource.save(
                Config(
                    matricVigia = 19035,
                    idLocal = 1
                )
            )
            val result = usecase(
                typeMov = TypeMovKey.RECEIPT,
                id = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> IMovChaveSharedPreferencesDatasource.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause!!.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_true_and_data_returned_receipt() =
        runTest {
            movChaveDao.insert(
                MovChaveRoomModel(
                    idChaveMovChave = 1,
                    matricVigiaMovChave = 19035,
                    dthrMovChave = Date().time,
                    tipoMovChave = TypeMovKey.REMOVE,
                    idLocalMovChave = 1,
                    matricColabMovChave = 19759,
                    observMovChave = "TESTE",
                    statusForeignerMovChave = StatusForeigner.INSIDE,
                    statusSendMovChave = StatusSend.SEND,
                    statusMovChave = StatusData.OPEN,
                )
            )
            configSharedPreferencesDatasource.save(
                Config(
                    matricVigia = 19035,
                    idLocal = 1
                )
            )
            movChaveSharedPreferencesDatasource.start(
                MovChaveSharedPreferencesModel(
                    dthrMovChave = Date(),
                    tipoMovChave = TypeMovKey.RECEIPT,
                    idChaveMovChave = 1,
                    matricColabMovChave = 18017,
                    observMovChave = "TESTE RETORNO"
                )
            )
            val listBefore = movChaveDao.listStatusForeigner(StatusForeigner.INSIDE)
            assertEquals(
                listBefore.size,
                1
            )
            val result = usecase(
                typeMov = TypeMovKey.RECEIPT,
                id = 1
            )
            assertEquals(
                result.isSuccess,
                true
            )
            val listAfter = movChaveDao.listStatusForeigner(StatusForeigner.INSIDE)
            assertEquals(
                listAfter.size,
                0
            )
            val listOpen = movChaveDao.listStatusData(StatusData.OPEN)
            assertEquals(
                listOpen.size,
                2
            )
            val movChave1 = listOpen[0]
            assertEquals(
                movChave1.idMovChave,
                1
            )
            assertEquals(
                movChave1.idChaveMovChave,
                1
            )
            assertEquals(
                movChave1.matricColabMovChave,
                19759
            )
            assertEquals(
                movChave1.statusForeignerMovChave,
                StatusForeigner.OUTSIDE
            )
            val movChave2 = listOpen[1]
            assertEquals(
                movChave2.idMovChave,
                2
            )
            assertEquals(
                movChave2.idChaveMovChave,
                1
            )
            assertEquals(
                movChave2.matricColabMovChave,
                18017
            )
            assertEquals(
                movChave2.statusForeignerMovChave,
                StatusForeigner.OUTSIDE
            )
        }

}