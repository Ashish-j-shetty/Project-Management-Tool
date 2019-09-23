package com.fullstack.pmtool.repositories;

import com.fullstack.pmtool.domain.Backlog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog,Long> {
    Backlog findByProjectIdentifier(String Identifier);

}
