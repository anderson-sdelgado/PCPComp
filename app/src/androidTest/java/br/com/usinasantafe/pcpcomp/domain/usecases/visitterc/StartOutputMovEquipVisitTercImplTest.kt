package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipVisitTercDao
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipVisitTercPassagDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipVisitTercPassagSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipVisitTercSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipVisitTercPassagRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipVisitTercRoomModel
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class StartOutputMovEquipVisitTercImplTest : KoinTest {

    private val usecase: StartOutputMovEquipVisitTerc by inject()
    private val movEquipVisitTercDao: MovEquipVisitTercDao by inject()
    private val movEquipVisitTercPassagDao: MovEquipVisitTercPassagDao by inject()
    private val movEquipVisitTercSharedPreferencesDatasource:
            MovEquipVisitTercSharedPreferencesDatasource by inject()
    private val movEquipVisitTercPassagSharedPreferencesDatasource:
            MovEquipVisitTercPassagSharedPreferencesDatasource by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_failure_if_not_have_data() =
        runTest {
            val result = usecase(id = 1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercRepositoryImpl.get"
            )
        }

    @Test
    fun check_return_true_if_start_output_execute_success_and_list_passag_empty() =
        runTest {
            movEquipVisitTercDao.insert(
                MovEquipVisitTercRoomModel(
                    idMovEquipVisitTerc = 1,
                    nroMatricVigiaMovEquipVisitTerc = 19759,
                    idLocalMovEquipVisitTerc = 1,
                    tipoMovEquipVisitTerc = TypeMov.INPUT,
                    idVisitTercMovEquipVisitTerc = 1000,
                    tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                    dthrMovEquipVisitTerc = 1723213270250,
                    veiculoMovEquipVisitTerc = "VEICULO TESTE",
                    placaMovEquipVisitTerc = "PLACA TESTE",
                    destinoMovEquipVisitTerc = "DESTINO TESTE",
                    observMovEquipVisitTerc = "OBSERV TESTE",
                    statusMovEquipVisitTerc = StatusData.OPEN,
                    statusSendMovEquipVisitTerc = StatusSend.SEND,
                    statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
                )
            )
            val result = usecase(id = 1)
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
            val resultEntity = movEquipVisitTercSharedPreferencesDatasource.get()
            assertTrue(resultEntity.isSuccess)
            val entity = resultEntity.getOrNull()!!
            assertEquals(entity.idVisitTercMovEquipVisitTerc, 1000)
            assertEquals(entity.tipoMovEquipVisitTerc, TypeMov.OUTPUT)
        }

    @Test
    fun check_return_true_if_start_output_execute_success() =
        runTest {
            movEquipVisitTercDao.insert(
                MovEquipVisitTercRoomModel(
                    idMovEquipVisitTerc = 1,
                    nroMatricVigiaMovEquipVisitTerc = 19759,
                    idLocalMovEquipVisitTerc = 1,
                    tipoMovEquipVisitTerc = TypeMov.INPUT,
                    idVisitTercMovEquipVisitTerc = 1000,
                    tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                    dthrMovEquipVisitTerc = 1723213270250,
                    veiculoMovEquipVisitTerc = "VEICULO TESTE",
                    placaMovEquipVisitTerc = "PLACA TESTE",
                    destinoMovEquipVisitTerc = "DESTINO TESTE",
                    observMovEquipVisitTerc = "OBSERV TESTE",
                    statusMovEquipVisitTerc = StatusData.OPEN,
                    statusSendMovEquipVisitTerc = StatusSend.SEND,
                    statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
                )
            )
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTercPassag = 1,
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 10
                )
            )
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTercPassag = 2,
                    idMovEquipVisitTerc = 1,
                    idVisitTerc = 20
                )
            )
            val result = usecase(id = 1)
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
            val resultEntity = movEquipVisitTercSharedPreferencesDatasource.get()
            assertTrue(resultEntity.isSuccess)
            val entity = resultEntity.getOrNull()!!
            assertEquals(entity.idVisitTercMovEquipVisitTerc, 1000)
            assertEquals(entity.tipoMovEquipVisitTerc, TypeMov.OUTPUT)
            val resultPassagList = movEquipVisitTercPassagSharedPreferencesDatasource.list()
            assertTrue(resultPassagList.isSuccess)
            val passagList = resultPassagList.getOrNull()!!
            assertEquals(passagList.size, 2)
            assertEquals(passagList[0], 10)
            assertEquals(passagList[1], 20)
        }

}