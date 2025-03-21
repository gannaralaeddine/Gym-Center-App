package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.*;
import com.example.gymcenterapp.interfaces.ISessionService;
import com.example.gymcenterapp.repositories.ImageModelRepository;
import com.example.gymcenterapp.repositories.MemberRepository;
import com.example.gymcenterapp.repositories.SessionRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SessionService implements ISessionService
{
    @Value("${image.storage.path}")
    private String directory;

    private final SessionRepository sessionRepository;
    private final EmailServiceImpl emailService;
    private final ImageModelService imageModelService;
    private final ImageModelRepository imageModelRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<Session> retrieveAllSessions() { return sessionRepository.findAll(); }

    @Override
    public Session retrieveSession(Long id) { return sessionRepository.findById(id).orElse(null); }

    @Override
    public void deleteSession(Long id) 
    { 
        Session session = sessionRepository.findById(id).orElse(null);

        if (session != null)
        {
            emailService.sendCancelSessionEmail(session);
            session.setSessionActivity(null);
            session.setSessionCoach(null);
            session.getSessionMembers().forEach((member) -> {
                member.getMemberSessions().remove(session);
            });
            sessionRepository.deleteById(sessionRepository.save(session).getSessionId());
        }
    }

    @Override
    public Session updateSession(Long id, Session session)
    {
        Session existingSession = sessionRepository.findById(id).orElse(null);

        if (existingSession != null)
        {
            existingSession.setSessionName(session.getSessionName());
            existingSession.setSessionDescription(session.getSessionDescription());
            existingSession.setSessionDate(session.getSessionDate());
            existingSession.setSessionTotalPlaces(session.getSessionTotalPlaces());
            existingSession.setSessionDescription(session.getSessionDescription());
            existingSession.setSessionActivity(session.getSessionActivity());
            existingSession.setSessionCoach(session.getSessionCoach());
            existingSession.setSessionPrice(session.getSessionPrice());
            return sessionRepository.save(existingSession);
        }

        return null;
    }

    @Override
    public Session addSessionWithOneImage(Session session, MultipartFile[] file) 
    {
        // generate unique name and file path for image
        String[] imageType = Objects.requireNonNull(file[0].getContentType()).split("/");
        String uniqueName = imageModelService.generateUniqueName() + "." + imageType[1];
        String filePath = directory + "sessions\\" + uniqueName;

        try
        {
            ImageModel imageModel = new  ImageModel();
            imageModel.setImageName(uniqueName);
            imageModel.setImageType(file[0].getContentType());
            imageModel.setImageSize(file[0].getSize());
            imageModel.setImageUrl(filePath);

            // populate images with recieved images
            HashSet<ImageModel> images = new HashSet<>();
            images.add(imageModel);

            // affect image name to sessionImage attribute
            session.setSessionImage(uniqueName);

            // affect multiple images to sessionImages attribute
            session.setSessionImages(images);

            // save image to specific directory
            file[0].transferTo(new File(filePath));

            session.setSessionDate(subtractOneHour(session.getSessionDate()));

            return sessionRepository.save(session);
        }
        catch (Exception e)
        {
            System.out.println("Error in create activity: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Session addImagesToSession(Long sessionId, MultipartFile[] files) 
    {
       try
        {
            Session session = sessionRepository.findById(sessionId).orElse(null);

            assert session != null;
            Set<ImageModel> images =  imageModelService.prepareFiles(files, session.getSessionImages(), directory + "sessions\\");

            session.setSessionImages(images);

            return sessionRepository.save(session);
        }
        catch (Exception e)
        {
            System.out.println("Error in add Images To session: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Session updateSession(Session session, MultipartFile[] file)
    {
        Session existingSession = sessionRepository.findById(session.getSessionId()).orElse(null);

        if (existingSession != null)
        {
            existingSession.setSessionName(session.getSessionName());
            existingSession.setSessionDescription(session.getSessionDescription());
            existingSession.setSessionActivity(session.getSessionActivity());
            existingSession.setSessionCoach(session.getSessionCoach());
            existingSession.setSessionPrice(session.getSessionPrice());
            existingSession.setSessionDate(subtractOneHour(session.getSessionDate()));

            String[] imageType = Objects.requireNonNull(file[0].getContentType()).split("/");
            String uniqueName = imageModelService.generateUniqueName() + "." + imageType[1];
            String filePath = directory + "sessions\\" + uniqueName;

            try
            {
                ImageModel imageModel = new ImageModel();
                imageModel.setImageName( uniqueName );
                imageModel.setImageType(file[0].getContentType());
                imageModel.setImageSize(file[0].getSize());
                imageModel.setImageUrl(filePath);

                Set<ImageModel> images = existingSession.getSessionImages();
                images.add(imageModel);

                file[0].transferTo(new File(filePath));

                ImageModel existingImageModel = imageModelService.findImageByName(existingSession.getSessionImage());

                images.remove(existingImageModel);
                imageModelRepository.delete(existingImageModel);
                imageModelService.removeFile(directory + "sessions\\", existingSession.getSessionImage());


                existingSession.setSessionImage( uniqueName );
                existingSession.setSessionImages(images);

                existingSession.setSessionDate(subtractOneHour(session.getSessionDate()));

                return sessionRepository.save(existingSession);
            }
            catch (Exception e)
            {
                System.out.println("Error in update session with image: " + e.getMessage());
                return null;
            }
        }
        else
        {
            return null;
        }
    }


    @Override
    public Session deleteSessionImage(Long sessionId, String imageName)
    {
        Session session = sessionRepository.findById(sessionId).orElse(null);
        ImageModel imageModel = imageModelRepository.findByName(imageName);

        if (session != null && imageModel != null)
        {
            session.getSessionImages().remove(imageModel);
            imageModelService.removeFile(directory + "sessions\\", imageName);
            imageModelRepository.delete(imageModel);
            return sessionRepository.save(session);
        }
        else
        {
            System.out.println("Session or image is null");
            return null;
        }
    }


    public ResponseEntity<String> assignMemberToSession(String email, Long sessionId)
    {

        Member member = memberRepository.findByEmail(email);
        Session session = sessionRepository.findById(sessionId).orElse(null);

        if ((member != null) && (session != null))
        {
            Set<Session> memberSessions = member.getMemberSessions();
            Set<Member> sessionMembers = session.getSessionMembers();

            if (sessionMembers.size() >= session.getSessionTotalPlaces())
            {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("No free places in this session !");
            }

            if (sessionMembers.contains(member))
            {
                return ResponseEntity.status(HttpStatus.FOUND).body("Member already participated to session !");
            }

            memberSessions.add(session);
            sessionMembers.add(member);

            member.setMemberSessions(memberSessions);
            session.setSessionMembers(sessionMembers);
            
            if (session.getSessionReservedPlaces() != null)
            {
                session.setSessionReservedPlaces(session.getSessionReservedPlaces() + 1);
            }
            else
            {
                session.setSessionReservedPlaces(1);
            }

            memberRepository.save(member);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Member or session is null in assignMemberToSession");
    }


    public ResponseEntity<String> removeMemberFromSession(String email, Long sessionId)
    {

        Member member = memberRepository.findByEmail(email);
        Session session = sessionRepository.findById(sessionId).orElse(null);

        if ((member != null) && (session != null))
        {
            Set<Session> memberSessions = member.getMemberSessions();
            Set<Member> sessionMembers = session.getSessionMembers();


            memberSessions.remove(session);
            sessionMembers.remove(member);

            member.setMemberSessions(memberSessions);
            session.setSessionMembers(sessionMembers);


            session.setSessionReservedPlaces(session.getSessionReservedPlaces() - 1);


            memberRepository.save(member);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Member or session is null in assignMemberToSession");
    }

    public boolean isMemberParticipatedToSession(String email, Long sessionId)
    {
        Member member = memberRepository.findByEmail(email);
        Session session = sessionRepository.findById(sessionId).orElse(null);

        if (member != null && session != null)
        {
            return session.getSessionMembers().contains(member);
        }
        return false;
    }

    public Session addSessionWithOneImage(Session session) 
    {
        return sessionRepository.save(session);
    }

    public Date subtractOneHour(Date date) {
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
