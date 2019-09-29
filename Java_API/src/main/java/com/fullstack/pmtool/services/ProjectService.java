package com.fullstack.pmtool.services;

import com.fullstack.pmtool.domain.Backlog;
import com.fullstack.pmtool.domain.Project;
import com.fullstack.pmtool.domain.User;
import com.fullstack.pmtool.exceptions.ProjectIdException;
import com.fullstack.pmtool.repositories.BacklogRepository;
import com.fullstack.pmtool.repositories.ProjectReporisitory;
import com.fullstack.pmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class  ProjectService {

     @Autowired
    private ProjectReporisitory projectReporisitory;

     @Autowired
     private BacklogRepository backlogRepository;

     @Autowired
     private UserRepository userRepository;

     public Project saveOrUpdateProject(Project project, String username){
         try{
              User user = userRepository.findByUsername(username);
              project.setUser(user);
              project.setProjectLeader(user.getUsername());

             project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

             if(project.getId()==null){  // during project creation
                 Backlog backlog=new Backlog();
                 project.setBacklog(backlog);
                 backlog.setProject(project);
                 backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase()  );
             }
             if(project.getId()!=null){  // during project updation
                 project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
             }

             return  projectReporisitory.save(project);
         }
         catch (Exception e){
             throw new ProjectIdException("Project Id '"+project.getProjectIdentifier().toUpperCase()+"' already Exists");
         }

     }

     public Project findProjectByIdentifier(String projectId){
        Project project=  projectReporisitory.findByProjectIdentifier(projectId.toUpperCase());

        if(project==null){
            throw  new ProjectIdException("Project Id ,"+projectId+"' does not Exists");
        }
        return project;
     }

     public Iterable<Project> findAllProjects(String username){
         return  projectReporisitory.findAllByProjectLeader(username);
     }

     public void deleteProjectById(String projectId){
         Project project=projectReporisitory.findByProjectIdentifier(projectId.toUpperCase());
         if(project==null){
             throw new ProjectIdException("Project Id'"+projectId+"'does not exits");
         }
         projectReporisitory.delete(project);
     }
}
