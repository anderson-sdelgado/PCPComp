package br.com.usinasantafe.pcpcomp.domain.usecases.initial

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.usecases.config.RecoverConfigInternal
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.utils.FlagUpdate
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class CheckAccessMainImplTest: KoinTest {

    private val usecase: CheckAccessMain by inject()
    private val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun verify_return_false_if_dont_data_config() = runTest {
        val result = usecase()
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, false)
    }

    @Test
    fun verify_return_false_if_data_config_field_flag_update_is_outdated() = runTest {
        configSharedPreferences.saveConfig(Config())
        val result = usecase()
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, false)
    }

    @Test
    fun verify_return_true_if_data_config_field_flag_update_is_updated() = runTest {
        configSharedPreferences.saveConfig(
            Config(flagUpdate = FlagUpdate.UPDATED)
        )
        val result = usecase()
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, true)
    }

}