package algonquin.cst2335.dictionaryapi;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

@Entity
@TypeConverters(Definition.class) // Add this annotation with the TypeConverter methods
public class Definition {
    @PrimaryKey(autoGenerate = true)
    public long id; // Add a primary key field

    public String word;
    public List<Meaning> meanings;
    public String definitionText; // Add this field to store the extracted definitions

    // Constructor, getters, setters (if needed)

    @TypeConverter
    public static List<Meaning> fromString(String value) {
        Type listType = new TypeToken<List<Meaning>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<Meaning> list) {
        return new Gson().toJson(list);
    }
}
