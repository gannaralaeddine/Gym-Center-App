package com.example.gymcenterapp.controllers;

import com.example.gymcenterapp.entities.Session;
import com.example.gymcenterapp.services.SessionService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/session")
@RequiredArgsConstructor
public class SessionController
{
    private final SessionService sessionService;

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


    @PutMapping(value = "/update-session/{session-id}")
    @ResponseBody
    public Session updateSession(@PathVariable("session-id") Long sessionId,@RequestBody Session session) { return sessionService.updateSession(sessionId,session); }


    @DeleteMapping(value = "/delete-session/{Session-id}")
    public void deleteSession(@PathVariable("Session-id") Long SessionId) { sessionService.deleteSession(SessionId); }

    @PutMapping(value = "/update-session-with-image", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @ResponseBody
    public Session updateSessionWithImage(@RequestPart("session") Session session, @RequestPart("imageFile") MultipartFile[] image) { return sessionService.updateSession(session,image); }


    // Add Images to Session
//--------------------------------------------------------------------------------------------------------------------------

    @PutMapping(value = { "/add-images-to-session" }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public Session addImagesToSession(@RequestPart("id") Long sessionId,
                                        @RequestPart("imageFile") MultipartFile[] images)
    {
        return sessionService.addImagesToSession(sessionId, images);
    }

// Delete Session image
//----------------------------------------------------------------------------------------------------------------------
    @DeleteMapping(value = { "/delete-session-image/{sessionId}/{imageName}" })
    @ResponseBody
    public Session deleteSessionImage(@PathVariable Long sessionId, @PathVariable String imageName)
    {
        return sessionService.deleteSessionImage(sessionId, imageName);
    }


// Assign member to session
//----------------------------------------------------------------------------------------------------------------------
    @PutMapping("/assign-member-to-session/{email}/{sessionId}")
    public ResponseEntity<String> assignMemberToSession(@PathVariable String email, @PathVariable Long sessionId)
    {
        return sessionService.assignMemberToSession(email, sessionId);
    }


// Remove member from session
//----------------------------------------------------------------------------------------------------------------------
    @PutMapping("/remove-member-from-session/{email}/{sessionId}")
    public ResponseEntity<String> removeMemberFromSession(@PathVariable String email, @PathVariable Long sessionId)
    {
        return sessionService.removeMemberFromSession(email, sessionId);
    }


    @PostMapping("/is-member-participated-to-session/{email}/{sessionId}")
    public boolean isMemberParticipatedToSession(@PathVariable String email, @PathVariable Long sessionId)
    {
        boolean b = sessionService.isMemberParticipatedToSession(email, sessionId);
        System.out.println("isMemberParticipatedToSession: " + b);
        return b;
    }
}
