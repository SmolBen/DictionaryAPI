package algonquin.cst2335.dictionaryapi;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

public interface SearchTermDao {
    @Insert
    void insertSearchTerm(SearchTerm searchTerm);

    @Query("SELECT * FROM SearchTerm")
    LiveData<List<SearchTerm>> getAllSearchTerms();
}