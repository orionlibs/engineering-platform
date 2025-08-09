package io.github.orionlibs.core.cryptology;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HMACSHAEncryptionKeyProvider
{
    @Value("${crypto.hmac-for-jwt.key}")
    private String jwtSigningKey;
    private String hmacAlgorithm = "HmacSHA512";


    /**
     * Computes a Base64‑encoded HMAC‑SHA512 of the given data using the provided key bytes.
     */
    public String getNewHMACBase64(String data, byte[] key)
    {
        try
        {
            Mac mac = Mac.getInstance(hmacAlgorithm);
            mac.init(new SecretKeySpec(key, hmacAlgorithm));
            byte[] rawHmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(rawHmac);
        }
        catch(Exception e)
        {
            throw new IllegalStateException("Failed to calculate HMAC‑SHA256", e);
        }
    }


    public String getJwtSigningKey()
    {
        return jwtSigningKey;
    }
}
