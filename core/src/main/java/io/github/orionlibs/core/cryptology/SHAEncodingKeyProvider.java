package io.github.orionlibs.core.cryptology;

import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SHAEncodingKeyProvider implements InitializingBean
{
    public static byte[] shaKey;
    @Value("${crypto.sha.key}")
    private String shaEncodingKey;
    private byte[] keyBytesForSHA256;


    @Override
    public void afterPropertiesSet() throws Exception
    {
        keyBytesForSHA256 = shaEncodingKey.getBytes(StandardCharsets.UTF_8);
        shaKey = loadKey();
    }


    public byte[] loadKey()
    {
        // TODO: fetch the wrapped key from Vault/KMS, unwrap it, and return a byte[]
        return keyBytesForSHA256;
    }
}
