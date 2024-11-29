package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.RLocalFluxo
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.RLocalFluxoDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class SaveAllRLocalFluxoImplTest : KoinTest {

    private val usecase: SaveRLocalFluxo by inject()
    private val rLocalFluxoDao: RLocalFluxoDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_success_if_data_is_correct() =
        runTest {
            val entityList = listOf(
                RLocalFluxo(
                    idRLocalFluxo = 1,
                    idLocal = 1,
                    idFluxo = 1
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
            val list = rLocalFluxoDao.listAll()
            assertEquals(
                list.size,
                1
            )
            val entity = list[0]
            assertEquals(
                entity.idRLocalFluxo,
                1
            )
            assertEquals(
                entity.idLocal,
                1
            )
            assertEquals(
                entity.idFluxo,
                1
            )
        }

    @Test
    fun check_return_failure_if_have_row_repeated() =
        runTest {
            val entityList = listOf(
                RLocalFluxo(
                    idRLocalFluxo = 1,
                    idLocal = 1,
                    idFluxo = 1
                    ),
                RLocalFluxo(
                    idRLocalFluxo = 1,
                    idLocal = 1,
                    idFluxo = 1
                )
            )
            val result = usecase(entityList)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> RLocalFluxoRoomDatasourceImpl.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_r_local_fluxo.idRLocalFluxo (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])"
            )
        }
}