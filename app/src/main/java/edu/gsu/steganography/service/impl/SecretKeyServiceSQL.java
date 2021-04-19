package edu.gsu.steganography.service.impl;

import java.util.List;
import java.util.UUID;

import edu.gsu.steganography.db.SecretKeyDBHelper;
import edu.gsu.steganography.model.SecretKey;
import edu.gsu.steganography.service.SecretKeyService;

public class SecretKeyServiceSQL implements SecretKeyService {

    SecretKeyDBHelper db;

    public SecretKeyServiceSQL(SecretKeyDBHelper db){
        this.db = db;
    }

    @Override
    public List<SecretKey> allKeys() {
        return db.getAllKeys();
    }

    @Override
    public SecretKey getKey(String id) {
        return db.getKey(id);
    }

    @Override
    public boolean deleteKey(String id) {
        return db.deleteKey(id);
    }

    @Override
    public boolean updateKey(SecretKey key) {
        return db.updateKey(key);
    }

    @Override
    public boolean insertKey(SecretKey key) {
        key.setId(UUID.randomUUID().toString());
        return db.insertKey(key);
    }

    @Override
    public boolean insertOrUpdate(SecretKey key) {
        if (key.getId() == null ||  db.getKey(key.getId()) == null){
            return insertKey(key);
        } else{
            return updateKey(key);
        }
    }
}
