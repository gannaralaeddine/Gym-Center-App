package com.example.gymcenterapp.controllers;

import com.example.gymcenterapp.entities.PrivateSession;
import com.example.gymcenterapp.services.PrivateSessionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@RequestMapping("/private-session")
@AllArgsConstructor
public class PrivateSessionController
{
    private PrivateSessionService privateSessionService;

    @PostMapping(value = "/add-private-session")
    @ResponseBody
    public PrivateSession addPrivateSession(@RequestBody PrivateSession privateSession) { return privateSessionService.addPrivateSession(privateSession); }

    @GetMapping("/retrieve-all-private-sessions")
    @ResponseBody
    public Set<PrivateSession> getAllPrivateSessions() { return privateSessionService.retrieveAvailablePrivateSessions(); }

    @GetMapping("/retrieve-private-session/{id}")
    @ResponseBody
    public PrivateSession retrievePrivateSession(@PathVariable("id") Long id) { return privateSessionService.retrievePrivateSession(id); }


    @PutMapping(value = "/cancel-private-session/{memberEmail}/{privateSessionId}")
    @ResponseBody
    public ResponseEntity<String> cancelPrivateSession(@PathVariable String memberEmail, @PathVariable Long privateSessionId)
    {
        return privateSessionService.cancelPrivateSession(memberEmail, privateSessionId);
    }
}
