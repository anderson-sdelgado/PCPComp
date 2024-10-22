package br.com.usinasantafe.pcpcomp.external.sharedpreferences.datasource

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
        val result = configSharedPreferencesDatasourceImpl.has()
        assertFalse(result.getOrNull()!!)
    }

    @Test
    fun `Return true if have data in Config SharedPreferences internal`() = runTest {
        configSharedPreferencesDatasourceImpl.save(Config(password = "12345"))
        val result = configSharedPreferencesDatasourceImpl.has()
        assertTrue(result.getOrNull()!!)
    }

    @Test
    fun `Check return data correct the Config SharedPreferences internal`() = runTest {
        val data = Config(password = "12345")
        configSharedPreferencesDatasourceImpl.save(data)
        val result = configSharedPreferencesDatasourceImpl.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, data)
    }

    @Test
    fun `Check return password correct the Config SharedPreferences internal`() = runTest {
        val data = Config(password = "12345")
        configSharedPreferencesDatasourceImpl.save(data)
        val result = configSharedPreferencesDatasourceImpl.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.password, "12345")
    }

    @Test
    fun `Check return true and data returned if clear execute successfully`() = runTest {
        val data = Config(password = "12345")
        configSharedPreferencesDatasourceImpl.save(data)
        val resultGetBefore = configSharedPreferencesDatasourceImpl.get()
        assertTrue(resultGetBefore.isSuccess)
        assertEquals(resultGetBefore.getOrNull()!!.password, "12345")
        val resultClear = configSharedPreferencesDatasourceImpl.clear()
        assertTrue(resultClear.isSuccess)
        val resultHas = configSharedPreferencesDatasourceImpl.has()
        assertTrue(resultHas.isSuccess)
        assertFalse(resultHas.getOrNull()!!)
    }

}