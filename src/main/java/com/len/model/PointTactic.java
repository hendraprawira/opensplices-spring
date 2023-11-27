package com.len.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Arrays;

@Entity
@Table(name = "tactical_figure")
public class PointTactic {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    public String getFigureType() {
        return figureType;
    }

    public void setFigureType(String figureType) {
        this.figureType = figureType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAmplifications() {
        return amplifications;
    }

    public void setAmplifications(String amplifications) {
        this.amplifications = amplifications;
    }

    public int getOpacity() {
        return opacity;
    }

    public void setOpacity(int opacity) {
        this.opacity = opacity;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public boolean isSaveDb() {
        return saveDb;
    }

    public void setSaveDb(boolean saveDb) {
        this.saveDb = saveDb;
    }

    public String getIdUnique() {
        return idUnique;
    }

    public void setIdUnique(String idUnique) {
        this.idUnique = idUnique;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    private PointTactic() {}
    public PointTactic(double[] coordinates, String figureType, String color, String amplifications, int opacity, double altitude, boolean saveDb, String idUnique, String method) {
        this.coordinates = coordinates;
        this.figureType = figureType;
        this.color = color;
        this.amplifications = amplifications;
        this.opacity = opacity;
        this.altitude = altitude;
        this.saveDb = saveDb;
        this.idUnique = idUnique;
        this.method = method;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private double[] coordinates;
    private String figureType;

    private String color;

    private String amplifications;

    private int opacity;

    private double altitude;

    private boolean saveDb;

    private String idUnique;

    private String method;

    @Override
    public String toString() {
        return "PointTactic{" +
                "id=" + id +
                ", coordinates=" + Arrays.toString(coordinates) +
                ", figureType='" + figureType + '\'' +
                ", color='" + color + '\'' +
                ", amplifications='" + amplifications + '\'' +
                ", opacity=" + opacity +
                ", altitude=" + altitude +
                ", saveDb=" + saveDb +
                ", idUnique='" + idUnique + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}
