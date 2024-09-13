import br.com.usinasantafe.pcpcomp.presenter.visitterc.model.MovEquipVisitTercModel

interface GetMovEquipVisitTercOpenList {
    suspend operator fun invoke(): Result<List<MovEquipVisitTercModel>>
}

class GetMovEquipVisitTercOpenListImpl(): GetMovEquipVisitTercOpenList {

    override suspend fun invoke(): Result<List<MovEquipVisitTercModel>> {
        TODO("Not yet implemented")
    }

}