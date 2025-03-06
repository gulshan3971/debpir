package org.debpir;

import org.hyperledger.fabric.contract.*;
import org.hyperledger.fabric.shim.ChaincodeStub;
import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@Contract(name = "DEBPIRContract")
@Default
public class DEBPIRContract implements ContractInterface {

    private static final int PRIVACY_THRESHOLD = 50;

    @Transaction
    public String PrivacyLabeling(Context ctx, String dataId, int[] sensitivities, int[] weights) {
        ChaincodeStub stub = ctx.getStub();
        int score = 0;
        for (int i = 0; i < sensitivities.length; i++) score += sensitivities[i] * weights[i];
        String label = (score > PRIVACY_THRESHOLD) ? "high" : "low";
        stub.putStringState(dataId, label);
        return label;
    }

    @Transaction
    public String EncryptData(Context ctx, String dataId, String data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[32], nonce = new byte[12];
        secureRandom.nextBytes(key);
        secureRandom.nextBytes(nonce);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new GCMParameterSpec(128, nonce));
        byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedData);
    }
}
