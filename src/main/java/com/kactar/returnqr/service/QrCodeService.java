package com.kactar.returnqr.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class QrCodeService {
    public byte[] generateQrCode(String strToEncode) throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(
                strToEncode,
                BarcodeFormat.QR_CODE,
                250,
                250
        );
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "PNG", out);
        return out.toByteArray();
    }
}
