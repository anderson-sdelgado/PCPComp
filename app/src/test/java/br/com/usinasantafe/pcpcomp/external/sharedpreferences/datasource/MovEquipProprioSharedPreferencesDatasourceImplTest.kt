package br.com.usinasantafe.pcpcomp.external.sharedpreferences.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.MovEquipProprioSharedPreferencesModel
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.Date

@RunWith(RobolectricTestRunner::class)
class MovEquipProprioSharedPreferencesDatasourceImplTest {

    private lateinit var context : Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var movEquipProprioSharedPreferencesDatasourceImpl : MovEquipProprioSharedPreferencesDatasourceImpl

    @Before
    fun before() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("teste", Context.MODE_PRIVATE)
        movEquipProprioSharedPreferencesDatasourceImpl = MovEquipProprioSharedPreferencesDatasourceImpl(sharedPreferences)
    }

    @Test
    fun `Check return data correct if MovEquipProprioSharedPreferences Start execute correctly`() = runTest {
        movEquipProprioSharedPreferencesDatasourceImpl.start(TypeMov.INPUT)
        val result = movEquipProprioSharedPreferencesDatasourceImpl.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipProprio, TypeMov.INPUT)
    }

    @Test
    fun `Check return matric correct if MovEquipProprioSharedPreferences setMatricColab execute correctly`() = runTest {
        movEquipProprioSharedPreferencesDatasourceImpl.start(TypeMov.INPUT)
        movEquipProprioSharedPreferencesDatasourceImpl.setMatricColab(19759)
        val result = movEquipProprioSharedPreferencesDatasourceImpl.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipProprio, TypeMov.INPUT)
        assertEquals(result.getOrNull()!!.nroMatricColabMovEquipProprio, 19759)
    }

    @Test
    fun `Check return idEquip correct if MovEquipProprioSharedPreferences setIdEquip execute correctly`() = runTest {
        movEquipProprioSharedPreferencesDatasourceImpl.start(TypeMov.INPUT)
        movEquipProprioSharedPreferencesDatasourceImpl.setIdEquip(10)
        val result = movEquipProprioSharedPreferencesDatasourceImpl.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipProprio, TypeMov.INPUT)
        assertEquals(result.getOrNull()!!.idEquipMovEquipProprio, 10)
    }

    @Test
    fun `Check return destino correct if MovEquipProprioSharedPreferences setDestino execute correctly`() = runTest {
        movEquipProprioSharedPreferencesDatasourceImpl.start(TypeMov.INPUT)
        movEquipProprioSharedPreferencesDatasourceImpl.setDestino("Teste")
        val result = movEquipProprioSharedPreferencesDatasourceImpl.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipProprio, TypeMov.INPUT)
        assertEquals(result.getOrNull()!!.destinoMovEquipProprio, "Teste")
    }

    @Test
    fun `Check return destino correct if MovEquipProprioSharedPreferences setNotaFiscal execute correctly`() = runTest {
        movEquipProprioSharedPreferencesDatasourceImpl.start(TypeMov.INPUT)
        movEquipProprioSharedPreferencesDatasourceImpl.setNotaFiscal(123456)
        val result = movEquipProprioSharedPreferencesDatasourceImpl.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipProprio, TypeMov.INPUT)
        assertEquals(result.getOrNull()!!.nroNotaFiscalMovEquipProprio, 123456)
    }
    @Test
    fun `Check return observ correct if MovEquipProprioSharedPreferences setObserv execute correctly`() = runTest {
        movEquipProprioSharedPreferencesDatasourceImpl.start(TypeMov.INPUT)
        movEquipProprioSharedPreferencesDatasourceImpl.setObserv("Teste")
        val result = movEquipProprioSharedPreferencesDatasourceImpl.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipProprio, TypeMov.INPUT)
        assertEquals(result.getOrNull()!!.observMovEquipProprio, "Teste")
    }
}