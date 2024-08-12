package br.com.usinasantafe.pcpcomp.external.sharedpreferences.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcpcomp.utils.BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_SEG
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MovEquipProprioSegSharedPreferencesDatasourceImplTest {

    private lateinit var context : Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var movEquipProprioSegSharedPreferencesDatasourceImpl : MovEquipProprioSegSharedPreferencesDatasourceImpl

    @Before
    fun before() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("teste", Context.MODE_PRIVATE)
        movEquipProprioSegSharedPreferencesDatasourceImpl = MovEquipProprioSegSharedPreferencesDatasourceImpl(sharedPreferences)
    }

    @Test
    fun `Check return list empty if not have data`() = runTest {
        val result = movEquipProprioSegSharedPreferencesDatasourceImpl.list()
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!.isEmpty())
    }

    @Test
    fun `Check return true if save data execute successfully`() = runTest {
        val result = movEquipProprioSegSharedPreferencesDatasourceImpl.add(1L)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }

    @Test
    fun `Check return list if have data in table internal`() = runTest {
        movEquipProprioSegSharedPreferencesDatasourceImpl.add(1L)
        val result = sharedPreferences.getString(BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_SEG, null)
        assertNotNull(result)
        assertEquals(result, "[1]")
        val resultList = movEquipProprioSegSharedPreferencesDatasourceImpl.list()
        val list = resultList.getOrNull()!!
        assertTrue(resultList.isSuccess)
        assertEquals(list.size, 1)
    }

    @Test
    fun `Check return true if MovEquipProprioSegSharedPreferencesDatasource clear execute successfully`() = runTest {
        movEquipProprioSegSharedPreferencesDatasourceImpl.add(1L)
        val result = sharedPreferences.getString(BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_SEG, null)
        assertNotNull(result)
        assertEquals(result, "[1]")
        val resultList = movEquipProprioSegSharedPreferencesDatasourceImpl.list()
        val list = resultList.getOrNull()!!
        assertTrue(resultList.isSuccess)
        assertEquals(list.size, 1)
        val clearResult = movEquipProprioSegSharedPreferencesDatasourceImpl.clear()
        assertTrue(clearResult.isSuccess)
        assertTrue(clearResult.getOrNull()!!)
        val resultListClear = movEquipProprioSegSharedPreferencesDatasourceImpl.list()
        val listClear = resultListClear.getOrNull()!!
        assertTrue(resultListClear.isSuccess)
        assertEquals(listClear.size, 0)
    }

}