package algonquin.cst2335.dictionaryapi;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SearchTerm {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String term;

    // Constructor, getters, and setters
}

