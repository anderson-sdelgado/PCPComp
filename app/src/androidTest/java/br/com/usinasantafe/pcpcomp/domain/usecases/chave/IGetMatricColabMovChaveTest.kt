package br.com.usinasantafe.pcpcomp.domain.usecases.chave

import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovChaveDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovChaveRoomModel
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMovKey
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import java.util.Date

class IGetMatricColabMovChaveTest : KoinTest {

    private val usecase: GetMatricColabMovChave by inject()
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
    fun check_return_list_empty_if_not_have_data() =
        runTest {
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> IMovChaveRepository.getMatricColab"
            )
        }

    @Test
    fun check_return_true_and_data_returned() =
        runTest {
            movChaveDao.insert(
                MovChaveRoomModel(
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
            )
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "19035"
            )
        }

}