package algonquin.cst2335.dictionaryapi;

import android.content.Context;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Definition.class, SearchTerm.class}, version = 1, exportSchema = false)
public abstract class DefinitionDatabase extends RoomDatabase {
    public abstract DefinitionDao definitionDao();

    private static volatile DefinitionDatabase INSTANCE;

    public static DefinitionDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DefinitionDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            DefinitionDatabase.class,
                            "definition_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}