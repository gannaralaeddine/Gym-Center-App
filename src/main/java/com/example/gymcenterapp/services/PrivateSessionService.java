package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.Member;
import com.example.gymcenterapp.entities.PrivateSession;
import com.example.gymcenterapp.repositories.MemberRepository;
import com.example.gymcenterapp.repositories.PrivateSessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;

@Service
@AllArgsConstructor
public class PrivateSessionService
{
    private PrivateSessionRepository privateSessionRepository;
    private MemberRepository memberRepository;
    private EmailServiceImpl emailServiceImpl;

    public PrivateSession addPrivateSession(PrivateSession privateSession)
    {
        privateSession.setPrivateSessionStartDateTime(subtractOneHour(privateSession.getPrivateSessionStartDateTime()));
        privateSession.setPrivateSessionEndDateTime(subtractOneHour(privateSession.getPrivateSessionEndDateTime()));

        return privateSessionRepository.save(privateSession);
    }

    public Set<PrivateSession> retrieveAvailablePrivateSessions()
    {
        Set<PrivateSession> privateSessions = privateSessionRepository.retrieveAvailablePrivateSessions();

        System.out.println("privateSessions: " + privateSessions);

        return privateSessions;
    }

    public PrivateSession retrievePrivateSession(Long id)
    {
        return privateSessionRepository.findById(id).orElse(null);
    }


    public ResponseEntity<String> cancelPrivateSession(String memberEmail, Long privateSessionId)
    {
        PrivateSession privateSession = privateSessionRepository.findById(privateSessionId).orElse(null);
        Member member = memberRepository.findByEmail(memberEmail);

        if (privateSession != null && member != null)
        {
            emailServiceImpl.sendCancelPrivateSessionEmail(privateSession);
            privateSession.setPrivateSessionMember(null);
            privateSession.setPrivateSessionIsReserved(false);
            member.setPrivateSessionsNumber(member.getPrivateSessionsNumber() + 1);
            memberRepository.save(member);
            privateSessionRepository.save(privateSession);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public void removePrivateSession(Long privateSessionId) 
    {
        PrivateSession privateSession = privateSessionRepository.findById(privateSessionId).orElse(null);

        if (privateSession != null)
        {
            privateSession.setPrivateSessionCoach(null);
            privateSession.setPrivateSessionMember(null);
            privateSessionRepository.deleteById(privateSessionRepository.save(privateSession).getPrivateSessionId());
        }
       
    }

    public static Date subtractOneHour(Date date) {
        // Convert Date to LocalDateTime
        LocalDateTime localDateTime = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        // Subtract one hour
        LocalDateTime adjustedDateTime = localDateTime.minusHours(1);

        // Convert LocalDateTime back to Date
        return Date.from(adjustedDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
