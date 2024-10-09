package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetMotoristaVisitTercImplTest {

    @Test
    fun `Check return failure if have error in VisitanteRepository `() = runTest{
        val terceiroRepository = mock<TerceiroRepository>()
        val visitanteRepository = mock<VisitanteRepository>()
        whenever(
            visitanteRepository.get(1)
        ).thenReturn(
            Result.failure(
                RepositoryException(
                    function = "VisitanteRepository.get",
                    cause = Exception()
                )
            )
        )
        val usecase = GetMotoristaVisitTercImpl(
            terceiroRepository = terceiroRepository,
            visitanteRepository = visitanteRepository
        )
        val result = usecase(
            typeVisitTerc = TypeVisitTerc.VISITANTE,
            idVisitTerc = 1
        )
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> VisitanteRepository.get"
        )
    }

    @Test
    fun `Check return failure if have error in TerceiroRepository `() = runTest{
        val terceiroRepository = mock<TerceiroRepository>()
        val visitanteRepository = mock<VisitanteRepository>()
        whenever(
            terceiroRepository.get(1)
        ).thenReturn(
            Result.failure(
                RepositoryException(
                    function = "TerceiroRepository.get",
                    cause = Exception()
                )
            )
        )
        val usecase = GetMotoristaVisitTercImpl(
            terceiroRepository = terceiroRepository,
            visitanteRepository = visitanteRepository
        )
        val result = usecase(
            typeVisitTerc = TypeVisitTerc.TERCEIRO,
            idVisitTerc = 1
        )
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Repository -> TerceiroRepository.get"
        )
    }

    @Test
    fun `Check return motorista if VisitanteRepository execute successfully`() = runTest{
        val terceiroRepository = mock<TerceiroRepository>()
        val visitanteRepository = mock<VisitanteRepository>()
        whenever(
            visitanteRepository.get(1)
        ).thenReturn(
            Result.success(
                Visitante(
                    idVisitante = 1,
                    nomeVisitante = "Anderson",
                    cpfVisitante = "123.456.789-00",
                    empresaVisitante = "Empresa Teste"
                )
            )
        )
        val usecase = GetMotoristaVisitTercImpl(
            terceiroRepository = terceiroRepository,
            visitanteRepository = visitanteRepository
        )
        val result = usecase(
            typeVisitTerc = TypeVisitTerc.VISITANTE,
            idVisitTerc = 1
        )
        assertTrue(result.isSuccess)
        assertEquals(
            result.getOrNull()!!,
            "123.456.789-00 - Anderson"
        )
    }

    @Test
    fun `Check return motorista if TerceiroRepository execute successfully`() = runTest{
        val terceiroRepository = mock<TerceiroRepository>()
        val visitanteRepository = mock<VisitanteRepository>()
        whenever(
            terceiroRepository.get(1)
        ).thenReturn(
            Result.success(
                Terceiro(
                    idTerceiro = 1,
                    idBDTerceiro = 1,
                    nomeTerceiro = "Anderson",
                    cpfTerceiro = "123.456.789-00",
                    empresaTerceiro = "Empresa Teste"
                )
            )
        )
        val usecase = GetMotoristaVisitTercImpl(
            terceiroRepository = terceiroRepository,
            visitanteRepository = visitanteRepository
        )
        val result = usecase(
            typeVisitTerc = TypeVisitTerc.TERCEIRO,
            idVisitTerc = 1
        )
        assertTrue(result.isSuccess)
        assertEquals(
            result.getOrNull()!!,
            "123.456.789-00 - Anderson"
        )
    }
}