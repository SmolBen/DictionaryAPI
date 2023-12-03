package algonquin.cst2335.dictionaryapi;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import java.util.List;

public class DefinitionRepository {
    private DefinitionDao definitionDao;
    private LiveData<List<Definition>> allDefinitions;
    private Handler backgroundHandler;
    private Handler mainHandler;

    public DefinitionRepository(Application application) {
        DefinitionDatabase database = DefinitionDatabase.getDatabase(application);
        definitionDao = database.definitionDao();
        backgroundHandler = new Handler(new HandlerThread("BackgroundThread").getLooper());
        mainHandler = new Handler(Looper.getMainLooper());
    }

    public LiveData<List<Definition>> getDefinitionsForWord(String word) {
        // Using LiveData from Room directly
        return definitionDao.getDefinitionsForWord(word);
    }

    public LiveData<List<SearchTerm>> getAllSearchTerms() {
        // Using LiveData from Room directly
        return definitionDao.getAllSearchTerms();
    }

    public void insertDefinition(Definition definition) {
        // Using Handler to perform database insert in a background thread
        backgroundHandler.post(() -> {
            definitionDao.insertDefinition(definition);
            // Use mainHandler to post a runnable to the main thread for UI updates
            mainHandler.post(() -> {
                // Perform UI updates here if needed
            });
        });
    }

    public void insertSearchTerm(SearchTerm searchTerm) {
        // Using Handler to perform database insert in a background thread
        backgroundHandler.post(() -> {
            definitionDao.insertSearchTerm(searchTerm);
            // Use mainHandler to post a runnable to the main thread for UI updates
            mainHandler.post(() -> {
                // Perform UI updates here if needed
            });
        });
    }

    public void deleteSearchTerm(String term) {
        // Using Handler to perform database delete in a background thread
        backgroundHandler.post(() -> {
            definitionDao.deleteSearchTerm(term);
            // Use mainHandler to post a runnable to the main thread for UI updates
            mainHandler.post(() -> {
                // Perform UI updates here if needed
            });
        });
    }
}

