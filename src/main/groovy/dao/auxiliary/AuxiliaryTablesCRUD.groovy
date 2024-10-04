package dao.auxiliary;

public interface AuxiliaryTablesCRUD<T, ID, ENUM> {

    void createAssociation(ID entityId, List<ENUM> enumList);
    void deleteCompetence(ID entityID, ENUM enumToRemove);
    T findById(ID entityId);
    void deleteAllCompetences(ID id);
    List<T> listAll();
    void updateCompetences(ID id, List<ENUM> enumList);
}
