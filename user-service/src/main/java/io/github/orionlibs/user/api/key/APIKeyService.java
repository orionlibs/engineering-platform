package io.github.orionlibs.user.api.key;

import io.github.orionlibs.user.api.key.model.ApiKeyDAO;
import io.github.orionlibs.user.api.key.model.ApiKeyModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class APIKeyService
{
    @Autowired private ApiKeyDAO apiKeyDAO;


    @Transactional
    public ApiKeyModel save(String userID, String apiKey, String apiSecret)
    {
        ApiKeyModel model = new ApiKeyModel(apiKey, apiSecret, userID);
        return apiKeyDAO.saveAndFlush(model);
    }


    @Transactional
    public void delete(String apiKey)
    {
        apiKeyDAO.deleteById(apiKey);
    }
}
