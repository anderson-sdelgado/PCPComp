package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetNomeVisitTercImplTest {

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository GetTypeVisitTerc`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercRepository.getTypeVisitTerc",
                        cause = Exception()
                    )
                )
            )
            val usecase = GetNomeVisitTercImpl(
                movEquipVisitTercRepository = movEquipVisitTercRepository,
                terceiroRepository = terceiroRepository,
                visitanteRepository = visitanteRepository
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> MovEquipVisitTercRepository.getTypeVisitTerc"
            )
        }

    @Test
    fun `Check return failure if have error in VisitanteRepository GetNome`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(TypeVisitTerc.VISITANTE)
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
            val usecase = GetNomeVisitTercImpl(
                movEquipVisitTercRepository = movEquipVisitTercRepository,
                terceiroRepository = terceiroRepository,
                visitanteRepository = visitanteRepository
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> VisitanteRepository.getNome"
            )
        }

    @Test
    fun `Check return failure if have error in TerceiroRepository GetNome`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(TypeVisitTerc.TERCEIRO)
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
            val usecase = GetNomeVisitTercImpl(
                movEquipVisitTercRepository = movEquipVisitTercRepository,
                terceiroRepository = terceiroRepository,
                visitanteRepository = visitanteRepository
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> TerceiroRepository.getNome"
            )
        }

    @Test
    fun `Check return failure if have error in VisitanteRepository GetEmpresas`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(TypeVisitTerc.VISITANTE)
            )
            whenever(
                visitanteRepository.getNome(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.success("Visitante")
            )
            whenever(
                visitanteRepository.getEmpresas(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "VisitanteRepository.getEmpresas",
                        cause = Exception()
                    )
                )
            )
            val usecase = GetNomeVisitTercImpl(
                movEquipVisitTercRepository = movEquipVisitTercRepository,
                terceiroRepository = terceiroRepository,
                visitanteRepository = visitanteRepository
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> VisitanteRepository.getEmpresas"
            )
        }

    @Test
    fun `Check return failure if have error in TerceiroRepository GetEmpresas`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(TypeVisitTerc.TERCEIRO)
            )
            whenever(
                terceiroRepository.getNome(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.success("Terceiro")
            )
            whenever(
                terceiroRepository.getEmpresas(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "TerceiroRepository.getEmpresas",
                        cause = Exception()
                    )
                )
            )
            val usecase = GetNomeVisitTercImpl(
                movEquipVisitTercRepository = movEquipVisitTercRepository,
                terceiroRepository = terceiroRepository,
                visitanteRepository = visitanteRepository
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isFailure)
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Repository -> TerceiroRepository.getEmpresas"
            )
        }

    @Test
    fun `Check return failure if GetNomeVisitTercImpl execute successfully Visitante`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(TypeVisitTerc.VISITANTE)
            )
            whenever(
                visitanteRepository.getNome(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.success("Visitante")
            )
            whenever(
                visitanteRepository.getEmpresas(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.success("Empresas")
            )
            val usecase = GetNomeVisitTercImpl(
                movEquipVisitTercRepository = movEquipVisitTercRepository,
                terceiroRepository = terceiroRepository,
                visitanteRepository = visitanteRepository
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isSuccess)
            val model = result.getOrNull()!!
            assertEquals(
                model.tipo,
                "VISITANTE"
            )
            assertEquals(
                model.nome,
                "Visitante"
            )
            assertEquals(
                model.empresa,
                "Empresas"
            )
        }

    @Test
    fun `Check return failure if GetNomeVisitTercImpl execute successfully Terceiro`() =
        runTest {
            val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
            val terceiroRepository = mock<TerceiroRepository>()
            val visitanteRepository = mock<VisitanteRepository>()
            whenever(
                movEquipVisitTercRepository.getTypeVisitTerc(
                    flowApp = FlowApp.CHANGE,
                    id = 1
                )
            ).thenReturn(
                Result.success(TypeVisitTerc.TERCEIRO)
            )
            whenever(
                terceiroRepository.getNome(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.success("Terceiro")
            )
            whenever(
                terceiroRepository.getEmpresas(
                    cpf = "123.456.789-00"
                )
            ).thenReturn(
                Result.success("Empresas")
            )
            val usecase = GetNomeVisitTercImpl(
                movEquipVisitTercRepository = movEquipVisitTercRepository,
                terceiroRepository = terceiroRepository,
                visitanteRepository = visitanteRepository
            )
            val result = usecase(
                cpf = "123.456.789-00",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            assertTrue(result.isSuccess)
            val model = result.getOrNull()!!
            assertEquals(
                model.tipo,
                "TERCEIRO"
            )
            assertEquals(
                model.nome,
                "Terceiro"
            )
            assertEquals(
                model.empresa,
                "Empresas"
            )
        }

}