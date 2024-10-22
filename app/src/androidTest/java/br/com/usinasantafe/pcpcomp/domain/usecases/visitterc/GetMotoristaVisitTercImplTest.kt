package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.external.room.dao.stable.TerceiroDao
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.VisitanteDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.TerceiroRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.VisitanteRoomModel
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class GetMotoristaVisitTercImplTest : KoinTest {

    private val usecase: GetMotoristaVisitTerc by inject()
    private val terceiroDao: TerceiroDao by inject()
    private val visitanteDao: VisitanteDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_failure_if_not_have_data_in_visitante() =
        runTest {
            val result = usecase(
                typeVisitTerc = TypeVisitTerc.VISITANTE,
                idVisitTerc = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> VisitanteRepositoryImpl.get"
            )
        }

    @Test
    fun check_return_motorista_if_have_data_in_visitante() =
        runTest {
            visitanteDao.insertAll(
                listOf(
                    VisitanteRoomModel(
                        idVisitante = 1,
                        cpfVisitante = "326.949.728-88",
                        nomeVisitante = "TESTE",
                        empresaVisitante = "EMPRESA TESTE"
                    )
                )
            )
            val result = usecase(
                typeVisitTerc = TypeVisitTerc.VISITANTE,
                idVisitTerc = 1
            )
            assertTrue(result.isSuccess)
            assertEquals(
                result.getOrNull()!!,
                "326.949.728-88 - TESTE"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_terceiro() =
        runTest {
            val result = usecase(
                typeVisitTerc = TypeVisitTerc.TERCEIRO,
                idVisitTerc = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> TerceiroRepositoryImpl.get"
            )
        }

    @Test
    fun check_return_motorista_if_have_data_in_terceiro() =
        runTest {
            terceiroDao.insertAll(
                listOf(
                    TerceiroRoomModel(
                        idTerceiro = 1,
                        idBDTerceiro = 1,
                        cpfTerceiro = "326.949.728-88",
                        nomeTerceiro = "TESTE",
                        empresaTerceiro = "EMPRESA TESTE"
                    )
                )
            )
            val result = usecase(
                typeVisitTerc = TypeVisitTerc.TERCEIRO,
                idVisitTerc = 1
            )
            assertTrue(result.isSuccess)
            assertEquals(
                result.getOrNull()!!,
                "326.949.728-88 - TESTE"
            )
        }
}