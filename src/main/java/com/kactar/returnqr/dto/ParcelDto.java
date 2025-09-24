package com.kactar.returnqr.dto;

import com.kactar.returnqr.model.ParcelStatus;

public record ParcelDto(Long id,
                        String deliveryAddress,
                        ParcelStatus parcelStatus,
                        String returnAddress,
                        String userEmail
                        ) {
}
