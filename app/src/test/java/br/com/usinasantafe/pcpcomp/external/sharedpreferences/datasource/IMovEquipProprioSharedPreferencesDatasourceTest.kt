package br.com.usinasantafe.pcpcomp.external.sharedpreferences.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IMovEquipProprioSharedPreferencesDatasourceTest {

    private lateinit var context : Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var IMovEquipProprioSharedPreferencesDatasource : IMovEquipProprioSharedPreferencesDatasource

    @Before
    fun before() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("teste", Context.MODE_PRIVATE)
        IMovEquipProprioSharedPreferencesDatasource = IMovEquipProprioSharedPreferencesDatasource(sharedPreferences)
    }

    @Test
    fun `Check return data correct if MovEquipProprioSharedPreferences Start execute correctly`() = runTest {
        IMovEquipProprioSharedPreferencesDatasource.start(TypeMov.INPUT)
        val result = IMovEquipProprioSharedPreferencesDatasource.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipProprio, TypeMov.INPUT)
    }

    @Test
    fun `Check altered in table if MovEquipProprioSharedPreferences setMatricColab execute correctly`() = runTest {
        IMovEquipProprioSharedPreferencesDatasource.start(TypeMov.INPUT)
        IMovEquipProprioSharedPreferencesDatasource.setMatricColab(19759)
        val result = IMovEquipProprioSharedPreferencesDatasource.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipProprio, TypeMov.INPUT)
        assertEquals(result.getOrNull()!!.matricColabMovEquipProprio, 19759)
    }

    @Test
    fun `Check return idEquip correct if MovEquipProprioSharedPreferences setIdEquip execute correctly`() = runTest {
        IMovEquipProprioSharedPreferencesDatasource.start(TypeMov.INPUT)
        IMovEquipProprioSharedPreferencesDatasource.setIdEquip(10)
        val result = IMovEquipProprioSharedPreferencesDatasource.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipProprio, TypeMov.INPUT)
        assertEquals(result.getOrNull()!!.idEquipMovEquipProprio, 10)
    }

    @Test
    fun `Check return destino correct if MovEquipProprioSharedPreferences setDestino execute correctly`() = runTest {
        IMovEquipProprioSharedPreferencesDatasource.start(TypeMov.INPUT)
        IMovEquipProprioSharedPreferencesDatasource.setDestino("Teste")
        val result = IMovEquipProprioSharedPreferencesDatasource.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipProprio, TypeMov.INPUT)
        assertEquals(result.getOrNull()!!.destinoMovEquipProprio, "Teste")
    }

    @Test
    fun `Check return destino correct if MovEquipProprioSharedPreferences setNotaFiscal execute correctly`() = runTest {
        IMovEquipProprioSharedPreferencesDatasource.start(TypeMov.INPUT)
        IMovEquipProprioSharedPreferencesDatasource.setNotaFiscal(123456)
        val result = IMovEquipProprioSharedPreferencesDatasource.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipProprio, TypeMov.INPUT)
        assertEquals(result.getOrNull()!!.notaFiscalMovEquipProprio, 123456)
    }

    @Test
    fun `Check return destino correct if MovEquipProprioSharedPreferences setNotaFiscal execute correctly and value is null`() = runTest {
        IMovEquipProprioSharedPreferencesDatasource.start(TypeMov.INPUT)
        IMovEquipProprioSharedPreferencesDatasource.setNotaFiscal(null)
        val result = IMovEquipProprioSharedPreferencesDatasource.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipProprio, TypeMov.INPUT)
        assertEquals(result.getOrNull()!!.notaFiscalMovEquipProprio, null)
    }

    @Test
    fun `Check return observ correct if MovEquipProprioSharedPreferences setObserv execute correctly`() = runTest {
        IMovEquipProprioSharedPreferencesDatasource.start(TypeMov.INPUT)
        IMovEquipProprioSharedPreferencesDatasource.setObserv("Teste")
        val result = IMovEquipProprioSharedPreferencesDatasource.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipProprio, TypeMov.INPUT)
        assertEquals(result.getOrNull()!!.observMovEquipProprio, "Teste")
    }
}