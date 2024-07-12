package br.com.usinasantafe.pcpcomp.domain.usecases

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject

class CheckPasswordConfigTest: KoinTest {

    private val usecase: CheckPasswordConfig by inject()
    private val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()

    @Test
    fun verify_return_true_if_dont_data_config() = runTest {
        val result = usecase("12345")
        assertTrue(result)
    }

    @Test
    fun verify_return_false_if_password_typed_incorrect() = runTest {
        configSharedPreferences.saveConfig(Config(passwordConfig = "12345"))
        val result = usecase("123456")
        assertFalse(result)
    }

    @Test
    fun verify_return_true_if_password_typed_correct() = runTest {
        configSharedPreferences.saveConfig(Config(passwordConfig = "12345"))
        val result = usecase("12345")
        assertTrue(result)
    }
}