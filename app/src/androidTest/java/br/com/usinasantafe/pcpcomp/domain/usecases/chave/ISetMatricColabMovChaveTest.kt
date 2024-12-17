package br.com.usinasantafe.pcpcomp.domain.usecases.chave

import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovChaveDao
import br.com.usinasantafe.pcpcomp.external.sharedpreferences.datasource.IMovChaveSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovChaveRoomModel
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMovKey
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import java.util.Date
import kotlin.test.assertEquals

class ISetMatricColabMovChaveTest: KoinTest {

    private val usecase: SetMatricColabMovChave by inject()
    private val movChaveSharedPreferencesDatasource:
            IMovChaveSharedPreferencesDatasource by inject()
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
    fun check_return_failure_if_not_have_started_mov_chave() =
        runTest {
            val result = usecase(
                matricColab = "19759",
                flowApp = FlowApp.ADD,
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
        }

    @Test
    fun check_return_failure_if_matric_incorrect() =
        runTest {
            val result = usecase(
                matricColab = "19759Asada",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Usecase -> ISetMatricColabMovChave"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause!!.message,
                "For input string: \"19759Asada\""
            )
        }

    @Test
    fun check_return_true_and_data_returned() =
        runTest {
            movChaveSharedPreferencesDatasource.start()
            val result = usecase(
                matricColab = "19759",
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isSuccess,
                true
            )
            val resultGet = movChaveSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val movChave = resultGet.getOrNull()!!
            assertEquals(
                movChave.matricColabMovChave,
                19759
            )
        }

    @Test
    fun check_return_true_and_data_returned_change() =
        runTest {
            val roomModel = MovChaveRoomModel(
                idMovChave = 1,
                dthrMovChave = Date().time,
                tipoMovChave = TypeMovKey.REMOVE,
                matricVigiaMovChave = 19759,
                idLocalMovChave = 1,
                idChaveMovChave = 1,
                matricColabMovChave = 19035,
                observMovChave = "OBSERV",
                statusMovChave = StatusData.OPEN,
                statusSendMovChave = StatusSend.SEND,
                statusForeignerMovChave = StatusForeigner.INSIDE
            )
            movChaveDao.insert(roomModel)
            val modelBefore = movChaveDao.get(1)
            Assert.assertEquals(
                modelBefore.matricColabMovChave,
                19035
            )
            val result = usecase(
                matricColab = "18017",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val modelAfter = movChaveDao.get(1)
            Assert.assertEquals(
                modelAfter.matricColabMovChave,
                18017
            )
        }
}