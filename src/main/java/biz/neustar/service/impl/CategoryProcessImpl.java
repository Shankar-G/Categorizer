package biz.neustar.service.impl;

import biz.neustar.service.CategoryProcess;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * This class reads the file and process the file contents based on categories and displays individual category with
 * their sub-category count.
 *
 * @author Shankar
 * @version 1.0 Build Apr 18, 2016.
 */
public class CategoryProcessImpl implements CategoryProcess {

    /**
     * The categoryAndSubCategoryMap hold the value of individual category and there associated sub-categories.
     */
    private Map<String, List<String>> categoryAndSubCategoryMap = null;

    /**
     * Initializes the map with allowed category.
     */
    public CategoryProcessImpl() {
        initalizeMapWithValidCategories();
    }

    /**
     * Reads the file and add its contents to a set.
     * 
     * @param fileName
     *            used to read file contents
     * 
     * @throws IOException
     *             If the input file name does not exist in the class-path {@code fileName} array.
     */
    @Override
    public Set<String> readFileContents(String fileName) throws IOException {
        String contentFromFile = null;
        Set<String> categoryAndSubCatSet = null;
        if (StringUtils.isNotEmpty(fileName)) {
            categoryAndSubCatSet = new LinkedHashSet<>();
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
            if (inputStream != null) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    while ((contentFromFile = reader.readLine()) != null) {
                        categoryAndSubCatSet.add(contentFromFile);
                    }
                    return categoryAndSubCatSet;
                }
            }
        }
        return categoryAndSubCatSet;
    }

    /**
     * iterates through the set containing contents read from the file, and removes Duplicate or Unauthorized categories
     * from set and also adds the individual category and their associated sub-categories to a map.
     * 
     * @param categoryAndSubCategorySet
     *            holding the file contents
     */
    @Override
    public void processValidCategories(Set<String> categoryAndSubCategorySet) {
        if (CollectionUtils.isNotEmpty(categoryAndSubCategorySet)) {
            for (Iterator<String> iterator = categoryAndSubCategorySet.iterator(); iterator.hasNext();) {
                String categoryAndSubCat = (String) iterator.next().trim();
                String[] array = categoryAndSubCat.split(" ");
                String category = array[0].trim();
                String subCategory = categoryAndSubCat.substring(category.length()).trim();
                if (!populateAllowedCategoryAndSubCategories(category, subCategory)) {
                    iterator.remove();
                }
            }
        }
    }

    /**
     * iterates through the set and prints the unique contents and also displays the category along with its
     * sub-categories count.
     * 
     * @param categoryAndSubCategorySet
     *            having unique contents
     */
    @Override
    public void displayCategoryAndSubcategoriesCount(Set<String> categoryAndSubCategorySet) {
        displayCategoriesWithCount();
        if (CollectionUtils.isNotEmpty(categoryAndSubCategorySet)) {
            for (String entry : categoryAndSubCategorySet) {
                System.out.print("\n" + entry);
            }
        }
    }

    /**
     * checks whether the category is allowed, if allowed adds the category and its sub-category to the map, else
     * removes the content from the set.
     * 
     */
    private boolean populateAllowedCategoryAndSubCategories(String category, String subCategory) {
        if (CategoryProcess.ALLOWED_CATEGORIES.contains(category) && StringUtils.isNotEmpty(subCategory)) {
            if (categoryAndSubCategoryMap.containsKey(category)
                    && categoryAndSubCategoryMap.get(category).contains(subCategory)) {
                return false;
            } else {
                categoryAndSubCategoryMap.get(category).add(subCategory);
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * 
     * Initializes the map with Allowed category.
     * 
     */
    private void initalizeMapWithValidCategories() {
        categoryAndSubCategoryMap = new LinkedHashMap<>();
        for (String category : CategoryProcess.ALLOWED_CATEGORIES) {
            categoryAndSubCategoryMap.put(category, new ArrayList<String>());
        }
    }

    /**
     * iterates the map and prints the category and its count.
     * 
     */
    private void displayCategoriesWithCount() {
        System.out.println("CATEGORY     COUNT");
        for (Map.Entry<String, List<String>> entry : categoryAndSubCategoryMap.entrySet()) {
            System.out.println(entry.getKey() + "     " + entry.getValue().size());
        }
    }
    
    /**
     * Returns the map holding the category and sub-category. 
     * 
     */
    public Map<String, List<String>> getCategoryAndSubCategoryMap() {
        return categoryAndSubCategoryMap;
    }

}
