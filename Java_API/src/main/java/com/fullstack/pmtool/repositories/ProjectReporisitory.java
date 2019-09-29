package com.fullstack.pmtool.repositories;

import com.fullstack.pmtool.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectReporisitory extends CrudRepository <Project,Long> {

     Project findByProjectIdentifier(String projectIDentifier);

     @Override
     Iterable<Project> findAll();

     Iterable<Project>  findAllByProjectLeader(String username);
}
