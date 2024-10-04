package br.com.usinasantafe.pcpcomp.external.sharedpreferences.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcpcomp.utils.BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_PROPRIO_PASSAG
import br.com.usinasantafe.pcpcomp.utils.BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC_PASSAG
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MovEquipVisitTercPassagSharedPreferencesDatasourceImplTest {

    private lateinit var context : Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var movEquipVisitTercPassagSharedPreferencesDatasourceImpl : MovEquipVisitTercPassagSharedPreferencesDatasourceImpl

    @Before
    fun before() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("teste", Context.MODE_PRIVATE)
        movEquipVisitTercPassagSharedPreferencesDatasourceImpl = MovEquipVisitTercPassagSharedPreferencesDatasourceImpl(sharedPreferences)
    }

    @Test
    fun `Check return list empty if not have data`() = runTest {
        val result = movEquipVisitTercPassagSharedPreferencesDatasourceImpl.list()
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!.isEmpty())
    }

    @Test
    fun `Check return true if save data execute successfully`() = runTest {
        val result = movEquipVisitTercPassagSharedPreferencesDatasourceImpl.add(1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }

    @Test
    fun `Check return list if have data in table internal`() = runTest {
        movEquipVisitTercPassagSharedPreferencesDatasourceImpl.add(1)
        val result = sharedPreferences.getString(
            BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC_PASSAG,
            null
        )
        assertNotNull(result)
        assertEquals(result, "[1]")
        val resultList = movEquipVisitTercPassagSharedPreferencesDatasourceImpl.list()
        val list = resultList.getOrNull()!!
        assertTrue(resultList.isSuccess)
        assertEquals(list.size, 1)
    }

    @Test
    fun `Check return true if clear execute successfully`() = runTest {
        movEquipVisitTercPassagSharedPreferencesDatasourceImpl.add(1)
        val result = sharedPreferences.getString(
            BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC_PASSAG,
            null
        )
        assertNotNull(result)
        assertEquals(result, "[1]")
        val resultList = movEquipVisitTercPassagSharedPreferencesDatasourceImpl.list()
        val list = resultList.getOrNull()!!
        assertTrue(resultList.isSuccess)
        assertEquals(list.size, 1)
        val clearResult = movEquipVisitTercPassagSharedPreferencesDatasourceImpl.clear()
        assertTrue(clearResult.isSuccess)
        assertTrue(clearResult.getOrNull()!!)
        val resultListClear = movEquipVisitTercPassagSharedPreferencesDatasourceImpl.list()
        val listClear = resultListClear.getOrNull()!!
        assertTrue(resultListClear.isSuccess)
        assertEquals(listClear.size, 0)
        assertTrue(listClear.isEmpty())
    }

    @Test
    fun `Check return true if delete execute successfully`() = runTest {
        movEquipVisitTercPassagSharedPreferencesDatasourceImpl.add(10)
        movEquipVisitTercPassagSharedPreferencesDatasourceImpl.add(20)
        val result = sharedPreferences.getString(
            BASE_SHARE_PREFERENCES_TABLE_MOV_EQUIP_VISIT_TERC_PASSAG,
            null
        )
        assertNotNull(result)
        assertEquals(result, "[10,20]")
        val resultList = movEquipVisitTercPassagSharedPreferencesDatasourceImpl.list()
        val list = resultList.getOrNull()!!
        assertTrue(resultList.isSuccess)
        assertEquals(list.size, 2)
        val clearResult = movEquipVisitTercPassagSharedPreferencesDatasourceImpl.delete(10)
        assertTrue(clearResult.isSuccess)
        assertTrue(clearResult.getOrNull()!!)
        val resultListClear = movEquipVisitTercPassagSharedPreferencesDatasourceImpl.list()
        val listClear = resultListClear.getOrNull()!!
        assertTrue(resultListClear.isSuccess)
        assertEquals(listClear.size, 1)
        assertEquals(listClear[0], 20)
    }

}