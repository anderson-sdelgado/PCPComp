package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Equip
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipProprioDao
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipProprioEquipSegDao
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipProprioPassagDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioEquipSegRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioPassagRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class GetDetalheProprioImplTest : KoinTest {

    private val usecase: GetDetalheProprio by inject()
    private val movEquipProprioDao: MovEquipProprioDao by inject()
    private val movEquipProprioEquipSegDao: MovEquipProprioEquipSegDao by inject()
    private val movEquipProprioPassagDao: MovEquipProprioPassagDao by inject()
    private val colabRepository: ColabRepository by inject()
    private val equipRepository: EquipRepository by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_success_if_recover_detalhe_proprio_execute_with_success() = runTest {
        movEquipProprioDao.insert(
            MovEquipProprioRoomModel(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMov.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 10,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND
            )
        )
        movEquipProprioEquipSegDao.insertAll(
            listOf(
                MovEquipProprioEquipSegRoomModel(
                    idMovEquipProprioEquipSeg = 1,
                    idMovEquipProprio = 1,
                    idEquip = 100
                )
            )
        )
        movEquipProprioPassagDao.insertAll(
            listOf(
                MovEquipProprioPassagRoomModel(
                    idMovEquipProprioPassag = 1,
                    idMovEquipProprio = 1,
                    matricColab = 19035
                )
            )
        )
        colabRepository.addAll(
            listOf(
                Colab(
                    matricColab = 19759,
                    nomeColab = "ANDERSON DA SILVA DELGADO"
                ),
                Colab(
                    matricColab = 19035,
                    nomeColab = "JOSE DONIZETE"
                )
            )
        )
        equipRepository.addAll(
            listOf(
                Equip(
                    idEquip = 10,
                    nroEquip = 100
                ),
                Equip(
                    idEquip = 100,
                    nroEquip = 200
                )
            )
        )
        val result = usecase(1)
        assertTrue(result.isSuccess)
        val model = result.getOrNull()!!
        assertEquals(model.dthr, "DATA/HORA: 09/08/2024 11:21")
        assertEquals(model.observ, "OBSERVAÇÕES: TESTE OBSERV")
    }

    @Test
    fun check_return_success_even_if_not_have_equip_seg_and_passag() = runTest {
        movEquipProprioDao.insert(
            MovEquipProprioRoomModel(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMov.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 10,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND
            )
        )
        colabRepository.addAll(
            listOf(
                Colab(
                    matricColab = 19759,
                    nomeColab = "ANDERSON DA SILVA DELGADO"
                ),
                Colab(
                    matricColab = 19035,
                    nomeColab = "JOSE DONIZETE"
                )
            )
        )
        equipRepository.addAll(
            listOf(
                Equip(
                    idEquip = 10,
                    nroEquip = 100
                ),
                Equip(
                    idEquip = 100,
                    nroEquip = 200
                )
            )
        )
        val result = usecase(1)
        assertTrue(result.isSuccess)
        val model = result.getOrNull()!!
        assertEquals(model.dthr, "DATA/HORA: 09/08/2024 11:21")
        assertEquals(model.observ, "OBSERVAÇÕES: TESTE OBSERV")
    }

    @Test
    fun check_return_failure_if_not_have_equip_in_table() = runTest {
        movEquipProprioDao.insert(
            MovEquipProprioRoomModel(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMov.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 10,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND
            )
        )
        colabRepository.addAll(
            listOf(
                Colab(
                    matricColab = 19759,
                    nomeColab = "ANDERSON DA SILVA DELGADO"
                ),
                Colab(
                    matricColab = 19035,
                    nomeColab = "JOSE DONIZETE"
                )
            )
        )
        val result = usecase(1)
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Repository -> EquipRepositoryImpl.getNro")
    }

    @Test
    fun check_return_failure_if_not_have_colab_in_table() = runTest {
        movEquipProprioDao.insert(
            MovEquipProprioRoomModel(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMov.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 10,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND
            )
        )
        equipRepository.addAll(
            listOf(
                Equip(
                    idEquip = 10,
                    nroEquip = 100
                ),
                Equip(
                    idEquip = 100,
                    nroEquip = 200
                )
            )
        )
        val result = usecase(1)
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Usecase -> RecoverDetalheProprioImpl")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "java.lang.NullPointerException")
    }
}