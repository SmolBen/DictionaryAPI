package algonquin.cst2335.dictionaryapi;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class DefinitionViewModel extends AndroidViewModel {
    private DefinitionRepository repository;

    public DefinitionViewModel(Application application) {
        super(application);
        repository = new DefinitionRepository(application);
    }

    public LiveData<List<Definition>> getDefinitionsForWord(String word) {
        return repository.getDefinitionsForWord(word);
    }

    public LiveData<List<SearchTerm>> getAllSearchTerms() {
        return repository.getAllSearchTerms();
    }

    public void insertDefinition(Definition definition) {
        repository.insertDefinition(definition);
    }

    public void insertSearchTerm(SearchTerm searchTerm) {
        repository.insertSearchTerm(searchTerm);
    }

    public void deleteSearchTerm(String term) {
        repository.deleteSearchTerm(term);
    }
}