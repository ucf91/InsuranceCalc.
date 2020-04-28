package com.elementsin.insuranceproduct.repository;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * The interface Base repository.
 * It adds another layer of abstraction which makes the implementers repos not coupled with
 * Mongo repository type So I can easily change it JpaRepository without having to change it in every repo.
 *
 * @param <T>  the type parameter
 * @param <ID> the type parameter
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends MongoRepository<T, ID> {

}
