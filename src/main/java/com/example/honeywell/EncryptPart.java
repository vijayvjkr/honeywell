package com.example.honeywell;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

//import static org.yaml.snakeyaml.tokens.Token.ID.Key;

@RestController
@RequestMapping("/ED")
public class EncryptPart {

    //Encrypt section
    @PostMapping("/encrypt")
    public ResponseEntity<String> encryptData(@PathVariable("inp") String input1 ) throws UnsupportedEncodingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException {
        if(input1 !=null){
            String result1 = "";
            String key = "vijay";

            byte[] keyBytes = key.getBytes("UTF-8");
            SecretKey secretKey = new SecretKeySpec(keyBytes, 0, 16, "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(input1.getBytes("UTF-8"));
            result1 = Base64.getEncoder().encodeToString(encryptedBytes);


            return new ResponseEntity<String>(result1, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>("Encryption failed",HttpStatus.BAD_REQUEST);
        }
    }


    //Decrypt section
    @PostMapping("/decrypt")
    public ResponseEntity<String> decryptData(@PathVariable("inp") String input2 ) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        if(input2!=null){
            String key = "vijay";
            if (key.length() < 16) {
                key = String.format("%-16s", key).substring(0, 16);
            }
            byte[] keyBytes = key.getBytes("UTF-8");
            SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(input2));
            String result2 = new String(decryptedBytes, "UTF-8"); // Return the original string


            return new ResponseEntity<String>(key+","+result2,HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>("Decryption failed",HttpStatus.BAD_REQUEST);
        }
    }

}
