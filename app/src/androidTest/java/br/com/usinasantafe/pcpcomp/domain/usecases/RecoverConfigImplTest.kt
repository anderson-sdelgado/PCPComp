package br.com.usinasantafe.pcpcomp.domain.usecases

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject

class RecoverConfigImplTest: KoinTest {

    private val usecase: RecoverConfig by inject()
    private val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()

    @Test
    fun verify_return_null_if_dont_data_config() = runTest {
        val result = usecase()
        assertNull(result)
    }

    @Test
    fun verify_return_config_if_have_data_config() = runTest {
        val config = Config(
            numberConfig = 16997417840,
            passwordConfig = "12345"
        )
        configSharedPreferences.saveConfig(config)
        val result = usecase()
        assertNotNull(result)
        assertEquals(result!!.number, "16997417840")
        assertEquals(result.password, "12345")
    }

}