package br.com.usinasantafe.pcpcomp.external.sharedpreferences.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcpcomp.utils.TypeMovKey
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class IMovChaveSharedPreferencesDatasourceTest {

    private lateinit var context : Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var iMovChaveSharedPreferencesDatasource : IMovChaveSharedPreferencesDatasource


    @Before
    fun before() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("teste", Context.MODE_PRIVATE)
        iMovChaveSharedPreferencesDatasource = IMovChaveSharedPreferencesDatasource(sharedPreferences)
    }

    @Test
    fun `get - Check return failure if not have data`() =
        runTest {
            val result = iMovChaveSharedPreferencesDatasource.get()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> IMovChaveSharedPreferencesDatasource.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `start and get - Check return correct if function execute successfully`() =
        runTest {
            val resultStart = iMovChaveSharedPreferencesDatasource.start()
            assertEquals(
                resultStart.isSuccess,
                true
            )
            val resultGet = iMovChaveSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val model = resultGet.getOrNull()!!
            assertEquals(
                model.tipoMovChave,
                TypeMovKey.REMOVE
            )
        }

    @Test
    fun `setIdChave - Check return failure if have error in not started`() =
        runTest {
            val resultStart = iMovChaveSharedPreferencesDatasource.setIdChave(1)
            assertEquals(
                resultStart.isFailure,
                true
            )
            assertEquals(
                resultStart.exceptionOrNull()!!.message,
                "Failure Datasource -> IMovChaveSharedPreferencesDatasource.get"
            )
            assertEquals(
                resultStart.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `setIdChave - Check return correct if function execute successfully`() =
        runTest {
            val resultStart = iMovChaveSharedPreferencesDatasource.start()
            assertEquals(
                resultStart.isSuccess,
                true
            )
            val resultSetIdChave = iMovChaveSharedPreferencesDatasource.setIdChave(1)
            assertEquals(
                resultSetIdChave.isSuccess,
                true
            )
            val resultGet = iMovChaveSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val model = resultGet.getOrNull()!!
            assertEquals(
                model.idChaveMovChave,
                1
            )
        }

    @Test
    fun `setMatriColab - Check return failure if have error in not started`() =
        runTest {
            val resultStart = iMovChaveSharedPreferencesDatasource.setMatricColab(19759)
            assertEquals(
                resultStart.isFailure,
                true
            )
            assertEquals(
                resultStart.exceptionOrNull()!!.message,
                "Failure Datasource -> IMovChaveSharedPreferencesDatasource.get"
            )
            assertEquals(
                resultStart.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `setMatriColab - Check return correct if function execute successfully`() =
        runTest {
            val resultStart = iMovChaveSharedPreferencesDatasource.start()
            assertEquals(
                resultStart.isSuccess,
                true
            )
            val resultSetIdChave = iMovChaveSharedPreferencesDatasource.setMatricColab(19759)
            assertEquals(
                resultSetIdChave.isSuccess,
                true
            )
            val resultGet = iMovChaveSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val model = resultGet.getOrNull()!!
            assertEquals(
                model.matricColabMovChave,
                19759
            )
        }

}