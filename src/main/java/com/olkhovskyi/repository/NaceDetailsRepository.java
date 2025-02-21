package com.olkhovskyi.repository;

import com.olkhovskyi.entity.NaceDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NaceDetailsRepository extends CrudRepository<NaceDetails, Long> {
}