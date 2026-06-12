package com.example.app.entity;

import javax.persistence.*;

@Entity
@Table(name = "distanceHotel")
public class DistanceHotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDistance")
    private Long idDistance;

    @Column(name = "idHotelDepart")
    private Long idHotelDepart;

    @Column(name = "idHotelArrivee")
    private Long idHotelArrivee;

    @Column(name = "distanceEntreHotel")
    private int distanceEntreHotel;

    public DistanceHotel() {}

    public Long getIdDistance() { return idDistance; }
    public void setIdDistance(Long idDistance) { this.idDistance = idDistance; }

    public Long getIdHotelDepart() { return idHotelDepart; }
    public void setIdHotelDepart(Long idHotelDepart) { this.idHotelDepart = idHotelDepart; }

    public Long getIdHotelArrivee() { return idHotelArrivee; }
    public void setIdHotelArrivee(Long idHotelArrivee) { this.idHotelArrivee = idHotelArrivee; }

    public int getDistanceEntreHotel() { return distanceEntreHotel; }
    public void setDistanceEntreHotel(int distanceEntreHotel) { this.distanceEntreHotel = distanceEntreHotel; }
}
