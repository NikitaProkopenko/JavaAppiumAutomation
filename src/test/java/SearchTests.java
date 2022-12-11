import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchTests extends CoreTestCase {
    @Test
    public void testSearchHasText() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
    }

    @Test
    public void testSearchFindSeveralAnswers() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.searchText("Java");
        int countOfSearchResults = SearchPageObject.findCountOfSearchResults();
        Assert.assertTrue("Search found less than 2 results", countOfSearchResults > 2);
        SearchPageObject.clickOnSearchCloseButton();
        SearchPageObject.checkSearchResultIsEmpty();
    }

    @Test
    public void testSearchResultsHaveKeyWord() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        String searchMessage = "Java";

        SearchPageObject.searchText(searchMessage);
        List<WebElement> elements = SearchPageObject.findAllTitlesInSearchResult();
        for (WebElement element : elements) {
            Assert.assertTrue("Result doesn't containt search message", element.getText().contains(searchMessage));
        }
    }

    @Test
    public void testSearchResultsByTitleAndDescription() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        String searchMessage = "Java";

        SearchPageObject.searchText(searchMessage);
        SearchPageObject.waitForElementByTitleAndDescription("Java", "Island of Indonesia, Southeast Asia");
        SearchPageObject.waitForElementByTitleAndDescription("JavaScript", "High-level programming language");
        SearchPageObject.waitForElementByTitleAndDescription("Java (programming language)", "Object-oriented programming language");
    }
}
