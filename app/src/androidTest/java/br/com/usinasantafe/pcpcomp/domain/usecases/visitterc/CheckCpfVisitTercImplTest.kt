package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.external.room.dao.stable.TerceiroDao
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.VisitanteDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipVisitTercSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.TerceiroRoomModel
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
    private val terceiroDao: TerceiroDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_true_if_cpf_is_valid_visitante() = runTest {
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

    @Test
    fun check_return_true_if_cpf_is_valid_terceiro() = runTest {
        movEquipVisitTercSharedPreferencesDatasource.start()
        movEquipVisitTercSharedPreferencesDatasource.setTipoVisitTerc(
            TypeVisitTerc.TERCEIRO
        )
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
            cpf = "326.949.728-88",
            flowApp = FlowApp.ADD,
            id = 0
        )
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }

    @Test
    fun check_return_failure_if_have_not_data_in_shared_preferences() = runTest {
        val result = usecase(
            cpf = "326.949.728-88",
            flowApp = FlowApp.ADD,
            id = 0
        )
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipVisitTercSharedPreferencesDatasourceImpl.get"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "java.lang.NullPointerException"
        )
    }

    @Test
    fun check_return_failure_if_have_data_in_shared_preferences_and_have_not_type() = runTest {
        movEquipVisitTercSharedPreferencesDatasource.start()
        val result = usecase(
            cpf = "326.949.728-88",
            flowApp = FlowApp.ADD,
            id = 0
        )
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Usecase -> CheckCpfVisitTerc"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "java.lang.NullPointerException"
        )
    }

    @Test
    fun check_return_false_if_have_not_data_in_visitante() = runTest {
        movEquipVisitTercSharedPreferencesDatasource.start()
        movEquipVisitTercSharedPreferencesDatasource.setTipoVisitTerc(
            TypeVisitTerc.VISITANTE
        )
        val result = usecase(
            cpf = "326.949.728-88",
            flowApp = FlowApp.ADD,
            id = 0
        )
        assertTrue(result.isSuccess)
        assertFalse(result.getOrNull()!!)
    }

    @Test
    fun check_return_false_if_not_exist_cpf_in_visitante() = runTest {
        movEquipVisitTercSharedPreferencesDatasource.start()
        movEquipVisitTercSharedPreferencesDatasource.setTipoVisitTerc(
            TypeVisitTerc.VISITANTE
        )
        visitanteDao.insertAll(
            listOf(
                VisitanteRoomModel(
                    idVisitante = 1,
                    nomeVisitante = "TESTE",
                    cpfVisitante = "123.456.789-00",
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
        assertFalse(result.getOrNull()!!)
    }

    @Test
    fun check_return_false_if_have_not_data_in_terceiro() = runTest {
        movEquipVisitTercSharedPreferencesDatasource.start()
        movEquipVisitTercSharedPreferencesDatasource.setTipoVisitTerc(
            TypeVisitTerc.TERCEIRO
        )
        val result = usecase(
            cpf = "326.949.728-88",
            flowApp = FlowApp.ADD,
            id = 0
        )
        assertTrue(result.isSuccess)
        assertFalse(result.getOrNull()!!)
    }

    @Test
    fun check_return_false_if_not_exist_cpf_in_terceiro() = runTest {
        movEquipVisitTercSharedPreferencesDatasource.start()
        movEquipVisitTercSharedPreferencesDatasource.setTipoVisitTerc(
            TypeVisitTerc.TERCEIRO
        )
        terceiroDao.insertAll(
            listOf(
                TerceiroRoomModel(
                    idTerceiro = 1,
                    idBDTerceiro = 1,
                    cpfTerceiro = "123.456.789-00",
                    nomeTerceiro = "TESTE",
                    empresaTerceiro = "EMPRESA TESTE"
                )
            )
        )
        val result = usecase(
            cpf = "326.949.728-88",
            flowApp = FlowApp.ADD,
            id = 0
        )
        assertTrue(result.isSuccess)
        assertFalse(result.getOrNull()!!)
    }

}