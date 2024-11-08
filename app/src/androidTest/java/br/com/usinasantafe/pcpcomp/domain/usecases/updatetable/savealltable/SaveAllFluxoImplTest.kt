package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savealltable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Fluxo
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.FluxoDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class SaveAllFluxoImplTest : KoinTest {

    private val usecase: SaveAllFluxo by inject()
    private val fluxoDao: FluxoDao by inject()

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
                Fluxo(
                    idFluxo = 1,
                    descrFluxo = "MOV. EQUIP. PRÓPRIO"
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
            val list = fluxoDao.getAll()
            assertEquals(
                list.size,
                1
            )
            val entity = list[0]
            assertEquals(
                entity.idFluxo,
                1
            )
            assertEquals(
                entity.descrFluxo,
                "MOV. EQUIP. PRÓPRIO"
            )
        }

    @Test
    fun check_return_failure_if_have_row_repeated() =
        runTest {
            val entityList = listOf(
                Fluxo(
                    idFluxo = 1,
                    descrFluxo = "MOV. EQUIP. PRÓPRIO"
                ),
                Fluxo(
                    idFluxo = 1,
                    descrFluxo = "MOV. EQUIP. PRÓPRIO"
                )
            )
            val result = usecase(entityList)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> FluxoRoomDatasourceImpl.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_fluxo.idFluxo (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])"
            )
        }
}