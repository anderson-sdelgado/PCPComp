package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable

import br.com.usinasantafe.pcpcomp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.RLocalFluxoDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.utils.updatePercentage
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class IUpdateRLocalFluxoTest : KoinTest {

    private val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()
    private val usecase: UpdateRLocalFluxo by inject()
    private val rLocalFluxoDao: RLocalFluxoDao by inject()

    @Test
    fun verify_return_data_if_success_usecase() =
        runTest {
            var pos = 0f
            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setBody(resultRLocalFluxoUpdate))
            loadKoinModules(generateTestAppComponent(server.url("/").toString()))
            configSharedPreferences.save(
                Config(
                    number = 16997417840,
                    password = "12345",
                    version = "6.00",
                    idBD = 1
                )
            )
            val result = usecase(
                sizeAll = 16f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(list.count(), 3)
            assertEquals(
                list[0],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_r_local_fluxo do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_r_local_fluxo",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_r_local_fluxo",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            val listData = rLocalFluxoDao.listAll()
            assertEquals(
                listData.size,
                1
            )
        }
}

val resultRLocalFluxoUpdate = """
    [{"idRLocalFluxo":1,"idFluxo":1,"idLocal":1}]
""".trimIndent()
