package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipVisitTercSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class SetTipoVisitTercImplTest : KoinTest {

    private val usecase: SetTipoVisitTerc by inject()
    private val movEquipVisitTercSharedPreferencesDatasource:
            MovEquipVisitTercSharedPreferencesDatasource by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_failure_if_not_have_data() =
        runTest {
            val result = usecase(TypeVisitTerc.TERCEIRO)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> MovEquipVisitTercSharedPreferencesDatasourceImpl.get"
            )
        }

    @Test
    fun check_return_true_if_set_tipo_execute_success_and_check_data() =
        runTest {
            movEquipVisitTercSharedPreferencesDatasource.start()
            val result = usecase(TypeVisitTerc.TERCEIRO)
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
            val resultGet = movEquipVisitTercSharedPreferencesDatasource.get()
            assertTrue(resultGet.isSuccess)
            val entity = resultGet.getOrNull()!!
            assertEquals(entity.tipoVisitTercMovEquipVisitTerc, TypeVisitTerc.TERCEIRO)
        }

}