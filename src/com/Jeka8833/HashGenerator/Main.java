package com.Jeka8833.HashGenerator;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        if (args.length == 0 || args[0].isEmpty()) {
            System.out.println("Please select path");
            return;
        }
        final Path folder = Paths.get(args[0]);
        final List<Path> filesInFolder = Files.walk(folder).filter(Files::isRegularFile).collect(Collectors.toList());
        final Map<String, String> hash = new HashMap<>();
        for (Path path : filesInFolder) {
            hash.put(folder.relativize(path).toString(), getHashFile(path));
        }
        Files.write(Paths.get("files.hash"), new Gson().toJson(new Info(args[1], hash)).getBytes(StandardCharsets.US_ASCII));
    }

    private static String getHashFile(final Path path) throws NoSuchAlgorithmException, IOException {
        final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.reset();
        messageDigest.update(Files.readAllBytes(path));
        return bytesToHex2(messageDigest.digest());
    }

    private static String bytesToHex2(final byte[] hashInBytes) {
        final StringBuilder sb = new StringBuilder();
        for (byte hashInByte : hashInBytes) {
            final String hex = Integer.toHexString(0xff & hashInByte);
            if (hex.length() == 1)
                sb.append('0');
            sb.append(hex);
        }
        return sb.toString();
    }
}
