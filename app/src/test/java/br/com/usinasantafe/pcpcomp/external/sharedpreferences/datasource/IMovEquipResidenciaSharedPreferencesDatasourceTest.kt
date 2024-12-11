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
    private lateinit var IMovEquipResidenciaSharedPreferencesDatasource : IMovEquipResidenciaSharedPreferencesDatasource

    @Before
    fun before() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("teste", Context.MODE_PRIVATE)
        IMovEquipResidenciaSharedPreferencesDatasource = IMovEquipResidenciaSharedPreferencesDatasource(sharedPreferences)
    }

    @Test
    fun `Check return data correct if Start execute correctly`() = runTest {
        IMovEquipResidenciaSharedPreferencesDatasource.start(
            MovEquipResidenciaSharedPreferencesModel()
        )
        val result = IMovEquipResidenciaSharedPreferencesDatasource.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipResidencia, TypeMovEquip.INPUT)
    }

    @Test
    fun `Check altered in table if setMotorista execute correctly`() = runTest {
        IMovEquipResidenciaSharedPreferencesDatasource.start(
            MovEquipResidenciaSharedPreferencesModel()
        )
        IMovEquipResidenciaSharedPreferencesDatasource.setMotorista("MOTORISTA")
        val result = IMovEquipResidenciaSharedPreferencesDatasource.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipResidencia, TypeMovEquip.INPUT)
        assertEquals(result.getOrNull()!!.motoristaMovEquipResidencia, "MOTORISTA")
    }

    @Test
    fun `Check altered in table if setObserv execute correctly`() = runTest {
        IMovEquipResidenciaSharedPreferencesDatasource.start(
            MovEquipResidenciaSharedPreferencesModel()
        )
        IMovEquipResidenciaSharedPreferencesDatasource.setObserv("OBSERV")
        val result = IMovEquipResidenciaSharedPreferencesDatasource.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipResidencia, TypeMovEquip.INPUT)
        assertEquals(result.getOrNull()!!.observMovEquipResidencia, "OBSERV")
    }

    @Test
    fun `Check altered in table if setObserv execute correctly and value is null`() = runTest {
        IMovEquipResidenciaSharedPreferencesDatasource.start(
            MovEquipResidenciaSharedPreferencesModel()
        )
        IMovEquipResidenciaSharedPreferencesDatasource.setObserv(null)
        val result = IMovEquipResidenciaSharedPreferencesDatasource.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipResidencia, TypeMovEquip.INPUT)
        assertEquals(result.getOrNull()!!.observMovEquipResidencia, null)
    }

    @Test
    fun `Check altered in table if setPlaca execute correctly`() = runTest {
        IMovEquipResidenciaSharedPreferencesDatasource.start(
            MovEquipResidenciaSharedPreferencesModel()
        )
        IMovEquipResidenciaSharedPreferencesDatasource.setPlaca("PLACA")
        val result = IMovEquipResidenciaSharedPreferencesDatasource.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipResidencia, TypeMovEquip.INPUT)
        assertEquals(result.getOrNull()!!.placaMovEquipResidencia, "PLACA")
        assertEquals(result.getOrNull()!!.observMovEquipResidencia, null)
    }

    @Test
    fun `Check altered in table if setVeiculo execute correctly`() = runTest {
        IMovEquipResidenciaSharedPreferencesDatasource.start(
            MovEquipResidenciaSharedPreferencesModel()
        )
        IMovEquipResidenciaSharedPreferencesDatasource.setVeiculo("VEICULO")
        val result = IMovEquipResidenciaSharedPreferencesDatasource.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipResidencia, TypeMovEquip.INPUT)
        assertEquals(result.getOrNull()!!.veiculoMovEquipResidencia, "VEICULO")
        assertEquals(result.getOrNull()!!.placaMovEquipResidencia, null)
    }

}