import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.ReadingListPageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ArticleTests extends CoreTestCase {
    @Test
    public void testArticleSaving() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ReadingListPageObject ReadingListPageObject = new ReadingListPageObject(driver);
        String searchMessage = "Java";
        String secondArticle = "JavaScript";

        //first article saving
        SearchPageObject.searchText(searchMessage);
        SearchPageObject.checkSearchResultIsNotEmpty();
        SearchPageObject.openArticleWithTitle(searchMessage);
        ArticlePageObject.articleTitleIsPresented(searchMessage);
        ArticlePageObject.openMoreOptionMenuAndAddToReadingList();
        ArticlePageObject.approveOnboarding();
        ArticlePageObject.createTestReadingList();
        ArticlePageObject.closeArticle();


        //second article saving
        SearchPageObject.searchText(searchMessage);
        SearchPageObject.checkSearchResultIsNotEmpty();
        SearchPageObject.openArticleWithTitle(secondArticle);
        ArticlePageObject.articleTitleIsPresented(secondArticle);
        ArticlePageObject.openMoreOptionMenuAndAddToReadingList();
        ArticlePageObject.chooseExistingReadingList();
        ArticlePageObject.closeArticle();

        //actions in reading list
        ReadingListPageObject.openReadingList();
        ReadingListPageObject.deleteFromReadingListBySwipe("JavaScript");
        ReadingListPageObject.checkArticleExistInReadingList("Java");
    }

    @Test
    public void testArticleHasTitle() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        String searchMessage = "Java";

        SearchPageObject.searchText(searchMessage);
        SearchPageObject.checkSearchResultIsNotEmpty();
        SearchPageObject.openArticleWithTitle(searchMessage);
        ArticlePageObject.articleTitleIsPresented(searchMessage);
    }
}
