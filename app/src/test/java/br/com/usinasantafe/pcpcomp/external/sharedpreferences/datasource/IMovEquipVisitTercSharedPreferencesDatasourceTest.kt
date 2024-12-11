package br.com.usinasantafe.pcpcomp.external.sharedpreferences.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.MovEquipVisitTercSharedPreferencesModel
import br.com.usinasantafe.pcpcomp.utils.TypeMovEquip
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IMovEquipVisitTercSharedPreferencesDatasourceTest {

    private lateinit var context : Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var iMovEquipVisitTercSharedPreferencesDatasource : IMovEquipVisitTercSharedPreferencesDatasource

    @Before
    fun before() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("teste", Context.MODE_PRIVATE)
        iMovEquipVisitTercSharedPreferencesDatasource = IMovEquipVisitTercSharedPreferencesDatasource(sharedPreferences)
    }

    @Test
    fun `Check return data correct if Start execute correctly`() = runTest {
        iMovEquipVisitTercSharedPreferencesDatasource.start(
            MovEquipVisitTercSharedPreferencesModel()
        )
        val result = iMovEquipVisitTercSharedPreferencesDatasource.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipVisitTerc, TypeMovEquip.INPUT)
    }

    @Test
    fun `Check altered in table if setDestino execute correctly`() = runTest {
        iMovEquipVisitTercSharedPreferencesDatasource.start(
            MovEquipVisitTercSharedPreferencesModel()
        )
        iMovEquipVisitTercSharedPreferencesDatasource.setDestino("DESTINO")
        val result = iMovEquipVisitTercSharedPreferencesDatasource.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipVisitTerc, TypeMovEquip.INPUT)
        assertEquals(result.getOrNull()!!.destinoMovEquipVisitTerc, "DESTINO")
    }

    @Test
    fun `Check altered in table if setIdVisitTerc execute correctly`() = runTest {
        iMovEquipVisitTercSharedPreferencesDatasource.start(
            MovEquipVisitTercSharedPreferencesModel()
        )
        iMovEquipVisitTercSharedPreferencesDatasource.setIdVisitTerc(10)
        val result = iMovEquipVisitTercSharedPreferencesDatasource.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipVisitTerc, TypeMovEquip.INPUT)
        assertEquals(result.getOrNull()!!.idVisitTercMovEquipVisitTerc, 10)
    }

    @Test
    fun `Check altered in table if setObserv execute correctly and value is null`() = runTest {
        iMovEquipVisitTercSharedPreferencesDatasource.start(
            MovEquipVisitTercSharedPreferencesModel()
        )
        iMovEquipVisitTercSharedPreferencesDatasource.setObserv(null)
        val result = iMovEquipVisitTercSharedPreferencesDatasource.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipVisitTerc, TypeMovEquip.INPUT)
        assertEquals(result.getOrNull()!!.observMovEquipVisitTerc, null)
    }

    @Test
    fun `Check altered in table if setObserv execute correctly`() = runTest {
        iMovEquipVisitTercSharedPreferencesDatasource.start(
            MovEquipVisitTercSharedPreferencesModel()
        )
        iMovEquipVisitTercSharedPreferencesDatasource.setObserv("OBSERV")
        val result = iMovEquipVisitTercSharedPreferencesDatasource.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipVisitTerc, TypeMovEquip.INPUT)
        assertEquals(result.getOrNull()!!.observMovEquipVisitTerc, "OBSERV")
    }

    @Test
    fun `Check altered in table if setPlaca execute correctly`() = runTest {
        iMovEquipVisitTercSharedPreferencesDatasource.start(
            MovEquipVisitTercSharedPreferencesModel()
        )
        iMovEquipVisitTercSharedPreferencesDatasource.setPlaca("PLACA")
        val result = iMovEquipVisitTercSharedPreferencesDatasource.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipVisitTerc, TypeMovEquip.INPUT)
        assertEquals(result.getOrNull()!!.placaMovEquipVisitTerc, "PLACA")
    }

    @Test
    fun `Check altered in table if setTipoVisitTerc execute correctly`() = runTest {
        iMovEquipVisitTercSharedPreferencesDatasource.start(
            MovEquipVisitTercSharedPreferencesModel()
        )
        iMovEquipVisitTercSharedPreferencesDatasource.setTipoVisitTerc(TypeVisitTerc.TERCEIRO)
        val result = iMovEquipVisitTercSharedPreferencesDatasource.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipVisitTerc, TypeMovEquip.INPUT)
        assertEquals(result.getOrNull()!!.tipoVisitTercMovEquipVisitTerc, TypeVisitTerc.TERCEIRO)
        assertEquals(result.getOrNull()!!.observMovEquipVisitTerc, null)
    }

    @Test
    fun `Check altered in table if setVeiculo execute correctly`() = runTest {
        iMovEquipVisitTercSharedPreferencesDatasource.start(
            MovEquipVisitTercSharedPreferencesModel()
        )
        iMovEquipVisitTercSharedPreferencesDatasource.setVeiculo("VEICULO")
        val result = iMovEquipVisitTercSharedPreferencesDatasource.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipVisitTerc, TypeMovEquip.INPUT)
        assertEquals(result.getOrNull()!!.veiculoMovEquipVisitTerc, "VEICULO")
        assertEquals(result.getOrNull()!!.placaMovEquipVisitTerc, null)
    }
}