package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Chave
import br.com.usinasantafe.pcpcomp.domain.entities.stable.LocalTrab
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.ChaveDao
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.LocalTrabDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class ISaveLocalTrabTest : KoinTest {

    private val usecase: SaveLocalTrab by inject()
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
    fun check_return_success_if_data_is_correct() =
        runTest {
            val entityList = listOf(
                LocalTrab(
                    idLocalTrab = 1,
                    descrLocalTrab = "TI",
                )
            )
            val result = usecase(entityList)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result,
                Result.success(true)
            )
            val listBefore = localTrabDao.listAll()
            assertEquals(
                listBefore.size,
                1
            )
        }

    @Test
    fun check_return_failure_if_have_row_repeated() =
        runTest {
            val entityList = listOf(
                LocalTrab(
                    idLocalTrab = 1,
                    descrLocalTrab = "TI",
                ),
                LocalTrab(
                    idLocalTrab = 1,
                    descrLocalTrab = "TI",
                )
            )
            val result = usecase(entityList)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> ILocalTrabRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_local_trab.idLocalTrab (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])"
            )
        }
}