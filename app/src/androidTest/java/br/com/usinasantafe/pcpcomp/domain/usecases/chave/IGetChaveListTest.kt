package br.com.usinasantafe.pcpcomp.domain.usecases.chave

import br.com.usinasantafe.pcpcomp.external.room.dao.stable.ChaveDao
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.LocalTrabDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.ChaveRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.LocalTrabRoomModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals

class IGetChaveListTest: KoinTest {

    private val usecase: GetChaveList by inject()
    private val chaveDao: ChaveDao by inject()
    private val localTrabDao: LocalTrabDao by inject()

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
    fun check_return_failure_if_not_have_data() =
        runTest {
            chaveDao.insertAll(
                listOf(
                    ChaveRoomModel(
                        idChave = 1,
                        descrChave = "01 - TI",
                        idLocalTrab = 1
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> ILocalTrabRoomDatasource.getDescr"
            )
        }

    @Test
    fun check_return_true_and_data_returned() =
        runTest {
            chaveDao.insertAll(
                listOf(
                    ChaveRoomModel(
                        idChave = 1,
                        descrChave = "01 - SALA TI",
                        idLocalTrab = 1
                    )
                )
            )
            localTrabDao.insertAll(
                listOf(
                    LocalTrabRoomModel(
                        idLocalTrab = 1,
                        descrLocalTrab = "TI"
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val entityList = result.getOrNull()!!
            assertEquals(
                entityList.size,
                1
            )
            val entity = entityList[0]
            assertEquals(
                entity.id,
                1
            )
            assertEquals(
                entity.descr,
                "01 - SALA TI - TI"
            )
        }

}