package net.sf.jabref;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import java.io.File;

/**
 * A base class for Testing in JabRef that comes along with some useful
 * functions.
 */
public class FileBasedTestCase {

    protected BibtexDatabase database;
    protected BibtexEntry entry;
    protected File root;

    private String oldPdfDirectory;
    private boolean oldUseRegExp;
    private boolean oldAutoLinkExcatKeyOnly;

    @Before
    public void setUp() throws Exception {
        Globals.prefs = JabRefPreferences.getInstance();
        oldUseRegExp = Globals.prefs.isUseRegExpSearch();
        oldAutoLinkExcatKeyOnly = JabRefPreferences.getInstance().isAutolinkExactKeyOnly();
        oldPdfDirectory = Globals.prefs.get("pdfDirectory");

        Globals.prefs.setUseRegExpSearch(false);
        JabRefPreferences.getInstance().setAutolinkExactKeyOnly(false);

        database = BibtexTestData.getBibtexDatabase();
        entry = database.getEntries().iterator().next();

        // Create file structure
        try {
            root = FileBasedTestHelper.createTempDir("UtilFindFileTest");

            Globals.prefs.put("pdfDirectory", root.getPath());

            File subDir1 = new File(root, "Organization Science");
            Assert.assertTrue(subDir1.mkdir());

            File pdf1 = new File(subDir1, "HipKro03 - Hello.pdf");
            Assert.assertTrue(pdf1.createNewFile());

            File pdf1a = new File(root, "HipKro03 - Hello.pdf");
            Assert.assertTrue(pdf1a.createNewFile());

            File subDir2 = new File(root, "pdfs");
            Assert.assertTrue(subDir2.mkdir());

            File subsubDir1 = new File(subDir2, "sub");
            Assert.assertTrue(subsubDir1.mkdir());

            File pdf2 = new File(subsubDir1, "HipKro03-sub.pdf");
            Assert.assertTrue(pdf2.createNewFile());

            File dir2002 = new File(root, "2002");
            Assert.assertTrue(dir2002.mkdir());

            File dir2003 = new File(root, "2003");
            Assert.assertTrue(dir2003.mkdir());

            File pdf3 = new File(dir2003, "Paper by HipKro03.pdf");
            Assert.assertTrue(pdf3.createNewFile());

            File dirTest = new File(root, "test");
            Assert.assertTrue(dirTest.mkdir());

            File pdf4 = new File(dirTest, "HipKro03.pdf");
            Assert.assertTrue(pdf4.createNewFile());

            File pdf5 = new File(dirTest, ".TEST");
            Assert.assertTrue(pdf5.createNewFile());

            File pdf6 = new File(dirTest, "TEST[");
            Assert.assertTrue(pdf6.createNewFile());

            File pdf7 = new File(dirTest, "TE.ST");
            Assert.assertTrue(pdf7.createNewFile());

            File foo = new File(dirTest, "foo.dat");
            Assert.assertTrue(foo.createNewFile());

            File graphicsDir = new File(root, "graphicsDir");
            Assert.assertTrue(graphicsDir.mkdir());

            File graphicsSubDir = new File(graphicsDir, "subDir");
            Assert.assertTrue(graphicsSubDir.mkdir());

            File jpg = new File(graphicsSubDir, "HipKro03test.jpg");
            Assert.assertTrue(jpg.createNewFile());

            File png = new File(graphicsSubDir, "HipKro03test.png");
            Assert.assertTrue(png.createNewFile());

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @After
    public void tearDown() {
        FileBasedTestHelper.deleteRecursive(root);
        JabRefPreferences.getInstance().setAutolinkExactKeyOnly(oldAutoLinkExcatKeyOnly);
        Globals.prefs.setUseRegExpSearch(oldUseRegExp);
        Globals.prefs.put("pdfDirectory", oldPdfDirectory);
        // TODO: This is not a great way to do this, sure ;-)
    }

}
