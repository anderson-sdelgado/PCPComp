package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcpcomp.external.room.dao.stable.TerceiroDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.TerceiroRoomModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class CleanTerceiroImplTest : KoinTest {

    private val usecase: CleanTerceiro by inject()
    private val terceiroDao: TerceiroDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_clean_execute_correct_and_check_data() =
        runTest {
            terceiroDao.insertAll(
                listOf(
                    TerceiroRoomModel(
                        idBDTerceiro = 1,
                        idTerceiro = 1,
                        nomeTerceiro = "Anderson",
                        cpfTerceiro = "123.456.789-00",
                        empresaTerceiro = "Teste"
                    )
                )
            )
            val listBefore = terceiroDao.listAll()
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
            val listAfter = terceiroDao.listAll()
            assertEquals(
                listAfter.size,
                0
            )
        }

}