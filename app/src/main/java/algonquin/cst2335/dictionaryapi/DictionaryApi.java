package algonquin.cst2335.dictionaryapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DictionaryApi {
    @GET("entries/en/{word}")
    Call<List<Definition>> getDefinitions(@Path("word") String word);
}