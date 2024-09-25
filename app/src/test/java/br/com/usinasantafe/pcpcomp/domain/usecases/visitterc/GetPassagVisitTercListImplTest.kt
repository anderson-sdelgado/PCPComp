package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipVisitTercPassag
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetPassagVisitTercListImplTest {

    @Test
    fun `Chech return failure if have error in MovEquipVisitTercRepository GetTypeVisitTerc`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercRepository.getTypeVisitTerc",
                        cause = Exception()
                    )
                )
            )
            val usecase = GetPassagVisitTercListImpl(
                movEquipVisitTercRepository = movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository = movEquipVisitTercPassagRepository,
                terceiroRepository = terceiroRepository,
                visitanteRepository = visitanteRepository
            )
            val result = usecase(
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercRepository.getTypeVisitTerc"
            )
        }

    @Test
    fun `Chech return failure if have error in MovEquipVisitTercPassagRepository list Visitante`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    TypeVisitTerc.VISITANTE
                )
            )
            whenever(
                movEquipVisitTercPassagRepository.list(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercPassagRepository.list",
                        cause = Exception()
                    )
                )
            )
            val usecase = GetPassagVisitTercListImpl(
                movEquipVisitTercRepository = movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository = movEquipVisitTercPassagRepository,
                terceiroRepository = terceiroRepository,
                visitanteRepository = visitanteRepository
            )
            val result = usecase(
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercPassagRepository.list"
            )
        }

    @Test
    fun `Chech return failure if have error in VisitanteRepository getCpf`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    TypeVisitTerc.VISITANTE
                )
            )
            whenever(
                movEquipVisitTercPassagRepository.list(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipVisitTercPassag(
                            idVisitTercMovEquipVisitTercPassag = 1
                        )
                    )
                )
            )
            whenever(
                visitanteRepository.getCpf(
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "VisitanteRepository.getCpf",
                        cause = Exception()
                    )
                )
            )
            val usecase = GetPassagVisitTercListImpl(
                movEquipVisitTercRepository = movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository = movEquipVisitTercPassagRepository,
                terceiroRepository = terceiroRepository,
                visitanteRepository = visitanteRepository
            )
            val result = usecase(
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> VisitanteRepository.getCpf"
            )
        }

    @Test
    fun `Chech return failure if have error in VisitanteRepository getNome`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    TypeVisitTerc.VISITANTE
                )
            )
            whenever(
                movEquipVisitTercPassagRepository.list(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipVisitTercPassag(
                            idVisitTercMovEquipVisitTercPassag = 1
                        )
                    )
                )
            )
            whenever(
                visitanteRepository.getCpf(
                    id = 1
                )
            ).thenReturn(
                Result.success("123.456.789-00")
            )
            whenever(
                visitanteRepository.getNome(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "VisitanteRepository.getNome",
                        cause = Exception()
                    )
                )
            )
            val usecase = GetPassagVisitTercListImpl(
                movEquipVisitTercRepository = movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository = movEquipVisitTercPassagRepository,
                terceiroRepository = terceiroRepository,
                visitanteRepository = visitanteRepository
            )
            val result = usecase(
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> VisitanteRepository.getNome"
            )
        }

    @Test
    fun `Chech return list if GetPassagVisitTercListImpl execute successfully Visitante`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    TypeVisitTerc.VISITANTE
                )
            )
            whenever(
                movEquipVisitTercPassagRepository.list(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipVisitTercPassag(
                            idVisitTercMovEquipVisitTercPassag = 1
                        )
                    )
                )
            )
            whenever(
                visitanteRepository.getCpf(
                    id = 1
                )
            ).thenReturn(
                Result.success("123.456.789-00")
            )
            whenever(
                visitanteRepository.getNome(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.success(
                    "ANDERSON DA SILVA DELGADO"
                )
            )
            val usecase = GetPassagVisitTercListImpl(
                movEquipVisitTercRepository = movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository = movEquipVisitTercPassagRepository,
                terceiroRepository = terceiroRepository,
                visitanteRepository = visitanteRepository
            )
            val result = usecase(
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            val ret = result.getOrNull()!!
            assertEquals(ret.size, 1)
            assertEquals(ret[0].id, 1)
            assertEquals(ret[0].cpf, "123.456.789-00")
            assertEquals(ret[0].nome, "ANDERSON DA SILVA DELGADO")
        }

    @Test
    fun `Chech return failure if have error in MovEquipVisitTercPassagRepository list Terceiro`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    TypeVisitTerc.TERCEIRO
                )
            )
            whenever(
                movEquipVisitTercPassagRepository.list(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercPassagRepository.list",
                        cause = Exception()
                    )
                )
            )
            val usecase = GetPassagVisitTercListImpl(
                movEquipVisitTercRepository = movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository = movEquipVisitTercPassagRepository,
                terceiroRepository = terceiroRepository,
                visitanteRepository = visitanteRepository
            )
            val result = usecase(
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercPassagRepository.list"
            )
        }

    @Test
    fun `Chech return failure if have error in TerceiroRepository getCpf`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    TypeVisitTerc.TERCEIRO
                )
            )
            whenever(
                movEquipVisitTercPassagRepository.list(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipVisitTercPassag(
                            idVisitTercMovEquipVisitTercPassag = 1
                        )
                    )
                )
            )
            whenever(
                terceiroRepository.getCpf(
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "TerceiroRepository.getCpf",
                        cause = Exception()
                    )
                )
            )
            val usecase = GetPassagVisitTercListImpl(
                movEquipVisitTercRepository = movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository = movEquipVisitTercPassagRepository,
                terceiroRepository = terceiroRepository,
                visitanteRepository = visitanteRepository
            )
            val result = usecase(
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> TerceiroRepository.getCpf"
            )
        }

    @Test
    fun `Chech return failure if have error in TerceiroRepository getNome`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    TypeVisitTerc.TERCEIRO
                )
            )
            whenever(
                movEquipVisitTercPassagRepository.list(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipVisitTercPassag(
                            idVisitTercMovEquipVisitTercPassag = 1
                        )
                    )
                )
            )
            whenever(
                terceiroRepository.getCpf(
                    id = 1
                )
            ).thenReturn(
                Result.success("123.456.789-00")
            )
            whenever(
                terceiroRepository.getNome(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "TerceiroRepository.getNome",
                        cause = Exception()
                    )
                )
            )
            val usecase = GetPassagVisitTercListImpl(
                movEquipVisitTercRepository = movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository = movEquipVisitTercPassagRepository,
                terceiroRepository = terceiroRepository,
                visitanteRepository = visitanteRepository
            )
            val result = usecase(
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> TerceiroRepository.getNome"
            )
        }

    @Test
    fun `Chech return list if GetPassagVisitTercListImpl execute successfully Terceiro`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val movEquipVisitTercPassagRepository = mock<MovEquipVisitTercPassagRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    TypeVisitTerc.TERCEIRO
                )
            )
            whenever(
                movEquipVisitTercPassagRepository.list(
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        MovEquipVisitTercPassag(
                            idVisitTercMovEquipVisitTercPassag = 1
                        )
                    )
                )
            )
            whenever(
                terceiroRepository.getCpf(
                    id = 1
                )
            ).thenReturn(
                Result.success("123.456.789-00")
            )
            whenever(
                terceiroRepository.getNome(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.success(
                    "ANDERSON DA SILVA DELGADO"
                )
            )
            val usecase = GetPassagVisitTercListImpl(
                movEquipVisitTercRepository = movEquipVisitTercRepository,
                movEquipVisitTercPassagRepository = movEquipVisitTercPassagRepository,
                terceiroRepository = terceiroRepository,
                visitanteRepository = visitanteRepository
            )
            val result = usecase(
                flowApp = FlowApp.ADD,
                id = 0
            )
            assertTrue(result.isSuccess)
            val ret = result.getOrNull()!!
            assertEquals(ret.size, 1)
            assertEquals(ret[0].id, 1)
            assertEquals(ret[0].cpf, "123.456.789-00")
            assertEquals(ret[0].nome, "ANDERSON DA SILVA DELGADO")
        }
}