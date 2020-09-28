package com.trackthatbug.trackthatbug.controllers;

import com.trackthatbug.trackthatbug.models.Issue;
import com.trackthatbug.trackthatbug.models.Result;
import com.trackthatbug.trackthatbug.repositories.IssueRepository;
import com.trackthatbug.trackthatbug.services.NextSequenceService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.time.ZonedDateTime;
import java.util.Date;

@RestController
public class IssueController {
    private IssueRepository issueRepository;
    private NextSequenceService nextSequenceService;

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping(value = "/createIssue", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Result> createIssue(@RequestPart("properties") @Valid Issue issue,
                                              @RequestPart("files") MultipartFile[] files, BindingResult result, Principal principal){
        String user = principal.getName();
        issue.setUser(user);
        issue.setCreatedBy(user);
        issue.setCreatedOn(new Date());
        issue.setIssueNumber(nextSequenceService.getNextSequence("CustomSequence"));
        try {
            issue.setFile(new Binary(BsonBinarySubType.BINARY, files[0].getBytes()));
        } catch(IOException e){
            //if file fails to load show error
            return new ResponseEntity<>(new Result(), HttpStatus.BAD_REQUEST);
        }
        issueRepository.save(issue);
        return new ResponseEntity<>(new Result(), HttpStatus.OK);
    }

    @Autowired
    public void setIssueRepository(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @Autowired
    public void setNextSequenceService(NextSequenceService nextSequenceService) {
        this.nextSequenceService = nextSequenceService;
    }
}