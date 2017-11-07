package org.applicationn.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import org.applicationn.domain.ArtisteEntity;
import org.applicationn.domain.ArtisteImage;
import org.applicationn.domain.SpectacleEntity;
import org.applicationn.service.ArtisteService;
import org.applicationn.service.SpectacleService;
import org.applicationn.service.security.SecurityWrapper;
import org.applicationn.web.util.MessageFactory;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.UploadedFile;

@Named("artisteBean")
@ViewScoped
public class ArtisteBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(ArtisteBean.class.getName());
    
    private List<ArtisteEntity> artisteList;

    private ArtisteEntity artiste;
    
    @Inject
    private ArtisteService artisteService;
    
    UploadedFile uploadedImage;
    byte[] uploadedImageContents;
    
    @Inject
    private SpectacleService spectacleService;
    
    private DualListModel<SpectacleEntity> spectacless;
    private List<String> transferedSpectaclesIDs;
    private List<String> removedSpectaclesIDs;
    
    public void prepareNewArtiste() {
        reset();
        this.artiste = new ArtisteEntity();
        // set any default values now, if you need
        // Example: this.artiste.setAnything("test");
    }

    public String persist() {

        if (artiste.getId() == null && !isPermitted("artiste:create")) {
            return "accessDenied";
        } else if (artiste.getId() != null && !isPermitted(artiste, "artiste:update")) {
            return "accessDenied";
        }
        
        String message;
        
        try {
            
            if (this.uploadedImage != null) {
                try {

                    BufferedImage image;
                    try (InputStream in = new ByteArrayInputStream(uploadedImageContents)) {
                        image = ImageIO.read(in);
                    }
                    image = Scalr.resize(image, Method.BALANCED, 300);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageOutputStream imageOS = ImageIO.createImageOutputStream(baos);
                    Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByMIMEType(
                            uploadedImage.getContentType());
                    ImageWriter imageWriter = (ImageWriter) imageWriters.next();
                    imageWriter.setOutput(imageOS);
                    imageWriter.write(image);
                    
                    baos.close();
                    imageOS.close();
                    
                    logger.log(Level.INFO, "Resized uploaded image from {0} to {1}", new Object[]{uploadedImageContents.length, baos.toByteArray().length});
            
                    ArtisteImage artisteImage = new ArtisteImage();
                    artisteImage.setContent(baos.toByteArray());
                    artiste.setImage(artisteImage);
                } catch (Exception e) {
                    FacesMessage facesMessage = MessageFactory.getMessage(
                            "message_upload_exception");
                    FacesContext.getCurrentInstance().addMessage(null, facesMessage);
                    FacesContext.getCurrentInstance().validationFailed();
                    return null;
                }
            }
            
            if (artiste.getId() != null) {
                artiste = artisteService.update(artiste);
                message = "message_successfully_updated";
            } else {
                artiste = artisteService.save(artiste);
                message = "message_successfully_created";
            }
        } catch (OptimisticLockException e) {
            logger.log(Level.SEVERE, "Error occured", e);
            message = "message_optimistic_locking_exception";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error occured", e);
            message = "message_save_exception";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
        
        artisteList = null;

        FacesMessage facesMessage = MessageFactory.getMessage(message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
        return null;
    }
    
    public String delete() {
        
        if (!isPermitted(artiste, "artiste:delete")) {
            return "accessDenied";
        }
        
        String message;
        
        try {
            artisteService.delete(artiste);
            message = "message_successfully_deleted";
            reset();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occured", e);
            message = "message_delete_exception";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
        FacesContext.getCurrentInstance().addMessage(null, MessageFactory.getMessage(message));
        
        return null;
    }
    
    public void onDialogOpen(ArtisteEntity artiste) {
        reset();
        this.artiste = artiste;
    }
    
    public void reset() {
        artiste = null;
        artisteList = null;
        
        spectacless = null;
        transferedSpectaclesIDs = null;
        removedSpectaclesIDs = null;
        
        uploadedImage = null;
        uploadedImageContents = null;
        
    }

    public DualListModel<SpectacleEntity> getSpectacless() {
        return spectacless;
    }

    public void setSpectacless(DualListModel<SpectacleEntity> spectacles) {
        this.spectacless = spectacles;
    }
    
    public List<SpectacleEntity> getFullSpectaclessList() {
        List<SpectacleEntity> allList = new ArrayList<>();
        allList.addAll(spectacless.getSource());
        allList.addAll(spectacless.getTarget());
        return allList;
    }
    
    public void onSpectaclessDialog(ArtisteEntity artiste) {
        // Prepare the spectacles PickList
        this.artiste = artiste;
        List<SpectacleEntity> selectedSpectaclesFromDB = spectacleService
                .findSpectaclessByArtistes(this.artiste);
        List<SpectacleEntity> availableSpectaclesFromDB = spectacleService
                .findAvailableSpectacless(this.artiste);
        this.spectacless = new DualListModel<>(availableSpectaclesFromDB, selectedSpectaclesFromDB);
        
        transferedSpectaclesIDs = new ArrayList<>();
        removedSpectaclesIDs = new ArrayList<>();
    }
    
    public void onSpectaclessPickListTransfer(TransferEvent event) {
        // If a spectacles is transferred within the PickList, we just transfer it in this
        // bean scope. We do not change anything it the database, yet.
        for (Object item : event.getItems()) {
            String id = ((SpectacleEntity) item).getId().toString();
            if (event.isAdd()) {
                transferedSpectaclesIDs.add(id);
                removedSpectaclesIDs.remove(id);
            } else if (event.isRemove()) {
                removedSpectaclesIDs.add(id);
                transferedSpectaclesIDs.remove(id);
            }
        }
        
    }
    
    public void updateSpectacles(SpectacleEntity spectacle) {
        // If a new spectacles is created, we persist it to the database,
        // but we do not assign it to this artiste in the database, yet.
        spectacless.getTarget().add(spectacle);
        transferedSpectaclesIDs.add(spectacle.getId().toString());
    }
    
    public void onSpectaclessSubmit() {
        // Now we save the changed of the PickList to the database.
        try {
            
            List<SpectacleEntity> selectedSpectaclesFromDB = spectacleService.findSpectaclessByArtistes(this.artiste);
            List<SpectacleEntity> availableSpectaclesFromDB = spectacleService.findAvailableSpectacless(this.artiste);

            for (SpectacleEntity spectacle : selectedSpectaclesFromDB) {
                if (removedSpectaclesIDs.contains(spectacle.getId().toString())) {
                    
                    // Because artistess are lazy loaded, we need to fetch them now
                    spectacle = spectacleService.fetchArtistess(spectacle);
                    spectacle.getArtistess().remove(artiste);
                    spectacleService.update(spectacle);
                    
                }
            }
    
            for (SpectacleEntity spectacle : availableSpectaclesFromDB) {
                if (transferedSpectaclesIDs.contains(spectacle.getId().toString())) {
                    
                    // Because artistess are lazy loaded, we need to fetch them now
                    spectacle = spectacleService.fetchArtistess(spectacle);
                    spectacle.getArtistess().add(artiste);
                    spectacleService.update(spectacle);
                    
                }
            }
            
            FacesMessage facesMessage = MessageFactory.getMessage("message_changes_saved");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            
            reset();

        } catch (OptimisticLockException e) {
            FacesMessage facesMessage = MessageFactory.getMessage(
                    "message_optimistic_locking_exception");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        } catch (PersistenceException e) {
            FacesMessage facesMessage = MessageFactory.getMessage(
                    "message_picklist_save_exception");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
    }
    
    public void handleImageUpload(FileUploadEvent event) {
        
        Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByMIMEType(
                event.getFile().getContentType());
        if (!imageWriters.hasNext()) {
            FacesMessage facesMessage = MessageFactory.getMessage(
                    "message_image_type_not_supported");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            return;
        }
        
        this.uploadedImage = event.getFile();
        this.uploadedImageContents = event.getFile().getContents();
        
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public byte[] getUploadedImageContents() {
        if (uploadedImageContents != null) {
            return uploadedImageContents;
        } else if (artiste != null && artiste.getImage() != null) {
            artiste = artisteService.lazilyLoadImageToArtiste(artiste);
            return artiste.getImage().getContent();
        }
        return null;
    }
    
    public ArtisteEntity getArtiste() {
        if (this.artiste == null) {
            prepareNewArtiste();
        }
        return this.artiste;
    }
    
    public void setArtiste(ArtisteEntity artiste) {
        this.artiste = artiste;
    }
    
    public List<ArtisteEntity> getArtisteList() {
        if (artisteList == null) {
            artisteList = artisteService.findAllArtisteEntities();
        }
        return artisteList;
    }

    public void setArtisteList(List<ArtisteEntity> artisteList) {
        this.artisteList = artisteList;
    }
    
    public boolean isPermitted(String permission) {
        return SecurityWrapper.isPermitted(permission);
    }

    public boolean isPermitted(ArtisteEntity artiste, String permission) {
        
        return SecurityWrapper.isPermitted(permission);
        
    }
    
}
