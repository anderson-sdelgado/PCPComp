package br.com.usinasantafe.pcpcomp.domain.usecases.chave

import br.com.usinasantafe.pcpcomp.external.sharedpreferences.datasource.IMovChaveSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals

class ISetIdChaveMovTest: KoinTest {

    private val usecase: SetIdChaveMovChave by inject()
    private val movChaveSharedPreferencesDatasource:
            IMovChaveSharedPreferencesDatasource by inject()

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
    fun check_return_failure_if_not_have_started_mov_chave() =
        runTest {
            val result = usecase(
                idChave = 1,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> IMovChaveSharedPreferencesDatasource.get"
            )
        }

    @Test
    fun check_return_true_and_data_returned() =
        runTest {
            movChaveSharedPreferencesDatasource.start()
            val result = usecase(
                idChave = 1,
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertEquals(
                result.isSuccess,
                true
            )
            val resultGet = movChaveSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val movChave = resultGet.getOrNull()!!
            assertEquals(
                movChave.idChaveMovChave,
                1
            )
        }


}