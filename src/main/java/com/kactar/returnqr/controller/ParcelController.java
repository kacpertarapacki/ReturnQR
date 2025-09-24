package com.kactar.returnqr.controller;

import com.google.zxing.WriterException;
import com.kactar.returnqr.model.Parcel;
import com.kactar.returnqr.model.ParcelStatus;
import com.kactar.returnqr.model.User;
import com.kactar.returnqr.repository.ParcelRepository;
import com.kactar.returnqr.service.ParcelService;
import com.kactar.returnqr.service.QrCodeService;
import com.kactar.returnqr.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/parcels")
public class ParcelController {
    private final ParcelService parcelService;
    private final QrCodeService qrCodeService;
    private final UserService userService;

    public ParcelController(ParcelService parcelService, QrCodeService qrCodeService, UserService userService) {
        this.parcelService = parcelService;
        this.qrCodeService = qrCodeService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Parcel>> getMyParcels(){
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(parcelService.getCurrentUserParcels(user));
    }

    @PostMapping
    public ResponseEntity<Parcel> createParcel(@RequestBody Parcel parcel){
        User user = userService.getCurrentUser();
        return ResponseEntity.status(HttpStatus.CREATED).body(parcelService.createParcelForCurrentUser(parcel, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParcel(@PathVariable Long id){
        User user = userService.getCurrentUser();
        parcelService.deleteParcelForCurrentUser(id, user);
        return ResponseEntity.noContent().build();
    }







    @GetMapping("/{id}")
    public ResponseEntity<Parcel> getParcel(@PathVariable Long id){
        Parcel parcel = parcelService.getParcelById(id);
        return ResponseEntity.ok(parcel);
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<Void> createReturn(@PathVariable Long id){
        parcelService.createReturn(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}/qr")
    public ResponseEntity<byte[]> getParcelQrCode(@PathVariable Long id) throws IOException, WriterException {
        byte[] qrImage = qrCodeService.generateQrCode(parcelService.prepareForQrGenerator(id));
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qrImage);
    }

}
