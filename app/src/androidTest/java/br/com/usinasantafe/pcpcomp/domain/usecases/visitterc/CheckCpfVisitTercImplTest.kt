package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.external.room.dao.stable.VisitanteDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipVisitTercSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.VisitanteRoomModel
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class CheckCpfVisitTercImplTest : KoinTest {

    private val usecase: CheckCpfVisitTerc by inject()
    private val movEquipVisitTercSharedPreferencesDatasource: MovEquipVisitTercSharedPreferencesDatasource by inject()
    private val visitanteDao: VisitanteDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_true_if_cpf_is_valid() = runTest {
        movEquipVisitTercSharedPreferencesDatasource.start()
        movEquipVisitTercSharedPreferencesDatasource.setTipoVisitTerc(
            TypeVisitTerc.VISITANTE
        )
        visitanteDao.insertAll(
            listOf(
                VisitanteRoomModel(
                    idVisitante = 1,
                    nomeVisitante = "TESTE",
                    cpfVisitante = "326.949.728-88",
                    empresaVisitante = "EMPRESA TESTE"
                )
            )
        )
        val result = usecase(
            cpf = "326.949.728-88",
            flowApp = FlowApp.ADD,
            id = 0
        )
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }
}