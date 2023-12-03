package algonquin.cst2335.dictionaryapi;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private DictionaryApi dictionaryApi;
    private DefinitionDao definitionDao;
    private SharedPreferences sharedPreferences;

    private EditText editTextSearch;
    private Button buttonSearch;
    private Button buttonViewSaved;
    private RecyclerView recyclerViewDefinitions;
    private DefinitionAdapter definitionAdapter;

    private DefinitionViewModel definitionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Retrofit and Room instances
        dictionaryApi = RetrofitClient.getInstance();
        definitionDao = DefinitionDatabase.getDatabase(this).definitionDao();
        sharedPreferences = getPreferences(MODE_PRIVATE);

        // Initialize UI components
        editTextSearch = findViewById(R.id.editTextSearch);
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonViewSaved = findViewById(R.id.buttonViewSaved);
        recyclerViewDefinitions = findViewById(R.id.recyclerViewDefinitions);

        // Set up RecyclerView
        recyclerViewDefinitions.setLayoutManager(new LinearLayoutManager(this));
        definitionAdapter = new DefinitionAdapter();
        recyclerViewDefinitions.setAdapter(definitionAdapter);

        // Initialize ViewModel
        definitionViewModel = new ViewModelProvider(this).get(DefinitionViewModel.class);
        definitionViewModel.getDefinitionsForWord("").observe(this, new Observer<List<Definition>>() {
            @Override
            public void onChanged(List<Definition> definitions) {
                updateUI(definitions);
            }


        });

        // Set click listeners
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchTerm = editTextSearch.getText().toString().trim();
                if (!searchTerm.isEmpty()) {
                    try {
                        // Encode the word before making the API call
                        String encodedWord = URLEncoder.encode(searchTerm, "UTF-8");

                        // Log the URL before making the API call
                        String apiUrl = "https://api.dictionaryapi.dev/api/v2/entries/en/" + encodedWord;
                        Log.d("MainActivity", "API URL: " + apiUrl);

                        // Make the API call with the encoded word
                        searchDefinitions(searchTerm);

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        // Handle encoding exception if necessary
                    }
                } else {
                    // Handle case when the word is empty
                    Toast.makeText(MainActivity.this, "Please enter a word to search", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonViewSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewSavedSearchTerms();
            }
        });

        // Retrieve last search term from SharedPreferences
        String lastSearchTerm = sharedPreferences.getString("last_search_term", "");
        editTextSearch.setText(lastSearchTerm);
    }

    // Implement API call function
    private void searchDefinitions(final String word) {
        Log.d("MainActivity", "searchDefinitions called");
        dictionaryApi.getDefinitions(word).enqueue(new Callback<List<Definition>>() {

            @Override
            public void onResponse(Call<List<Definition>> call, Response<List<Definition>> response) {
                if (response.isSuccessful()) {
                    Log.d("MainActivity", "onResponse called");
                    List<Definition> definitions = response.body();

                    for (Definition definition : definitions) {
                        StringBuilder extractedDefinitions = new StringBuilder();

                        // Iterate through meanings and definitions
                        for (Meaning meaning : definition.meanings) {
                            extractedDefinitions.append(meaning.partOfSpeech).append(": ");

                            // Iterate through definitions
                            for (String definitionText : meaning.definitions) {
                                extractedDefinitions.append("- ").append(definitionText).append("\n");
                            }
                        }

                        // Set the extracted definitions to the definitionText field in your Definition object
                        definition.definitionText = extractedDefinitions.toString();
                    }

                    definitionAdapter.setDefinitions(definitions);

                    // Insert search term into Room database
                    SearchTerm searchTermEntity = new SearchTerm();
                    searchTermEntity.term = word;
                    insertSearchTerm(searchTermEntity);

                    // Update SharedPreferences with the last search term
                    sharedPreferences.edit().putString("last_search_term", word).apply();
                } else {
                    // Handle unsuccessful response (e.g., non-200 HTTP status code)
                    Log.e("MainActivity", "Failed to retrieve definitions. HTTP status code: " + response.code());
                    Toast.makeText(MainActivity.this, "Failed to retrieve definitions", Toast.LENGTH_SHORT).show();
                }
            }




            @Override
            public void onFailure(Call<List<Definition>> call, Throwable t) {
                // Handle failure (e.g., network error)
                Log.e("MainActivity", "Failed to retrieve definitions: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Failed to retrieve definitions", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Implement function to view saved search terms
    private void viewSavedSearchTerms() {
        definitionViewModel.getAllSearchTerms().observe(this, new Observer<List<SearchTerm>>() {
            @Override
            public void onChanged(List<SearchTerm> searchTerms) {
                // Display saved search terms in a dialog or another activity
                // Allow the user to select a term to view definitions
                // ...
            }
        });
    }

    // Implement function to insert a search term into Room database
    private void insertSearchTerm(SearchTerm searchTerm) {
        definitionViewModel.insertSearchTerm(searchTerm);
    }

    // Implement function to update UI with definitions
    private void updateUI(List<Definition> definitions) {
        // Update RecyclerView with definitions
        definitionAdapter.setDefinitions(definitions);
    }
}
