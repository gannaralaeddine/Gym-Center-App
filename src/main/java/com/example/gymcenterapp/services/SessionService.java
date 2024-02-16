package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.*;
import com.example.gymcenterapp.interfaces.ISessionService;
import com.example.gymcenterapp.repositories.ImageModelRepository;
import com.example.gymcenterapp.repositories.MemberRepository;
import com.example.gymcenterapp.repositories.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
public class SessionService implements ISessionService
{
    SessionRepository sessionRepository;
    ImageModelService imageModelService;   
    ImageModelRepository imageModelRepository;
    MemberRepository memberRepository;

    final String directory = "C:\\Users\\ganna\\IdeaProjects\\Gym-Center-App\\src\\main\\resources\\static\\sessions\\";
//    final String directory = "C:\\Users\\awadi\\Desktop\\Projet PFE\\back\\Gym-Center-App\\src\\main\\resources\\static\\sessions\\";


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
            existingSession.setSessionName(session.getSessionName());
            existingSession.setSessionDescription(session.getSessionDescription());
            existingSession.setSessionDeadline(session.getSessionDeadline());
            existingSession.setSessionTotalPlaces(session.getSessionTotalPlaces());
            existingSession.setSessionReservedPlaces(session.getSessionReservedPlaces());
            existingSession.setSessionDescription(session.getSessionDescription());
            existingSession.setSessionActivity(session.getSessionActivity());
            existingSession.setSessionCoach(session.getSessionCoach());
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
        String filePath = directory + uniqueName;

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
            Set<ImageModel> images =  imageModelService.prepareFiles(files, session.getSessionImages(), directory);

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

            String[] imageType = Objects.requireNonNull(file[0].getContentType()).split("/");
            String uniqueName = imageModelService.generateUniqueName() + "." + imageType[1];
            String filePath = directory + uniqueName;

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
                imageModelService.removeFile(directory, existingSession.getSessionImage());


                existingSession.setSessionImage( uniqueName );
                existingSession.setSessionImages(images);


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
            imageModelService.removeFile(directory, imageName);
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
            sessionRepository.save(session);

            return ResponseEntity.ok("Member participated successfully in session !");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Member or session is null in assignMemberToSession");
    }
}
