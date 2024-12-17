package br.com.usinasantafe.pcpcomp.external.sharedpreferences.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.MovEquipResidenciaSharedPreferencesModel
import br.com.usinasantafe.pcpcomp.utils.TypeMovEquip
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IMovEquipResidenciaSharedPreferencesDatasourceTest {

    private lateinit var context : Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var iMovEquipResidenciaSharedPreferencesDatasource : IMovEquipResidenciaSharedPreferencesDatasource

    @Before
    fun before() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("teste", Context.MODE_PRIVATE)
        iMovEquipResidenciaSharedPreferencesDatasource = IMovEquipResidenciaSharedPreferencesDatasource(sharedPreferences)
    }

    @Test
    fun `Check return data correct if Start execute correctly`() = runTest {
        iMovEquipResidenciaSharedPreferencesDatasource.start(
            MovEquipResidenciaSharedPreferencesModel()
        )
        val result = iMovEquipResidenciaSharedPreferencesDatasource.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipResidencia, TypeMovEquip.INPUT)
    }

    @Test
    fun `Check altered in table if setMotorista execute correctly`() = runTest {
        iMovEquipResidenciaSharedPreferencesDatasource.start(
            MovEquipResidenciaSharedPreferencesModel()
        )
        iMovEquipResidenciaSharedPreferencesDatasource.setMotorista("MOTORISTA")
        val result = iMovEquipResidenciaSharedPreferencesDatasource.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipResidencia, TypeMovEquip.INPUT)
        assertEquals(result.getOrNull()!!.motoristaMovEquipResidencia, "MOTORISTA")
    }

    @Test
    fun `Check altered in table if setObserv execute correctly`() = runTest {
        iMovEquipResidenciaSharedPreferencesDatasource.start(
            MovEquipResidenciaSharedPreferencesModel()
        )
        iMovEquipResidenciaSharedPreferencesDatasource.setObserv("OBSERV")
        val result = iMovEquipResidenciaSharedPreferencesDatasource.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipResidencia, TypeMovEquip.INPUT)
        assertEquals(result.getOrNull()!!.observMovEquipResidencia, "OBSERV")
    }

    @Test
    fun `Check altered in table if setObserv execute correctly and value is null`() = runTest {
        iMovEquipResidenciaSharedPreferencesDatasource.start(
            MovEquipResidenciaSharedPreferencesModel()
        )
        iMovEquipResidenciaSharedPreferencesDatasource.setObserv(null)
        val result = iMovEquipResidenciaSharedPreferencesDatasource.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipResidencia, TypeMovEquip.INPUT)
        assertEquals(result.getOrNull()!!.observMovEquipResidencia, null)
    }

    @Test
    fun `Check altered in table if setPlaca execute correctly`() = runTest {
        iMovEquipResidenciaSharedPreferencesDatasource.start(
            MovEquipResidenciaSharedPreferencesModel()
        )
        iMovEquipResidenciaSharedPreferencesDatasource.setPlaca("PLACA")
        val result = iMovEquipResidenciaSharedPreferencesDatasource.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipResidencia, TypeMovEquip.INPUT)
        assertEquals(result.getOrNull()!!.placaMovEquipResidencia, "PLACA")
        assertEquals(result.getOrNull()!!.observMovEquipResidencia, null)
    }

    @Test
    fun `Check altered in table if setVeiculo execute correctly`() = runTest {
        iMovEquipResidenciaSharedPreferencesDatasource.start(
            MovEquipResidenciaSharedPreferencesModel()
        )
        iMovEquipResidenciaSharedPreferencesDatasource.setVeiculo("VEICULO")
        val result = iMovEquipResidenciaSharedPreferencesDatasource.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipResidencia, TypeMovEquip.INPUT)
        assertEquals(result.getOrNull()!!.veiculoMovEquipResidencia, "VEICULO")
        assertEquals(result.getOrNull()!!.placaMovEquipResidencia, null)
    }

}