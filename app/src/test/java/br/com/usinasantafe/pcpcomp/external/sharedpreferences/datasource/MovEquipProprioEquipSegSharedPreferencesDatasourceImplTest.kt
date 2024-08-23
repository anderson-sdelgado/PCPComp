package br.com.usinasantafe.pcpcomp.external.sharedpreferences.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcpcomp.utils.BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_EQUIP_SEG
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MovEquipProprioEquipSegSharedPreferencesDatasourceImplTest {

    private lateinit var context : Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var movEquipProprioEquipSegSharedPreferencesDatasourceImpl : MovEquipProprioEquipSegSharedPreferencesDatasourceImpl

    @Before
    fun before() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("teste", Context.MODE_PRIVATE)
        movEquipProprioEquipSegSharedPreferencesDatasourceImpl = MovEquipProprioEquipSegSharedPreferencesDatasourceImpl(sharedPreferences)
    }

    @Test
    fun `Check return list empty if not have data`() = runTest {
        val result = movEquipProprioEquipSegSharedPreferencesDatasourceImpl.list()
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!.isEmpty())
    }

    @Test
    fun `Check return true if save data execute successfully`() = runTest {
        val result = movEquipProprioEquipSegSharedPreferencesDatasourceImpl.add(1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }

    @Test
    fun `Check return list if have data in table internal`() = runTest {
        movEquipProprioEquipSegSharedPreferencesDatasourceImpl.add(1)
        val result = sharedPreferences.getString(BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_EQUIP_SEG, null)
        assertNotNull(result)
        assertEquals(result, "[1]")
        val resultList = movEquipProprioEquipSegSharedPreferencesDatasourceImpl.list()
        val list = resultList.getOrNull()!!
        assertTrue(resultList.isSuccess)
        assertEquals(list.size, 1)
    }

    @Test
    fun `Check return true if MovEquipProprioSegSharedPreferencesDatasource clear execute successfully`() = runTest {
        movEquipProprioEquipSegSharedPreferencesDatasourceImpl.add(1)
        val result = sharedPreferences.getString(BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_EQUIP_SEG, null)
        assertNotNull(result)
        assertEquals(result, "[1]")
        val resultList = movEquipProprioEquipSegSharedPreferencesDatasourceImpl.list()
        val list = resultList.getOrNull()!!
        assertTrue(resultList.isSuccess)
        assertEquals(list.size, 1)
        val clearResult = movEquipProprioEquipSegSharedPreferencesDatasourceImpl.clear()
        assertTrue(clearResult.isSuccess)
        assertTrue(clearResult.getOrNull()!!)
        val resultListClear = movEquipProprioEquipSegSharedPreferencesDatasourceImpl.list()
        val listClear = resultListClear.getOrNull()!!
        assertTrue(resultListClear.isSuccess)
        assertEquals(listClear.size, 0)
    }

    @Test
    fun `Check return true if MovEquipProprioPassagSharedPreferencesDatasource delete execute successfully`() = runTest {
        movEquipProprioEquipSegSharedPreferencesDatasourceImpl.add(10)
        movEquipProprioEquipSegSharedPreferencesDatasourceImpl.add(20)
        val result = sharedPreferences.getString(
            BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_EQUIP_SEG, null)
        assertNotNull(result)
        assertEquals(result, "[10,20]")
        val resultList = movEquipProprioEquipSegSharedPreferencesDatasourceImpl.list()
        val list = resultList.getOrNull()!!
        assertTrue(resultList.isSuccess)
        assertEquals(list.size, 2)
        val clearResult = movEquipProprioEquipSegSharedPreferencesDatasourceImpl.delete(10)
        assertTrue(clearResult.isSuccess)
        assertTrue(clearResult.getOrNull()!!)
        val resultListClear = movEquipProprioEquipSegSharedPreferencesDatasourceImpl.list()
        val listClear = resultListClear.getOrNull()!!
        assertTrue(resultListClear.isSuccess)
        assertEquals(listClear.size, 1)
        assertEquals(listClear[0], 20L)
    }

}