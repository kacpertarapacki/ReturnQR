package com.kactar.returnqr.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Entity
@Table(name = "parcels")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Parcel {

    public Parcel(String deliveryAddress, ParcelStatus parcelStatus, String returnAddress, User user) {
        this.deliveryAddress = deliveryAddress;
        this.parcelStatus = parcelStatus;
        this.returnAddress = returnAddress;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String deliveryAddress;
    @Enumerated(EnumType.STRING)
    @NotNull
    private ParcelStatus parcelStatus;
    @NotBlank
    private String returnAddress;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

}
