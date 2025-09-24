package com.kactar.returnqr.service;

import com.kactar.returnqr.dto.ParcelDto;
import com.kactar.returnqr.mapper.ParcelMapper;
import com.kactar.returnqr.model.Parcel;
import com.kactar.returnqr.model.ParcelStatus;
import com.kactar.returnqr.model.User;
import com.kactar.returnqr.repository.ParcelRepository;
import com.kactar.returnqr.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ParcelService {
    private final ParcelRepository parcelRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public ParcelService(ParcelRepository parcelRepository, UserRepository userRepository, EmailService emailService) {
        this.parcelRepository = parcelRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public List<Parcel> getCurrentUserParcels(User user){
        return parcelRepository.findByUser(user);
    }

    public Parcel createParcelForCurrentUser(Parcel parcel, User user){
        parcel.setUser(user);
        parcel.setParcelStatus(ParcelStatus.LABEL_CREATED);
        return parcelRepository.save(parcel);
    }


    public void deleteParcelForCurrentUser(Long id, User user){
        Parcel parcel = parcelRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parcel not found"));
        if (!parcel.getUser().getId().equals(user.getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot delete someone else's parcel!");
        }
        parcelRepository.delete(parcel);
    }

    public Parcel addParcelToUser(Long id, Parcel parcel){
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        parcel.setUser(user);
        return parcelRepository.save(parcel);
    }

    public Parcel getParcelById(Long id){
        return parcelRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parcel not found"));
    }

    public void createReturn(Long id){
        Parcel parcel = parcelRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parcel not found"));
        if (parcel.getParcelStatus() != ParcelStatus.DELIVERED){
            System.out.println("Current status: " + parcel.getParcelStatus());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not allowed to change status");
        }
        parcel.setParcelStatus(ParcelStatus.RETURN_REQUESTED);
        parcelRepository.save(parcel);
    }

    public List<ParcelDto> getAllParcels(){
        return parcelRepository.findAll()
                .stream().map(ParcelMapper::toDto).toList();
    }

    public ParcelDto setStatusAsAdmin(Long id, ParcelStatus parcelStatus){
        Parcel parcel = parcelRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parcel not found"));
        parcel.setParcelStatus(parcelStatus);
        Parcel saved = parcelRepository.save(parcel);
        if (saved.getUser() != null && saved.getUser().getEmail() != null && !saved.getUser().getEmail().isBlank()) {
            emailService.sendParcelStatusEmail(saved.getUser().getEmail(), saved);
        }

        return ParcelMapper.toDto(parcelRepository.save(parcel));
    }



    public void deleteParcel(Long id){
        if (!parcelRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Parcel not found");
        }
        parcelRepository.deleteById(id);
    }

    public String prepareForQrGenerator(Long id){
        Parcel parcel = parcelRepository.findById(id).orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND, "Parcel not found"));
        return "name: " + parcel.getUser().getName() + ", return address: " + parcel.getReturnAddress();
    }
}
