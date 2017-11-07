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
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import org.applicationn.domain.ArtisteEntity;
import org.applicationn.domain.SpectacleEntity;
import org.applicationn.domain.SpectacleGenre;
import org.applicationn.domain.SpectacleImage;
import org.applicationn.domain.SpectaclePublicc;
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

@Named("spectacleBean")
@ViewScoped
public class SpectacleBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(SpectacleBean.class.getName());
    
    private List<SpectacleEntity> spectacleList;

    private SpectacleEntity spectacle;
    
    @Inject
    private SpectacleService spectacleService;
    
    UploadedFile uploadedImage;
    byte[] uploadedImageContents;
    
    @Inject
    private ArtisteService artisteService;
    
    private DualListModel<ArtisteEntity> artistess;
    private List<String> transferedArtistesIDs;
    private List<String> removedArtistesIDs;
    
    public void prepareNewSpectacle() {
        reset();
        this.spectacle = new SpectacleEntity();
        // set any default values now, if you need
        // Example: this.spectacle.setAnything("test");
    }

    public String persist() {

        if (spectacle.getId() == null && !isPermitted("spectacle:create")) {
            return "accessDenied";
        } else if (spectacle.getId() != null && !isPermitted(spectacle, "spectacle:update")) {
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
            
                    SpectacleImage spectacleImage = new SpectacleImage();
                    spectacleImage.setContent(baos.toByteArray());
                    spectacle.setImage(spectacleImage);
                } catch (Exception e) {
                    FacesMessage facesMessage = MessageFactory.getMessage(
                            "message_upload_exception");
                    FacesContext.getCurrentInstance().addMessage(null, facesMessage);
                    FacesContext.getCurrentInstance().validationFailed();
                    return null;
                }
            }
            
            if (spectacle.getId() != null) {
                spectacle = spectacleService.update(spectacle);
                message = "message_successfully_updated";
            } else {
                spectacle = spectacleService.save(spectacle);
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
        
        spectacleList = null;

        FacesMessage facesMessage = MessageFactory.getMessage(message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
        return null;
    }
    
    public String delete() {
        
        if (!isPermitted(spectacle, "spectacle:delete")) {
            return "accessDenied";
        }
        
        String message;
        
        try {
            spectacleService.delete(spectacle);
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
    
    public void onDialogOpen(SpectacleEntity spectacle) {
        reset();
        this.spectacle = spectacle;
    }
    
    public void reset() {
        spectacle = null;
        spectacleList = null;
        
        artistess = null;
        transferedArtistesIDs = null;
        removedArtistesIDs = null;
        
        uploadedImage = null;
        uploadedImageContents = null;
        
    }

    public DualListModel<ArtisteEntity> getArtistess() {
        return artistess;
    }

    public void setArtistess(DualListModel<ArtisteEntity> artistes) {
        this.artistess = artistes;
    }
    
    public List<ArtisteEntity> getFullArtistessList() {
        List<ArtisteEntity> allList = new ArrayList<>();
        allList.addAll(artistess.getSource());
        allList.addAll(artistess.getTarget());
        return allList;
    }
    
    public void onArtistessDialog(SpectacleEntity spectacle) {
        // Prepare the artistes PickList
        this.spectacle = spectacle;
        List<ArtisteEntity> selectedArtistesFromDB = artisteService
                .findArtistessBySpectacles(this.spectacle);
        List<ArtisteEntity> availableArtistesFromDB = artisteService
                .findAvailableArtistess(this.spectacle);
        this.artistess = new DualListModel<>(availableArtistesFromDB, selectedArtistesFromDB);
        
        transferedArtistesIDs = new ArrayList<>();
        removedArtistesIDs = new ArrayList<>();
    }
    
    public void onArtistessPickListTransfer(TransferEvent event) {
        // If a artistes is transferred within the PickList, we just transfer it in this
        // bean scope. We do not change anything it the database, yet.
        for (Object item : event.getItems()) {
            String id = ((ArtisteEntity) item).getId().toString();
            if (event.isAdd()) {
                transferedArtistesIDs.add(id);
                removedArtistesIDs.remove(id);
            } else if (event.isRemove()) {
                removedArtistesIDs.add(id);
                transferedArtistesIDs.remove(id);
            }
        }
        
    }
    
    public void updateArtistes(ArtisteEntity artiste) {
        // If a new artistes is created, we persist it to the database,
        // but we do not assign it to this spectacle in the database, yet.
        artistess.getTarget().add(artiste);
        transferedArtistesIDs.add(artiste.getId().toString());
    }
    
    public void onArtistessSubmit() {
        // Now we save the changed of the PickList to the database.
        try {
            
            List<ArtisteEntity> selectedArtistesFromDB = artisteService.findArtistessBySpectacles(this.spectacle);
            List<ArtisteEntity> availableArtistesFromDB = artisteService.findAvailableArtistess(this.spectacle);

            // Because artistess are lazily loaded, we need to fetch them now
            this.spectacle = spectacleService.fetchArtistess(this.spectacle);
            
            for (ArtisteEntity artiste : selectedArtistesFromDB) {
                if (removedArtistesIDs.contains(artiste.getId().toString())) {
                    
                    this.spectacle.getArtistess().remove(artiste);
                    
                }
            }
    
            for (ArtisteEntity artiste : availableArtistesFromDB) {
                if (transferedArtistesIDs.contains(artiste.getId().toString())) {
                    
                    this.spectacle.getArtistess().add(artiste);
                    
                }
            }
            
            this.spectacle = spectacleService.update(this.spectacle);
            
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
    
    public SelectItem[] getGenreSelectItems() {
        SelectItem[] items = new SelectItem[SpectacleGenre.values().length];

        int i = 0;
        for (SpectacleGenre genre : SpectacleGenre.values()) {
            items[i++] = new SelectItem(genre, getLabelForGenre(genre));
        }
        return items;
    }
    
    public String getLabelForGenre(SpectacleGenre value) {
        if (value == null) {
            return "";
        }
        String label = MessageFactory.getMessageString(
                "enum_label_spectacle_genre_" + value);
        return label == null? value.toString() : label;
    }
    
    public SelectItem[] getPubliccSelectItems() {
        SelectItem[] items = new SelectItem[SpectaclePublicc.values().length];

        int i = 0;
        for (SpectaclePublicc publicc : SpectaclePublicc.values()) {
            items[i++] = new SelectItem(publicc, getLabelForPublicc(publicc));
        }
        return items;
    }
    
    public String getLabelForPublicc(SpectaclePublicc value) {
        if (value == null) {
            return "";
        }
        String label = MessageFactory.getMessageString(
                "enum_label_spectacle_publicc_" + value);
        return label == null? value.toString() : label;
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
        } else if (spectacle != null && spectacle.getImage() != null) {
            spectacle = spectacleService.lazilyLoadImageToSpectacle(spectacle);
            return spectacle.getImage().getContent();
        }
        return null;
    }
    
    public SpectacleEntity getSpectacle() {
        if (this.spectacle == null) {
            prepareNewSpectacle();
        }
        return this.spectacle;
    }
    
    public void setSpectacle(SpectacleEntity spectacle) {
        this.spectacle = spectacle;
    }
    
    public List<SpectacleEntity> getSpectacleList() {
        if (spectacleList == null) {
            spectacleList = spectacleService.findAllSpectacleEntities();
        }
        return spectacleList;
    }

    public void setSpectacleList(List<SpectacleEntity> spectacleList) {
        this.spectacleList = spectacleList;
    }
    
    public boolean isPermitted(String permission) {
        return SecurityWrapper.isPermitted(permission);
    }

    public boolean isPermitted(SpectacleEntity spectacle, String permission) {
        
        return SecurityWrapper.isPermitted(permission);
        
    }
    
}
