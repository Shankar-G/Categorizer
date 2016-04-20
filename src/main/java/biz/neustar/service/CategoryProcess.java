package biz.neustar.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public interface CategoryProcess {

    /**
     * Holds the List of allowed categories
     */
    List<String> ALLOWED_CATEGORIES = Arrays.asList("PERSON", "PLACE", "ANIMAL", "COMPUTER", "OTHER");

    /**
     * Reads the file and add its contents to a set.
     * 
     * @param fileName
     *  used to read file contents
     * @return data read from the file in a set of string format.
     * @throws IOException
     */
    Set<String> readFileContents(String fileName) throws IOException;

    /**
     * Process the categoryAndSubCategorySet read from the file.
     * 
     * @param categoryAndSubCategorySet
     * holds the file contents 
     */
    void processValidCategories(Set<String> categoryAndSubCategorySet);
}
