package biz.neustar.util;

import biz.neustar.service.CategoryProcess;
import biz.neustar.service.impl.CategoryProcessImpl;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public class CategoryProcessLauncher {

    public static void main(String[] args) {
        try {
            if (args.length > 0 && StringUtils.isNotEmpty(args[0])) {
                String fileName = args[0].trim();
                CategoryProcess categoryProcess = new CategoryProcessImpl();
                Set<String> categoryAndSubCategorySet = categoryProcess.readFileContents(fileName);
                categoryProcess.processValidCategories(categoryAndSubCategorySet);
                displayCategoriesWithCount(((CategoryProcessImpl) categoryProcess).getCategoryAndSubCategoryMap());
                displayCategoryAndSubcategoriesCount(categoryAndSubCategorySet);
            } else {
                System.err.println("Usage: java CategoryProcessLauncher <FileName>");
                System.err.println("Pass valid file name as Argument!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * iterates through the set and prints the unique contents and also displays the category along with its
     * sub-categories count.
     * 
     * @param categoryAndSubCategorySet
     *            having unique contents
     */
    private static void displayCategoryAndSubcategoriesCount(Set<String> categoryAndSubCategorySet) {
        if (CollectionUtils.isNotEmpty(categoryAndSubCategorySet)) {
            for (String entry : categoryAndSubCategorySet) {
                System.out.print("\n" + entry);
            }
        }
    }

    /**
     * iterates the map and prints the category and its count.
     * 
     */
    private static void displayCategoriesWithCount(Map<String, List<String>> categoryAndSubCategoryMap) {
        System.out.println("CATEGORY     COUNT");
        for (Map.Entry<String, List<String>> entry : categoryAndSubCategoryMap.entrySet()) {
            System.out.println(entry.getKey() + "     " + entry.getValue().size());
        }
    }
}
