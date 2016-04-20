package biz.neustar.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import biz.neustar.service.CategoryProcess;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CategoryProcessImplTest {

    private CategoryProcess categoryProcess;

    @Before
    public void setUp() {
        categoryProcess = new CategoryProcessImpl();
    }

    @Test
    public void testReadFileContentsWithValidData() throws IOException {
        List<String> listOfFileNames = Arrays.asList("categoryWithAllcombination.txt", "file_not_exist", null);
        for (String fileName : listOfFileNames) {
            Set<String> allowedCategoriesSet = categoryProcess.readFileContents(fileName);
            if (fileName != null) {
                if ("categoryWithAllcombination.txt".equals(fileName)) {
                    assertNotNull(allowedCategoriesSet);
                    assertTrue(allowedCategoriesSet.size() > 0);
                } else {
                    assertNotNull(allowedCategoriesSet);
                    assertTrue(allowedCategoriesSet.isEmpty());
                }
            } else {
                // when file name is null
                assertNull(allowedCategoriesSet);
            }
        }
    }

    @Test
    public void testDisplayCategoryAndSubcategoriesCount() {
        int index = 0;
        final List<String> ALLOWED_CATEGORIES = Arrays.asList("PERSON", "PLACE", "ANIMAL", "COMPUTER", "OTHER");
        final int[] SUB_CATEGORY_COUNT = { 2, 2, 2, 1, 1 };
        Set<String> randomlyOrderedSet = getFakeCatAndSubCatInMixedOrder();
        categoryProcess.processValidCategories(randomlyOrderedSet);
        categoryProcess.displayCategoryAndSubcategoriesCount(randomlyOrderedSet);
        Map<String, List<String>> categoryAndSubCatMap = ((CategoryProcessImpl) categoryProcess)
                .getCategoryAndSubCategoryMap();
        for (Map.Entry<String, List<String>> catAndSubCat : categoryAndSubCatMap.entrySet()) {
            assertEquals(ALLOWED_CATEGORIES.get(index), catAndSubCat.getKey());
            assertEquals(SUB_CATEGORY_COUNT[index], catAndSubCat.getValue().size());
            index++;
        }
    }

    @Test
    public void testProcessValidCategories() {
        Set<String> fakeCatAndSubCatSet = getFakeCategoryAndSubCategorySet();
        int totalSizeBeforeProcess = fakeCatAndSubCatSet.size();
        categoryProcess.processValidCategories(fakeCatAndSubCatSet);
        assertNotEquals(totalSizeBeforeProcess, fakeCatAndSubCatSet.size());
        assertFalse(fakeCatAndSubCatSet.contains("ANIMAL"));
        assertFalse(fakeCatAndSubCatSet.contains("            "));
        assertFalse(fakeCatAndSubCatSet.contains("    Tree"));
        assertFalse(fakeCatAndSubCatSet.contains("PERSON  Bob Jones"));
        assertFalse(fakeCatAndSubCatSet.contains("FOOD Steak"));
    }

    private Set<String> getFakeCategoryAndSubCategorySet() {
        Set<String> set = new LinkedHashSet<>();
        set.add("PERSON Bob Jones");
        set.add("PLACE Washington");
        set.add("PERSON Mary");
        set.add("COMPUTER Mac");
        set.add("PERSON Bob Jones");
        set.add("ANIMAL Dog");
        set.add("PLACE Texas");
        set.add("FOOD Steak");
        set.add("OTHER Tree");
        set.add("ANIMAL Cat");
        set.add("PERSON  Bob Jones");
        set.add("ANIMAL");
        set.add("            ");
        set.add("    Tree");
        return set;
    }

    private Set<String> getFakeCatAndSubCatInMixedOrder() {
        Set<String> set = new LinkedHashSet<>();
        set.add("FOOD Steak");
        set.add("COMPUTER Mac");
        set.add("ANIMAL Dog");
        set.add("PLACE Texas");
        set.add("PLACE Washington");
        set.add("    Tree");
        set.add("            ");
        set.add("PERSON Bob Jones");
        set.add("OTHER Tree");
        set.add("PERSON Mary");
        set.add("ANIMAL Cat");
        set.add("PERSON  Bob Jones");
        set.add("ANIMAL");
        return set;
    }

    @After
    public void tearDown() {
        categoryProcess = null;
    }

}
