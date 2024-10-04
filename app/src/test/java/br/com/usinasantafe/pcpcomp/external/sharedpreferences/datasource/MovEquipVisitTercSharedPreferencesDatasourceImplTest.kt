package br.com.usinasantafe.pcpcomp.external.sharedpreferences.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.MovEquipVisitTercSharedPreferencesModel
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MovEquipVisitTercSharedPreferencesDatasourceImplTest {

    private lateinit var context : Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var movEquipVisitTercSharedPreferencesDatasourceImpl : MovEquipVisitTercSharedPreferencesDatasourceImpl

    @Before
    fun before() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("teste", Context.MODE_PRIVATE)
        movEquipVisitTercSharedPreferencesDatasourceImpl = MovEquipVisitTercSharedPreferencesDatasourceImpl(sharedPreferences)
    }

    @Test
    fun `Check return data correct if Start execute correctly`() = runTest {
        movEquipVisitTercSharedPreferencesDatasourceImpl.start(
            MovEquipVisitTercSharedPreferencesModel()
        )
        val result = movEquipVisitTercSharedPreferencesDatasourceImpl.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipVisitTerc, TypeMov.INPUT)
    }

    @Test
    fun `Check altered in table if setDestino execute correctly`() = runTest {
        movEquipVisitTercSharedPreferencesDatasourceImpl.start(
            MovEquipVisitTercSharedPreferencesModel()
        )
        movEquipVisitTercSharedPreferencesDatasourceImpl.setDestino("DESTINO")
        val result = movEquipVisitTercSharedPreferencesDatasourceImpl.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipVisitTerc, TypeMov.INPUT)
        assertEquals(result.getOrNull()!!.destinoMovEquipVisitTerc, "DESTINO")
    }

    @Test
    fun `Check altered in table if setIdVisitTerc execute correctly`() = runTest {
        movEquipVisitTercSharedPreferencesDatasourceImpl.start(
            MovEquipVisitTercSharedPreferencesModel()
        )
        movEquipVisitTercSharedPreferencesDatasourceImpl.setIdVisitTerc(10)
        val result = movEquipVisitTercSharedPreferencesDatasourceImpl.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipVisitTerc, TypeMov.INPUT)
        assertEquals(result.getOrNull()!!.idVisitTercMovEquipVisitTerc, 10)
    }

    @Test
    fun `Check altered in table if setObserv execute correctly and value is null`() = runTest {
        movEquipVisitTercSharedPreferencesDatasourceImpl.start(
            MovEquipVisitTercSharedPreferencesModel()
        )
        movEquipVisitTercSharedPreferencesDatasourceImpl.setObserv(null)
        val result = movEquipVisitTercSharedPreferencesDatasourceImpl.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipVisitTerc, TypeMov.INPUT)
        assertEquals(result.getOrNull()!!.observMovEquipVisitTerc, null)
    }

    @Test
    fun `Check altered in table if setObserv execute correctly`() = runTest {
        movEquipVisitTercSharedPreferencesDatasourceImpl.start(
            MovEquipVisitTercSharedPreferencesModel()
        )
        movEquipVisitTercSharedPreferencesDatasourceImpl.setObserv("OBSERV")
        val result = movEquipVisitTercSharedPreferencesDatasourceImpl.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipVisitTerc, TypeMov.INPUT)
        assertEquals(result.getOrNull()!!.observMovEquipVisitTerc, "OBSERV")
    }

    @Test
    fun `Check altered in table if setPlaca execute correctly`() = runTest {
        movEquipVisitTercSharedPreferencesDatasourceImpl.start(
            MovEquipVisitTercSharedPreferencesModel()
        )
        movEquipVisitTercSharedPreferencesDatasourceImpl.setPlaca("PLACA")
        val result = movEquipVisitTercSharedPreferencesDatasourceImpl.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipVisitTerc, TypeMov.INPUT)
        assertEquals(result.getOrNull()!!.placaMovEquipVisitTerc, "PLACA")
    }

    @Test
    fun `Check altered in table if setTipoVisitTerc execute correctly`() = runTest {
        movEquipVisitTercSharedPreferencesDatasourceImpl.start(
            MovEquipVisitTercSharedPreferencesModel()
        )
        movEquipVisitTercSharedPreferencesDatasourceImpl.setTipoVisitTerc(TypeVisitTerc.TERCEIRO)
        val result = movEquipVisitTercSharedPreferencesDatasourceImpl.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipVisitTerc, TypeMov.INPUT)
        assertEquals(result.getOrNull()!!.tipoVisitTercMovEquipVisitTerc, TypeVisitTerc.TERCEIRO)
        assertEquals(result.getOrNull()!!.observMovEquipVisitTerc, null)
    }

    @Test
    fun `Check altered in table if setVeiculo execute correctly`() = runTest {
        movEquipVisitTercSharedPreferencesDatasourceImpl.start(
            MovEquipVisitTercSharedPreferencesModel()
        )
        movEquipVisitTercSharedPreferencesDatasourceImpl.setVeiculo("VEICULO")
        val result = movEquipVisitTercSharedPreferencesDatasourceImpl.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipVisitTerc, TypeMov.INPUT)
        assertEquals(result.getOrNull()!!.veiculoMovEquipVisitTerc, "VEICULO")
        assertEquals(result.getOrNull()!!.placaMovEquipVisitTerc, null)
    }
}