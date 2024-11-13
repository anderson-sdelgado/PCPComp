package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcpcomp.external.room.dao.stable.LocalDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.LocalRoomModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class CleanLocalImplTest : KoinTest {

    private val usecase: CleanLocal by inject()
    private val localDao: LocalDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_clean_execute_correct_and_check_data() =
        runTest {
            localDao.insertAll(
                listOf(
                    LocalRoomModel(
                        idLocal = 1,
                        descrLocal = "USINA"
                    )
                )
            )
            val listBefore = localDao.getAll()
            assertEquals(
                listBefore.size,
                1
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result,
                Result.success(true)
            )
            val listAfter = localDao.getAll()
            assertEquals(
                listAfter.size,
                0
            )
        }
}