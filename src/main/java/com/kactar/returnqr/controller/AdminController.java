package com.kactar.returnqr.controller;

import com.kactar.returnqr.dto.ParcelDto;
import com.kactar.returnqr.model.ParcelStatus;
import com.kactar.returnqr.service.ParcelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final ParcelService parcelService;

    public AdminController(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    @GetMapping("/parcels")
    public List<ParcelDto> allParcels(){
        return parcelService.getAllParcels();
    }

    @PutMapping("/parcels/{id}/status")
    public ParcelDto setParcelStatus(@PathVariable Long id, @RequestBody ParcelStatus parcelStatus){
        return parcelService.setStatusAsAdmin(id, parcelStatus);
    }

}
