package com.example.recipes_system;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.recipes_system.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecipesHomepage extends AppCompatActivity {
    //Declare local variables/objects
    RecyclerView recyclerView_object;
    recipesHomepage_recyclerViewer_searchBar_ADAPTER recipesList_Adapter;

    DatabaseHelper recipes_database;
    ArrayList<String> recipe_name_ArrayList, ingredients_ArrayList;
    ArrayList<Boolean> vegan_ArrayList, vegetarian_ArrayList, gluten_free_ArrayList, nutritional_information__ArrayList;
    ArrayList<Integer> serving_size_ArrayList, calories_ArrayList, fat_ArrayList, carbs_ArrayList, protein_ArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes_home_page);

        //This prevents the keyboard from automatically opening up upon creation of activity
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Create button object for activity 2
        Button addRecipe_buttonObject = findViewById(R.id.addRecipeButton);
        addRecipe_buttonObject.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view_object)
            {
                openAddRecipePage();
            }
        });

        //Load all the recipes for viewing and into various data structures
        //load_Recipes();
        recipes_database =  new DatabaseHelper(RecipesHomepage.this);
        recipe_name_ArrayList = new ArrayList<>();
        ingredients_ArrayList = new ArrayList<>();
        serving_size_ArrayList = new ArrayList<>();
        vegan_ArrayList = new ArrayList<>();
        vegetarian_ArrayList = new ArrayList<>();
        gluten_free_ArrayList = new ArrayList<>();
        nutritional_information__ArrayList = new ArrayList<>();
        calories_ArrayList = new ArrayList<>();
        fat_ArrayList = new ArrayList<>();
        carbs_ArrayList = new ArrayList<>();
        protein_ArrayList = new ArrayList<>();

        //This function stores all recipes database data into usable ArrayLists
        insertRecipes_intoArrayLists();

        setUp_scrollableRecipesRecyclerViewer();

        //setUp_recipesSearchBarFunctionality();

    } // End on Create

    //Sets up the recycler viewer
    private void setUp_scrollableRecipesRecyclerViewer(){
        recipesList_Adapter = new recipesHomepage_recyclerViewer_searchBar_ADAPTER(this,
                recipe_name_ArrayList,
                ingredients_ArrayList,
                serving_size_ArrayList,
                vegan_ArrayList,
                vegetarian_ArrayList,
                gluten_free_ArrayList,
                nutritional_information__ArrayList,
                calories_ArrayList,
                fat_ArrayList,
                carbs_ArrayList,
                protein_ArrayList);


        recyclerView_object = (RecyclerView) findViewById(R.id.recipesRecyclerView);
        recyclerView_object.setHasFixedSize(true);

        recyclerView_object.setAdapter(recipesList_Adapter);

        //By default this is vertical recyclerView in LinearLayout
        //recipesList_layoutManager = new LinearLayoutManager(this);
        recyclerView_object.setLayoutManager(new LinearLayoutManager(this));


    }

    /*

     private void setUp_recipesSearchBarFunctionality(){
        //Connect to searchBar in Recipes Homepage
        searchBarRecipesHomepage = findViewById(R.id.searchRecipesView);

        searchBarRecipesHomepage.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do nothing here since we are filtering our search results in real time
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recipesList_Adapter.getFilter().filter(newText);
                return false;
            }
        });

        searchBarRecipesHomepage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view_objectRecipesSearchBar)
            {
                /*
                This makes it so user can click anywhere on the search
                bar and it will be clickable, instead of having to click
                the icon to start the search

                searchBarRecipesHomepage.setIconified(false);
}

        });

                }

     */


    public void openAddRecipePage()
    {
        Intent intent = new Intent(this, AddRecipePage.class);
        startActivity(intent);
    }

    void insertRecipes_intoArrayLists(){
        Cursor cursor = recipes_database.readAllData();

        if(cursor.getCount() == 0)
            Toast.makeText(this, "No Recipes Found in Database!", Toast.LENGTH_LONG).show();
        else{
            while(cursor.moveToNext()){
                recipe_name_ArrayList.add(cursor.getString(1));
                ingredients_ArrayList.add(cursor.getString(2));
                serving_size_ArrayList.add(cursor.getInt(3));
                vegan_ArrayList.add(toBoolean(cursor.getInt(4)));
                vegetarian_ArrayList.add(toBoolean(cursor.getInt(5)));
                gluten_free_ArrayList.add(toBoolean(cursor.getInt(6)));
                nutritional_information__ArrayList.add(toBoolean(cursor.getInt(7)));
                calories_ArrayList.add(cursor.getInt(8));
                fat_ArrayList.add(cursor.getInt(9));
                carbs_ArrayList.add(cursor.getInt(10));
                protein_ArrayList.add(cursor.getInt(11));
            }
        }
    }

    //Simple integer to boolean conversion function
    Boolean toBoolean(int input_integer){
        if(input_integer == 1)
            return true;
        else
            return false;
    }

    /*


       @author Kamaljot Saini
       Load the recipes into input HashMap
       PS Remember objects are passed by reference, so
       technically the object itself is being mutated

    public void load_Recipes()
    {
        //Declare local variables/objects
        FileInputStream fileInputStream_object = null;
        int nextEntity_counter = 1;
        boolean flag_nextEntity = false;

        //Temporary empty string holders
        String temp_nameRecipe = "";
        String temp_nameIngredient = "";
        String temp_numServings = "";
        int temp_intNumServings;
        double temp_doubleQuantityIngredient;
        String temp_veganFlagSTRING = "";
        String temp_vegetarianFlagSTRING = "";
        String temp_glutenFreeFLAG = "";
        String temp_optionalNutritionalInformationFlag = "";
        boolean temp_veganFlagBOOLEAN, temp_vegetarianFlagBOOLEAN, temp_glutenFreeFlagBOOLEAN, temp_optionalNutritionalInformationBOOLEAN;
        String temp_caloriesSTRING, temp_fatSTRING, temp_carbsSTRING, temp_proteinSTRING;
        int temp_caloriesINT, temp_fatINT, temp_carbsINT, temp_proteinINT;

        try {
            fileInputStream_object = openFileInput(FILE_NAME);
            InputStreamReader inputStreamReader_object = new InputStreamReader(fileInputStream_object);
            BufferedReader bufferedReader_object =  new BufferedReader(inputStreamReader_object);
            StringBuilder stringBuilder_object = new StringBuilder();
            String text_fromFile;

            //Appending the contents of file line by line to string format
            //Basically preparing it for the next step of output to screen
            //Now we have to break up the contents of the file and store into objects properly
            while((text_fromFile = bufferedReader_object.readLine()) != null){
                char[] text_fromFileARRAY = null; //Reset the char array
                text_fromFileARRAY = text_fromFile.toCharArray();

                //Reset Temp Holders
                temp_nameRecipe = "";
                temp_nameIngredient = "";
                temp_numServings = "";
                temp_intNumServings = 0;
                temp_doubleQuantityIngredient = 0.0;
                temp_veganFlagSTRING = "";
                temp_vegetarianFlagSTRING = "";
                temp_glutenFreeFLAG = "";
                temp_optionalNutritionalInformationFlag = "";
                temp_caloriesSTRING = "";
                temp_fatSTRING = "";
                temp_carbsSTRING = "";
                temp_proteinSTRING = "";
                temp_caloriesINT = 0;
                temp_fatINT = 0;
                temp_carbsINT = 0;
                temp_proteinINT= 0;

                for(int index = 0; index < text_fromFile.length(); index++) //Shuffle through the string as an array
                {
                    if(text_fromFileARRAY[index] == '/')
                        nextEntity_counter++;

                    flag_nextEntity = false;
                    switch(nextEntity_counter){
                        case 1:
                            //Name of Recipe
                            temp_nameRecipe = temp_nameRecipe + text_fromFileARRAY[index];
                            break;
                        case 2:
                            //Name of Ingredient
                            if(text_fromFileARRAY[index] == '/'){
                                //Do nothing
                                //We don't want to concatentate the back-end divider '/'
                            }
                            else
                                temp_nameIngredient = temp_nameIngredient + text_fromFileARRAY[index];
                            break;
                        case 3:
                            //Number of Servings
                            if(text_fromFileARRAY[index] == '/'){
                                //Do nothing
                                //We don't want to concatentate the back-end divider '/'
                            }
                            else
                                temp_numServings = temp_numServings + text_fromFileARRAY[index];
                            break;
                        case 4:
                            //veganFlag
                            if(text_fromFileARRAY[index] == '/'){
                                //Do nothing
                                //We don't want to concatentate the back-end divider '/'
                            }
                            else
                                temp_veganFlagSTRING = temp_veganFlagSTRING + text_fromFileARRAY[index];

                            break;
                        case 5:
                            //vegetarianFlag
                            if(text_fromFileARRAY[index] == '/'){
                                //Do nothing
                                //We don't want to concatentate the back-end divider '/'
                            }
                            else
                                temp_vegetarianFlagSTRING = temp_vegetarianFlagSTRING + text_fromFileARRAY[index];

                            break;
                        case 6:
                            //glutenFreeFlag
                            if(text_fromFileARRAY[index] == '/'){
                                //Do nothing
                                //We don't want to concatentate the back-end divider '/'
                            }
                            else
                                temp_glutenFreeFLAG = temp_glutenFreeFLAG + text_fromFileARRAY[index];

                            break;
                        case 7:
                            //optionalNutritionalInformation_Flag
                            if(text_fromFileARRAY[index] == '/'){
                                //Do nothing
                                //We don't want to concatentate the back-end divider '/'
                            }
                            else
                                temp_optionalNutritionalInformationFlag = temp_optionalNutritionalInformationFlag + text_fromFileARRAY[index];

                            break;
                        case 8:
                            //Calories
                            //optionalNutritionalInformation_Flag
                            if(text_fromFileARRAY[index] == '/'){
                                //Do nothing
                                //We don't want to concatentate the back-end divider '/'
                            }
                            else
                                temp_caloriesSTRING = temp_caloriesSTRING + text_fromFileARRAY[index];


                            break;
                        case 9:
                            //Total Fat
                            //optionalNutritionalInformation_Flag
                            if(text_fromFileARRAY[index] == '/'){
                                //Do nothing
                                //We don't want to concatentate the back-end divider '/'
                            }
                            else
                                temp_fatSTRING = temp_fatSTRING + text_fromFileARRAY[index];

                            break;
                        case 10:
                            //Total Carbs
                            //optionalNutritionalInformation_Flag
                            if(text_fromFileARRAY[index] == '/'){
                                //Do nothing
                                //We don't want to concatentate the back-end divider '/'
                            }
                            else
                                temp_carbsSTRING = temp_carbsSTRING + text_fromFileARRAY[index];

                            break;
                        case 11:
                            //Total Protein
                            //optionalNutritionalInformation_Flag
                            if(text_fromFileARRAY[index] == '/'){
                                //Do nothing
                                //We don't want to concatentate the back-end divider '/'
                            }
                            else
                                temp_proteinSTRING = temp_proteinSTRING + text_fromFileARRAY[index];
                            break;
                        default:
                            break;

                    } //End Switch


                    if(nextEntity_counter == 11 && (index == text_fromFile.length() - 1)) //index == text_fromFile.length() because we want to double check that we read the entire line so input is correct
                        nextEntity_counter = 1; //Reset the counter for the next line

                } //End for loop

                //Convert to appropriate data values
                if(temp_numServings != "")
                    temp_intNumServings = Integer.parseInt(temp_numServings);

                if(temp_caloriesSTRING != "")
                    temp_caloriesINT = Integer.parseInt(temp_caloriesSTRING);

                if(temp_fatSTRING != "")
                    temp_fatINT = Integer.parseInt(temp_fatSTRING);

                if(temp_carbsSTRING != "")
                    temp_carbsINT = Integer.parseInt(temp_carbsSTRING);

                if(temp_proteinSTRING != "")
                    temp_proteinINT = Integer.parseInt(temp_proteinSTRING);

                //What if the user does not check one of these boxes? Then by default it is false
                if(temp_veganFlagSTRING.equals("true"))
                    temp_veganFlagBOOLEAN = true;
                else
                    temp_veganFlagBOOLEAN = false;

                if(temp_vegetarianFlagSTRING.equals("true"))
                    temp_vegetarianFlagBOOLEAN = true;
                else
                    temp_vegetarianFlagBOOLEAN = false;

                if(temp_glutenFreeFLAG.equals("true"))
                    temp_glutenFreeFlagBOOLEAN = true;
                else
                    temp_glutenFreeFlagBOOLEAN = false;

                if(temp_optionalNutritionalInformationFlag.equals("true"))
                    temp_optionalNutritionalInformationBOOLEAN = true;
                else
                    temp_optionalNutritionalInformationBOOLEAN = false;


                //Create a temporary recipe object and store it into the HashMap
                Recipes temp_recipeObject = new Recipes(temp_nameRecipe, temp_nameIngredient, temp_intNumServings,
                        temp_veganFlagBOOLEAN, temp_vegetarianFlagBOOLEAN, temp_glutenFreeFlagBOOLEAN, temp_optionalNutritionalInformationBOOLEAN,
                        temp_caloriesINT, temp_fatINT, temp_carbsINT, temp_proteinINT);
                loadFile_hashMap.put(temp_recipeObject.getName_recipe(), temp_recipeObject);
                recipesList_arrayList.add(temp_recipeObject);

                //Show the user input from file
                stringBuilder_object.append(text_fromFile).append("\n");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally
        {
            if(fileInputStream_object != null) {
                try {
                    fileInputStream_object.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    } //End Method "Load File"

     */


} //End class "RecipesHomepage"
