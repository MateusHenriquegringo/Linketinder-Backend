package repository.auxiliary;


interface AuxiliaryTablesCRUD<T, ID> {

    void create(ID entityId, List<ID> anotherEntityID);
    void delete(ID entityID, List<ID> propertiesToRemove);
    T findById(ID entityId);
    List<T> listAllWithCompetence(ID competeceId);
}
