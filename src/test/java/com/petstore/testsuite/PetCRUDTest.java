package com.petstore.testsuite;

import com.petstore.petstoreinfo.PetSteps;
import com.petstore.testbase.TestBase;
import com.petstore.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasValue;


@RunWith(SerenityRunner.class)
public class PetCRUDTest extends TestBase {

    static int id = TestUtils.getRandomNum();
    static String name = "Prince";
    static String status = "owned";

    static int petID;

    @Steps
    PetSteps petSteps;

    @Title("This will add a pet information")
    @Test
    public void test001() {

        HashMap<Object, Object> newCategory = new HashMap<>();
        newCategory.put("name", "Dog");
        newCategory.put("id", 2);

        List<Object> photoUrl = new ArrayList<>();
        photoUrl.add("https://www.google.com/url?sa=i&url=https%3A%2F%2Fcatastic.pet%2Fcat-breeds%2F11-fun-facts-about-tabby-cat%2F&psig=AOvVaw0tNqe9Q6z1VZOFGGguOVWP&ust=1672427102352000&source=images&cd=vfe&ved=0CA8QjRxqFwoTCOjW9szCn_wCFQAAAAAdAAAAABAJ");


        List<HashMap<Object, Object>> tagList = new ArrayList<>();
        HashMap<Object, Object> tagHash = new HashMap<>();
        tagHash.put("id", 1);
        tagHash.put("name", "Doggie");
        tagList.add(tagHash);


        ValidatableResponse response = petSteps.createDataForNewPet(id, newCategory, name, photoUrl, tagList, status);
        response.log().all().statusCode(200);
    }

    @Title("This will find pet information by petID")
    @Test
    public void test002() {

        HashMap<String, Object> petMap = petSteps.findPetById(id);
        Assert.assertThat(petMap, hasValue(id));
        petID = (int) petMap.get("id");

    }

    @Title("This will update pet information")
    @Test
    public void test003() {

        name = name + "_updated";

        HashMap<Object, Object> newCategory = new HashMap<>();
        newCategory.put("id", 2);
        newCategory.put("name", "Dog");

        List<Object> photoUrl = new ArrayList<>();
        photoUrl.add("https://www.google.com/url?sa=i&url=https%3A%2F%2Fcatastic.pet%2Fcat-breeds%2F11-fun-facts-about-tabby-cat%2F&psig=AOvVaw0tNqe9Q6z1VZOFGGguOVWP&ust=1672427102352000&source=images&cd=vfe&ved=0CA8QjRxqFwoTCOjW9szCn_wCFQAAAAAdAAAAABAJ");


        List<HashMap<Object, Object>> tagList = new ArrayList<>();
        HashMap<Object, Object> tagHash = new HashMap<>();
        tagHash.put("name", "Doggie");
        tagHash.put("id", 1);
        tagList.add(tagHash);


        ValidatableResponse response = petSteps.updateDataForPet(petID, newCategory, name, photoUrl, tagList, status);
        response.log().all().statusCode(200);

    }
    @Title("Deleting pet info and verifying pet info is deleted")
    @Test
    public void test004(){
        petSteps.deletePet(petID).statusCode(200);
        petSteps.findPetByIdAfterDeletion(petID).statusCode(404);

    }

}
