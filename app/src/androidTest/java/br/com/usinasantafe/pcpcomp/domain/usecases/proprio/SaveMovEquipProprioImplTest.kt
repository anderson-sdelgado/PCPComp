package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.external.sharedpreferences.datasource.MovEquipProprioSharedPreferencesDatasourceImpl
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.MovEquipProprioSharedPreferencesModel
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class SaveMovEquipProprioImplTest : KoinTest {

    private val usecase: SaveMovEquipProprio by inject()
    private val configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource by inject()
    private val movEquipProprioSharedPreferencesDatasource: MovEquipProprioSharedPreferencesDatasourceImpl by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_failure_if_not_have_data_in_config_internal() = runTest {
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Usecase -> SaveMovEquipProprioImpl"
        )
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "java.lang.NullPointerException")
    }

    @Test
    fun check_return_failure_if_matric_vigia_is_null() = runTest {
        configSharedPreferencesDatasource.save(
            Config()
        )
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Usecase -> SaveMovEquipProprioImpl"
        )
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "java.lang.NullPointerException")
    }

    @Test
    fun check_return_failure_if_not_have_data_mov_equip_proprio_internal() = runTest {
        configSharedPreferencesDatasource.save(
            Config(
                matricVigia = 19759,
                idLocal = 1
            )
        )
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipProprioSharedPreferencesDatasourceImpl.get"
        )
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "java.lang.NullPointerException")
    }


    @Test
    fun check_return_true_if_execute_correctly() = runTest {
        configSharedPreferencesDatasource.save(
            Config(
                matricVigia = 19759,
                idLocal = 1
            )
        )
        movEquipProprioSharedPreferencesDatasource.save(
            MovEquipProprioSharedPreferencesModel(
                tipoMovEquipProprio = TypeMov.INPUT,
                idEquipMovEquipProprio = 1,
                nroMatricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "Teste",
                nroNotaFiscalMovEquipProprio = 123456,
                observMovEquipProprio = "Teste Observ",
            )
        )
        val result = usecase()
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }
}