package algonquin.cst2335.dictionaryapi;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

public interface DictionaryDao {
    @Insert
    void insertSearchTerm(SearchTerm searchTerm);

    @Insert
    void insertDefinition(Definition definition);

    @Query("SELECT * FROM SearchTerm")
    LiveData<List<SearchTerm>> getAllSearchTerms();

    @Query("SELECT * FROM Definition WHERE word = :word")
    LiveData<List<Definition>> getDefinitionsForWord(String word);

    @Query("DELETE FROM Definition WHERE word = :word")
    void deleteDefinitionsForWord(String word);

    @Query("DELETE FROM SearchTerm WHERE term = :term")
    void deleteSearchTerm(String term);
}