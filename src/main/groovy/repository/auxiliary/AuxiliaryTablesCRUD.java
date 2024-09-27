package repository.auxiliary;

import java.util.List;

public interface AuxiliaryTablesCRUD<T, D, ID> {

    void create(ID entityId, List<ID> anotherEntityID);
    void delete(ID entityID, List<ID> propertiesToRemove);
    T findById(ID entityId);
    List<T> findAllWithCompetence(ID competeceId);
}
