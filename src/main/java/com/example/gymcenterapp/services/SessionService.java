package com.example.gymcenterapp.services;

import com.example.gymcenterapp.entities.ImageModel;
import com.example.gymcenterapp.entities.Session;
import com.example.gymcenterapp.interfaces.ISessionService;
import com.example.gymcenterapp.repositories.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class SessionService implements ISessionService
{
    SessionRepository sessionRepository;
    ImageModelService imageModelService;   
    
//    final String directory = "C:\\Users\\ganna\\IdeaProjects\\Gym-Center-App\\src\\main\\resources\\static\\sessions\\";
    final String directory = "C:\\Users\\awadi\\Desktop\\Projet PFE\\back\\Gym-Center-App\\src\\main\\resources\\static\\sessions\\";

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
        System.out.println("session: " + session);
        Session existingSession = sessionRepository.findById(id).orElse(null);
        
        if (existingSession != null)
        {
            existingSession.setSessionName(session.getSessionName());
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
        String[] imageType = file[0].getContentType().split("/");
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
            System.out.println("Error in add Images To Category: " + e.getMessage());
            return null;
        }
    }
}
