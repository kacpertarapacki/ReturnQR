package com.kactar.returnqr.repository;

import com.kactar.returnqr.model.Parcel;
import com.kactar.returnqr.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParcelRepository extends JpaRepository<Parcel, Long> {
    List<Parcel> findByUser(User user);
}
