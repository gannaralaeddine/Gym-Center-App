package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.Session;
import com.example.gymcenterapp.interfaces.ISessionService;
import com.example.gymcenterapp.repositories.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class SessionService implements ISessionService
{ 
    SessionRepository sessionRepository;

    @Override
    public Session addSession(Session session) { return sessionRepository.save(session); }

    @Override
    public List<Session> retrieveAllSessions() { return sessionRepository.findAll(); }

    @Override
    public Session retrieveSession(Long id) { return sessionRepository.findById(id).orElse(null); }

    @Override
    public void deleteSession(Long id) { sessionRepository.deleteById(id);}

    @Override
    public Session updateSession(Long id, Session session)
    {
        Session existingSession = sessionRepository.findById(id).orElse(null);

        if (existingSession != null)
        {
            existingSession.setSessionActivity(session.getSessionActivity());
            existingSession.setSessionCoach(session.getSessionCoach());
            existingSession.setSessionMembers(session.getSessionMembers());
            return sessionRepository.save(existingSession);
        }

        return null;
    }
}
