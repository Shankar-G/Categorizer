package biz.neustar.util;

import biz.neustar.service.CategoryProcess;
import biz.neustar.service.impl.CategoryProcessImpl;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

public class CategoryProcessLauncher {

    public static void main(String[] args) {
        try {
            if (args.length > 0 && StringUtils.isNotEmpty(args[0])) {
                String fileName = args[0].trim();
                CategoryProcess categoryProcess = new CategoryProcessImpl();
                Set<String> categoryAndSubCategorySet = categoryProcess.readFileContents(fileName);
                categoryProcess.processValidCategories(categoryAndSubCategorySet);
                categoryProcess.displayCategoryAndSubcategoriesCount(categoryAndSubCategorySet);
            } else {
                System.err.println("Usage: java CategoryProcessLauncher <FileName>");
                System.err.println("Pass valid file name as Argument!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
