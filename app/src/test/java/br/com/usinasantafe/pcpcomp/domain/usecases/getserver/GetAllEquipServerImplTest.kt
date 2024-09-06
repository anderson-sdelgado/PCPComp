package br.com.usinasantafe.pcpcomp.domain.usecases.getserver

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Equip
import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetAllEquipServerImplTest {

    @Test
    fun `Check return Failure Datasouce if have failure in Config Repository`() = runTest {
        val configRepository = mock<ConfigRepository>()
        val equipRepository = mock<EquipRepository>()
        whenever(configRepository.getConfig()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "ConfigRepository.getConfig",
                    cause = Exception()
                )
            )
        )
        val usecase = GetAllEquipServerImpl(configRepository, equipRepository)
        val result = usecase()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ConfigRepository.getConfig")
    }

    @Test
    fun `Check return Failure Usecase if have empty fields in object Config return `() = runTest {
        val configRepository = mock<ConfigRepository>()
        val equipRepository = mock<EquipRepository>()
        whenever(configRepository.getConfig()).thenReturn(
            Result.success(
                Config(
                    number = 16997417840,
                    version = "6.00"
                )
            )
        )
        val usecase = GetAllEquipServerImpl(configRepository, equipRepository)
        val result = usecase()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Usecase -> RecoverEquipServer")
    }

    @Test
    fun `Check return Failure Datasource if have success getConfig and failure Datasource in recoverAll`() = runTest {
        val configRepository = mock<ConfigRepository>()
        val equipRepository = mock<EquipRepository>()
        whenever(configRepository.getConfig()).thenReturn(
            Result.success(
                Config(
                    number = 16997417840,
                    version = "6.00",
                    idBD = 1
                )
            )
        )
        whenever(equipRepository.recoverAll("Bearer E49AD0C7AAA85FA6AB01FFD4AF7205C7")).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "EquipRepository.recoverAll",
                    cause = Exception()
                )
            )
        )
        val usecase = GetAllEquipServerImpl(configRepository, equipRepository)
        val result = usecase()
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> EquipRepository.recoverAll")
    }

    @Test
    fun `Check return list if have success getConfig and success recoverAll`() = runTest {
        val equipList = listOf(
            Equip(
                idEquip = 10,
                nroEquip = 200,
            )
        )
        val configRepository = mock<ConfigRepository>()
        val equipRepository = mock<EquipRepository>()
        whenever(configRepository.getConfig()).thenReturn(
            Result.success(
                Config(
                    number = 16997417840,
                    version = "6.00",
                    idBD = 1
                )
            )
        )
        whenever(equipRepository.recoverAll("Bearer E49AD0C7AAA85FA6AB01FFD4AF7205C7")).thenReturn(
            Result.success(equipList))
        val usecase = GetAllEquipServerImpl(configRepository, equipRepository)
        val result = usecase()
        assertEquals(result.isSuccess, true)
        assertEquals(result, Result.success(equipList))
    }

}