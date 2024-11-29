package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable

import br.com.usinasantafe.pcpcomp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Equip
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.cleantable.CleanEquip
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.getserver.GetServerEquip
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable.SaveEquip
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.updatePercentage
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IUpdateEquipTest {

    private val cleanEquip = mock<CleanEquip>()
    private val getServerEquip = mock<GetServerEquip>()
    private val saveEquip = mock<SaveEquip>()
    private fun getUsecase() = IUpdateEquip(
        cleanEquip = cleanEquip,
        getServerEquip = getServerEquip,
        saveEquip = saveEquip,
    )

    @Test
    fun `check return failure usecase if have error in usecase RecoverEquipServer`() =
        runTest {
            var pos = 0f
            whenever(
                getServerEquip()
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "GetAllEquipServer",
                        cause = NullPointerException()
                    )
                )
            )
            val usecase = getUsecase()
            val result = usecase(
                sizeAll = 16f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(result.count(), 2)
            assertEquals(
                list[0],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> GetAllEquipServer -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> GetAllEquipServer -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure usecase if have error in usecase CleanEquip`() =
        runTest {
            var pos = 0f
            whenever(
                getServerEquip()
            ).thenReturn(
                Result.success(
                    equipList
                )
            )
            whenever(
                cleanEquip()
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "CleanEquip",
                        cause = NullPointerException()
                    )
                )
            )
            val usecase = getUsecase()
            val result = usecase(
                sizeAll = 16f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(result.count(), 3)
            assertEquals(
                list[0],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_equip",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> CleanEquip -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanEquip -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure usecase if have error in datasource SaveAllEquip`() =
        runTest {
            var pos = 0f
            whenever(
                getServerEquip()
            ).thenReturn(
                Result.success(
                    equipList
                )
            )
            whenever(
                cleanEquip()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveEquip(equipList)
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "SaveAllEquip",
                        cause = NullPointerException()
                    )
                )
            )
            val usecase = getUsecase()
            val result = usecase(
                sizeAll = 16f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(result.count(), 4)
            assertEquals(
                list[0],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_equip",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_equip",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[3],
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> SaveAllEquip -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> SaveAllEquip -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return true if have UpdateEquip execute successfully`() =
        runTest {
            var pos = 0f
            whenever(
                getServerEquip()
            ).thenReturn(
                Result.success(
                    equipList
                )
            )
            whenever(
                cleanEquip()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveEquip(equipList)
            ).thenReturn(
                Result.success(true)
            )
            val usecase = getUsecase()
            val result = usecase(
                sizeAll = 16f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(result.count(), 3)
            assertEquals(
                list[0],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_equip",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_equip",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
        }

}

val equipList = listOf(
    Equip(
        idEquip = 1,
        nroEquip = 10
    )
)
