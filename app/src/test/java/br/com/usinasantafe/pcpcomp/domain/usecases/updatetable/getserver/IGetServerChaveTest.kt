package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.getserver

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Chave
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ChaveRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.common.GetToken
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IGetServerChaveTest {

    private val getToken = mock<GetToken>()
    private val chaveRepository = mock<ChaveRepository>()
    private val usecase = IGetServerChave(
        getToken,
        chaveRepository
    )

    @Test
    fun `Check return Failure Datasource if have failure in Config Repository`() = runTest {
        whenever(
            getToken()
        ).thenReturn(
            Result.failure(
                DatasourceException(
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
            "Failure Datasource -> GetToken"
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
            "Failure Usecase -> GetServerChave"
        )
    }

    @Test
    fun `Check return Failure Datasource if have success getConfig and failure Datasource in recoverAll`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                chaveRepository.recoverAll("token")
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "ChaveRepository.recoverAll",
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
                "Failure Datasource -> ChaveRepository.recoverAll"
            )
        }

    @Test
    fun `Check return list if have success getConfig and success recoverAll`() = runTest {
        val chaveList = listOf(
            Chave(
                idChave = 1,
                descrChave = "02 - TI",
                idLocalTrab = 1
            )
        )
        whenever(
            getToken()
        ).thenReturn(
            Result.success("token")
        )
        whenever(
            chaveRepository.recoverAll("token")
        ).thenReturn(
            Result.success(chaveList)
        )
        val result = usecase()
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result,
            Result.success(chaveList)
        )
    }

}