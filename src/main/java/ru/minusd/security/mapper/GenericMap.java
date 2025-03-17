package ru.minusd.security.mapper;

import java.io.Serializable;

public interface GenericMap<T, DTO extends Serializable> {
    DTO map(T object);

}
