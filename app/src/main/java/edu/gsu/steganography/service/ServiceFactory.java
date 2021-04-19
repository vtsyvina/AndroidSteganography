package edu.gsu.steganography.service;

import edu.gsu.steganography.db.SecretKeyDBHelper;
import edu.gsu.steganography.service.impl.InMemorySecretKeyService;
import edu.gsu.steganography.service.impl.SecretKeyServiceSQL;

public class ServiceFactory {

    private static final SecretKeyService secretKeyService = new InMemorySecretKeyService();

    private static SecretKeyService secretKeyServiceSQL;

    private static SecretKeyDBHelper dbHelper;

    public static SecretKeyService getSecretKeyService(){
        return  secretKeyServiceSQL;
    }

    public static void setDbHelper(SecretKeyDBHelper db){
        dbHelper = db;
        secretKeyServiceSQL = new SecretKeyServiceSQL(db);
    }
}
