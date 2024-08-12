package br.com.usinasantafe.pcpcomp.domain.usecases.common

import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipProprioDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class CloseAllMovOpenImplTest: KoinTest {

    private val usecase: CloseAllMovOpen by inject()
    private val movEquipProprioDao: MovEquipProprioDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    private val movEquipProprioRoomModel = MovEquipProprioRoomModel(
        idMovEquipProprio = 1,
        nroMatricVigiaMovEquipProprio = 19759,
        idLocalMovEquipProprio = 1,
        tipoMovEquipProprio = TypeMov.INPUT,
        dthrMovEquipProprio = 1723213270250,
        idEquipMovEquipProprio = 1,
        nroMatricColabMovEquipProprio = 19759,
        destinoMovEquipProprio = "TESTE DESTINO",
        nroNotaFiscalMovEquipProprio = 123456789,
        observMovEquipProprio = "TESTE OBSERV",
        statusMovEquipProprio = StatusData.OPEN,
        statusSendMovEquipProprio = StatusSend.SEND
    )

    @Test
    fun check_return_true_if_not_have_mov_open() = runTest {
        val result = usecase()
        assertTrue(result.isSuccess)
    }

    @Test
    fun check_close_mov_open() = runTest {
        movEquipProprioDao.insert(
            movEquipProprioRoomModel
        )
        val movEquipProprioRoomModelBefore = movEquipProprioDao.getId(movEquipProprioRoomModel.idMovEquipProprio!!)
        assertEquals(movEquipProprioRoomModelBefore.statusMovEquipProprio, StatusData.OPEN)
        val result = usecase()
        val movEquipProprioRoomModelAfter = movEquipProprioDao.getId(movEquipProprioRoomModel.idMovEquipProprio!!)
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, true);
        assertEquals(movEquipProprioRoomModelAfter.statusMovEquipProprio, StatusData.CLOSE)
    }
}