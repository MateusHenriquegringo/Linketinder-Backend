package DAO

interface CRUD<T, ID> {

    void create(T entity);
    void update(T entity, ID id);
    void delete(ID id);
    List<T> listAll();
    T findById(ID id);

}