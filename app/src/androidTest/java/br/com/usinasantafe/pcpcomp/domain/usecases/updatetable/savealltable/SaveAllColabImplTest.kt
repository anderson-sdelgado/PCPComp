package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savealltable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.ColabDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class SaveAllColabImplTest: KoinTest {

    private val usecase: SaveAllColab by inject()
    private val colabDao: ColabDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_success_if_data_is_correct() = runTest {
        val colabList = listOf(
            Colab(
                matricColab = 19759,
                nomeColab = "ANDERSON DA SILVA DELGADO"
            )
        )
        val result = usecase(colabList)
        assertTrue(result.isSuccess)
        assertEquals(result, Result.success(true))
        val listBefore = colabDao.getAll()
        assertEquals(
            listBefore.size,
            1
        )
    }

    @Test
    fun check_return_failure_if_have_row_repeated() = runTest {
        val colabList = listOf(
            Colab(
                matricColab = 19759,
                nomeColab = "ANDERSON DA SILVA DELGADO"
            ),
            Colab(
                matricColab = 19759,
                nomeColab = "ANDERSON DA SILVA DELGADO"
            )
        )
        val result = usecase(colabList)
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ColabRoomDatasourceImpl.addAll")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_colab.matricColab (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])")
    }

}