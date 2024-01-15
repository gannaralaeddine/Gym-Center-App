package com.example.gymcenterapp.controllers;

import com.example.gymcenterapp.entities.Session;
import com.example.gymcenterapp.services.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/session")
@AllArgsConstructor

public class SessionController
{
    SessionService sessionService;

    @PostMapping(value = { "/create-session" }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public Session addSessionWithOneImage(@RequestPart("session") Session session,
                                            @RequestPart("imageFile") MultipartFile[] images)
    {
        return sessionService.addSessionWithOneImage(session, images);
    }

    @GetMapping("/retrieve-all-sessions")
    @ResponseBody
    public List<Session> getAllSessions() { return sessionService.retrieveAllSessions(); }


    @GetMapping("/retrieve-session/{session-id}")
    @ResponseBody
    public Session retrieveSession(@PathVariable("session-id") Long sessionId) { return sessionService.retrieveSession(sessionId); }


    @PostMapping(value = "/add-session")
    @ResponseBody
    public Session addSession(@RequestBody Session session) { return sessionService.addSession(session); }

    @PutMapping(value = "/update-session/{session-id}")
    @ResponseBody
    public Session updateSession(@PathVariable("session-id") Long sessionId,@RequestBody Session session) { return sessionService.updateSession(sessionId,session); }


    @DeleteMapping(value = "/delete-session/{Session-id}")
    public void deleteSession(@PathVariable("Session-id") Long SessionId) { sessionService.deleteSession(SessionId); }

}
