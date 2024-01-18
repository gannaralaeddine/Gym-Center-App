package com.example.gymcenterapp.interfaces;

import com.example.gymcenterapp.entities.Session;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ISessionService
{
    Session addSessionWithOneImage(Session session, MultipartFile[] file);

    Session addImagesToSession( Long sessionId, MultipartFile[] files );

    List<Session> retrieveAllSessions();

    Session retrieveSession(Long id);

    void deleteSession(Long id);

    Session updateSession(Long id, Session session);

    Session updateSession(Session session, MultipartFile[] file);
}
