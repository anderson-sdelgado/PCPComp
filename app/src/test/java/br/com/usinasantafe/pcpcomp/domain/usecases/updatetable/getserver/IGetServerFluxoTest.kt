package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.getserver

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Fluxo
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.FluxoRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.common.GetToken
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class IGetServerFluxoTest {

    private val getToken = mock<GetToken>()
    private val fluxoRepository = mock<FluxoRepository>()
    private val usecase = IGetServerFluxo(
        getToken,
        fluxoRepository
    )

    @Test
    fun `Check return failure if have error in GetToken`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "GetToken",
                        cause = Exception()
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
                "Failure Usecase -> GetToken"
            )
        }

    @Test
    fun `Check return Failure Usecase if have empty fields in object Config return `() = runTest {
        whenever(
            getToken()
        ).thenReturn(
            Result.success("token")
        )
        val result = usecase()
        assertEquals(
            result.isFailure,
            true
        )
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Usecase -> GetServerFluxo"
        )
    }

    @Test
    fun `Check return failure if have error in FluxoRepository recoverAll`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                fluxoRepository.recoverAll("token")
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "FluxoRepository.recoverAll",
                        cause = Exception()
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
                "Failure Repository -> FluxoRepository.recoverAll"
            )
        }

    @Test
    fun `Check return true if GetAllFluxoServerImplTest execute successfully`() =
        runTest {
            val fluxoList = listOf(
                Fluxo(
                    idFluxo = 1,
                    descrFluxo = "MOV. EQUIP. PRÓPRIO"
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                fluxoRepository.recoverAll("token")
            ).thenReturn(
                Result.success(fluxoList)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result,
                Result.success(fluxoList)
            )
        }

}