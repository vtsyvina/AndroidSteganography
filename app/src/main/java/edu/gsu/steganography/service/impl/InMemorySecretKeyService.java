package edu.gsu.steganography.service.impl;

import java.util.ArrayList;
import java.util.List;

import edu.gsu.steganography.model.SecretKey;
import edu.gsu.steganography.service.SecretKeyService;

public class InMemorySecretKeyService implements SecretKeyService {
    List<SecretKey> keys = new ArrayList<>();

    public InMemorySecretKeyService(){
        keys.add(new SecretKey("1", "Slava's key", "1234"));
        keys.add(new SecretKey("2", "Seconnd key", "abcdefghijklmnop"));
        keys.add(new SecretKey("2", "Third key", "abcdefghijklmnopq"));
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
            keys.add(key);
        }
        return idx == -1;
    }

    private int indexOf(String id){
        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i).getId().equals(id)){
                return i;
            }
        }
        return -1;
    }
}
