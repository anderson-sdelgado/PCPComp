package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.getserver

import br.com.usinasantafe.pcpcomp.domain.entities.stable.RLocalFluxo
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.RLocalFluxoRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.common.GetToken
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class IGetServerRLocalFluxoTest {

    private val getToken = mock<GetToken>()
    private val rLocalFluxoRepository = mock<RLocalFluxoRepository>()
    private val usecase = IGetServerRLocalFluxo(
        getToken,
        rLocalFluxoRepository
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
            "Failure Usecase -> GetServerRLocalFluxo"
        )
    }

    @Test
    fun `Check return failure if have error in RLocalFluxoRepository recoverAll`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                rLocalFluxoRepository.recoverAll("token")
            ).thenReturn(
                Result.failure(
                    RepositoryException(
                        function = "RLocalFluxoRepository.recoverAll",
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
                "Failure Repository -> RLocalFluxoRepository.recoverAll"
            )
        }

    @Test
    fun `Check return true if GetAllRLocalFluxoServerImplTest execute successfully`() =
        runTest {
            val rLocalFluxoList = listOf(
                RLocalFluxo(
                    idRLocalFluxo = 1,
                    idFluxo = 1,
                    idLocal = 1
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                rLocalFluxoRepository.recoverAll("token")
            ).thenReturn(
                Result.success(rLocalFluxoList)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result,
                Result.success(rLocalFluxoList)
            )
        }

}