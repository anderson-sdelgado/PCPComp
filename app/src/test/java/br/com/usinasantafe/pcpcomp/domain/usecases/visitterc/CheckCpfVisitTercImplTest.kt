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

class CheckCpfVisitTercImplTest {

    @Test
    fun `Check return failure if have error in MovEquipVisitTercRepository GetTypeVisitTerc`() = runTest {
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
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
        val usecase = ICheckCpfVisitTerc(
            movEquipVisitTercRepository,
            terceiroRepository,
            visitanteRepository
        )
        val result = usecase(
            cpf = "326.949.728-88",
            flowApp = FlowApp.ADD,
            0
        )
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message
            , "Failure Repository -> MovEquipVisitTercRepository.getTypeVisitTerc"
        )
    }

    @Test
    fun `Check return failure if have error in VisitanteRepository CheckCPF`() = runTest {
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
        val terceiroRepository = mock<TerceiroRepository>()
        val visitanteRepository = mock<VisitanteRepository>()
        whenever(
            movEquipVisitTercRepository.getTypeVisitTerc(
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(TypeVisitTerc.VISITANTE)
        )
        whenever(
            visitanteRepository.checkCPF(
                cpf = "326.949.728-88"
            )
        ).thenReturn(
            Result.failure(
                RepositoryException(
                    function = "VisitanteRepository.checkCPF",
                    cause = Exception()
                )
            )
        )
        val usecase = ICheckCpfVisitTerc(
            movEquipVisitTercRepository,
            terceiroRepository,
            visitanteRepository
        )
        val result = usecase(
            cpf = "326.949.728-88",
            flowApp = FlowApp.ADD,
            0
        )
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message
            , "Failure Repository -> VisitanteRepository.checkCPF"
        )
    }

    @Test
    fun `Check return failure if have error in TerceiroRepository CheckCPF`() = runTest {
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
        val terceiroRepository = mock<TerceiroRepository>()
        val visitanteRepository = mock<VisitanteRepository>()
        whenever(
            movEquipVisitTercRepository.getTypeVisitTerc(
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(TypeVisitTerc.TERCEIRO)
        )
        whenever(
            terceiroRepository.checkCPF(
                cpf = "326.949.728-88"
            )
        ).thenReturn(
            Result.failure(
                RepositoryException(
                    function = "TerceiroRepository.checkCPF",
                    cause = Exception()
                )
            )
        )
        val usecase = ICheckCpfVisitTerc(
            movEquipVisitTercRepository,
            terceiroRepository,
            visitanteRepository
        )
        val result = usecase(
            cpf = "326.949.728-88",
            flowApp = FlowApp.ADD,
            0
        )
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message
            , "Failure Repository -> TerceiroRepository.checkCPF"
        )
    }

    @Test
    fun `Check return false if CheckCPF is invalid`() = runTest {
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
        val terceiroRepository = mock<TerceiroRepository>()
        val visitanteRepository = mock<VisitanteRepository>()
        whenever(
            movEquipVisitTercRepository.getTypeVisitTerc(
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(TypeVisitTerc.TERCEIRO)
        )
        whenever(
            terceiroRepository.checkCPF(
                cpf = "326.949.728-88"
            )
        ).thenReturn(
            Result.success(false)
        )
        val usecase = ICheckCpfVisitTerc(
            movEquipVisitTercRepository,
            terceiroRepository,
            visitanteRepository
        )
        val result = usecase(
            cpf = "326.949.728-88",
            flowApp = FlowApp.ADD,
            0
        )
        assertTrue(result.isSuccess)
        assertFalse(result.getOrNull()!!)
    }

    @Test
    fun `Check return false if CheckCPF is valid`() = runTest {
        val movEquipVisitTercRepository = mock<MovEquipVisitTercRepository>()
        val terceiroRepository = mock<TerceiroRepository>()
        val visitanteRepository = mock<VisitanteRepository>()
        whenever(
            movEquipVisitTercRepository.getTypeVisitTerc(
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(TypeVisitTerc.TERCEIRO)
        )
        whenever(
            terceiroRepository.checkCPF(
                cpf = "326.949.728-88"
            )
        ).thenReturn(
            Result.success(true)
        )
        val usecase = ICheckCpfVisitTerc(
            movEquipVisitTercRepository,
            terceiroRepository,
            visitanteRepository
        )
        val result = usecase(
            cpf = "326.949.728-88",
            flowApp = FlowApp.ADD,
            0
        )
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }
}