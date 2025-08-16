package io.github.orionlibs.core.database;

import java.util.List;

public interface DAO
{
    OrionModel save(OrionModel modelToSave);


    void delete(OrionModel modelToDelete, List<String> columnsForCondition);
}
