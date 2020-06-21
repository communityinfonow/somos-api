package info.cinow.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * PhotoTest
 */
@RunWith(SpringRunner.class)
public class PhotoTest {

    private Photo photo;

    @Before
    public void setup() {
        this.photo = new Photo();
        photo.setId(1L);
        photo.setFileName("testFile.jpeg");
    }

    @Test
    public void photoFilePathName_IsCorrect() {
        assertEquals("1_testFile.jpeg", this.photo.getFilePathName());
    }

    @Test
    public void photoCropFilePathName_IsCorrect() {
        assertEquals("CROP_1_testFile.jpeg", this.photo.getCroppedFilePathName());
    }

}