package br.com.usinasantafe.pcpcomp.external.sharedpreferences.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.MovEquipResidenciaSharedPreferencesModel
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.MovEquipVisitTercSharedPreferencesModel
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MovEquipResidenciaSharedPreferencesDatasourceImplTest {

    private lateinit var context : Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var movEquipResidenciaSharedPreferencesDatasourceImpl : MovEquipResidenciaSharedPreferencesDatasourceImpl

    @Before
    fun before() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("teste", Context.MODE_PRIVATE)
        movEquipResidenciaSharedPreferencesDatasourceImpl = MovEquipResidenciaSharedPreferencesDatasourceImpl(sharedPreferences)
    }

    @Test
    fun `Check return data correct if Start execute correctly`() = runTest {
        movEquipResidenciaSharedPreferencesDatasourceImpl.start(
            MovEquipResidenciaSharedPreferencesModel()
        )
        val result = movEquipResidenciaSharedPreferencesDatasourceImpl.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipResidencia, TypeMov.INPUT)
    }

    @Test
    fun `Check altered in table if setMotorista execute correctly`() = runTest {
        movEquipResidenciaSharedPreferencesDatasourceImpl.start(
            MovEquipResidenciaSharedPreferencesModel()
        )
        movEquipResidenciaSharedPreferencesDatasourceImpl.setMotorista("MOTORISTA")
        val result = movEquipResidenciaSharedPreferencesDatasourceImpl.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipResidencia, TypeMov.INPUT)
        assertEquals(result.getOrNull()!!.motoristaMovEquipResidencia, "MOTORISTA")
    }

    @Test
    fun `Check altered in table if setObserv execute correctly`() = runTest {
        movEquipResidenciaSharedPreferencesDatasourceImpl.start(
            MovEquipResidenciaSharedPreferencesModel()
        )
        movEquipResidenciaSharedPreferencesDatasourceImpl.setObserv("OBSERV")
        val result = movEquipResidenciaSharedPreferencesDatasourceImpl.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipResidencia, TypeMov.INPUT)
        assertEquals(result.getOrNull()!!.observMovEquipResidencia, "OBSERV")
    }

    @Test
    fun `Check altered in table if setObserv execute correctly and value is null`() = runTest {
        movEquipResidenciaSharedPreferencesDatasourceImpl.start(
            MovEquipResidenciaSharedPreferencesModel()
        )
        movEquipResidenciaSharedPreferencesDatasourceImpl.setObserv(null)
        val result = movEquipResidenciaSharedPreferencesDatasourceImpl.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipResidencia, TypeMov.INPUT)
        assertEquals(result.getOrNull()!!.observMovEquipResidencia, null)
    }

    @Test
    fun `Check altered in table if setPlaca execute correctly`() = runTest {
        movEquipResidenciaSharedPreferencesDatasourceImpl.start(
            MovEquipResidenciaSharedPreferencesModel()
        )
        movEquipResidenciaSharedPreferencesDatasourceImpl.setPlaca("PLACA")
        val result = movEquipResidenciaSharedPreferencesDatasourceImpl.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipResidencia, TypeMov.INPUT)
        assertEquals(result.getOrNull()!!.placaMovEquipResidencia, "PLACA")
        assertEquals(result.getOrNull()!!.observMovEquipResidencia, null)
    }

    @Test
    fun `Check altered in table if setVeiculo execute correctly`() = runTest {
        movEquipResidenciaSharedPreferencesDatasourceImpl.start(
            MovEquipResidenciaSharedPreferencesModel()
        )
        movEquipResidenciaSharedPreferencesDatasourceImpl.setVeiculo("VEICULO")
        val result = movEquipResidenciaSharedPreferencesDatasourceImpl.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.tipoMovEquipResidencia, TypeMov.INPUT)
        assertEquals(result.getOrNull()!!.veiculoMovEquipResidencia, "VEICULO")
        assertEquals(result.getOrNull()!!.placaMovEquipResidencia, null)
    }

}