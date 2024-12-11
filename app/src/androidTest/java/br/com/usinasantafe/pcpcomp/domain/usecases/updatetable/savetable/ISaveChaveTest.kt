package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Chave
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.ChaveDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class ISaveChaveTest : KoinTest {

    private val usecase: SaveChave by inject()
    private val chaveDao: ChaveDao by inject()

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
                Chave(
                    idChave = 1,
                    descrChave = "TI",
                    idLocalTrab = 1
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
            val listBefore = chaveDao.listAll()
            assertEquals(
                listBefore.size,
                1
            )
        }

    @Test
    fun check_return_failure_if_have_row_repeated() =
        runTest {
            val entityList = listOf(
                Chave(
                    idChave = 1,
                    descrChave = "TI",
                    idLocalTrab = 1
                ),
                Chave(
                    idChave = 1,
                    descrChave = "TI",
                    idLocalTrab = 1
                )
            )
            val result = usecase(entityList)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> IChaveRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_chave.idChave (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])"
            )
        }
}