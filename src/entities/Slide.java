/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.File;
import java.util.Objects;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author wajdi
 */
public class Slide {

    private SimpleIntegerProperty id;
    private SimpleIntegerProperty id_formation;
    private SimpleStringProperty video_slide;
    private SimpleStringProperty image_slide;
    private SimpleStringProperty text_slide;
    private SimpleIntegerProperty ordre;
    private SimpleStringProperty loc;
    private SimpleStringProperty url;

    public Slide() {
    }

    public Slide(int id, int id_formation, String video_slide, String image_slide, String text_slide, int ordre) {
        this.id = new SimpleIntegerProperty(id);
        this.id_formation = new SimpleIntegerProperty(id_formation);
        this.video_slide = new SimpleStringProperty(video_slide);
        this.image_slide = new SimpleStringProperty(image_slide);
        this.text_slide = new SimpleStringProperty(text_slide);
        this.ordre = new SimpleIntegerProperty(ordre);
    }

    public Slide(int id_formation, String video_slide, String image_slide, String text_slide, int ordre) {

        this.id_formation = new SimpleIntegerProperty(id_formation);
        this.video_slide = new SimpleStringProperty(video_slide);
        this.image_slide = new SimpleStringProperty(image_slide);
        this.text_slide = new SimpleStringProperty(text_slide);
        this.ordre = new SimpleIntegerProperty(ordre);
    }

    public void setId_formation(int id_formation) {
        this.id_formation = new SimpleIntegerProperty(id_formation);
    }

    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public void setVideo_slide(String video_slide) {
        this.video_slide = new SimpleStringProperty(video_slide);
    }

    public void setImage_slide(String image_slide) {
        this.image_slide = new SimpleStringProperty(image_slide);
    }

    public void setText_slide(String text_slide) {
        this.text_slide = new SimpleStringProperty(text_slide);
    }

    public void setOrdre(int ordre) {
        this.ordre = new SimpleIntegerProperty(ordre);
    }

    public SimpleIntegerProperty getId() {
        return id;
    }

    public SimpleIntegerProperty getId_formation() {
        return id_formation;
    }

    public SimpleStringProperty getVideo_slide() {
        return video_slide;
    }

    public SimpleStringProperty getImage_slide() {
        return image_slide;
    }

    public SimpleStringProperty getText_slide() {
        return text_slide;
    }

    public SimpleIntegerProperty getOrdre() {
        return ordre;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Slide other = (Slide) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.id_formation != other.id_formation) {
            return false;
        }
        if (this.ordre != other.ordre) {
            return false;
        }
        if (!Objects.equals(this.video_slide, other.video_slide)) {
            return false;
        }
        if (!Objects.equals(this.image_slide, other.image_slide)) {
            return false;
        }
        if (!Objects.equals(this.text_slide, other.text_slide)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Slide{" + "id=" + id + ", id_formation=" + id_formation + ", video_slide=" + video_slide + ", image_slide=" + image_slide + ", text_slide=" + text_slide + ", ordre=" + ordre + '}';
    }

    public SimpleStringProperty getUrl() {
        if (!this.getText_slide().get().equals("")) {
            url = getText_slide();
              return url;
        } else if (!(this.getImage_slide().get().equals(""))) {
            url = getImage_slide();
              return url;
        } else {
            url = getVideo_slide();
              return url;
        }

    }

   

}
