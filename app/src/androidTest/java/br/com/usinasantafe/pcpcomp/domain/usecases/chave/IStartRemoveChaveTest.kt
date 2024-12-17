package br.com.usinasantafe.pcpcomp.domain.usecases.chave

import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovChaveSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.utils.TypeMovKey
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals

class IStartRemoveChaveTest : KoinTest {

    private val usecase: StartRemoveMovChave by inject()
    private val movChaveSharedPreferencesDatasource:
            MovChaveSharedPreferencesDatasource by inject()

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
    fun check_return_true_and_data_returned() =
        runTest {
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val resultGet = movChaveSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val entity = resultGet.getOrNull()!!
            assertEquals(
                entity.tipoMovChave,
                TypeMovKey.REMOVE
            )
        }

}