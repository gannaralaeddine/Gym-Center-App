package com.example.gymcenterapp.interfaces;

import com.example.gymcenterapp.entities.Session;

import java.util.List;

public interface ISessionService
{
    Session addSession(Session session);

    List<Session> retrieveAllSessions();

    Session retrieveSession(Long id);

    void deleteSession(Long id);

    Session updateSession(Long id, Session session);
}
