package br.com.usinasantafe.pcpcomp.domain.usecases.background

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcpcomp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipProprioDao
import br.com.usinasantafe.pcpcomp.external.sharedpreferences.datasource.IConfigSharedPreferencesDatasource
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ProcessWorkManagerTest {

    private lateinit var context : Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var IConfigSharedPreferencesDatasource: IConfigSharedPreferencesDatasource
    private lateinit var movEquipProprioDao: MovEquipProprioDao
    private lateinit var db: AppDatabaseRoom

    @Before
    fun before() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("teste", Context.MODE_PRIVATE)
        IConfigSharedPreferencesDatasource = IConfigSharedPreferencesDatasource(sharedPreferences)
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabaseRoom::class.java).allowMainThreadQueries().build()
        movEquipProprioDao = db.movEquipProprioDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun `Check return retry if have mov to send`() = runTest {
//        configSharedPreferencesDatasourceImpl.save(
//            Config(
//                password = "12345",
//                number = 16997417840,
//                version = "6.00",
//                idBD = 1,
//                flagUpdate = FlagUpdate.UPDATED,
//            )
//        )
//        movEquipProprioDao.insert(
//            MovEquipProprioRoomModel(
//                matricVigiaMovEquipProprio = 19759,
//                idLocalMovEquipProprio = 1,
//                tipoMovEquipProprio = TypeMov.INPUT,
//                dthrMovEquipProprio = 1723213270250,
//                idEquipMovEquipProprio = 1,
//                matricColabMovEquipProprio = 19759,
//                destinoMovEquipProprio = "TESTE DESTINO",
//                notaFiscalMovEquipProprio = 123456789,
//                observMovEquipProprio = "TESTE OBSERV",
//                statusMovEquipProprio = StatusData.OPEN,
//                statusSendMovEquipProprio = StatusSend.SEND
//            )
//        )
//        val worker = TestListenableWorkerBuilder<ProcessWorkManager>(
//            context = context,
//        ).build()
//        val result = worker.doWork()
//        assertEquals(result, ListenableWorker.Result.retry())
    }
}