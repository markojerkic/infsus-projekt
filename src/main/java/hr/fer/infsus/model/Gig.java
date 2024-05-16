package hr.fer.infsus.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Gig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private float hourlyRate;
    @Column(nullable = false)
    private Integer duration;
    @Column
    private Date date;

    @ManyToOne
    private Artist artist;
    @ManyToOne
    private User customer;
}
