package br.com.usinasantafe.pcpcomp.external.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ConfigSharedPreferencesDatasourceImplTest {

    private lateinit var context : Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var configSharedPreferencesDatasourceImpl : ConfigSharedPreferencesDatasourceImpl

    @Before
    fun before() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("teste", Context.MODE_PRIVATE)
        configSharedPreferencesDatasourceImpl = ConfigSharedPreferencesDatasourceImpl(sharedPreferences)
    }

    @Test
    fun `Return false if don't have data in Config SharedPreferences internal`() = runTest {
        assertFalse(configSharedPreferencesDatasourceImpl.hasConfig())
    }

    @Test
    fun `Return true if have data in Config SharedPreferences internal`() = runTest {
        configSharedPreferencesDatasourceImpl.saveConfig(Config(passwordConfig = "12345"))
        assertTrue(configSharedPreferencesDatasourceImpl.hasConfig())
    }

    @Test
    fun `Check return data correct the Config SharedPreferences internal`() = runTest {
        val data = Config(passwordConfig = "12345")
        configSharedPreferencesDatasourceImpl.saveConfig(data)
        val config = configSharedPreferencesDatasourceImpl.getConfig()
        assertEquals(config, data)
    }

    @Test
    fun `Check return password correct the Config SharedPreferences internal`() = runTest {
        val data = Config(passwordConfig = "12345")
        configSharedPreferencesDatasourceImpl.saveConfig(data)
        val config = configSharedPreferencesDatasourceImpl.getConfig()
        assertEquals(config.passwordConfig, "12345")
    }
}