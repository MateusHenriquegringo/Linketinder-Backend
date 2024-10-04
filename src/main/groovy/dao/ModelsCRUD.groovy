package dao

interface ModelsCRUD<T, ID> {

    long create(T entity);
    void update(T entity, ID id);
    void delete(ID id);
    List<T> listAll();
    T findById(ID id);

}