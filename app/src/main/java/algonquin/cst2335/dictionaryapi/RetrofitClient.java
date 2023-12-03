package algonquin.cst2335.dictionaryapi;
import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://api.dictionaryapi.dev/api/v2/";
    private static Retrofit retrofit = null;

    public static DictionaryApi getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(DictionaryApi.class);
    }
}