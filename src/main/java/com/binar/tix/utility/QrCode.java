package com.binar.tix.utility;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.util.text.StrongTextEncryptor;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class QrCode {

    private QrCode() {
    }

    public static String generate(String invoiceNo) throws WriterException, IOException {
        String qrCodeUrl = "";
        String result = AES.encrypt(invoiceNo);
        assert result != null;
        String key = URLEncoder.encode(result, StandardCharsets.UTF_8);
        BitMatrix bitMatrix = new QRCodeWriter().encode(Constant.STAGING_QR+key, BarcodeFormat.QR_CODE, 250, 250);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "png", out);

        Map<Object, Object> config = new HashMap<>();
        config.put("cloud_name", "dsbm2cqxn");
        config.put("api_key", "454119776123392");
        config.put("api_secret", "CBFk_yQ5N0uzGPhX6mwOYycPI4I");
        Cloudinary cloudinary = new Cloudinary(config);
        try {
            JSONObject resp = new JSONObject(cloudinary.uploader().upload(out.toByteArray(), ObjectUtils.emptyMap()));
            qrCodeUrl = resp.getString("secure_url");
        } catch (Exception exception) {
            log.error("Generate Qr Code Error : "+exception.getMessage());
        }
        return qrCodeUrl;
    }
}
