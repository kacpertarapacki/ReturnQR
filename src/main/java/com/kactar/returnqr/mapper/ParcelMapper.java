package com.kactar.returnqr.mapper;

import com.kactar.returnqr.dto.ParcelDto;
import com.kactar.returnqr.model.Parcel;

public class ParcelMapper {
    public static ParcelDto toDto(Parcel parcel){
        return new ParcelDto(
                parcel.getId(),
                parcel.getDeliveryAddress(),
                parcel.getParcelStatus(),
                parcel.getReturnAddress(),
                parcel.getUser() != null ? parcel.getUser().getEmail() : null);
    }
}
