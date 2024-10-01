package service

interface BuildDTO<T, D> {

    T buildDTO(D model);
}