package repository.auxiliary;

public interface AuxiliaryTablesCRUD<T, ID, ENUM> {

    void create(ID entityId, List<ENUM> enumList);
    void delete(ID entityID, List<ENUM> enumsToRemove);
    T findById(ID entityId);
    List<T> listAll();
}