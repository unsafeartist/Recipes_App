package com.example.recipes_system;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "RecipesDatabase.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_recipes";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_RECIPE_NAME = "recipe_name";
    private static final String COLUMN_INGREDIENTS = "ingredients";
    private static final String COLUMN_SERVING_SIZE = "serving_size";
    private static final String COLUMN_VEGAN = "vegan";
    private static final String COLUMN_VEGETARIAN = "vegetarian";
    private static final String COLUMN_GLUTEN_FREE = "gluten_free";
    private static final String COLUMN_NUTRITIONAL_INFORMATION = "nutritional_information";
    private static final String COLUMN_CALORIES = "calories";
    private static final String COLUMN_FAT = "fat_in_grams";
    private static final String COLUMN_CARBS = "carbs_in_grams";
    private static final String COLUMN_PROTEIN = "protein_in_grams";



    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_RECIPE_NAME + " TEXT, " +
                        COLUMN_INGREDIENTS + " TEXT, " +
                        COLUMN_SERVING_SIZE + " INTEGER, " +
                        COLUMN_VEGAN + " BOOLEAN, " +
                        COLUMN_VEGETARIAN + " BOOLEAN, " +
                        COLUMN_GLUTEN_FREE + " BOOLEAN, " +
                        COLUMN_NUTRITIONAL_INFORMATION + " BOOLEAN, " +
                        COLUMN_CALORIES + " INTEGER, " +
                        COLUMN_FAT + " INTEGER, " +
                        COLUMN_CARBS + " INTEGER, " +
                        COLUMN_PROTEIN + " INTEGER); ";

        //Run the query to check
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addRecipe(String recipe_name, String ingredients, int serving_size, boolean vegan,
                   boolean vegetarian, boolean gluten_free, boolean nutritional_information,
                   int calories, int fat, int carbs, int protein){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_RECIPE_NAME, recipe_name);
        cv.put(COLUMN_INGREDIENTS, ingredients);
        cv.put(COLUMN_SERVING_SIZE, serving_size);
        cv.put(COLUMN_VEGAN, vegan);
        cv.put(COLUMN_VEGETARIAN, vegetarian);
        cv.put(COLUMN_GLUTEN_FREE, gluten_free);
        cv.put(COLUMN_NUTRITIONAL_INFORMATION, nutritional_information);
        cv.put(COLUMN_CALORIES, calories);
        cv.put(COLUMN_FAT, fat);
        cv.put(COLUMN_CARBS, carbs);
        cv.put(COLUMN_PROTEIN, protein);

        long result = db.insert(TABLE_NAME, null, cv);

        //-1 means not addedd sucessfully and anything else returned is successful
        if(result == -1)
            Toast.makeText(context, "Failed to Add Recipe to Database...", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Added Recipe Successfully to Database!", Toast.LENGTH_SHORT).show();


    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            db.rawQuery(query, null);
        }
        return cursor;
    }
}
