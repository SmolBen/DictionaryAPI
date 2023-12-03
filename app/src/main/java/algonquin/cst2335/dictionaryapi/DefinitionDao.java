package algonquin.cst2335.dictionaryapi;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface DefinitionDao {

    @Insert
    void insertDefinition(Definition definition);

    @Insert
    void insertSearchTerm(SearchTerm searchTerm);

    @Delete
    void deleteSearchTerm(SearchTerm searchTerm);

    @Query("SELECT * FROM Definition WHERE word = :word")
    LiveData<List<Definition>> getDefinitionsForWord(String word);

    @Query("SELECT * FROM SearchTerm")
    LiveData<List<SearchTerm>> getAllSearchTerms();
}


