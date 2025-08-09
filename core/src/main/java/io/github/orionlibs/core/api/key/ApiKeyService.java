package io.github.orionlibs.core.api.key;

import io.github.orionlibs.core.api.key.model.ApiKeyDAO;
import io.github.orionlibs.core.api.key.model.ApiKeyModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApiKeyService
{
    @Autowired
    private ApiKeyDAO apiKeyDAO;


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
