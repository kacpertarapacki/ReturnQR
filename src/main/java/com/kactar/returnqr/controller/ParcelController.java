package com.kactar.returnqr.controller;

import com.google.zxing.WriterException;
import com.kactar.returnqr.model.Parcel;
import com.kactar.returnqr.model.ParcelStatus;
import com.kactar.returnqr.service.ParcelService;
import com.kactar.returnqr.service.QrCodeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/parcels")
public class ParcelController {
    private final ParcelService parcelService;
    private final QrCodeService qrCodeService;

    public ParcelController(ParcelService parcelService, QrCodeService qrCodeService) {
        this.parcelService = parcelService;
        this.qrCodeService = qrCodeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parcel> getParcel(@PathVariable Long id){
        Parcel parcel = parcelService.getParcelById(id);
        return ResponseEntity.ok(parcel);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> changeStatus(@PathVariable Long id){
        parcelService.updateParcelStatus(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParcel(@PathVariable Long id){
        parcelService.deleteParcel(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/qr")
    public ResponseEntity<byte[]> getParcelQrCode(@PathVariable Long id) throws IOException, WriterException {
        byte[] qrImage = qrCodeService.generateQrCode(parcelService.prepareForQrGenerator(id));
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qrImage);
    }

}
