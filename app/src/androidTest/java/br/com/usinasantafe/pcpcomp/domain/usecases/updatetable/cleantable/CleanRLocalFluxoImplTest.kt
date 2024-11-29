package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcpcomp.external.room.dao.stable.RLocalFluxoDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.RLocalFluxoRoomModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class CleanRLocalFluxoImplTest: KoinTest {

    private val usecase: CleanRLocalFluxo by inject()
    private val rLocalFluxoDao: RLocalFluxoDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_clean_execute_correct_and_check_data() =
        runTest {
            rLocalFluxoDao.insertAll(
                listOf(
                    RLocalFluxoRoomModel(
                        idRLocalFluxo = 1,
                        idFluxo = 1,
                        idLocal = 1
                    )
                )
            )
            val listBefore = rLocalFluxoDao.listAll()
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
            val listAfter = rLocalFluxoDao.listAll()
            assertEquals(
                listAfter.size,
                0
            )
        }


}