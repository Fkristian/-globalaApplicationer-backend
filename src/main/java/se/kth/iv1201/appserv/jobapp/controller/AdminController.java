package se.kth.iv1201.appserv.jobapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.kth.iv1201.appserv.jobapp.domain.User;
import se.kth.iv1201.appserv.jobapp.domain.external.request.StatusRequst;
import se.kth.iv1201.appserv.jobapp.domain.external.response.GenericResponse;
import se.kth.iv1201.appserv.jobapp.exceptions.IllegalJobApplicationUpdateException;
import se.kth.iv1201.appserv.jobapp.service.ApplicationService;


import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "https://frontendjobbapp.herokuapp.com")
@RequestMapping("/admin")
public class AdminController {

    private final ApplicationService applicationService;

    /**
     * Method to check if user has acces to admin backend.
     *
     * @return An OK response.
     */
    @GetMapping("hello")
    public GenericResponse sayHello(){
        return GenericResponse.OK;
    }

    /**
     * Method to retrieve all current applications in the database to the Front End.
     *
     * @return All current users as a JSON-object.
     */
    @GetMapping("all")
    public ResponseEntity <List<User>> getAllApplications(){
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    /**
     * Method to retrieve one specific application to the Front End from the database.
     *
     * @param id the integer ID used to find the application.
     * @return the specified application as a JSON-object.
     */
    @PostMapping("specificApplication")
    public ResponseEntity <User> getApplicationById(@RequestBody int id){
        return ResponseEntity.ok(applicationService.getApplicationById(id));
    }

    /**
     * Method used to update an application from the Front End in the database.
     * @param statusRequest the request-body containing information about the application status.
     * @return an HTTP-status code to inform the Front End how the transaction went.
     */
    @PutMapping("update-status")
    public ResponseEntity <?> updateApplicationStatus(@RequestBody StatusRequst statusRequest) throws IllegalJobApplicationUpdateException {
        return applicationService.updateApplicationStatus(statusRequest);
    }
}
