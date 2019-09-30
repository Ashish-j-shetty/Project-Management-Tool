package com.fullstack.pmtool.web;

import com.fullstack.pmtool.domain.Project;
import com.fullstack.pmtool.domain.ProjectTask;
import com.fullstack.pmtool.services.MapValidationErrorService;
import com.fullstack.pmtool.services.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;


@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class  BacklogController {

     @Autowired
     private ProjectTaskService projectTaskService;

     @Autowired
     private MapValidationErrorService mapValidationErrorService;

     @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addProjectTaskToBacklog(@Valid @RequestBody ProjectTask projectTask,
                                                     BindingResult result, @PathVariable String backlog_id,
                                                     Principal principal){
        ResponseEntity<?> errorMap=mapValidationErrorService.mapValidationService(result);
        if(errorMap !=null){
            return errorMap;
        }
        ProjectTask projectTask1=projectTaskService.addProjectTask(backlog_id,projectTask,principal.getName());

        return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);
     }

     @GetMapping("/{backlog_id}")
     public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id,Principal principal){
         return projectTaskService.findBacklogById(backlog_id , principal.getName());

     }

     @GetMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> getProjectTaskBySequence(@PathVariable String backlog_id,@PathVariable String pt_id ,Principal principal){
         ProjectTask projectTask=projectTaskService.findPTByProjectSequence(backlog_id,pt_id,principal.getName());
         return new ResponseEntity<ProjectTask>(projectTask,HttpStatus.OK);

     }

     @PatchMapping("/{backlog_id}/{pt_id}")
        public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask , BindingResult result ,
                                                   @PathVariable String backlog_id,@PathVariable String pt_id ,Principal principal){
             ResponseEntity<?> errorMap=mapValidationErrorService.mapValidationService(result);
             if(errorMap !=null){
                 return errorMap;
             }

             ProjectTask updatedProjectTask =  projectTaskService.updatedByProjectSequence(projectTask,backlog_id,pt_id,principal.getName());

             return new ResponseEntity<ProjectTask>(updatedProjectTask,HttpStatus.OK);
         }

    @DeleteMapping("/{backlog_id}/{pt_id}")
    public  ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String  pt_id,Principal principal){
            projectTaskService.deletePTByProjectSequence(backlog_id,pt_id,principal.getName());
            return  new ResponseEntity<String>("Project task '"+pt_id+"'deleted successfully",HttpStatus.OK);
    }

}
