package edu.gsu.steganography.service;

import java.util.List;

import edu.gsu.steganography.model.SecretKey;

public interface SecretKeyService {

    List<SecretKey> allKeys();

    SecretKey getKey(String id);

    boolean deleteKey(String id);

    boolean updateKey(SecretKey key);

    boolean insertKey(SecretKey key);
}
