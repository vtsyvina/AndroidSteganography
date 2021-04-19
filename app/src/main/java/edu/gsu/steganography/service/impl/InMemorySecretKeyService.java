package edu.gsu.steganography.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.gsu.steganography.model.SecretKey;
import edu.gsu.steganography.service.SecretKeyService;

public class InMemorySecretKeyService implements SecretKeyService {
    List<SecretKey> keys = new ArrayList<>();

    public InMemorySecretKeyService(){
        keys.add(new SecretKey("1", "Slava's key", "1234"));
        keys.add(new SecretKey("2", "Seconnd key", "abcdefghijklmnop"));
        keys.add(new SecretKey("3", "Third key", "abcdefghijklmnopq"));
        keys.add(new SecretKey("4", "Third key", "abcdefghijklmnopq"));
        keys.add(new SecretKey("5", "Third key", "abcdefghijklmnopq"));
        keys.add(new SecretKey("6", "Third key", "abcdefghijklmnopq"));

        keys.add(new SecretKey("7", "Second to last key", "abcdefghijklmnopq"));
        keys.add(new SecretKey("8", "Last key", "abcdefghijklmnopq"));
    }

    @Override
    public List<SecretKey> allKeys() {
        return keys;
    }

    @Override
    public SecretKey getKey(String id) {
        int idx = indexOf(id);
        if (idx != -1){
            return keys.get(idx);
        }
        return null;
    }

    @Override
    public boolean deleteKey(String id) {
        int idx = indexOf(id);
        if (idx != -1){
            keys.remove(idx);
        }
        return idx != -1;
    }

    @Override
    public boolean updateKey(SecretKey key) {
        int idx = indexOf(key.getId());
        if (idx != -1){
            keys.set(idx, key);
        }
        return idx != -1;
    }

    @Override
    public boolean insertKey(SecretKey key) {
        int idx = indexOf(key.getId());
        if (idx == -1){
            key.setId(UUID.randomUUID().toString());
            keys.add(key);
        }
        return idx == -1;
    }

    @Override
    public boolean insertOrUpdate(SecretKey key) {
        if (key.getId() == null){
            return  insertKey(key);
        } else {
            return updateKey(key);
        }
    }

    private int indexOf(String id){
        if (id == null){
            return  -1;
        }
        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i).getId().equals(id)){
                return i;
            }
        }
        return -1;
    }
}
