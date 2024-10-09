package br.com.usinasantafe.pcpcomp.infra.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.VisitanteRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable.VisitanteRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.VisitanteRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class VisitanteRepositoryImplTest {

    private val visitanteRoomDatasource = mock<VisitanteRoomDatasource>()
    private val visitanteRetrofitDatasource = mock<VisitanteRetrofitDatasource>()
    private fun getRepository() = VisitanteRepositoryImpl(
        visitanteRoomDatasource,
        visitanteRetrofitDatasource
    )

    @Test
    fun `Check execution correct deleteAll`() = runTest {
        whenever(
            visitanteRoomDatasource.deleteAll()
        ).thenReturn(
            Result.success(true)
        )
        val repository = getRepository()
        val result = repository.deleteAll()
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }

    @Test
    fun `Check execution incorrect deleteAll`() = runTest {
        whenever(
            visitanteRoomDatasource.deleteAll()
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "VisitanteRoomDatasource.deleteAll",
                    cause = Exception()
                )
            )
        )
        val repository = getRepository()
        val result = repository.deleteAll()
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> VisitanteRoomDatasource.deleteAll"
        )
    }

    val token = "Bearer E49AD0C7AAA85FA6AB01FFD4AF7205C7"

    @Test
    fun `Check failure Datasource in recover data`() = runTest {
        whenever(
            visitanteRetrofitDatasource.recoverAll(token)
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "VisitanteRetrofitDatasource.recoverAll",
                    cause = Exception()
                )
            )
        )
        val repository = getRepository()
        val result = repository.recoverAll(token)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> VisitanteRetrofitDatasource.recoverAll"
        )
    }

    @Test
    fun `Check data is correct in recover data`() = runTest {
        val entityList = listOf(
            Visitante(
                idVisitante = 1,
                nomeVisitante = "Visitante",
                cpfVisitante = "123.456.789-00",
                empresaVisitante = "Empresa Visitante"
            )
        )
        whenever(
            visitanteRetrofitDatasource.recoverAll(token)
        ).thenReturn(
            Result.success(entityList)
        )
        val repository = getRepository()
        val result = repository.recoverAll(token)
        assertTrue(result.isSuccess)
        val resultList = result.getOrNull()!!
        assertTrue(resultList.isNotEmpty())
        val entity = resultList[0]
        assertEquals(
            entity.nomeVisitante,
            "Visitante"
        )
    }

    @Test
    fun `Check execution correct addAll`() = runTest {
        val visitanteList = listOf(
            Visitante(
                idVisitante = 1,
                nomeVisitante = "Visitante",
                cpfVisitante = "123.456.789-00",
                empresaVisitante = "Empresa Visitante"
            )
        )
        val visitanteRoomModelList = listOf(
            VisitanteRoomModel(
                idVisitante = 1,
                nomeVisitante = "Visitante",
                cpfVisitante = "123.456.789-00",
                empresaVisitante = "Empresa Visitante"
            )
        )
        whenever(
            visitanteRoomDatasource.addAll(visitanteRoomModelList)
        ).thenReturn(
            Result.success(
                true
            )
        )
        val repository = getRepository()
        val result = repository.addAll(visitanteList)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }

    @Test
    fun `Check execution incorrect addAll`() = runTest {
        val visitanteList = listOf(
            Visitante(
                idVisitante = 1,
                nomeVisitante = "Visitante",
                cpfVisitante = "123.456.789-00",
                empresaVisitante = "Empresa Visitante"
            )
        )
        val visitanteRoomModelList = listOf(
            VisitanteRoomModel(
                idVisitante = 1,
                nomeVisitante = "Visitante",
                cpfVisitante = "123.456.789-00",
                empresaVisitante = "Empresa Visitante"
            )
        )
        whenever(
            visitanteRoomDatasource.addAll(visitanteRoomModelList)
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "VisitanteRoomDatasource.addAll",
                    cause = Exception()
                )
            )
        )
        val repository = getRepository()
        val result = repository.addAll(visitanteList)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> VisitanteRoomDatasource.addAll"
        )
    }

    @Test
    fun `Check return failure if have error in VisitanteRoomDatasource checkCPF`() =
        runTest {
            whenever(
                visitanteRoomDatasource.checkCpf("123.456.789-00")
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "VisitanteRoomDatasource.checkCPF",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.checkCPF("123.456.789-00")
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> VisitanteRoomDatasource.checkCPF"
            )
        }

    @Test
    fun `Check return true if CheckCPF execute successfully`() =
        runTest {
            whenever(
                visitanteRoomDatasource.checkCpf("123.456.789-00")
            ).thenReturn(
                Result.success(true)
            )
            val repository = getRepository()
            val result = repository.checkCPF("123.456.789-00")
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `Check return false if CheckCPF execute successfully`() =
        runTest {
            whenever(
                visitanteRoomDatasource.checkCpf("123.456.789-00")
            ).thenReturn(
                Result.success(false)
            )
            val repository = getRepository()
            val result = repository.checkCPF("123.456.789-00")
            assertTrue(result.isSuccess)
            assertFalse(result.getOrNull()!!)
        }

    @Test
    fun `Get - Check return failure if have error in VisitanteRoomDatasource get`() =
        runTest {
            whenever(
                visitanteRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "VisitanteRoomDatasource.get",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.get(1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> VisitanteRoomDatasource.get"
            )
        }

    @Test
    fun `Check return true if VisitanteRepositoryImplTest Get execute successfully`() =
        runTest {
            val roomModel = VisitanteRoomModel(
                idVisitante = 1,
                nomeVisitante = "Visitante",
                cpfVisitante = "123.456.789-00",
                empresaVisitante = "Empresa Visitante"
            )
            whenever(
                visitanteRoomDatasource.get(1)
            ).thenReturn(
                Result.success(roomModel)
            )
            val repository = getRepository()
            val result = repository.get(1)
            assertTrue(result.isSuccess)
            val resultRoomModel = result.getOrNull()!!
            assertEquals(
                resultRoomModel.nomeVisitante,
                "Visitante"
            )
        }

    @Test
    fun `GetCPF - Check return failure if have error in VisitanteRoomDatasource getCPF`() =
        runTest {
            whenever(
                visitanteRoomDatasource.get(1)
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "VisitanteRoomDatasource.getCPF",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.getCpf(1)
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> VisitanteRoomDatasource.getCPF"
            )
        }

    @Test
    fun `Check return true if VisitanteRepositoryImplTest GetCPF execute successfully`() =
        runTest {
            val roomModel = VisitanteRoomModel(
                idVisitante = 1,
                nomeVisitante = "Visitante",
                cpfVisitante = "123.456.789-00",
                empresaVisitante = "Empresa Visitante"
            )
            whenever(
                visitanteRoomDatasource.get(1)
            ).thenReturn(
                Result.success(roomModel)
            )
            val repository = getRepository()
            val result = repository.getCpf(1)
            assertTrue(result.isSuccess)
            assertEquals(
                result.getOrNull()!!,
                "123.456.789-00"
            )
        }

    @Test
    fun `GetId - Check return failure if have error in VisitanteRoomDatasource get`() =
        runTest {
            whenever(
                visitanteRoomDatasource.get("123.456.789-00")
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "VisitanteRoomDatasource.getId",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.getId("123.456.789-00")
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> VisitanteRoomDatasource.getId"
            )
        }

    @Test
    fun `Check return true if VisitanteRepositoryImplTest GetId execute successfully`() =
        runTest {
            val roomModel = VisitanteRoomModel(
                idVisitante = 1,
                nomeVisitante = "Visitante",
                cpfVisitante = "123.456.789-00",
                empresaVisitante = "Empresa Visitante"
            )
            whenever(
                visitanteRoomDatasource.get("123.456.789-00")
            ).thenReturn(
                Result.success(roomModel)
            )
            val repository = getRepository()
            val result = repository.getId("123.456.789-00")
            assertTrue(result.isSuccess)
            assertEquals(result.getOrNull()!!, 1)
        }

    @Test
    fun `GetNome - Check return failure if have error in VisitanteRoomDatasource get`() =
        runTest {
            whenever(
                visitanteRoomDatasource.get("123.456.789-00")
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "VisitanteRoomDatasource.getNome",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.getNome("123.456.789-00")
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> VisitanteRoomDatasource.getNome"
            )
        }

    @Test
    fun `Check return true if VisitanteRepositoryImplTest GetNome execute successfully`() =
        runTest {
            val roomModel = VisitanteRoomModel(
                idVisitante = 1,
                nomeVisitante = "Visitante",
                cpfVisitante = "123.456.789-00",
                empresaVisitante = "Empresa Visitante"
            )
            whenever(
                visitanteRoomDatasource.get("123.456.789-00")
            ).thenReturn(
                Result.success(roomModel)
            )
            val repository = getRepository()
            val result = repository.getNome("123.456.789-00")
            assertTrue(result.isSuccess)
            assertEquals(result.getOrNull()!!, "Visitante")
        }

    @Test
    fun `GetEmpresas - Check return failure if have error in VisitanteRoomDatasource get`() =
        runTest {
            whenever(
                visitanteRoomDatasource.get("123.456.789-00")
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "VisitanteRoomDatasource.getEmpresas",
                        cause = Exception()
                    )
                )
            )
            val repository = getRepository()
            val result = repository.getEmpresas("123.456.789-00")
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> VisitanteRoomDatasource.getEmpresas"
            )
        }

    @Test
    fun `Check return true if VisitanteRepositoryImplTest GetEmpresas execute successfully`() =
        runTest {
            val roomModel = VisitanteRoomModel(
                idVisitante = 1,
                nomeVisitante = "Visitante",
                cpfVisitante = "123.456.789-00",
                empresaVisitante = "Empresa Visitante"
            )
            whenever(
                visitanteRoomDatasource.get("123.456.789-00")
            ).thenReturn(
                Result.success(roomModel)
            )
            val repository = getRepository()
            val result = repository.getEmpresas("123.456.789-00")
            assertTrue(result.isSuccess)
            assertEquals(result.getOrNull()!!, "Empresa Visitante")
        }
}