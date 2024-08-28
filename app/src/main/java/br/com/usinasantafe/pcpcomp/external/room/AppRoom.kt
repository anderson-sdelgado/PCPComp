package br.com.usinasantafe.pcpcomp.external.room

import android.content.Context
import androidx.room.*
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.ColabDao
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.EquipDao
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.LocalDao
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.TerceiroDao
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.VisitanteDao
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipProprioDao
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipProprioEquipSegDao
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipProprioPassagDao
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipResidenciaDao
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipVisitTercDao
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.LocalRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.TerceiroRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.VisitanteRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioEquipSegRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioPassagRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipResidenciaRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipVisitTercRoomModel
import br.com.usinasantafe.pcpcomp.utils.BASE_DB
import br.com.usinasantafe.pcpcomp.utils.VERSION_DB

@Database(
    entities = [
        ColabRoomModel::class,
        EquipRoomModel::class,
        LocalRoomModel::class,
        TerceiroRoomModel::class,
        VisitanteRoomModel::class,
        MovEquipProprioRoomModel::class,
        MovEquipProprioPassagRoomModel::class,
        MovEquipProprioEquipSegRoomModel::class,
        MovEquipResidenciaRoomModel::class,
        MovEquipVisitTercRoomModel::class
    ],
    version = VERSION_DB, exportSchema = true,
)
abstract class AppDatabaseRoom : RoomDatabase() {
    abstract fun colabDao(): ColabDao
    abstract fun equipDao(): EquipDao
    abstract fun localDao(): LocalDao
    abstract fun terceiroDao(): TerceiroDao
    abstract fun visitanteDao(): VisitanteDao
    abstract fun movEquipProprioDao(): MovEquipProprioDao
    abstract fun movEquipProprioPassagDao(): MovEquipProprioPassagDao
    abstract fun movEquipProprioEquipSegDao(): MovEquipProprioEquipSegDao
    abstract fun movEquipResidenciaDao(): MovEquipResidenciaDao
    abstract fun movEquipVisitTercDao(): MovEquipVisitTercDao
}

fun provideRoom(appContext: Context): AppDatabaseRoom {
    return Room.databaseBuilder(
        appContext,
        AppDatabaseRoom::class.java,
        BASE_DB
    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
}
