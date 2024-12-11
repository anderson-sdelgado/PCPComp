package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable

import br.com.usinasantafe.pcpcomp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.ChaveDao
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

class IUpdateChaveTest : KoinTest {

    private val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()
    private val usecase: UpdateChave by inject()
    private val chaveDao: ChaveDao by inject()

    @Test
    fun verify_return_data_if_success_usecase() =
        runTest {
            var pos = 0f
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultChaveUpdate)
            )
            loadKoinModules(
                generateTestAppComponent(
                    server.url("/").toString()
                )
            )
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
                    msgProgress = "Recuperando dados da tabela tb_chave do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_chave",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_chave",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            val roomModelList = chaveDao.listAll()
            assertEquals(
                roomModelList.size,
                1
            )
        }
}

val resultChaveUpdate = """
    [{"idChave":1,"descrChave":"01 - TI","idLocalTrab":1}]
""".trimIndent()
