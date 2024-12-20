package com.personal.ai.chatbot.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtConfig {

    private final ResourceLoader resourceLoader;

    @Value("${spring.security.jwt.expiration}")
    private Long tokenExpiration;

    public String readResourceFile(String filename) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + filename);
        // Opening an InputStream to read the content of the resource
        InputStream inputStream = resource.getInputStream();
        // Creating a BufferedReader to read text from the InputStream efficiently
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        // StringBuilder to accumulate the lines read from the file
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        // Reading each line from the file and appending it to the StringBuilder
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        // Closing the BufferedReader
        reader.close();
        // Returning the contents of the file as a string
        return stringBuilder.toString();
    }

    @Bean
    public PrivateKey privateKey() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        String key = readResourceFile("private_key.pem");
        String privateKeyPEM = key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PRIVATE KEY-----", "");

        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return keyFactory.generatePrivate(keySpec);
    }

    @Bean
    public PublicKey publicKey() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        String key = readResourceFile("public_key.pem");
        String publicKeyPEM = key
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PUBLIC KEY-----", "");

        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        return keyFactory.generatePublic(keySpec);
    }
}
