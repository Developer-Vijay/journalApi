package com.vijay.journalApp.controller;


import com.vijay.journalApp.entity.JournalEntry;
import com.vijay.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")

//controller will call the service and service will call the repository  controller -----> service ----> repository
public class JournalEntryControllerV2 {

    @Autowired
    JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getAll() {
        return journalEntryService.getAll();
    }
    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry myEntry) {
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return myEntry;
    }

    @GetMapping("id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId myId) {
        return  journalEntryService.getJournalById(myId).orElse(null);
    }


    @DeleteMapping("id/{myId}")
    public boolean deleteJournalEntryById(@PathVariable ObjectId myId) {
        journalEntryService.deleteJournalById(myId);
        return  true;
    }

    @PutMapping("id/{myId}")
    public boolean updateMyJournalEntry(@PathVariable ObjectId myId,@RequestBody JournalEntry updatedEntry){
      JournalEntry old=  journalEntryService.getJournalById(myId).orElse(null);
      if(old!=null)
      {
       old.setContent(updatedEntry.getContent()!=null && !updatedEntry.getContent().isEmpty() ? updatedEntry.getContent():old.getContent());
       old.setTitle(updatedEntry.getTitle()!=null && !updatedEntry.getTitle().isEmpty() ? updatedEntry.getTitle():old.getTitle());
      }
      journalEntryService.saveEntry(old);
        return true;
    }


}
