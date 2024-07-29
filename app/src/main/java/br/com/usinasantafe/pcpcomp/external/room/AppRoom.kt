package br.com.usinasantafe.pcpcomp.external.room

import android.content.Context
import androidx.room.*
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.ColabDao
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.EquipDao
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.LocalDao
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.TerceiroDao
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.VisitanteDao
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.LocalRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.TerceiroRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.VisitanteRoomModel
import br.com.usinasantafe.pcpcomp.utils.BASE_DB
import br.com.usinasantafe.pcpcomp.utils.VERSION_DB

@Database(
    entities = [
        ColabRoomModel::class,
        EquipRoomModel::class,
        LocalRoomModel::class,
        TerceiroRoomModel::class,
        VisitanteRoomModel::class,
    ],
    version = VERSION_DB, exportSchema = true,
)
abstract class AppDatabaseRoom : RoomDatabase() {
    abstract fun colabDao(): ColabDao
    abstract fun equipDao(): EquipDao
    abstract fun localDao(): LocalDao
    abstract fun terceiroDao(): TerceiroDao
    abstract fun visitanteDao(): VisitanteDao
}

fun provideRoom(appContext: Context): AppDatabaseRoom {
    return Room.databaseBuilder(
        appContext,
        AppDatabaseRoom::class.java,
        BASE_DB
    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
}
