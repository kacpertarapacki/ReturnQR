package com.kactar.returnqr.repository;

import com.kactar.returnqr.model.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelRepository extends JpaRepository<Parcel, Long> {
}
