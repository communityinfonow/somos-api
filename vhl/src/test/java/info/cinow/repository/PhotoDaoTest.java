package info.cinow.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import info.cinow.model.Photo;

/**
 * PhotoDaoTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PhotoDaoTest {

    @Autowired
    PhotoDao dao;

    @Test
    public void getsModifiedInformation() {
        Photo photo = new Photo();
        photo.setFileName("name");

        photo = this.dao.save(photo);
        assertNotNull(photo.getAudit().getLastModified());
        assertEquals(LocalDateTime.now().getDayOfYear(), photo.getAudit().getLastModified().getDayOfYear());
    }

}