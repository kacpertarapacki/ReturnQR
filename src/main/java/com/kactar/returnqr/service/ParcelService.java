package com.kactar.returnqr.service;

import com.kactar.returnqr.model.Parcel;
import com.kactar.returnqr.model.ParcelStatus;
import com.kactar.returnqr.model.User;
import com.kactar.returnqr.repository.ParcelRepository;
import com.kactar.returnqr.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ParcelService {
    private final ParcelRepository parcelRepository;
    private final UserRepository userRepository;

    public ParcelService(ParcelRepository parcelRepository, UserRepository userRepository) {
        this.parcelRepository = parcelRepository;
        this.userRepository = userRepository;
    }

    public Parcel addParcelToUser(Long id, Parcel parcel){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        parcel.setUser(user);
        return parcelRepository.save(parcel);
    }

    public Parcel getParcelById(Long id){
        return parcelRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void updateParcelStatus(Long id, ParcelStatus parcelStatus){
        parcelRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")).setParcelStatus(parcelStatus);
    }
    public void deleteParcel(Long id){
        parcelRepository.deleteById(id);
    }


}
