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
    fun `Check return data correct the Config SharedPreferences internal`() = runTest {
        val data = MovEquipProprioSharedPreferencesModel(
            idEquipMovEquipProprio = 1L,
            dthrMovEquipProprio = Date(),
            tipoMovEquipProprio = TypeMov.INPUT
        )
        movEquipProprioSharedPreferencesDatasourceImpl.save(data)
        val result = movEquipProprioSharedPreferencesDatasourceImpl.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.idEquipMovEquipProprio, 1L)
    }

}