package com.fullstack.pmtool.services;

import com.fullstack.pmtool.domain.Backlog;
import com.fullstack.pmtool.domain.Project;
import com.fullstack.pmtool.domain.ProjectTask;
import com.fullstack.pmtool.exceptions.ProjectNotFoundException;
import com.fullstack.pmtool.repositories.BacklogRepository;
import com.fullstack.pmtool.repositories.ProjectReporisitory;
import com.fullstack.pmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {
    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectReporisitory projectReporisitory;

    public ProjectTask addProjectTask(String  projectIdetifier , ProjectTask projectTask){
        //Exception project not found.
        try{
        //PTs  to be added to a specific project , project != null , ie backlog exists

            Backlog backlog=backlogRepository.findByProjectIdentifier(projectIdetifier);

            // set the backlog to a project task
            projectTask.setBacklog(backlog);
            //we want our project sequence to be like this : idpro-1 idpro-2
            Integer backLogSequence= backlog.getPTsequence();
            //Update the backlog Sequence.
             backLogSequence++;

             backlog.setPTsequence(backLogSequence);
            // add project to the project task
             projectTask.setProjectSequence(projectIdetifier+"-"+backLogSequence);
             projectTask.setProjectIdentifier(projectIdetifier);
            //set initial priority when priority is null
           if( projectTask.getPriority()==0 || projectTask.getPriority() ==null){
                projectTask.setPriority(3);
            }
            //set initial status when status is null
            if(projectTask.getStatus() == null || projectTask.getStatus()==""){
                projectTask.setStatus("TO_DO");
        }
            return projectTaskRepository.save(projectTask);
        }
        catch (Exception e){
             throw  new ProjectNotFoundException("Project Not Found");
        }


    }

   public  Iterable<ProjectTask> findBacklogById(String id){

       Project project=projectReporisitory.findByProjectIdentifier(id);

       if(project==null){
           throw  new ProjectNotFoundException("Project with ID: '"+id+"' does not exist");
       }

       return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }


    public ProjectTask findPTByProjectSequence(String backlog_id,String pt_id){
        //make sure we are searching  on the right backlog
        Backlog backlog=backlogRepository.findByProjectIdentifier(backlog_id);
        if(backlog==null){
            throw new ProjectNotFoundException("Project with ID: '"+backlog_id+"' does not exist");
        }
        ProjectTask projectTask=projectTaskRepository.findByProjectSequence(pt_id);
        if(projectTask==null){
            throw new ProjectNotFoundException("Project task '"+pt_id+"'not found");
        }
        if(!projectTask.getProjectIdentifier().equals(backlog_id )){
            throw  new ProjectNotFoundException("Project task '"+pt_id+"'does not exits in  project :'"+backlog_id);
        }
        return projectTask;
    }

    public ProjectTask  updatedByProjectSequence(ProjectTask updatedTask, String backlog_id,String pt_id){
        ProjectTask projectTask=findPTByProjectSequence(backlog_id,pt_id);
        projectTask= updatedTask;
        return projectTaskRepository.save(projectTask);
    }

    public void deletePTByProjectSequence(String backlog_id,String pt_id){
        ProjectTask projectTask=findPTByProjectSequence(backlog_id,pt_id);
        projectTaskRepository.delete(projectTask);

    }

}
