package br.com.usinasantafe.pcpcomp.domain.usecases.initial

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.FluxoDao
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.RLocalFluxoDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.FluxoRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.RLocalFluxoRoomModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals

class IGetFlowListTest: KoinTest {

    private val usecase: GetFlowList by inject()
    private val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()
    private val rLocalFluxoDao: RLocalFluxoDao by inject()
    private val fluxoDao: FluxoDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_true_and_data_returned() =
        runTest {
            configSharedPreferences.save(
                Config(
                    matricVigia = 19759,
                    idLocal = 1
                )
            )
            rLocalFluxoDao.insertAll(
                listOf(
                    RLocalFluxoRoomModel(
                        idRLocalFluxo = 1,
                        idLocal = 1,
                        idFluxo = 1
                    ),
                    RLocalFluxoRoomModel(
                        idRLocalFluxo = 2,
                        idLocal = 1,
                        idFluxo = 2
                    )
                )
            )
            fluxoDao.insertAll(
                listOf(
                    FluxoRoomModel(
                        idFluxo = 1,
                        descrFluxo = "Mov. Equip. Próprio"
                    ),
                    FluxoRoomModel(
                        idFluxo = 2,
                        descrFluxo = "Mov. Equip. Visitante/Terceiro"
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                2
            )
        }

    @Test
    fun check_return_failure_if_not_have_data() =
        runTest {
            configSharedPreferences.save(
                Config(
                    matricVigia = 19759,
                    idLocal = 1
                )
            )
            rLocalFluxoDao.insertAll(
                listOf(
                    RLocalFluxoRoomModel(
                        idRLocalFluxo = 1,
                        idLocal = 1,
                        idFluxo = 1
                    ),
                    RLocalFluxoRoomModel(
                        idRLocalFluxo = 2,
                        idLocal = 1,
                        idFluxo = 2
                    )
                )
            )
            fluxoDao.insertAll(
                listOf(
                    FluxoRoomModel(
                        idFluxo = 1,
                        descrFluxo = "Mov. Equip. Próprio"
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> FluxoRepositoryImpl.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

}